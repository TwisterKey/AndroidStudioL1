package com.example.logineshopping;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logineshopping.model.Produs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class IncarcareProdusActivity extends AppCompatActivity {
    private static final int pick_image_request = 1;

    private Button alegeImagine;
    private Button incarcaImaginea;
    private TextView arata_incarcatele;
    private EditText nume_fisier;
    private ImageView imageview;
    private ProgressBar progressbar;
    private Button meniu;
    private EditText descriere_produs;
    private EditText pret_prdus;

    private Uri imagineuri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incarcare_produs);

        ViewSetup();

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");


        alegeImagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, pick_image_request);
            }
        });

        meniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncarcareProdusActivity.this, ListaProduseActivity.class);
                IncarcareProdusActivity.this.startActivity(intent);
            }
        });

        incarcaImaginea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        if (requestcode == pick_image_request && resultcode == RESULT_OK && data != null && data.getData() != null) {
            imagineuri = data.getData();

            Picasso.with(this).load(imagineuri).into(imageview);
        }
    }

    private void ViewSetup() {
        alegeImagine = findViewById(R.id.alege_fisier);
        incarcaImaginea = findViewById(R.id.incarca_fisier);
        nume_fisier = findViewById(R.id.nume_fisier);
        imageview = findViewById(R.id.imageView);
        progressbar = findViewById(R.id.progressBar);
        meniu = findViewById(R.id.button3);
        descriere_produs = findViewById(R.id.descriere_produs);
        pret_prdus = findViewById(R.id.pret_produs);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    void uploadFile() {
        if (imagineuri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imagineuri));

            fileReference.putFile(imagineuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(IncarcareProdusActivity.this, "Succes", Toast.LENGTH_SHORT).show();

                                    Produs produs = new Produs(uri.toString(), nume_fisier.getText().toString().trim(), descriere_produs.getText().toString().trim(), pret_prdus.getText().toString());
                                    String uploadId = mDatabaseRef.push().getKey(); // alta intrare in baza de date cu un id unic
                                    produs.setId(uploadId);
                                    mDatabaseRef.child(uploadId).setValue(produs);
                                    //String key = mDatabaseRef.child("Users").push().getKey();

                                }
                            });

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressbar.setProgress(0);
                                }
                            }, 5000); // da delay la progressbar pentru 5 secunde


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(IncarcareProdusActivity.this, "Eroare trimitere", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressbar.setProgress((int) progress);
                        } //cum se incarca bara de progres
                    });
        } else {
            Toast.makeText(this, "Nu ati selectat fisier", Toast.LENGTH_SHORT).show();
        }
    }
}