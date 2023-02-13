package com.project.trip.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.project.trip.application.controller.OfferesAdapter;
import com.project.trip.application.controller.OffersModel;
import com.project.trip.application.controller.TripAdapter;
import com.project.trip.application.model.AddTripModel;

import java.util.ArrayList;
import java.util.List;


public class OfferesActivity extends AppCompatActivity {

    DatabaseReference ref;
    private List<OffersModel> offersModels ;
    private RecyclerView mRecyclerView;
    private OfferesAdapter madapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offeres);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        offersModels = new ArrayList<>();


        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("offers");

        madapter = new OfferesAdapter(OfferesActivity.this, offersModels);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OfferesActivity.this));
        mRecyclerView.setAdapter(madapter);
        madapter.notifyDataSetChanged();

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                offersModels.clear();

//                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
//                    AddTripModel upload = Snapshot.getValue(AddTripModel.class);
//                    upload.setKey(Snapshot.getKey());
//                    tripList.add(upload);
//                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    OffersModel model = postSnapshot.getValue(OffersModel.class);
                    offersModels.add(model);
                }

                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OfferesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });




    }


}
