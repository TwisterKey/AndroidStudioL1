package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private ImageAdapter mAdapter;

    private DatabaseReference mDabaseRef;
    private List<Produs> mProduses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        mRecyclerview=findViewById(R.id.recycle_view);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mProduses = new ArrayList<>();
        mDabaseRef= FirebaseDatabase.getInstance().getReference("Uploads");
        mDabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProduses.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Produs produs = postSnapshot.getValue(Produs.class);
                    mProduses.add(produs);
                    Log.i("oooooooooooooooooo", produs.getImageUrl() + " " + produs.getName());
                }
                mAdapter = new ImageAdapter(Lista.this, mProduses);
                mRecyclerview.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Lista.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}