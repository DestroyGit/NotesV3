package com.example.notesv3.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesv3.R;
import com.example.notesv3.domain.Notes;
import com.example.notesv3.domain.NotesRepository;
import com.example.notesv3.domain.NotesRepositoryImpl;
import com.example.notesv3.ui.MainRouter;
import com.example.notesv3.ui.RouterHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;
import java.util.List;

public class NotesListFragment extends Fragment {

    // интерфейс для открытия новой активити для отображения самой заметки
    public interface OnNotesClicked{
        void onNotesClicked(Notes notes);
    }

    private NotesRepository notesRepository;
    private NotesAdapter notesAdapter;

    // добавляем экземпляр класса, реализующий интерфейс OnNotesClicked
    private OnNotesClicked onNotesClicked;

    private int longClickedIndex;
    private Notes longClickedNote;

    // когда к активити подцепились
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // если активити данный интерфейс реализует, то создаем на нее ссылку
        if (context instanceof OnNotesClicked){
            onNotesClicked = (OnNotesClicked) context;
        }
    }

    // когда от активити отцепились
    @Override
    public void onDetach() {
        super.onDetach();
        // зануляем ссылку, чтобы не хранить ссылку на активити, к которой не прицеплены
        onNotesClicked = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesAdapter = new NotesAdapter(this);
        notesRepository = new NotesRepositoryImpl();

        notesAdapter.setListener(new NotesAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClickedListener(@NonNull Notes note) {
                if (onNotesClicked != null){
                    onNotesClicked.onNotesClicked(note);
                }
            }
        });

        notesAdapter.setLongClickedListener(new NotesAdapter.OnNoteLongClickedListener() {
            @Override
            public void onNoteLongClickedListener(@NonNull Notes note, int index) {
                longClickedIndex = index;
                longClickedNote = note;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // находим макет фрагмента, куда будем добавлять вьюхи с заметками
        RecyclerView notesList = view.findViewById(R.id.notes_list_container);

        // ниже 4 строки про анимации на добавление и удаление заметок
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1500L); // добавляем заметку с анимацией 5 секунд
        animator.setRemoveDuration(1500L); // удаляем заметку с анимацией 5 секунд
        notesList.setItemAnimator(animator);


        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_add){
                    Notes addedNote = notesRepository.add("9","Новая заметка", new Date(), "Новая запись в новой заметке");
                    int index = notesAdapter.add(addedNote);
                    notesAdapter.notifyItemInserted(index);
                    notesList.scrollToPosition(index);
                    return true;
                }
                return true;
            }
        });

        // отображаем линейно в вертикальной резметке
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        notesList.setLayoutManager(linearLayoutManager);

        //создаем разделитель между карточками
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_separatore));
        notesList.addItemDecoration(dividerItemDecoration);
        // достаем список данных по заметкам из репозитория
        List<Notes> notes = notesRepository.getNotes();

        notesAdapter.setData(notes);

        notesList.setAdapter(notesAdapter);
    }

    // метод для создания контекстного меню
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // у активити есть инфлейтер, потому можно воспользоваться им
        requireActivity().getMenuInflater().inflate(R.menu.menu_notes_context, menu);
    }

    // чтобы можно было тыкать на менюшки
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit){

            if (requireActivity() instanceof RouterHolder){
                MainRouter router = ((RouterHolder) requireActivity()).getMainRouter();
                router.showEditNote(longClickedNote);
            }
            return true;
        }

        if (item.getItemId() == R.id.action_delete){
            notesRepository.remove(longClickedNote);
            notesAdapter.remove(longClickedNote); // подобное удаление для адаптера, чтобы и там сохранить
            notesAdapter.notifyItemRemoved(longClickedIndex); // говорим адаптеру, что заметка была удалена по такому индексу
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
