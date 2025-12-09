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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AdminFragment() {
        // Required empty public constructor
    }

    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnAll = view.findViewById(R.id.btnAll);

        // الحصول على الـ NavController الصحيح
        NavController navController = Navigation.findNavController(view);

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
