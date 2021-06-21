package com.example.notesv3.ui.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.notesv3.R;
import com.example.notesv3.domain.Notes;

/**
Эта активити не используется
 **/

public class NotesDetailsActivity extends AppCompatActivity {

    public static final String ARG_NOTES = "ARG_NOTES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        if (savedInstanceState == null){ // для восстановления состояния
            // при первом создании активити..

            Notes notes = getIntent().getParcelableExtra(ARG_NOTES);

            getSupportFragmentManager() //так как мы в активити
                    .beginTransaction()
                    .replace(R.id.container, NotesDetailsFragment.newInstance(notes)) // вместо R.id.container помещаем необходимый фрагмент
                    .commit();
        }
    }
}