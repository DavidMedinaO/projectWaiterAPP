package com.example.waiterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class Cliente extends AppCompatActivity {

    private Button button, buttonElimi, scann;
    private FirebaseAuth mAuth;
    private EditText resultadoQR;

    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<ItemMenu> listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

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
                startActivity(new Intent(Cliente.this, Login.class));
            }
        });

        //Metodo para boton QR al dar click
        //Escanea y recibe informacion del QR
        scann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrador = new IntentIntegrator(Cliente.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES); //lee todos los tipo de QR
                integrador.setPrompt("Lector - QR"); //Mensaje del QR
                integrador.setCameraId(0); //0 es camara de atras
                integrador.setBeepEnabled(true); //Suene cuando escanee
                integrador.setBarcodeImageEnabled(true); // lea codigo de barras
                integrador.initiateScan();

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
        this.scann = findViewById(R.id.btnScan);
        this.resultadoQR = findViewById(R.id.txtResultadoQR);
    }

    // Metodo para obtener los resultados del QR
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                resultadoQR.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
                    Toast.makeText( Cliente.this, "Se elimino correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Cliente.this, Login.class));
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

    //Cargar el arraylist con esos valores
    private ArrayList<ItemMenu>  ListHabit() {

        ArrayList<ItemMenu> listItem = new ArrayList<>();

        listItem.add(new ItemMenu(R.drawable.comida4,"PIZZAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida4,"PIZZAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida4,"PIZZAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida5,"SOPAS","PEDIR"));
        listItem.add(new ItemMenu(R.drawable.comida6,"PASTAS","PEDIR"));
        return listItem;
    }
}