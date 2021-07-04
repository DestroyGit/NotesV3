package com.example.notesv3.ui;

import androidx.fragment.app.FragmentManager;

import com.example.notesv3.domain.Notes;

import com.example.notesv3.R;
import com.example.notesv3.ui.auth.AuthFragment;
import com.example.notesv3.ui.list.NotesListFragment;
import com.example.notesv3.ui.update.UpdateNotesFragment;

public class MainRouter {

    private final FragmentManager fragmentManager;


    public MainRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showEditNote (Notes notes){
        fragmentManager
                .beginTransaction()
                .replace(R.id.notes_list_fragment, UpdateNotesFragment.newInstance(notes), UpdateNotesFragment.TAG)
                .addToBackStack(UpdateNotesFragment.TAG)
                .commit();
    }

    public void showAuth (){
        fragmentManager
                .beginTransaction()
                .replace(R.id.notes_list_fragment, AuthFragment.newInstance(), AuthFragment.TAG)
                .commit();
    }

    public void showNotes (){
        fragmentManager
                .beginTransaction()
                .replace(R.id.notes_list_fragment, NotesListFragment.newInstance(), NotesListFragment.TAG)
                .commit();
    }
}
