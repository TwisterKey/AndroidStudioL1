package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logineshopping.model.Produs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.logineshopping.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private String id;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }
        gpsTracker = new GpsTracker(MapsActivity.this);
        double tl = gpsTracker.getLatitude();
        double tL = gpsTracker.getLongitude();
        LatLng curenta = new LatLng(tl, tL);
        mMap.addMarker(new MarkerOptions().position(curenta).title("Aici e pozitia ta"));
        mDatabaseRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Produs produs = snapshot.getValue(Produs.class);
                if (produs != null) {
                    String latitudine = produs.getLatitudine();
                    String longitudine = produs.getLongitudine();
                    double l = Double.parseDouble(latitudine);
                    double L = Double.parseDouble(longitudine);
                    float[] results = new float[1];
                    Location.distanceBetween(tl, tL, l, L, results);
                    float d = results[0];
                    @SuppressLint("DefaultLocale") String numar = String.format("%.2f", d);
                    String text = "Te afli la aproximativ "+ numar+" m de locatia curenta";
                    Toast.makeText(MapsActivity.this, text, Toast.LENGTH_LONG).show();

                    LatLng sydney = new LatLng(l, L);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Aici a fost facuta poza"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15), 5000, null);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Add a marker in Sydney and move the camera
    }
}