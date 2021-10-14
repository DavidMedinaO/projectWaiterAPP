package com.example.waiterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private EditText email, passw, name, phone, confirmar, codigo;
    private TextView login;
    private Spinner lista;

    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init(); //conexion con java y xml

        //Llenar el spinner
        String [] SPtipos ={"Tipo usuario","Cliente","Admin"};
        ArrayAdapter<String> Adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,SPtipos);
        lista.setAdapter(Adapter3);

        //Cuando se seleccione al admin en el spinner
        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(lista.getSelectedItemPosition() == 0){
                    Toast.makeText( Register.this, "Registro", Toast.LENGTH_SHORT).show();
                }else if(lista.getSelectedItemPosition() == 1){
                    Toast.makeText( Register.this, "cliente", Toast.LENGTH_SHORT).show();
                    name.setHint("Nombre completo");
                    codigo.setVisibility(View.INVISIBLE);
                    codigo.setText("2021");
                }else{
                    Toast.makeText( Register.this, "Admin", Toast.LENGTH_SHORT).show();
                    name.setHint("Nombre restaurante");
                    codigo.setVisibility(View.VISIBLE);
                    codigo.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText( Register.this, "No selecciono nada", Toast.LENGTH_SHORT).show();
            }
        });

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
        this.lista = findViewById(R.id.spinner);
        this.codigo = findViewById(R.id.editTextTextCodigoRes);
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
        int tipoUsuario = lista.getSelectedItemPosition();
        String codi = codigo.getText().toString();

        if(TextUtils.isEmpty(nombre)){
            Toast.makeText( Register.this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
        }else if(tipoUsuario == 0){
            Toast.makeText( Register.this, "Seleccione un tipo de usuario", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(mail)){
            Toast.makeText( Register.this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(codi)){
            Toast.makeText( Register.this, "Ingrese el codigo del restaurante", Toast.LENGTH_SHORT).show();
        }else if(!codigo.getText().toString().equals("2021")){
            Toast.makeText( Register.this, "Codigo no valido", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(telefo)){
            Toast.makeText( Register.this, "Ingrese un telefono", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText( Register.this, "Ingrese una Contraseña", Toast.LENGTH_SHORT).show();
        }else if(!confirmar.getText().toString().equals(passw.getText().toString())){
            Toast.makeText( Register.this, "La contraseña debe ser la misma", Toast.LENGTH_SHORT).show();
        }else if(!email.getText().toString().contains("@")){
            Toast.makeText( Register.this, "Le falta el @", Toast.LENGTH_SHORT).show();
        }else{

            //Aqui se insertan los usuarios con sus datos
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