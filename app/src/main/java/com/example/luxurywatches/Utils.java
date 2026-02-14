package com.example.luxurywatches;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Utils {

    private static Utils instance;
    private FirebaseServices fbs;

    private Utils() {
        fbs = FirebaseServices.getInstance();
    }

    // Singleton
    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    // Show message dialog
    public void showMessageDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Message");
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    // Upload image to Firebase Storage
    public void uploadImage(Context context, Uri selectedImageUri) {
        if (selectedImageUri == null) {
            Toast.makeText(context, "Please choose an image first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique image name
        String imageName = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef =
                fbs.getStorage().getReference().child("images/" + imageName);

        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imageRef.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        fbs.setSelectedImageURL(uri);
                                        Toast.makeText(context,
                                                "Image uploaded successfully",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Utils", "Upload failed: " + e.getMessage());
                        Toast.makeText(context,
                                "Failed to upload image",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
