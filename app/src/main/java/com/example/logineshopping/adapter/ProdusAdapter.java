package com.example.logineshopping.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.logineshopping.GpsTracker;
import com.example.logineshopping.IncarcareProdusActivity;
import com.example.logineshopping.ListaProduseActivity;
import com.example.logineshopping.MapsActivity;
import com.example.logineshopping.ProfilUtilizator;
import com.example.logineshopping.R;
import com.example.logineshopping.UpdateProdus;
import com.example.logineshopping.model.Produs;
import com.example.logineshopping.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.logging.ConsoleHandler;

public class ProdusAdapter extends RecyclerView.Adapter<ProdusAdapter.ProdusViewHolder> {
    private Context context;
    private List<Produs> produsList;
    private DatabaseReference ref;
    private DatabaseReference refprod;
    private FirebaseUser mUser;
    private String userID;
    private FirebaseFirestore firestore;





    public ProdusAdapter(Context context, List<Produs> produsList) {
        this.context = context;
        this.produsList = produsList;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ProdusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.produs_item, parent, false);

        return new ProdusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdusViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Produs produsCurent = produsList.get(position);
        holder.textViewNumeProdus.setText(produsCurent.getName());
        holder.descriere.setText(produsCurent.getDescriere());
        holder.locatie.setText(produsCurent.getLocatie());
        holder.timp.setText(produsCurent.getTimp());
        Picasso.with(context)
                .load(produsCurent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageViewImagineProdus);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(produsCurent.getIdpersoana().equals(postSnapshot.child("id").getValue()))
                    {
                        holder.numepersoana.setText("Persoana care a incarcat: "+postSnapshot.child("nume").getValue().toString());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }});

        if(mUser!= null && produsCurent.getIdpersoana().equals(mUser.getUid())){
            holder.update_produs.setVisibility(View.VISIBLE);
            holder.stergere_produs.setVisibility(View.VISIBLE);
        }
        else{
            holder.update_produs.setVisibility(View.GONE);
            holder.stergere_produs.setVisibility(View.GONE);
        }




        holder.stergere_produs.setTag(produsCurent.getId());
    }

    @Override
    public int getItemCount() {
        return produsList.size();
    }


    public class ProdusViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNumeProdus;
        public ImageView imageViewImagineProdus;
        public TextView descriere;
        public TextView locatie;
        public Button stergere_produs;
        public Button update_produs;
        private TextView timp;
        private TextView numepersoana;

        public ProdusViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNumeProdus = itemView.findViewById(R.id.text_view_numeProdus);
            imageViewImagineProdus = itemView.findViewById(R.id.image_view_imagineProdus);
            descriere = itemView.findViewById(R.id.text_view_descriereprodus);
            locatie = itemView.findViewById(R.id.text_view_locatie);
            timp = itemView.findViewById(R.id.text_view_timp);
            numepersoana = itemView.findViewById(R.id.text_view_numepersoana);

            stergere_produs = itemView.findViewById(R.id.Stergere_produs);
            update_produs = itemView.findViewById(R.id.Update_produs);

            mUser = FirebaseAuth.getInstance().getCurrentUser();
            ref = FirebaseDatabase.getInstance().getReference("Users");
            refprod = FirebaseDatabase.getInstance().getReference().child("Uploads");

            stergere_produs.setOnClickListener(new View.OnClickListener() {
                String str;
                @Override
                public void onClick(View v) {
                    refprod.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                if(stergere_produs.getTag().toString()==postSnapshot.child("id").getValue().toString())
                                {
                                    postSnapshot.getRef().removeValue();
                                    ((ListaProduseActivity)context).refreshActivity();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }});
                }
            });

            update_produs.setOnClickListener(new View.OnClickListener() {
                String str;
                @Override
                public void onClick(View v) {
                    refprod.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                if(stergere_produs.getTag().toString()==postSnapshot.child("id").getValue().toString())
                                {
                                    UpdateProdus updateProdus = new UpdateProdus();
                                    updateProdus.setid(postSnapshot.child("id").getValue().toString());
                                    Intent i = new Intent(context, UpdateProdus.class);
                                    i.putExtra("id",postSnapshot.child("id").getValue().toString());
                                    context.startActivity(i);
                                    ((ListaProduseActivity)context).refreshActivity();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }});
                }
            });

            imageViewImagineProdus.setOnClickListener(new View.OnClickListener() {
                String str;
                @Override
                public void onClick(View v) {
                    refprod.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                //  System.out.println(holder.stergere_produs.getTag());
                                //  System.out.println(postSnapshot.child("imageUrl").getValue().toString());
                                if(stergere_produs.getTag().toString()==postSnapshot.child("id").getValue().toString())
                                {
                                    String a;
                                    UpdateProdus updateProdus = new UpdateProdus();
                                    updateProdus.setid(postSnapshot.child("id").getValue().toString());
                                    Intent i = new Intent(context, MapsActivity.class);
                                    i.putExtra("id",postSnapshot.child("id").getValue().toString());
                                    context.startActivity(i);
                                    ((ListaProduseActivity)context).refreshActivity();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }});
                }
            });


        }
    }
}
