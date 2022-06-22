package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
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

import java.util.ArrayList;
import java.util.List;

public class Poze_useri extends AppCompatActivity {
    private GpsTracker gpsTracker;
    private DatabaseReference refprod;
    private RecyclerView mRecyclerview;
    private List<Produs> mProduse;
    private ProdusAdapter mAdapter;
    private Context context;
    private FirebaseUser mUser;
    private ImageView cont;
    private ImageView galerie;
    private ImageView log_out;
    private ImageView locatie_aproape;
    private ImageView adauga_view;
    private ImageView profil;
    private ImageView help;
    private ImageView galIndivid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poze_useri);
        tool();
        toolbar();
        refprod = FirebaseDatabase.getInstance().getReference().child("Uploads");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        verif();
        mRecyclerview = findViewById(R.id.recycle2);

        refprod.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRecyclerview.setHasFixedSize(true);
                mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                mProduse = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    boolean ok = mUser.getUid().equals(postSnapshot.child("idpersoana").getValue());
                        if (ok) {
                            Produs produs = postSnapshot.getValue(Produs.class);
                            mProduse.add(produs);
                            mAdapter = new ProdusAdapter(Poze_useri.this, mProduse);
                            mRecyclerview.setAdapter(mAdapter);

                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
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

    public void toolbar(){
        adauga_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Poze_useri.this, IncarcareProdusActivity.class);
                Poze_useri.this.startActivity(intent);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Poze_useri.this, "V-ati delogat cu succes", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(Poze_useri.this, ProfilUtilizator.class);
                Poze_useri.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Poze_useri.this, Obiective_apropiate.class);
                Poze_useri.this.startActivity(intent);
            }
        });
        galerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Poze_useri.this, ListaProduseActivity.class);
                Poze_useri.this.startActivity(intent);
            }
        });
        locatie_aproape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Poze_useri.this, Obiective_apropiate.class);
                Poze_useri.this.startActivity(intent);
            }
        });
        galIndivid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Poze_useri.this, Poze_useri.class);
                Poze_useri.this.startActivity(intent);
            }
        });
    }
}