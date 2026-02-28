package com.example.luxurywatches;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginFragment extends Fragment {
    private EditText etUsername,etPassword;
    private TextView tvSignupLink;
    private TextView tvForgotPasswardLink;
    private Button btnLogin;
    private FirebaseServices fbs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        // connecting components
        fbs = FirebaseServices.getInstance();
        etUsername=getView().findViewById(R.id.etUsernameLogin);
        tvSignupLink=getView().findViewById(R.id.tvSignupLinkLogin);
        tvForgotPasswardLink=getView().findViewById(R.id.tvForgotPasswordLogin);
        etPassword=getView().findViewById(R.id.etPasswordLogin);
        btnLogin=getView().findViewById(R.id.btnLoginLogin);

        tvSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignupFragment();
            }
        });

        tvForgotPasswardLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFrgotPasswordFragment();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Data validation

                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                if(username.trim().isEmpty()||password.trim().isEmpty()){
                    Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    return;

                }


                fbs.getAuth().signInWithEmailAndPassword(username,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            gotoAddWatchesFragment();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "failed to login! check user or password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }

            private void gotoAddWatchesFragment() {
            }
        });

    }


    public void gotoAllFragment() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AllFragment());
        ft.commit();
    }
    private void gotoAddCarFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddWatchesFragment());
        ft.commit();
    }
    private void gotoSignupFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new SignupFragment());
        ft.commit();

    }

    private void gotoFrgotPasswordFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new LoginFragment());
        ft.commit();

    }
    private void gotoAddWatchesFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddWatchesFragment());
        ft.commit();

    }

}