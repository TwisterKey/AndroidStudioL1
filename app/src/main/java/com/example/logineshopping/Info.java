package com.example.logineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Info extends AppCompatActivity {
    private ImageView cont;
    private ImageView galerie;
    private ImageView log_out;
    private ImageView locatie_aproape;
    private ImageView adauga_view;
    private ImageView profil;
    private ImageView help;
    private ImageView galIndivid;
    private FirebaseUser mUser;
    private TextView informatii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        tool();
        toolbar();
        verif();
        informatii = findViewById(R.id.textView12);

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
            help.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(Info.this, IncarcareProdusActivity.class);
                Info.this.startActivity(intent);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Info.this, "V-ati delogat cu succes", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(Info.this, ProfilUtilizator.class);
                Info.this.startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, Info.class);
                Info.this.startActivity(intent);
            }
        });
        galerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, ListaProduseActivity.class);
                Info.this.startActivity(intent);
            }
        });
        locatie_aproape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, Obiective_apropiate.class);
                Info.this.startActivity(intent);
            }
        });
        galIndivid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, Poze_useri.class);
                Info.this.startActivity(intent);
            }
        });
    }
}