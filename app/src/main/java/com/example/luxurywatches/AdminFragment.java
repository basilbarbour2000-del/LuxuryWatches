package com.example.luxurywatches;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        Button btnAdd = getActivity().findViewById(R.id.btnAdd);
        Button btnAll = getActivity().findViewById(R.id.btnAll);

        // الحصول على الـ NavController الصحيح
        NavController navController = Navigation.findNavController(getView());

        // انتقال لصفحة الإضافة
        btnAdd.setOnClickListener(v ->
                navController.navigate(R.id.action_adminFragment_to_addFragment)
        );

        // انتقال لصفحة العرض الكامل
        btnAll.setOnClickListener(v ->
                navController.navigate(R.id.action_adminFragment_to_allFragment)
        );
    }
}
