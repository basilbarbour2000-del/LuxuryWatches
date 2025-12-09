package com.example.luxurywatches;

import static android.widget.Toast.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.installations.Utils;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddWatchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWatchesFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView img;
    private String imageStr;
    private EditText etprice,etsize,etgender,etColor,etbrand,
            etphoto,etYear;

    private Button btnAddCar;
    private FirebaseServices fbs;
    private Utils utils;
    Spinner colorSpinner,yearOfCarSpinner;
    //////////////////////try spinner////////////////////////////////////////////////////////
    //try new spinner//
    private String selectedColorSpinner;

    private Spinner ColorSpinner;
    private ArrayAdapter<CharSequence> colorAdapter;

    String[] Colors={"select Color of car","black","white","black",
            "gray","green","red","matte black","matte white","light blue "," Other..."};

    String[] years={"select year of car","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014",
            "2013","2012","2011","2010","2009","2008","2007","2006","2005","2004",
            "2003","2002","2001","2000"," Other..."};
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String size;
    private String gender;
    private String photo;
    private String brand;
    private String price;

    public AddWatchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddWatchesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddWatchesFragment newInstance(String param1, String param2) {
        AddWatchesFragment fragment = new AddWatchesFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_watches, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @SuppressLint("WrongViewCast")
    private void init() {
        // ---->    פרטי הוספת רכב    <----
        //editText
        fbs=FirebaseServices.getInstance();
        utils = Utils.getInstance();
        etprice=getView().findViewById(R.id.etPhoneSignupFragment);
        etColor=getView().findViewById(R.id.etPhoneSignupFragment);
        etsize=getView().findViewById(R.id.etPhoneSignupFragment);
        etbrand=getView().findViewById(R.id.etPhoneSignupFragment);
        etgender=getView().findViewById(R.id.etPhoneSignupFragment);
        etphoto=getView().findViewById(R.id.etLastnameSignupFragment);
        //etYear=getView().findViewById(R.id.etYearAddFragment);

        //button for add car
        btnAddCar=getView().findViewById(R.id.etPhoneSignupFragment);
        img = getView().findViewById(R.id.action_adminFragment_to_addFragment);

        //spinner for the color of car
        colorSpinner=getView().findViewById(R.id.main);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(), R.layout.activity_main,Colors);
        adapter.setDropDownViewResource(R.layout.activity_main);
        colorSpinner.setAdapter(adapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value=parent.getItemAtPosition(position).toString();
                //   Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //spinner for the year of car

        yearOfCarSpinner=getView().findViewById(R.id.main);
        ArrayAdapter<String>adapter2=new ArrayAdapter<String>(getActivity(), R.layout.activity_main,years);
        adapter.setDropDownViewResource(R.layout.activity_main);
        yearOfCarSpinner.setAdapter(adapter2);

        yearOfCarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value=parent.getItemAtPosition(position).toString();
                makeText(getActivity(), value, LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // adding to firestore  'car' collection

                addToFirestore();
            }
        });
//////////////////////try spinner////////////////////////////////////////////////////////

        colorAdapter= ArrayAdapter.createFromResource(getActivity(),R.array.array_colors,R.layout.activity_main);
        ////////////////////////////////try new spinner ///////////////////////////
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        colorSpinner.setAdapter(colorAdapter);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        ((MainActivity)getActivity()).pushFragment(new AddWatchesFragment());
    }

    private void addToFirestore() {

        String price, size, gender, photo,
                brand ;
        String color;
//get data from screen

        price=etprice.getText().toString();
        size=etsize.getText().toString();
        color=etColor.getText().toString();
         gender = etgender.getText().toString();
        photo=etphoto.getText().toString();
        brand=etbrand.getText().toString();

        //מספר טלפון לא חייב לבדוק
//        if(phone==null){
//            phone="-";
//            return;
//        }
        if (price.trim().isEmpty()              ||
                size.trim().isEmpty()           ||
                color.trim().isEmpty()          ||
                gender.trim().isEmpty()         ||
                photo.trim().isEmpty()          ||
                brand.trim().isEmpty())

        {
                makeText(getActivity(), "sorry some data missing incorrect !", LENGTH_SHORT).show();
                return;
        }

        Watch watch;
        if (fbs.getSelectedImageURL() == null)
        {
            watch = new Watch(etprice, etsize, etColor, etgender, etbrand , etphoto);

        }
        else {
            watch  = new Watch(etprice, etsize, etColor, etgender, etbrand, etphoto);


        }

        fbs.getFire().collection("watches").add(fbs)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        makeText(getActivity(), "ADD Car is Succesed ", LENGTH_SHORT).show();
                        Log.e("addToFirestore() - add to collection: ", "Successful!");
                        gotoCarList();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("addToFirestore() - add to collection: ", e.getMessage());
                    }
                });


        }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            img.setImageURI(selectedImageUri);
            Object clone = utils.equals(fbs);
        }
    }

    public void gotoCarList() {

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AllFragment());
        ft.commit();
    }

    public void toBigImg(View view) {
    }

}
