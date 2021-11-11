package com.example.waiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class admineditar extends AppCompatActivity {

    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<ItemMenu> listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admineditar);

        lvItems = findViewById(R.id.lvItems);
        listMenu = GetArrayItems();
        adaptador = new Adaptador(this,GetArrayItems());
        lvItems.setAdapter(adaptador);


    }

    private ArrayList<ItemMenu> GetArrayItems(){

        ArrayList<ItemMenu> listItems = new ArrayList<>();

        //listItems.add(new Entidad(R.drawable.libro,"hola","DISPONIBLE"));

        listItems = ListHabit();
        return listItems;
    }

    private ArrayList<ItemMenu>  ListHabit() {

        ArrayList<ItemMenu> listItem = new ArrayList<>();

        listItem.add(new ItemMenu(R.drawable.comida4,"PIZZAS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","EDITAR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","EDITAR"));
        return listItem;
    }


}