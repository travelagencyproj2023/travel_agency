package com.project.trip.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MoreActivity extends AppCompatActivity {

    CardView cv_profile,prv,payment,call,loginout,cv_addTrip,cv_addoffers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        cv_profile = findViewById(R.id.cv_profile);
        prv = findViewById(R.id.prv);
        payment = findViewById(R.id.payment);
        call = findViewById(R.id.call);
        loginout = findViewById(R.id.loginout);
        cv_addTrip = findViewById(R.id.cv_addTrip);
        cv_addoffers = findViewById(R.id.cv_addoffers);

        cv_addoffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddOffersActivity.class);
                startActivity(intent);

            }
        });
        cv_addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTripActivity.class);
                startActivity(intent);

            }
        });
        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);

            }
        });
        prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivacyActivity.class);
                startActivity(intent);

            }
        });
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDialog
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MoreActivity.this);
                builder1.setMessage("يرجى التواصل معنا عبر : 11111111111");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "إتصال",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder1.show();

            }
        });
    }
}