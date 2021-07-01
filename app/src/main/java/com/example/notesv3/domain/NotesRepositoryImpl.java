package com.example.notesv3.domain;

// будет возвращать список всех заметок

import androidx.annotation.Nullable;

import com.example.notesv3.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotesRepositoryImpl implements NotesRepository{

    private final ArrayList<Notes> result = new ArrayList<>();

    public NotesRepositoryImpl(){
        result.add(new Notes("1", "Заметка №1", new Date(), "Первая запись"));
        result.add(new Notes("2", "Заметка №2", new Date(), "Вторая запись"));
        result.add(new Notes("3", "Заметка №3", new Date(), "Третья запись"));
        result.add(new Notes("4", "Заметка №4", new Date(), "Четвертая запись"));
        result.add(new Notes("5", "Заметка №5", new Date(), "Пятая запись"));
        result.add(new Notes("6", "Заметка №6", new Date(), "Шестая запись"));
        result.add(new Notes("7", "Заметка №7", new Date(), "Седьмая запись"));
        result.add(new Notes("8", "Заметка №8", new Date(), "Восьмая запись"));
    }

    @Override
    public List<Notes> getNotes(){
        return result;
    }

    @Override
    public Notes add(String id, String name, Date date, String note) {
        Notes notes = new Notes(UUID.randomUUID().toString(), name, new Date(), note);
        result.add(notes);
        return notes;
    }

    @Override
    public void remove(Notes notes) {
        result.remove(notes);
    }

    @Override
    public Notes update(@Nullable Notes notes, @Nullable String name, @Nullable Date date, @Nullable String note) {

        for (int i = 0; i < result.size(); i++) { // проходимся по всем заметкам, чтоб найти нужный элемент для редактирования
            Notes item = result.get(i);
            if (item.getId().equals(notes.getId())){
                String nameToSet = item.getName();
                Date dateToSet = item.getDate();
                String noteToSet = item.getNote();

                if (name != null){ // то обновляем
                    nameToSet = name;
                }

                if (date != null){ // то обновляем
                    dateToSet = date;
                }

                if (note != null){ // то обновляем
                    noteToSet = note;
                }

                Notes newNote = new Notes(notes.getId(), nameToSet, dateToSet, noteToSet);
                result.remove(i);
                result.add(i, newNote);
            }
        }
        return notes;
    }
}
