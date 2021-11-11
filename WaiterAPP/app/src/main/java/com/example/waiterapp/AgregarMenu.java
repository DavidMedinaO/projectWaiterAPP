package com.example.waiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AgregarMenu extends AppCompatActivity {
    private ItemMenu Item;
    Button btnImagen, btnGuardar;
    TextView lbTitulo;
    EditText ediproducto,edidescripcion,ediprecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_menu);

        btnImagen = findViewById(R.id.btnproducto);
        btnGuardar = findViewById(R.id.btnGuardar);
        lbTitulo= findViewById(R.id.lbTitulo);
        ediproducto = findViewById(R.id.ediproducto);
        edidescripcion = findViewById(R.id.edidescripcion);
        ediprecio = findViewById(R.id.ediprecio);

        //Item = (ItemMenu) getIntent().getSerializableExtra("ObjetoE");

        //btnImagen.setBackgroundResource(Item.getImgFoto());
        //lbTitulo.setText(Item.getTitulo());

        //ediproducto.setText(Item.getTitulo());


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardar(ediproducto.getText().toString(),edidescripcion.getText().toString(),ediprecio.getText().toString());
            }
        });


    }


    private void Guardar (String nombre, String descripcion, String precio){

        DbHelper helper = new DbHelper(this,"BD",null,1);
        SQLiteDatabase db= helper.getWritableDatabase();

        try{

            ContentValues cv = new ContentValues();
            cv.put("Nombre", nombre);
            cv.put("Descripcion", descripcion);
            cv.put("Precio", precio);


            db.insert("Menu",null,cv);
            db.close();
            Toast.makeText(this, "Registrado Correctamente",Toast.LENGTH_LONG).show();
            Intent I = new Intent(getApplicationContext(), admineditar.class);
            startActivity(I);

        }catch(Exception ex)
        {

            Toast.makeText(this,"Error"+ex.getMessage(), Toast.LENGTH_LONG).show();

        }




    }


    private void Editar( String nombre, String descripcion, String precio){

        DbHelper helper = new DbHelper(this,"BD",null,1);
        SQLiteDatabase db= helper.getWritableDatabase();

        String SQL = "Update Menu set  Descripcion = '"+descripcion+"', Precio = '"+precio+"' Where Nombre= " + nombre;

        Toast.makeText(this, "Editado Correctamente",Toast.LENGTH_LONG).show();
        db.execSQL(SQL);
        db.close();

    }
}