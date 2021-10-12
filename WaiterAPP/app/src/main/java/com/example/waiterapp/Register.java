package com.example.waiterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private AnimationDrawable anim;
    private Button button;
    private EditText email, passw, name, phone, confirmar;
    private TextView login;

    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init(); //conexion con java y xml

        // animacion para los botones
        anim = (AnimationDrawable) button.getBackground();
        anim.setEnterFadeDuration(2300);
        anim.setExitFadeDuration(2300);

        //Texview cuando lo presionan
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        //Metodo para boton regiter
        // registra a firebase y va hacia activity Login
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        //conexion firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }//End OnCreate

    //Metodo para abrir el login
    private void openLoginActivity() {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }

    //Conexion de java y xml
    private void init(){
        this.button = findViewById(R.id.button);
        this.email = findViewById(R.id.editTextTextEmail);
        this.passw = findViewById(R.id.editTextTextPassword);
        this.name = findViewById(R.id.editTextTextName);
        this.phone = findViewById(R.id.editTextTextTelefono);
        this.login = findViewById(R.id.login);
        this.confirmar = findViewById(R.id.editTextTextConfirmar);
    }

    //inicia la animacion de los botones
    @Override
    protected void onResume() {
        super.onResume();
        if(anim != null && !anim.isRunning()){
            anim.start();
        }
    }

    //detiene la animacion de los botones
    @Override
    protected void onPause() {
        super.onPause();
        if(anim != null && anim.isRunning()){
            anim.stop();
        }
    }

    //Metodo para crear usuarios en firebase y sus colecciones
    public void createUser(){
        String nombre = name.getText().toString();
        String mail = email.getText().toString();
        String telefo = phone.getText().toString();
        String password = passw.getText().toString();
        String confir = confirmar.getText().toString();

        if(TextUtils.isEmpty(nombre)){
            Toast.makeText( Register.this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(mail)){
            Toast.makeText( Register.this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(telefo)){
            Toast.makeText( Register.this, "Ingrese un telefono", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText( Register.this, "Ingrese una Contraseña", Toast.LENGTH_SHORT).show();
        }else if(!confirmar.getText().toString().equals(passw.getText().toString())){
            Toast.makeText( Register.this, "La contraseña debe ser la misma", Toast.LENGTH_SHORT).show();
        }else if(!email.getText().toString().contains("@")){
            Toast.makeText( Register.this, "Le falta el @", Toast.LENGTH_SHORT).show();
        }else{

            mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);

                        Map<String,Object> user = new HashMap<>();
                        user.put("Nombre", nombre);
                        user.put("Correo", mail);
                        user.put("Telefono", telefo);
                        user.put("Contraseña", password);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: Datos registrados"+userID);
                            }
                        });
                        Toast.makeText( Register.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    }else{
                        Toast.makeText( Register.this, "Usuario no registrado: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}