package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logineshopping.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class UpdateProfilUtilizator extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private EditText editNume;
    private EditText editAdresa;
    private EditText editTelefon;
    private Button Update;
    private String userID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private Button galerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil_utilizator);

        editNume = findViewById(R.id.editTextNume);
        editAdresa = findViewById(R.id.editTextTextAdresa);
        editTelefon = findViewById(R.id.editTextTextPersonTelefon);
        Update = findViewById(R.id.updateb);
        galerie = findViewById(R.id.button6);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userID);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null)
                {
                    String n = userProfile.nume;
                    String a = userProfile.adresa;
                    String t = userProfile.numar_telefon;

                    editNume.setText(n);
                    editAdresa.setText(a);
                    editTelefon.setText(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String _nume, _adresa, _telefon;
        _nume = editNume.getText().toString();
        _adresa = editAdresa.getText().toString();
        _telefon = editTelefon.getText().toString();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!_nume.equals(editNume.getText().toString()))
                    reference.child(userID).child("nume").setValue(editNume.getText().toString());

                if(!_adresa.equals(editAdresa.getText().toString()))
                    reference.child(userID).child("adresa").setValue(editAdresa.getText().toString());

                if(!_telefon.equals(editTelefon.getText().toString()))
                    reference.child(userID).child("numar_telefon").setValue(editTelefon.getText().toString());


                Intent intent = new Intent(UpdateProfilUtilizator.this, ListaProduseActivity.class);
                UpdateProfilUtilizator.this.startActivity(intent);
            }
        });
        galerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfilUtilizator.this, ListaProduseActivity.class);
                UpdateProfilUtilizator.this.startActivity(intent);
            }
        });

    }
}