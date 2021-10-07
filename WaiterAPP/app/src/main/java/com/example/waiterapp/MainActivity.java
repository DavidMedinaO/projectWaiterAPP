package com.example.waiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AnimationDrawable anim, anim2;
    private Button button, button2;
    private EditText email, passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init(); //conexion con java y xml

        // animacion para los botones
        anim = (AnimationDrawable) button.getBackground();
        anim.setEnterFadeDuration(2300);
        anim.setExitFadeDuration(2300);

        anim2 = (AnimationDrawable) button2.getBackground();
        anim2.setEnterFadeDuration(2800);
        anim2.setExitFadeDuration(2800);

        //Metodo para boton login ir a activity Cliente y Admin
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().equals("") || passw.getText().toString().equals("")){
                    Toast.makeText( MainActivity.this, "Ingrese un Email o contraseña", Toast.LENGTH_SHORT).show();
                }else if(email.getText().toString().equals("dany") && passw.getText().toString().equals("123")){
                    Intent admin = new Intent(MainActivity.this, Admin.class);
                    startActivity(admin);
                    email.getText().clear();
                    passw.getText().clear();
                }else if(!email.getText().toString().contains("@")){
                    Toast.makeText( MainActivity.this, "Le falta el @", Toast.LENGTH_SHORT).show();
                } else{
                    Intent cliente = new Intent(MainActivity.this, Cliente.class);
                    startActivity(cliente);
                    email.getText().clear();
                    passw.getText().clear();
                }

            }

        });

        //conexion firebase

    }

    //Conexion de java y xml
    private void init(){
        this.button = findViewById(R.id.button);
        this.button2 = findViewById(R.id.button2);
        this.email = findViewById(R.id.editTextTextPersonName);
        this.passw = findViewById(R.id.editTextTextPassword);
    }

    //inicia la animacion de los botones
    @Override
    protected void onResume() {
        super.onResume();
        if(anim != null && !anim.isRunning()){
            anim.start();
        }
        if(anim2 != null && !anim2.isRunning()){
            anim2.start();
        }
    }

    //detiene la animacion de los botones
    @Override
    protected void onPause() {
        super.onPause();
        if(anim != null && anim.isRunning()){
            anim.stop();
        }
        if(anim2 != null && anim2.isRunning()){
            anim2.stop();
        }
    }
}