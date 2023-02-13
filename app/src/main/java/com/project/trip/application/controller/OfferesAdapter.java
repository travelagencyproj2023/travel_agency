package com.project.trip.application.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.project.trip.application.PaymentActivity;
import com.project.trip.application.R;
import com.project.trip.application.model.AddTripModel;

import java.io.IOException;
import java.util.List;



        import android.app.AlertDialog;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.util.Base64;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.project.trip.application.MainActivity;
        import com.project.trip.application.MoreActivity;
        import com.project.trip.application.PaymentActivity;
        import com.project.trip.application.R;
        import com.project.trip.application.model.AddTripModel;
        import com.project.trip.application.model.TripsModel;
        import com.squareup.picasso.Picasso;

        import java.io.IOException;
        import java.util.List;

public class OfferesAdapter extends RecyclerView.Adapter<OfferesAdapter.StudentHolder> {
    private Context context;
    private List<OffersModel> offerssList;
    DatabaseReference reference;
    FirebaseUser fuser;
    //StorageReference storageReference;

    public OfferesAdapter(Context context, List<OffersModel> list) {
        this.context = context;
        this.offerssList = list;
    }

    @Override
    public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new StudentHolder(row);
    }

    @Override
    public void onBindViewHolder(StudentHolder holder, int position) {
        final OffersModel model = offerssList.get(position);

        holder.title.setText(model.getTitle());
        //gs://trip-app-69906.appspot.com/add_trip/1675478542126.jpg
        try {
            holder.imageTrip.setImageBitmap(decodeFromFirebaseBase64(model.getImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.btn.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return offerssList == null ? 0 : offerssList.size() ;
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        private TextView title ;
        ImageView imageTrip;
        Button btn;

        public StudentHolder(View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.title);
            imageTrip =itemView.findViewById(R.id.imageTrip);
            btn =itemView.findViewById(R.id.btn);

        }
    }

    public Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
