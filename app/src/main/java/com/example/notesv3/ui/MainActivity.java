package com.example.notesv3.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.notesv3.R;
import com.example.notesv3.domain.Notes;
import com.example.notesv3.ui.details.NotesDetailsActivity;
import com.example.notesv3.ui.details.NotesDetailsFragment;
import com.example.notesv3.ui.list.NotesListFragment;
import com.example.notesv3.ui.toolbar.FavoritesFragment;
import com.example.notesv3.ui.toolbar.HelpFragment;
import com.example.notesv3.ui.toolbar.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NotesListFragment.OnNotesClicked, RouterHolder {

    private MainRouter router;

    @Override
    public MainRouter getMainRouter() {
        return router;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // для создания меню

        createStartFragment(); // добавляем фрагмент со списком заметок NotesListFragment
        createNavDrawer(toolbar); // создание drawer
    }

    private void createStartFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_list_fragment, new NotesListFragment(), "NotesListFragment")
                .commit();
    }

    private void createNavDrawer(Toolbar toolbar){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( // подкрепляем необходимые элементы
                this,
                drawerLayout,
                toolbar,
                R.string.app_name, // заглушка
                R.string.app_name // заглушка
        );
        drawerLayout.addDrawerListener(toggle); // вешаем лиснер
        toggle.syncState(); // для сцепления шторки к тулбару

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.profile){
                    Toast.makeText(MainActivity.this, "Your profile", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START); // свернуть дравер
                    return true;
                }

                if (item.getItemId() == R.id.change_user){
                    Toast.makeText(MainActivity.this, "Change user", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }

                if (item.getItemId() == R.id.help){
                    Fragment fragment = new HelpFragment();
                    pushMenuButtons(fragment, "HelpFragment");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    // метод для показа кнопок тулбара
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appbar, menu); // надуваем кнопки в тулбаре;

        // Ниже строки для поисковой строки. Чтобы при отправке строки из поиска приложение считывало строку - как раз метод onQueryTextSubmit
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    // метод для нажатий кнопок в меню тулбара
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.settings){
            Fragment fragment = new SettingsFragment();
            pushMenuButtons(fragment, "SettingsFragment");
            return true;
        }
        if (itemId == R.id.favorites){
            Fragment fragment = new FavoritesFragment();
            pushMenuButtons(fragment, "FavoritesFragment");
            return true;
        }
        if (itemId == R.id.help){
            Fragment fragment = new HelpFragment();
            pushMenuButtons(fragment, "HelpFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // метод для отображения фрагментов после нажатия кнопок меню. С addToBackStack
    private void pushMenuButtons(Fragment fragment, String tag){
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.notes_list_fragment, fragment, tag)
                .commit();
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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_list_fragment,NotesDetailsFragment.newInstance(notes))
                    .addToBackStack(null)
                    .commit();
        }
    }
}