package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.logineshopping.adapter.ProdusAdapter;
import com.example.logineshopping.model.Produs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Obiective_apropiate extends AppCompatActivity {
    private GpsTracker gpsTracker;
    private DatabaseReference refprod;
    private RecyclerView mRecyclerview;
    private List<Produs> mProduse;
    private ProdusAdapter mAdapter;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obiective_apropiate);
        refprod = FirebaseDatabase.getInstance().getReference().child("Uploads");
        mRecyclerview = findViewById(R.id.recycle1);

        refprod.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gpsTracker = new GpsTracker(Obiective_apropiate.this);
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                mRecyclerview.setHasFixedSize(true);
                mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                mProduse = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (gpsTracker.canGetLocation()) {
                        float[] results = new float[1];
                        String latp = postSnapshot.child("latitudine").getValue().toString();
                        String longp = postSnapshot.child("longitudine").getValue().toString();
                        Location.distanceBetween(latitude, longitude, Double.parseDouble(latp), Double.parseDouble(longp), results);
                        float distantametrii = results[0];
                        boolean ok = distantametrii < 1000;
                        if (ok) {
                            Produs produs = postSnapshot.getValue(Produs.class);
                            mProduse.add(produs);
                            mAdapter = new ProdusAdapter(Obiective_apropiate.this, mProduse);
                            mRecyclerview.setAdapter(mAdapter);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}