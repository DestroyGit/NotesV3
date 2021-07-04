package com.example.notesv3.domain;

import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NotesFirestoreRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesFirestoreRepository();

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();//ссылка на базу данных

    private final static String NOTES = "notes"; // название коллекции в firebase

    private final static String DATE = "date";
    private final static String NAME = "name";
    private final static String NOTE = "note";

    @Override
    public void getNotes(Callback<List<Notes>> callback) {
        firebaseFirestore.collection(NOTES)
                .orderBy(DATE, Query.Direction.DESCENDING) // сортировка заметок по убыванию по дате
                .get() // получаем все данные из коллекции из БД
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ArrayList<Notes> result = new ArrayList<>(); // сюда будем записывать заметки - переводим в аш вид заметок
                            for (QueryDocumentSnapshot document : task.getResult()){ //получить все документы из БД
                                String name = (String) document.get(NAME);
                                String note = (String) document.get(NOTE);
                                Date date = ((Timestamp) document.get(DATE)).toDate();

                                result.add(new Notes(document.getId(), name, date, note));
                            }
                            callback.onSuccess(result);
                        } else{
                            task.getException();
                        }
                    }
                });
    }

    @Override
    public void add(String id, String name, Date date, String note, Callback<Notes> callback) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(NAME, name);
        data.put(DATE,date);
        data.put(NOTE,note);
        firebaseFirestore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            Notes notes = new Notes(task.getResult().getId(), name, date, note);

                            callback.onSuccess(notes);
                        }
                    }
                });
    }

    @Override
    public void remove(Notes notes, Callback<Object> callback) {
        firebaseFirestore.collection(NOTES)
                .document(notes.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            callback.onSuccess(notes);
                        }
                    }
                });
    }

    @Override
    public Notes update(@Nullable Notes notes, @Nullable String name, @Nullable Date date, @Nullable String note) {
        return null;
    }
}
