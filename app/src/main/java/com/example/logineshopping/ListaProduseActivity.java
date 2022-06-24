package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.logineshopping.adapter.ProdusAdapter;
import com.example.logineshopping.model.Produs;
import com.example.logineshopping.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaProduseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private ProdusAdapter mAdapter;
    private DatabaseReference mDabaseRef;
    private List<Produs> mProduse;
    private ImageView cont;
    private ImageView galerie;
    private ImageView log_out;
    private ImageView locatie_aproape;
    private ImageView adauga_view;
    private ImageView profil;
    private ImageView help;
    private ImageView galIndivid;
    private FirebaseUser mUser;
    private SearchView search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produse);
        cont = findViewById(R.id.Cont);
        galerie = findViewById(R.id.Galerie);
        log_out = findViewById(R.id.Logout);
        adauga_view = findViewById(R.id.Adaugare);
        profil = findViewById(R.id.Profil);
        help = findViewById(R.id.Help);
        locatie_aproape = findViewById(R.id.Locuri_apropiate);
        galIndivid = findViewById(R.id.Galerie_proprie);


        search = findViewById(R.id.searchView);
        search.clearFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

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
        //iau datele de la useri din baza de date


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProduseActivity.this, Login.class);
                ListaProduseActivity.this.startActivity(intent);
            }
        });
        toolbar();
        mDabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");


        mRecyclerview = findViewById(R.id.recycle_view);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mProduse = new ArrayList<>();

        mDabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Produs produs = postSnapshot.getValue(Produs.class);
                    mProduse.add(produs);
                }

                mAdapter = new ProdusAdapter(ListaProduseActivity.this, mProduse);
                mRecyclerview.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListaProduseActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void filterList(String text) {
        List<Produs> filteredList = new ArrayList<>();
        for(Produs item : mProduse){
            if(item.getLocatie().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty())
            System.out.println("Nu s-a gasit nimic");
        else{
            mAdapter.setFilteredList(filteredList);
        }
    }

    public void toolbar(){
        adauga_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProduseActivity.this, IncarcareProdusActivity.class);
                ListaProduseActivity.this.startActivity(intent);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ListaProduseActivity.this, "V-ati delogat cu succes", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(ListaProduseActivity.this, ProfilUtilizator.class);
                ListaProduseActivity.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProduseActivity.this, Info.class);
                ListaProduseActivity.this.startActivity(intent);
            }
        });
        galerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProduseActivity.this, ListaProduseActivity.class);
                ListaProduseActivity.this.startActivity(intent);
            }
        });
        locatie_aproape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProduseActivity.this, Obiective_apropiate.class);
                ListaProduseActivity.this.startActivity(intent);
            }
        });
        galIndivid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProduseActivity.this, Poze_useri.class);
                ListaProduseActivity.this.startActivity(intent);
            }
        });
    }



    public void refreshActivity(){
        recreate();
    }
}