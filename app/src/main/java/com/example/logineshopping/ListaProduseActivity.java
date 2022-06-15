package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.logineshopping.adapter.ProdusAdapter;
import com.example.logineshopping.model.Produs;
import com.example.logineshopping.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaProduseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private ProdusAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDabaseRef;
    private DatabaseReference ref;
    private List<Produs> mProduse;
    private ImageView cont;
    private ImageView cos;
    private ImageView log_out;
    private ImageView adauga_produs;
    private ImageView profil;
    private String userID;
    private FirebaseUser mUser;
    private Button update_produs;
    private Button stergere_produs;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produse);
        cont = findViewById(R.id.Cont);
        cos = findViewById(R.id.Cart);
        log_out = findViewById(R.id.Logout);
        adauga_produs = findViewById(R.id.Adaugare);
        profil = findViewById(R.id.Profil);
        update_produs = findViewById(R.id.Update_produs);
        stergere_produs = findViewById(R.id.Stergere_produs);


        //update_produs.setVisibility(View.INVISIBLE);
        //stergere_produs.setVisibility(View.INVISIBLE);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Users");


        if (mUser != null) {
            //Toast.makeText(ListaProduseActivity.this, "Bine ati revenit!", Toast.LENGTH_SHORT).show();
            cos.setVisibility(View.VISIBLE);
            log_out.setVisibility(View.VISIBLE);
            adauga_produs.setVisibility(View.INVISIBLE);
            profil.setVisibility(View.VISIBLE);
            cont.setVisibility(View.INVISIBLE);

            userID = mUser.getUid();

            ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);
                    String veradmin = userProfile.admin;
                    if(veradmin.equals("1")) {
                        System.out.println("-----------------------------------------------------"+veradmin);
                        adauga_produs.setVisibility(View.VISIBLE);
                        //update_produs.setVisibility(View.VISIBLE);
                        //stergere_produs.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            cos.setVisibility(View.INVISIBLE);
            log_out.setVisibility(View.INVISIBLE);
            adauga_produs.setVisibility(View.INVISIBLE);
            profil.setVisibility(View.INVISIBLE);
            cont.setVisibility(View.VISIBLE);
        }
        //iau datele de la useri din baza de date


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProduseActivity.this, Login.class);
                ListaProduseActivity.this.startActivity(intent);
            }
        });

        adauga_produs.setOnClickListener(new View.OnClickListener() {
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
                    cos.setVisibility(View.INVISIBLE);
                    log_out.setVisibility(View.INVISIBLE);
                    adauga_produs.setVisibility(View.INVISIBLE);
                    profil.setVisibility(View.INVISIBLE);
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



        mRecyclerview = findViewById(R.id.recycle_view);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mProduse = new ArrayList<>();
        mDabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");

        mDabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Produs produs = postSnapshot.getValue(Produs.class);
                    mProduse.add(produs);
                }

                mAdapter = new ProdusAdapter(ListaProduseActivity.this, mProduse);
                mRecyclerview.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListaProduseActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


    }

    public void refreshActivity(){
        recreate();
    }
}