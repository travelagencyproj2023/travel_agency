package com.project.trip.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import java.util.List;

public class MyBookedActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_my_booked);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        tripList = new ArrayList<>();


        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("my_booked");

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
                madapter = new TripAdapter(MyBookedActivity.this, tripList,"booked");
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MyBookedActivity.this));
                mRecyclerView.setAdapter(madapter);
                madapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MyBookedActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });




    }


}