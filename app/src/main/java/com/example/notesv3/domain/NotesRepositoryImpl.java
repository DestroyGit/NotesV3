package com.example.notesv3.domain;

// будет возвращать список всех заметок

import com.example.notesv3.R;

import java.util.ArrayList;
import java.util.List;

public class NotesRepositoryImpl implements NotesRepository{

    @Override
    public List<Notes> getNotes(){
        ArrayList<Notes> result = new ArrayList<>();
        result.add(new Notes(R.string.name_1, R.string.date_1, R.string.note_1));
        result.add(new Notes(R.string.name_2, R.string.date_2, R.string.note_2));
        result.add(new Notes(R.string.name_3, R.string.date_3, R.string.note_3));
        result.add(new Notes(R.string.name_4, R.string.date_4, R.string.note_4));
        result.add(new Notes(R.string.name_5, R.string.date_5, R.string.note_5));
        result.add(new Notes(R.string.name_6, R.string.date_6, R.string.note_6));
        result.add(new Notes(R.string.name_7, R.string.date_7, R.string.note_7));
        result.add(new Notes(R.string.name_8, R.string.date_8, R.string.note_8));

        return result;
    }
}
