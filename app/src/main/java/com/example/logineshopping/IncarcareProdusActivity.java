package com.example.logineshopping;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


//import com.google.android.gms.location.LocationRequest;

import com.example.logineshopping.model.Produs;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.security.SignatureSpi;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class IncarcareProdusActivity extends AppCompatActivity {
    private static final int camera_request_code = 1;

    private Button alegeImagine;
    private Button incarcaImaginea;
    private TextView arata_incarcatele;
    private EditText nume_fisier;
    private ImageView imageview;
    private ProgressBar progressbar;
    private Button meniu;
    private EditText descriere_produs;
    private EditText pret_prdus;
    private FirebaseUser mUser;


    private Uri imagineuri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private GpsTracker gpsTracker;
    private TextView t1;
    private TextView t2;
    private Geocoder geocoder;
    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incarcare_produs);

        ViewSetup();

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        geocoder = new Geocoder(this, Locale.getDefault());




//        if(ContextCompat.checkSelfPermission(IncarcareProdusActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(IncarcareProdusActivity.this, new String[]{Manifest.permission.CAMERA}, camera_request_code);
//        }


        alegeImagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(ContextCompat.checkSelfPermission(IncarcareProdusActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(IncarcareProdusActivity.this, new String[]{Manifest.permission.CAMERA}, camera_request_code);
//                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, camera_request_code);
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
                try {
                    getLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                uploadFile();
                getTime();
            }
        });
    }

    public void getTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(" d MMM yyyy HH:mm:ss ");
        String time =  format.format(calendar.getTime());
    }


    public void getLocation() throws IOException {
        gpsTracker = new GpsTracker(IncarcareProdusActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            //t1.setText(addresses.get(0).getAddressLine(0));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }


    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

//        if (requestcode == camera_request_code) {
//            if (requestcode == camera_request_code && resultcode == RESULT_OK && data != null && data.getData() != null) {
//            imagineuri = data.getData();
//            imageview.setImageURI(imagineuri);
//
//            Picasso.with(this).load(imagineuri).into(imageview);

            if(requestcode == camera_request_code && resultcode == RESULT_OK){
                imagineuri = data.getData();
                //imageview.setImageURI(imagineuri);
                Picasso.with(this).load(imagineuri).into(imageview);

//                CropImage.activity(mImageUri)
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setAspectRatio(1,1)
//                        .start(this);




        /* Bitmap mImageUri1 = (Bitmap) data.getExtras().get("data");
         mSelectImage.setImageBitmap(mImageUri1);

          Toast.makeText(this, "Image saved to:\n" +
                  data.getExtras().get("data"), Toast.LENGTH_LONG).show();


*/
                }

//                if (requestcode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                    if (resultCode == RESULT_OK) {
//                        Uri resultUri = result.getUri();
//
//                        imageview.setImageURI(resultUri);
//                        imagineuri = resultUri;
//
//                    } else if (resultcode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                        Exception error = result.getError();
//                    }
//                }

    }

    private void ViewSetup() {
        alegeImagine = findViewById(R.id.alege_fisier);
        incarcaImaginea = findViewById(R.id.incarca_fisier);
        nume_fisier = findViewById(R.id.nume_fisier);
        imageview = findViewById(R.id.imageView);
        progressbar = findViewById(R.id.progressBar);
        meniu = findViewById(R.id.button3);
        descriere_produs = findViewById(R.id.descriere_produs);
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
                                    double latitude = gpsTracker.getLatitude();
                                    double longitude = gpsTracker.getLongitude();
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat format = new SimpleDateFormat(" d MMM yyyy HH:mm:ss ");
                                    String time =  format.format(calendar.getTime());
                                    mUser = FirebaseAuth.getInstance().getCurrentUser();
                                    Toast.makeText(IncarcareProdusActivity.this, "Succes", Toast.LENGTH_SHORT).show();

                                    try {
                                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    String adresa = addresses.get(0).getAddressLine(0);

                                    Produs produs = new Produs(uri.toString(), nume_fisier.getText().toString().trim(), descriere_produs.getText().toString().trim(), mUser.getUid(), adresa, time, String.valueOf(latitude), String.valueOf(longitude));
                                    String uploadId = mDatabaseRef.push().getKey(); // alta intrare in baza de date cu un id unic
                                    produs.setId(uploadId);
                                    mDatabaseRef.child(uploadId).setValue(produs);
                                    mDatabaseRef.child(uploadId).child("");
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