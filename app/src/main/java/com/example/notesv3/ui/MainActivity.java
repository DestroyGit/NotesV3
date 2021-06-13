package com.example.notesv3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.notesv3.R;
import com.example.notesv3.domain.Notes;
import com.example.notesv3.ui.details.NotesDetailsActivity;
import com.example.notesv3.ui.details.NotesDetailsFragment;
import com.example.notesv3.ui.list.NotesListFragment;

public class MainActivity extends AppCompatActivity implements NotesListFragment.OnNotesClicked {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // сюда приходит управление, когда пользователь нажмет на элемент списка
    @Override
    public void onNotesClicked(Notes notes) {
        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);
        if(isLandscape){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_details_fragment,NotesDetailsFragment.newInstance(notes))
                    .commit();
        } else {
            Intent intent = new Intent(this, NotesDetailsActivity.class);
            intent.putExtra(NotesDetailsActivity.ARG_NOTES,notes);
            startActivity(intent);
        }
    }
}