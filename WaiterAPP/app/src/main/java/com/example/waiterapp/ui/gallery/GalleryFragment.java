package com.example.waiterapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.waiterapp.ClienteMenuInicio;
import com.example.waiterapp.R;
import com.example.waiterapp.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

private FragmentGalleryBinding binding;

    Button btn1;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentGalleryBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


    this.btn1 = root.findViewById(R.id.hola);

    btn1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent intent = new Intent(getContext(), ClienteMenuInicio.class);
            getContext().startActivity(intent);
        }
    });

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}