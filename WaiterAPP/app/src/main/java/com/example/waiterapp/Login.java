package com.example.waiterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private AnimationDrawable anim;
    private Button button;
    private EditText email, passw;
    private FirebaseAuth mAuth;
    private TextView registra;

    TabLayout tabLayout;
    ViewPager2 viewPager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager2);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager2.setAdapter(new LoginAdapter(getSupportFragmentManager(), getLifecycle()));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        init(); //conexion con java y xml



        // animacion para los botones
//        anim = (AnimationDrawable) button.getBackground();
  //      anim.setEnterFadeDuration(2300);
    //    anim.setExitFadeDuration(2300);

        //Texview cuando lo presionan
        //Abre el activity register
/*
     registra.setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View view) {
         openRegisterActivity();
            }
     });

        //Metodo para boton login al dar click
        // va hacia activity Cliente o Admin
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
*/
        //conexion firebase
        mAuth = FirebaseAuth.getInstance();

    }//End onCreate

    //Metodo para abrir el register
    private void openRegisterActivity() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    //Metodo para abrir el Cliente
    private void openClienteActivity() {
        Intent intent = new Intent(Login.this, Cliente.class);
        startActivity(intent);
    }

    //Metodo para abrir el Admin
    private void openAdminActivity() {
        Intent intent = new Intent(Login.this, Admin.class);
        startActivity(intent);
    }

    //Conexion de java y xml
    private void init(){
        this.button = findViewById(R.id.button);
        this.email = findViewById(R.id.editTextTextPersonName);
        this.passw = findViewById(R.id.editTextTextPassword);
        this.registra = findViewById(R.id.register);
    }

    //inicia la animacion de los botones
    //@Override
    //protected void onResume() {
      //  super.onResume();
       // if(anim != null && !anim.isRunning()){
         //   anim.start();
        //}
    //}

    //detiene la animacion de los botones
    @Override
    protected void onPause() {
        super.onPause();
        if(anim != null && anim.isRunning()){
            anim.stop();
        }
    }

    //Firebase
    //Login
    public void userLogin(){
        String mail = email.getText().toString();
        String password = passw.getText().toString();

        if(TextUtils.isEmpty(mail)){
            Toast.makeText( Login.this, "Ingrese un Email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText( Login.this, "Ingrese una Contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(!email.getText().toString().contains("@")){
            Toast.makeText( Login.this, "Le falta el @", Toast.LENGTH_SHORT).show();
        } else{

            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if(mAuth.getCurrentUser().getUid().equals("dW31puoYi9bVRiZ5O43HPoQTVAg2")){
                            Toast.makeText( Login.this, "Hola ADMIN", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, Admin.class));
                        }else{
                            Toast.makeText( Login.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, Cliente.class));
                        }
                    }else{
                        Log.w("TAG", "Error:", task.getException());
                        Toast.makeText( Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}