package com.example.luxurywatches;

import android.annotation.SuppressLint;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FirebaseServices {
    private  static FirebaseServices instance;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    private FirebaseStorage storage;
    private Uri selectedImageURL;
    @SuppressLint("RestrictedApi")
    private User currentUser;
    private boolean userChangeFlag;

    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }

    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    public  FirebaseServices ()
    {
        auth=FirebaseAuth.getInstance();
        fire=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        getCurrentObjectUser(new UserCallback() {
            public void onUserLoaded(@SuppressLint("RestrictedApi") User user) {
                // Access the currentUser here
                if (user != null) {
                    setCurrentUser(user);
                }
            }
        });

        userChangeFlag = false;
        selectedImageURL = null;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseFirestore getFire() {
        return fire;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public  static FirebaseServices getInstance(){
        if (instance==null){
            instance=new FirebaseServices();

        }
        return instance;
    }

    public static FirebaseServices reloadInstance(){
        instance=new FirebaseServices();
        return instance;
    }

    public boolean isUserChangeFlag() {
        return userChangeFlag;
    }

    public void setUserChangeFlag(boolean userChangeFlag) {
        this.userChangeFlag = userChangeFlag;
    }

    public void getCurrentObjectUser(UserCallback callback) {        @SuppressLint("RestrictedApi") ArrayList<User> usersInternal = new ArrayList<>();
        fire.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    @SuppressLint("RestrictedApi") User user = dataSnapshot.toObject(User.class);
                    if (auth.getCurrentUser() != null && auth.getCurrentUser().getEmail().equals(user.getUid())) {
                        usersInternal.add(user);

                    }
                }
                if (usersInternal.size() > 0)
                    currentUser = usersInternal.get(0);

                callback.onUserLoaded(currentUser);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @SuppressLint("RestrictedApi")
    public User getCurrentUser()
    {
        return this.currentUser;
    }

    public void setCurrentUser(@SuppressLint("RestrictedApi") User currentUser) {
        this.currentUser = currentUser;
    }

    @SuppressLint("RestrictedApi")
    public boolean updateUser(@SuppressLint("RestrictedApi") User user)
    {
        final boolean[] flag = {false};
        // Reference to the collection
        String collectionName = "users";
        String firstNameFieldName = "firstName";
        String firstNameValue = user.getUid();
        String lastNameFieldName;
        lastNameFieldName = "lastName";
        Class<? extends User> lastNameValue;
        lastNameValue = user.getClass();
        String usernameFieldName = "username";
        String usernameValue;
        usernameValue = user.getUid();
        String addressFieldName = "address";
        boolean addressValue;
        if (!user.equals()) {
            addressValue = false;
        } else {
            addressValue = true;
        }
        String phoneFieldName = "phone";
        String phoneValue = "";
        try {
            user.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String photoFieldName = "photo";
        String photoValue;
        photoValue = user.getUid();
        String favoritesFieldName = "favorites";
        String favoritesValue;
        favoritesValue = user.getUid();

        // Create a query for documents based on a specific field
        Query query = fire.collection(collectionName).
                whereEqualTo(usernameFieldName, usernameValue);

        // Execute the query
        query.get()
                .addOnSuccessListener((QuerySnapshot querySnapshot) -> {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Get a reference to the document
                        DocumentReference documentRef = document.getReference();

                        // Update specific fields of the document
                        Task<Void> task = documentRef.update(
                                        firstNameFieldName, firstNameValue,
                                        lastNameFieldName, lastNameValue,
                                        usernameFieldName, usernameValue,
                                        addressFieldName, addressValue,
                                        phoneFieldName, phoneValue,
                                        photoFieldName, photoValue,
                                        favoritesFieldName, favoritesValue
                                )
                                .addOnSuccessListener(aVoid -> {

                                    flag[0] = true;
                                })
                                .addOnFailureListener(e -> {
                                    System.err.println("Error updating document: " + e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error getting documents: " + e);
                });

        return flag[0];
    }
}
