package com.example.waiterapp.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.database.sqlite.SQLiteDatabase;

import com.example.waiterapp.Adaptador;
import com.example.waiterapp.AgregarMenu;
import com.example.waiterapp.DbHelper;
import com.example.waiterapp.ItemMenu;
import com.example.waiterapp.Login;
import com.example.waiterapp.Pedidos;
import com.example.waiterapp.R;
import com.example.waiterapp.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<ItemMenu> listMenu;
    private Button button, buttonElimi, btnagregar,btnpedido;
    private FirebaseAuth mAuth;


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        //Mostrar items Menú

        btnpedido =root.findViewById(R.id.btnpedido);
        button = root.findViewById(R.id.salir);
        lvItems = root.findViewById(R.id.lvItems);
        listMenu = GetArrayItems();
        adaptador = new Adaptador(getContext(),GetArrayItems());
        lvItems.setAdapter(adaptador);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), Login.class));
            }
        });

        btnpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Pedidos.class);
                getContext().startActivity(intent);
            }
        });

        return root;
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

       /* listItem.add(new ItemMenu(R.drawable.comida4,"PIZZAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida3,"HAMBURGUESA","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida7,"PERROS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida8,"CARNES","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida9,"ENSALADAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","PEDIR"));*/
        listItem = ListHabit2();
        return listItem;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<ItemMenu>  ListHabit2(){

        ArrayList<ItemMenu> listItem = new ArrayList<>();
        DbHelper helper = new DbHelper(getContext(),"BD",null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String SQL = "Select Id , Nombre,Descripcion,Precio from Menu";
        //String SQL = "Select * from Contactos";
        Cursor c = db.rawQuery(SQL,null);

        if(c.moveToFirst()){

            do{

                String Nombre = c.getString(1);
                String Descripcion = c.getString(2);
                String Precio = c.getString(3);


                    if(c.getString(1).equals("PIZZAS")){

                            listItem.add(new ItemMenu(R.drawable.comida4,Nombre,"PEDIR"));

                    }

                    if(c.getString(1).equals("SOPAS")){

                            listItem.add(new ItemMenu(R.drawable.comida5,Nombre,"PEDIR"));

                    }

                    if(c.getString(1).equals("PASTAS")){

                        listItem.add(new ItemMenu(R.drawable.comida6,Nombre,"PEDIR"));

                    }

                    if(c.getString(1).equals("HAMBURGUESA")){

                        listItem.add(new ItemMenu(R.drawable.comida3,Nombre,"PEDIR"));

                    }

                    if(c.getString(1).equals("PERROS")){

                        listItem.add(new ItemMenu(R.drawable.comida7,Nombre,"PEDIR"));

                    }
                    if(c.getString(1).equals("CARNES")){

                        listItem.add(new ItemMenu(R.drawable.comida8,Nombre,"PEDIR"));

                    }

                    if(c.getString(1).equals("ENSALADAS")){

                        listItem.add(new ItemMenu(R.drawable.comida9,Nombre,"PEDIR"));

                    }

            }while (c.moveToNext());

        }

        db.close();
        return listItem;
    }
}