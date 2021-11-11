package com.example.waiterapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends BaseAdapter  {
    private Context context;
    private ArrayList<ItemMenu> ListItems;

    public Adaptador(Context context, ArrayList<ItemMenu> listItems) {
        this.context = context;
        ListItems = listItems;
    }

    @Override
    public int getCount() {
        return ListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return ListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ItemMenu Item = (ItemMenu) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.itemlista,null);
        /*
        //ImageView imgFoto= convertView.findViewById(R.id.imgFoto);
        Button imgFoto= convertView.findViewById(R.id.btnImg);
        TextView tvTitulo = convertView.findViewById(R.id.tvTitulo);
        TextView tvEditar = convertView.findViewById(R.id.tvEditar);

        //imgFoto.setImageResource(Item.getImgFoto());
        imgFoto.setBackgroundResource(Item.getImgFoto());
        tvTitulo.setText(Item.getTitulo());
        tvEditar.setText(Item.getContenido());
        */


        CircleImageView imgFoto= convertView.findViewById(R.id.img_comida);
        TextView tvTitulo = convertView.findViewById(R.id.tv_tipocomida);
        TextView tvPantalla = convertView.findViewById(R.id.tv_pantalla);


        imgFoto.setImageResource(Item.getImgFoto());
        tvTitulo.setText(Item.getTitulo());
        tvPantalla.setText(Item.getContenido());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pantalla = (String) tvPantalla.getText();


                if(pantalla.equals("EDITAR")){

                    Intent intent = new Intent(context, adminprueba.class);
                    intent.putExtra("ObjetoE", Item);
                    context.startActivity(intent);



                }else{
                    Intent intent = new Intent(context, AgregarMenu.class);
                    intent.putExtra("ObjetoE", Item);
                    context.startActivity(intent);

                }


            }
        });


        return convertView;
    }
}
