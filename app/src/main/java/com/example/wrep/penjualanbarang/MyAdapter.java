package com.example.wrep.penjualanbarang;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);
        Picasso.get().load(listItem.getGambar()).into(holder.barangGambar);
        holder.barangJudul.setText(listItem.getJudul());
        holder.barangHarga.setText(listItem.getHarga());

        holder.barangGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(context, DetailItem.class);
                pindah.putExtra("Kode", listItem.getKode());
                pindah.putExtra("Image", listItem.getGambar());
                pindah.putExtra("Judul", listItem.getJudul());
                pindah.putExtra("Jumlah", listItem.getJumlah());
                pindah.putExtra("Harga", listItem.getHarga());
                context.startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView barangGambar;
        public TextView barangJudul;
        public TextView barangHarga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            barangGambar = (ImageView) itemView.findViewById(R.id.barangGambar);
            barangJudul = (TextView) itemView.findViewById(R.id.barangJudul);
            barangHarga = (TextView) itemView.findViewById(R.id.barangHarga);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
}

