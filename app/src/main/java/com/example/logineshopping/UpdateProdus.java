package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logineshopping.model.Produs;
import com.example.logineshopping.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UpdateProdus extends AppCompatActivity {
    private String id;
    private EditText nume_fisier;
    private Button meniu;
    private EditText descriere_produs;
    private Button Update;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_produs);
        ViewSetup();

        Update = findViewById(R.id.incarca_fisier);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }

        //System.out.println(id + "111111111111111111111111111111111111111111");

        mDatabaseRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Produs produs = snapshot.getValue(Produs.class);
                if (produs != null) {
                    String n = produs.getName();
                    String a = produs.getDescriere();

                    nume_fisier.setText(n);
                    descriere_produs.setText(a);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        alegeImagine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, pick_image_request);
//            }
//        });

        String _nume, _descriere, _pret;
        _nume = nume_fisier.getText().toString();
        _descriere = descriere_produs.getText().toString();

        meniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProdus.this, ListaProduseActivity.class);
                UpdateProdus.this.startActivity(intent);
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!_nume.equals(nume_fisier.getText().toString()))
                    mDatabaseRef.child(id).child("name").setValue(nume_fisier.getText().toString());

                if (!_descriere.equals(descriere_produs.getText().toString()))
                    mDatabaseRef.child(id).child("descriere").setValue(descriere_produs.getText().toString());
                Intent intent = new Intent(UpdateProdus.this, ListaProduseActivity.class);
                UpdateProdus.this.startActivity(intent);
            }
        });
    }


    private void ViewSetup() {
        nume_fisier = findViewById(R.id.nume_fisier);
        meniu = findViewById(R.id.button3);
        descriere_produs = findViewById(R.id.descriere_produs);
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getId(){ return id;}

    public void onBackPressed() {
        finish();
    }
}