package com.example.notesv3.ui.update;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notesv3.R;
import com.example.notesv3.domain.Notes;

import java.util.Calendar;

public class UpdateNotesFragment extends Fragment {

    public static final String TAG = "UpdateNotesFragment";

    private static final String ARG_NOTE = "ARG_NOTE";

    public static UpdateNotesFragment newInstance(Notes notes){
        UpdateNotesFragment fragment = new UpdateNotesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, notes);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Notes notes = getArguments().getParcelable(ARG_NOTE);

        EditText name = view.findViewById(R.id.name);
        name.setText(notes.getName());

        DatePicker datePicker = view.findViewById(R.id.picker);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(notes.getDate());

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

            }
        });
    }
}
