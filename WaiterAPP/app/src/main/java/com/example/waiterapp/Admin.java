package com.example.waiterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    private Button button, buttonElimi;
    private FirebaseAuth mAuth;

    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<ItemMenu> listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Mostrar items Menú
        lvItems = findViewById(R.id.lvItems);
        listMenu = GetArrayItems();
        adaptador = new Adaptador(this,GetArrayItems());
        lvItems.setAdapter(adaptador);

        init(); //conexion con java y xml

        //Metodo para boton salir al dar click
        // sale hacia activity Login
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Admin.this, Login.class));
            }
        });

        //Metodo para boton eliminar al dar click
        //Elimina el usuario actual
        buttonElimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
            }
        });

        //conexion firebase
        mAuth = FirebaseAuth.getInstance();




    }//End onCreate

    //Conexion de java y xml
    private void init(){

        this.button = findViewById(R.id.salir);
        this.buttonElimi = findViewById(R.id.eliminar);

    }

    //Metodo para eliminar usuario de firebase
    private void deleteAccount() {
        Log.d("TAG", "ingreso a deleteAccount");
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG","OK! Works fine!");
                    Toast.makeText( Admin.this, "Se elimino correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Admin.this, Login.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG","Ocurrio un error durante la eliminación del usuario", e);
            }
        });
    }//fin metodo deleteaccount


    //Llenar lista con los items del menu
    private ArrayList<ItemMenu> GetArrayItems(){

        ArrayList<ItemMenu> listItems = new ArrayList<>();
        listItems = ListHabit();
        return listItems;
    }

    private ArrayList<ItemMenu>  ListHabit() {

        ArrayList<ItemMenu> listItem = new ArrayList<>();

        listItem.add(new ItemMenu(R.drawable.comida4,"PIZZAS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida3,"HAMBURGUESAS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida7,"PERROS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida8,"CARNES","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida9,"ENSALADAS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","EDITAR"));
        return listItem;
    }

}