package com.project.trip.application;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.project.trip.application.controller.TripAdapter;
import com.project.trip.application.model.AddTripModel;
import com.project.trip.application.model.TripsModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BookActivity extends AppCompatActivity {

    DatabaseReference ref;
    private List<AddTripModel> tripList ;
    private RecyclerView mRecyclerView;
    private TripAdapter madapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        tripList = new ArrayList<>();


        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("add_trip");

        madapter = new TripAdapter(BookActivity.this, tripList,"");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(BookActivity.this));
        mRecyclerView.setAdapter(madapter);
        madapter.notifyDataSetChanged();

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tripList.clear();

//                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
//                    AddTripModel upload = Snapshot.getValue(AddTripModel.class);
//                    upload.setKey(Snapshot.getKey());
//                    tripList.add(upload);
//                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AddTripModel model = postSnapshot.getValue(AddTripModel.class);
                    tripList.add(model);
                }

                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BookActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });




    }


}