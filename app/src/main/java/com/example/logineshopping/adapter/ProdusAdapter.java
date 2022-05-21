package com.example.logineshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logineshopping.R;
import com.example.logineshopping.model.Produs;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProdusAdapter extends RecyclerView.Adapter<ProdusAdapter.ProdusViewHolder> {
    private Context context;
    private List<Produs> produsList;

    public ProdusAdapter(Context context, List<Produs> produsList) {
        this.context = context;
        this.produsList = produsList;
    }

    @NonNull
    @Override
    public ProdusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.produs_item, parent, false);

        return new ProdusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdusViewHolder holder, int position) {
        Produs produsCurent = produsList.get(position);

        holder.textViewNumeProdus.setText(produsCurent.getName());
        Picasso.with(context)
                .load(produsCurent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageViewImagineProdus);
    }

    @Override
    public int getItemCount() {
        return produsList.size();
    }

    public class ProdusViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNumeProdus;
        public ImageView imageViewImagineProdus;

        public ProdusViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNumeProdus = itemView.findViewById(R.id.text_view_numeProdus);
            imageViewImagineProdus = itemView.findViewById(R.id.image_view_imagineProdus);
        }
    }
}
