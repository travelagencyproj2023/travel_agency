package com.project.trip.application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.ContentResolver;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
        import android.webkit.MimeTypeMap;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.StorageTask;
        import com.google.firebase.storage.UploadTask;
import com.project.trip.application.model.AddTripModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddTripActivity extends AppCompatActivity {

    private EditText titleID ;
    ImageView imageTrip;
    Button btn_add;
    private ProgressBar uploadProgressBar;
    FirebaseUser fuser;

    private Uri mImageUri;
    Bitmap bitmap;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private static final int PICK_IMAGE_REQUEST = 1;

    private StorageTask mUploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);


        titleID =findViewById(R.id.titleID);
        btn_add =findViewById(R.id.btn_add);
        imageTrip =findViewById(R.id.img_trip);
        uploadProgressBar =findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("add_trip");

        imageTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AddTripActivity.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            try {
                Bitmap mbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                bitmap = mbitmap;
                imageTrip.setImageBitmap(mbitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null) {
//            mImageUri = data.getData();
//
//            Picasso.with(this).load(mImageUri).into(imageTrip);
//        }
//    }
//
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        //checking if file is available
        if (mImageUri != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = mStorageRef.child("add_trip/" + System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            //adding the file to reference
            sRef.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
                            //AddTripModel model = new AddTripModel(titleID.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());
                            AddTripModel model = new AddTripModel(titleID.getText().toString().trim(), encodeBitmapAndSaveToFirebase(bitmap));

                            //adding an upload to firebase database
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(model);

                            openImagesActivity();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }
//
//    private void uploadFile() {
//        if (mImageUri != null) {
//            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
//                    + "." + getFileExtension(mImageUri));
//
//
//            uploadProgressBar.setVisibility(View.VISIBLE);
//            uploadProgressBar.setIndeterminate(true);
//
//            mUploadTask = fileReference.putFile(mImageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    uploadProgressBar.setVisibility(View.VISIBLE);
//                                    uploadProgressBar.setIndeterminate(false);
//                                    uploadProgressBar.setProgress(0);
//                                }
//                            }, 500);
//
//                            fuser = FirebaseAuth.getInstance().getCurrentUser();
//
//                            AddTripModel model = new AddTripModel(
//                                    titleID.getText().toString().trim(),
//                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()
//                            );
//
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(model);
//
//                            uploadProgressBar.setVisibility(View.INVISIBLE);
//                            Toast.makeText(AddTripActivity.this, " تمت العملية بنجاح", Toast.LENGTH_LONG).show();
//
//                            openImagesActivity ();
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            uploadProgressBar.setVisibility(View.INVISIBLE);
//                            Toast.makeText(AddTripActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            uploadProgressBar.setProgress((int) progress);
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT).show();
//        }
//    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}