package com.example.notesv3.domain;

import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public interface NotesRepository {

    List<Notes> getNotes();

    Notes add(String id, String name, Date date, String note);

    void remove(Notes notes);

    // метод для показывания изменения заметки
    Notes update (@Nullable Notes notes, @Nullable String name, @Nullable Date date, @Nullable String note);
}
