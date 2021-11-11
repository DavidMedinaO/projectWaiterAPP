package com.example.waiterapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.waiterapp.Adaptador;
import com.example.waiterapp.ItemMenu;
import com.example.waiterapp.R;
import com.example.waiterapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<ItemMenu> listMenu;

private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        //Mostrar items Menú

        lvItems = root.findViewById(R.id.lvItems);
        listMenu = GetArrayItems();
        adaptador = new Adaptador(getContext(),GetArrayItems());
        lvItems.setAdapter(adaptador);

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

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}