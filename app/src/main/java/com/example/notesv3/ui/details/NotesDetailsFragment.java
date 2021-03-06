package com.example.notesv3.ui.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesv3.R;
import com.example.notesv3.domain.Notes;

public class NotesDetailsFragment extends Fragment {

    public static final String ARG_NOTES = "ARG_NOTES";

    // передача фрагменту аргумента
    public static NotesDetailsFragment newInstance(Notes notes) {
        NotesDetailsFragment fragment = new NotesDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTES, notes);
        fragment.setArguments(bundle);
        return fragment;
    }

    public NotesDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // делаем намерение на добавочные кнопки в меню, затем метод onCreateOptionsMenu и
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_notes_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView notesName = view.findViewById(R.id.notes_name);
        TextView notesNote = view.findViewById(R.id.notes_note);

        Notes notes = getArguments().getParcelable(ARG_NOTES);

        notesName.setText(notes.getName());
        notesNote.setText(notes.getNote());
    }

    // надувание кнопок в тулбаре
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details_fragment, menu);
    }

    // формируем возможность нажатия на кнопки этого фрагмента в тулбаре
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clear_text) {
            Toast.makeText(requireContext(), "Clear all text", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}