package com.example.notesv3.ui.toolbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.notesv3.R;

public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // делаем попап меню для текста во фрагменте
        view.findViewById(R.id.settings_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(requireContext(), view);

                //надуваем попап меню
                requireActivity().getMenuInflater().inflate(R.menu.menu_popup_settings, menu.getMenu());

                //делаем кликабельными кнопки в попап меню
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.reset_settings){
                            Toast.makeText(requireContext(), "Reset settings", Toast.LENGTH_LONG).show();
                            return true;
                        }
                        return false;
                    }
                });

                menu.show();
            }
        });
    }
}