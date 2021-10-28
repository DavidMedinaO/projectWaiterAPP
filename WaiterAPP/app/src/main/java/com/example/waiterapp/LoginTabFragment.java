package com.example.waiterapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    EditText email, passw;
    Button button;
    TextView registra, textView3;
    float v = 0;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        conectar(root);

        email.setTranslationX(800);
        passw.setTranslationX(800);
        passw.setTranslationX(800);
        registra.setTranslationX(800);
        textView3.setTranslationX(800);

        email.setAlpha(v);
        passw.setAlpha(v);
        button.setAlpha(v);
        registra.setAlpha(v);
        textView3.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        passw.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        button.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        registra.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        textView3.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Cliente.class);
                startActivity(intent);
            }
        });

        //conexion firebase
        mAuth = FirebaseAuth.getInstance();

        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

        //Metodo para boton login al dar click
        // va hacia activity Cliente o Admin
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });


        return root;
}

    //Metodo para abrir el register
    private void openRegisterActivity() {
     // Intent intent = new Intent(getContext(), SigupTabFragment.class);
       // startActivity(intent);
    }

    //Metodo para abrir el Cliente
    private void openClienteActivity() {
        Intent intent = new Intent(getContext(), Cliente.class);
        startActivity(intent);
    }

    //Metodo para abrir el Admin
    private void openAdminActivity() {
        Intent intent = new Intent(getContext(), Admin.class);
        startActivity(intent);
    }

    private void conectar(ViewGroup root) {
        email = root.findViewById(R.id.editTextTextPersonName);
        passw = root.findViewById(R.id.editTextTextPassword);
        button = root.findViewById(R.id.button);
        registra  =root.findViewById(R.id.register);
        textView3 = root.findViewById(R.id.textView3);
    }


    //Firebase
    //Login
    public void userLogin(){
        String mail = email.getText().toString();
        String password = passw.getText().toString();

        if(TextUtils.isEmpty(mail)){
            Toast.makeText( getContext(), "Ingrese un Email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText( getContext(), "Ingrese una Contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(!email.getText().toString().contains("@")){
            Toast.makeText( getContext(), "Le falta el @", Toast.LENGTH_SHORT).show();
        } else{

            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if(mAuth.getCurrentUser().getUid().equals("dW31puoYi9bVRiZ5O43HPoQTVAg2")){
                            Toast.makeText( getContext(), "Hola ADMIN", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), Admin.class));
                        }else{
                            Toast.makeText( getContext(), "Bienvenid@", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), Cliente.class));
                        }
                    }else{
                        Log.w("TAG", "Error:", task.getException());
                        Toast.makeText( getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


