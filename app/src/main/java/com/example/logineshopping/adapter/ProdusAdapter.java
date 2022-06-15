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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logineshopping.IncarcareProdusActivity;
import com.example.logineshopping.ListaProduseActivity;
import com.example.logineshopping.R;
import com.example.logineshopping.model.Produs;
import com.example.logineshopping.model.User;
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
    private Button stergere_produs;
    private Button update_produs;
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
        holder.pret.setText(produsCurent.getPret());
        Picasso.with(context)
                .load(produsCurent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageViewImagineProdus);
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
        public TextView pret;
        public Button stergere_produs;

        public ProdusViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNumeProdus = itemView.findViewById(R.id.text_view_numeProdus);
            imageViewImagineProdus = itemView.findViewById(R.id.image_view_imagineProdus);
            descriere = itemView.findViewById(R.id.text_view_descriereprodus);
            pret = itemView.findViewById(R.id.text_view_pret);

            stergere_produs = itemView.findViewById(R.id.Stergere_produs);
            update_produs = itemView.findViewById(R.id.Update_produs);

            mUser = FirebaseAuth.getInstance().getCurrentUser();
            ref = FirebaseDatabase.getInstance().getReference("Users");
            refprod = FirebaseDatabase.getInstance().getReference().child("Uploads");
            //String prodkey =
//            stergere_produs.setId(positionID);
//            positionID++;


            stergere_produs.setOnClickListener(new View.OnClickListener() {
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


            if (mUser != null) {
                //Toast.makeText(ListaProduseActivity.this, "Bine ati revenit!", Toast.LENGTH_SHORT).show();
                userID = mUser.getUid();

                ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile = snapshot.getValue(User.class);
                        String veradmin = userProfile.admin;
                        if (veradmin.equals("1")) {
                            System.out.println("-----------------------------------------------------" + veradmin);
                            update_produs.setVisibility(View.VISIBLE);
                            stergere_produs.setVisibility(View.VISIBLE);

                        }
                        else{
                            update_produs.setVisibility(View.INVISIBLE);
                            stergere_produs.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else {
                update_produs.setVisibility(View.GONE);
                stergere_produs.setVisibility(View.GONE);
            }


        }
    }
}
