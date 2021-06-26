package com.example.notesv3.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesv3.R;
import com.example.notesv3.domain.Notes;
import com.example.notesv3.domain.NotesRepository;
import com.example.notesv3.domain.NotesRepositoryImpl;

import java.util.List;

public class NotesListFragment extends Fragment {

    // интерфейс для открытия новой активити для отображения самой заметки
    public interface OnNotesClicked{
        void onNotesClicked(Notes notes);
    }

    private NotesRepository notesRepository;

    // добавляем экземпляр класса, реализующий интерфейс OnNotesClicked
    private OnNotesClicked onNotesClicked;

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

        notesRepository = new NotesRepositoryImpl();
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

        // отображаем линейно в вертикальной резметке
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        notesList.setLayoutManager(linearLayoutManager);

        //создаем разделитель между карточками
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_separatore));
        notesList.addItemDecoration(dividerItemDecoration);
        // достаем список данных по заметкам из репозитория
        List<Notes> notes = notesRepository.getNotes();

        NotesAdapter notesAdapter = new NotesAdapter();
        notesAdapter.setData(notes);
        notesAdapter.setListener(new NotesAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClickedListener(@NonNull Notes note) {
                if (onNotesClicked != null){
                        onNotesClicked.onNotesClicked(note);
                }
            }
        });
        notesList.setAdapter(notesAdapter);
    }
}
