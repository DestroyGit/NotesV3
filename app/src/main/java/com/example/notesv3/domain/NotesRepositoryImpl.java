package com.example.notesv3.domain;

// будет возвращать список всех заметок

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.example.notesv3.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepositoryImpl implements NotesRepository{

    public static final NotesRepository INSTANCE = new NotesRepositoryImpl();

    private final ArrayList<Notes> result = new ArrayList<>();

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper()); // заюзать в главном потоке

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
    public void getNotes(Callback<List<Notes>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
            }
        });
    }

    @Override
    public void add(String id, String name, Date date, String note, Callback<Notes> callback) {
        Notes notes = new Notes(UUID.randomUUID().toString(), name, new Date(), note);
        result.add(notes);
        callback.onSuccess(notes);
    }

    @Override
    public void remove(Notes notes, Callback<Object> callback) {
        result.remove(notes);
        callback.onSuccess(notes);
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
