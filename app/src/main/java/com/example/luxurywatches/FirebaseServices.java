package com.example.luxurywatches;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseServices {

    private static FirebaseServices instance;

    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    private FirebaseStorage storage;

    private Uri selectedImageURL;
    private User currentUser;

    public interface UserCallback {
        void onUserLoaded(User user);
    }

    private FirebaseServices() {
        auth = FirebaseAuth.getInstance();
        fire = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        selectedImageURL = null;
    }

    public static FirebaseServices getInstance() {
        if (instance == null)
            instance = new FirebaseServices();
        return instance;
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

    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }

    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    // Load user from Firestore
    public void loadCurrentUser(UserCallback callback) {
        if (auth.getCurrentUser() == null) {
            callback.onUserLoaded(null);
            return;
        }

        String email = auth.getCurrentUser().getEmail();

        fire.collection("users")
                .whereEqualTo("username", email)
                .get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        currentUser = query.getDocuments().get(0).toObject(User.class);
                        callback.onUserLoaded(currentUser);
                    } else {
                        callback.onUserLoaded(null);
                    }
                })
                .addOnFailureListener(e -> callback.onUserLoaded(null));
    }

    public User getCurrentUser() {
        return currentUser;
    }


    public void updateUser(User user, OnSuccessListener<Void> success, OnFailureListener failure) {
        if (user.getUsername() == null) {
            failure.onFailure(new Exception("User has no username"));
            return;
        }

        Task<Void> users = fire.collection("users")
                .document(user.getUsername())
                .set(user);
    ;}
    }