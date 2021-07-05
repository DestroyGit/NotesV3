package com.example.notesv3.domain;

import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Notes>> callback);

    void add(String id, String name, Date date, String note, Callback<Notes> callback);

    void remove(Notes notes, Callback<Object> callback);

    // метод для показывания изменения заметки
    Notes update (@Nullable Notes notes, @Nullable String name, @Nullable Date date, @Nullable String note);
}
