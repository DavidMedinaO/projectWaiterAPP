package com.example.waiterapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SigupTabFragment extends Fragment {

    private Button button;
    private EditText email, passw, name, phone, confirmar, codigo;
    private TextView login;
    private Spinner lista;

    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.sigup_tab_fragment, container, false);

        conectar(root);

//Llenar el spinner
        String [] SPtipos ={"Tipo usuario","Cliente","Admin"};
        ArrayAdapter<String> Adapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,SPtipos);
        lista.setAdapter(Adapter3);

        //Cuando se seleccione al admin en el spinner
        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(lista.getSelectedItemPosition() == 0){
                    Toast.makeText( getContext(), "Registro", Toast.LENGTH_SHORT).show();
                }else if(lista.getSelectedItemPosition() == 1){
                    Toast.makeText( getContext(), "cliente", Toast.LENGTH_SHORT).show();
                    name.setHint("Nombre completo");
                    codigo.setVisibility(View.INVISIBLE);
                    codigo.setText("2021");
                }else{
                    Toast.makeText( getContext(), "Admin", Toast.LENGTH_SHORT).show();
                    name.setHint("Nombre restaurante");
                    codigo.setVisibility(View.VISIBLE);
                    codigo.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText( getContext(), "No selecciono nada", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        //conexion firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        return (root);

    }

    private void conectar(ViewGroup root) {
        button = root.findViewById(R.id.button);
        email = root.findViewById(R.id.editTextTextEmail);
        passw = root.findViewById(R.id.editTextTextPassword);
        name = root.findViewById(R.id.editTextTextName);
        phone = root.findViewById(R.id.editTextTextTelefono);
        login = root.findViewById(R.id.login);
        confirmar = root.findViewById(R.id.editTextTextConfirmar);
        lista = root.findViewById(R.id.spinner);
        codigo = root.findViewById(R.id.editTextTextCodigoRes);

    }

    //Metodo para crear usuarios en firebase y sus colecciones
    public void createUser(){
        String nombre = name.getText().toString();
        String mail = email.getText().toString();
        String telefo = phone.getText().toString();
        String password = passw.getText().toString();
        int tipoUsuario = lista.getSelectedItemPosition();
        String codi = codigo.getText().toString();

        if(TextUtils.isEmpty(nombre)){
            Toast.makeText( getContext(), "Ingrese un nombre", Toast.LENGTH_SHORT).show();
        }else if(tipoUsuario == 0){
            Toast.makeText( getContext(), "Seleccione un tipo de usuario", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(mail)){
            Toast.makeText( getContext(), "Ingrese un correo", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(codi)){
            Toast.makeText( getContext(), "Ingrese el codigo del restaurante", Toast.LENGTH_SHORT).show();
        }else if(!codigo.getText().toString().equals("2021")){
            Toast.makeText( getContext(), "Codigo no valido", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(telefo)){
            Toast.makeText( getContext(), "Ingrese un telefono", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText( getContext(), "Ingrese una Contraseña", Toast.LENGTH_SHORT).show();
        }else if(!confirmar.getText().toString().equals(passw.getText().toString())){
            Toast.makeText( getContext(), "La contraseña debe ser la misma", Toast.LENGTH_SHORT).show();
        }else if(!email.getText().toString().contains("@")){
            Toast.makeText( getContext(), "Le falta el @", Toast.LENGTH_SHORT).show();
        }else{

            //Aqui se insertan los usuarios con sus datos
            mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);

                        Map<String,Object> user = new HashMap<>();
                        user.put("Nombre", nombre);
                        user.put("Correo", mail);
                        user.put("Telefono", telefo);
                        user.put("Contraseña", password);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: Datos registrados"+userID);
                            }
                        });
                        Toast.makeText( getContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), LoginTabFragment.class));
                    }else{
                        Toast.makeText( getContext(), "Usuario no registrado: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}


