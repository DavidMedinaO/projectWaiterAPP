package com.example.waiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Cliente extends AppCompatActivity {

    private Button button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        init(); //conexion con java y xml

        //Metodo para boton salir al dar click
        // sale hacia activity Login
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Cliente.this, Login.class));
            }
        });

        //conexion firebase
        mAuth = FirebaseAuth.getInstance();
    }//End onCreate

    //Conexion de java y xml
    private void init(){
        this.button = findViewById(R.id.salir);
    }
}