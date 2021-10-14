package com.example.waiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminprueba extends AppCompatActivity {

    private ItemMenu Item;
    Button btnImagen, btnGuardar;
    TextView lbTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminprueba);

        btnImagen = findViewById(R.id.btnproducto);
        btnGuardar = findViewById(R.id.btnGuardar);
        lbTitulo= findViewById(R.id.lbTitulo);

        Item = (ItemMenu) getIntent().getSerializableExtra("ObjetoE");

        btnImagen.setBackgroundResource(Item.getImgFoto());
        lbTitulo.setText(Item.getTitulo());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(),Admin.class);
                startActivity(I);
            }
        });


    }
}