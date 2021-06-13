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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

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
        LinearLayout notesList = view.findViewById(R.id.notes_list_container);

        // достаем список данных по заметкам из репозитория
        List<Notes> notes = notesRepository.getNotes();

        int count = 1; // счетчик для четных и нечетных строк для закрашивания в серый четные заметки

        // проходимся по всем заметкам
        for(Notes note : notes){

            // даем возможность надувать контейнер (фрагмент) вьюхами.
            // В скобках: вьюха, макет фрагмента, перезапись - если true, то в одном и том же месте будет
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_notes, notesList,false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // если не нулл, то "вот по такому городу произвели нажатие"
                    if (onNotesClicked != null){
                        onNotesClicked.onNotesClicked(note);
                    }
                }
            });

            // находим элементы макета
            TextView notesName = itemView.findViewById(R.id.notes_name);
            TextView notesDate = itemView.findViewById(R.id.notes_date);

            // добавляем в найденные элементы макета текст для вывода на экран - то, чем заполняем TextView
            notesName.setText(note.getName());
            notesDate.setText(note.getDate());

            // каждая четная строка подкрашивается в серый цвет
            if (count%2 == 0){
                itemView.setBackgroundResource(R.color.light_grey);
            }
            count++;

            // добавляем заметку
            notesList.addView(itemView);
        }


    }
}
