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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private ImageView cont;
    private ImageView galerie;
    private ImageView log_out;
    private ImageView locatie_aproape;
    private ImageView adauga_view;
    private ImageView profil;
    private ImageView help;
    private ImageView galIndivid;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obiective_apropiate);
        refprod = FirebaseDatabase.getInstance().getReference().child("Uploads");
        mRecyclerview = findViewById(R.id.recycle1);
        tool();
        toolbar();
        verif();
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
    public void tool(){
        cont = findViewById(R.id.Cont);
        galerie = findViewById(R.id.Galerie);
        log_out = findViewById(R.id.Logout);
        adauga_view = findViewById(R.id.Adaugare);
        profil = findViewById(R.id.Profil);
        help = findViewById(R.id.Help);
        locatie_aproape = findViewById(R.id.Locuri_apropiate);
        galIndivid = findViewById(R.id.Galerie_proprie);

    }

    public void verif(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            //Toast.makeText(ListaProduseActivity.this, "Bine ati revenit!", Toast.LENGTH_SHORT).show();
            galerie.setVisibility(View.VISIBLE);
            log_out.setVisibility(View.VISIBLE);
            adauga_view.setVisibility(View.VISIBLE);
            profil.setVisibility(View.VISIBLE);
            cont.setVisibility(View.INVISIBLE);
            locatie_aproape.setVisibility(View.VISIBLE);
            galIndivid.setVisibility(View.VISIBLE);
        } else {
            log_out.setVisibility(View.GONE);
            adauga_view.setVisibility(View.GONE);
            profil.setVisibility(View.GONE);
            cont.setVisibility(View.VISIBLE);
            galIndivid.setVisibility(View.GONE);
        }
    }

    public void toolbar(){
        adauga_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Obiective_apropiate.this, IncarcareProdusActivity.class);
                Obiective_apropiate.this.startActivity(intent);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Obiective_apropiate.this, "V-ati delogat cu succes", Toast.LENGTH_SHORT).show();
                galIndivid.setVisibility(View.GONE);
                log_out.setVisibility(View.GONE);
                adauga_view.setVisibility(View.GONE);
                profil.setVisibility(View.GONE);
                cont.setVisibility(View.VISIBLE);
                finish();
                startActivity(getIntent());
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Obiective_apropiate.this, ProfilUtilizator.class);
                Obiective_apropiate.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Obiective_apropiate.this, Info.class);
                Obiective_apropiate.this.startActivity(intent);
            }
        });
        galerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Obiective_apropiate.this, ListaProduseActivity.class);
                Obiective_apropiate.this.startActivity(intent);
            }
        });
        locatie_aproape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Obiective_apropiate.this, Obiective_apropiate.class);
                Obiective_apropiate.this.startActivity(intent);
            }
        });
        galIndivid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Obiective_apropiate.this, Poze_useri.class);
                Obiective_apropiate.this.startActivity(intent);
            }
        });
    }
}