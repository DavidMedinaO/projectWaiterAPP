package com.example.waiterapp.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.waiterapp.Cliente;
import com.example.waiterapp.LoginTabFragment;
import com.example.waiterapp.R;
import com.example.waiterapp.databinding.FragmentSlideshowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SlideshowFragment extends Fragment {


private FragmentSlideshowBinding binding;
EditText txtcorreo1;
Button btnEliminar;

    private FirebaseAuth mAuth;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {


    binding = FragmentSlideshowBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    txtcorreo1 = root.findViewById(R.id.asignacionCorreo);
    btnEliminar = root.findViewById(R.id.eliminarUser);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        txtcorreo1.setText(mAuth.getCurrentUser().getEmail());

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), LoginTabFragment.class));
            }
        });

        return root;


    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
                    Toast.makeText( getContext(), "Se elimino correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginTabFragment.class));
                    //finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG","Ocurrio un error durante la eliminación del usuario", e);
            }
        });
    }//fin metodo deleteaccount


}

