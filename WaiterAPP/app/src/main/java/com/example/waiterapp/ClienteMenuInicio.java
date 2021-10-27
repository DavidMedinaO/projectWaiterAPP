package com.example.waiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ClienteMenuInicio extends AppCompatActivity {

    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<ItemMenu> listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_menu_inicio);

        //Mostrar items Menú
        lvItems = findViewById(R.id.lvItems);
        listMenu = GetArrayItems();
        adaptador = new Adaptador(this,GetArrayItems());
        lvItems.setAdapter(adaptador);
    }

    //Llenar lista con los items del menu
    private ArrayList<ItemMenu> GetArrayItems(){

        ArrayList<ItemMenu> listItems = new ArrayList<>();
        listItems = ListHabit();
        return listItems;
    }

    //Cargar el arraylist con esos valores
    private ArrayList<ItemMenu>  ListHabit() {

        ArrayList<ItemMenu> listItem = new ArrayList<>();

        listItem.add(new ItemMenu(R.drawable.comida4,"PIZZAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida3,"HAMBURGUESA","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida7,"PERROS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida8,"CARNES","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida9,"ENSALADAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","PEDIR"));
        return listItem;
    }
}