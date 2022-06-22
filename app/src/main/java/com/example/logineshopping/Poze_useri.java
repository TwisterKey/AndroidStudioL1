package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poze_useri);

        refprod = FirebaseDatabase.getInstance().getReference().child("Uploads");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
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
}