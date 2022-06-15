package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.logineshopping.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilUtilizator extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference referance;
    private Button editare;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_utilizator);

        editare = (Button) findViewById(R.id.button4);
        user = FirebaseAuth.getInstance().getCurrentUser();
        referance = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView nume = (TextView) findViewById(R.id.textView4);
        final TextView email = (TextView) findViewById(R.id.textView8);
        final TextView adresa = (TextView) findViewById(R.id.textView9);
        final TextView telefon = (TextView) findViewById(R.id.textView10);

        referance.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null)
                {
                    String n = userProfile.nume;
                    String e = userProfile.email;
                    String a = userProfile.adresa;
                    String t = userProfile.numar_telefon;

                    nume.setText(n);
                    email.setText(e);
                    adresa.setText(a);
                    telefon.setText(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilUtilizator.this, UpdateProfilUtilizator.class);
                ProfilUtilizator.this.startActivity(intent);
            }
        });



    }
}