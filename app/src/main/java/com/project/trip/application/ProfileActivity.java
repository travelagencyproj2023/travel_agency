package com.project.trip.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    EditText username ,email,password,address;
    Button btn;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView img_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        img_profile =findViewById(R.id.img_profile);
        btn = findViewById(R.id.btn);

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ProfileActivity.this, "تمت العملية بنجاح", Toast.LENGTH_SHORT).show();
            }
        });

        username.setText(getData("username").toString());
        email.setText(getData("email").toString());
        password.setText(getData("password").toString());
        address.setText(getData("address").toString());
    }

    String getData(String key ){

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        return sh.getString(key, "");
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(img_profile);
        }
    }
}