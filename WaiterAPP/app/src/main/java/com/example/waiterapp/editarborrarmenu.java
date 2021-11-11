package com.example.waiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class editarborrarmenu extends AppCompatActivity {
    private ItemMenu Item;
    Button btnImagen, btnGuardar, btneliminar;
    TextView lbTitulo;
    EditText ediproducto,edidescripcion,ediprecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarborrarmenu);

        btnImagen = findViewById(R.id.btnproducto);
        btnGuardar = findViewById(R.id.btnGuardar);
        btneliminar = findViewById(R.id.eliminar);
        lbTitulo= findViewById(R.id.lbTitulo);
        ediproducto = findViewById(R.id.ediproducto);
        edidescripcion = findViewById(R.id.edidescripcion);
        ediprecio = findViewById(R.id.ediprecio);

        Item = (ItemMenu) getIntent().getSerializableExtra("ObjetoE");

        btnImagen.setBackgroundResource(Item.getImgFoto());
        lbTitulo.setText(Item.getTitulo());

        ediproducto.setText(Item.getTitulo());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar(ediproducto.getText().toString(),edidescripcion.getText().toString(),ediprecio.getText().toString());

            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Eliminar(ediproducto.getText().toString());
            }
        });
    }


    private void Editar( String nombre, String descripcion, String precio){

        DbHelper helper = new DbHelper(this,"BD",null,1);
        SQLiteDatabase db= helper.getWritableDatabase();

        String SQL = "Update Menu set  Descripcion = '"+descripcion+"', Precio = '"+precio+"' Where Nombre= " + nombre;

        Toast.makeText(this, "Editado Correctamente",Toast.LENGTH_LONG).show();
        db.execSQL(SQL);
        db.close();

    }

    private void Eliminar(String id){

        DbHelper helper = new DbHelper(this,"BD",null,1);
        SQLiteDatabase db= helper.getWritableDatabase();

        String SQL = "delete from Menu Where Nombre= " + id;

        Toast.makeText(this, "Producto Eliminado",Toast.LENGTH_LONG).show();
        db.execSQL(SQL);
        db.close();

    }
}