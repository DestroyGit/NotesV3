package com.example.notesv3.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notesv3.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

public class AuthFragment extends Fragment {

    private static final int AUTH_REQUEST_ID = 1;

    public static final String TAG = "AuthFragment";
    public static final String AUTH_RESULT = "AUTH_RESULT";

    public static AuthFragment newInstance(){
        return new AuthFragment();
    }

    public GoogleSignInClient googleSignInClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // получение экземпляра клиента для авторизации пользователя
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() //будем получать емэйл
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), options);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // при нажатии на кнопку откроется активити для гугловых моментов
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, AUTH_REQUEST_ID);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTH_REQUEST_ID){
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = accountTask.getResult();

                getParentFragmentManager().setFragmentResult(AUTH_RESULT, new Bundle());
            } catch (Exception exception){
                Toast.makeText(requireContext(), "Auth error", Toast.LENGTH_LONG).show();
            }

        }
    }
}
