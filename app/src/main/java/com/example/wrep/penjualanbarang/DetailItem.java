package com.example.wrep.penjualanbarang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailItem extends AppCompatActivity {

    private ImageView imageView;
    private TextView txtJudul, txtJumlah, txtHarga;
    private String judul, jumlah, harga;
    private Button pesan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        Intent dapat = getIntent();
        final String judul = dapat.getExtras().getString("Judul");
        final String jumlah = dapat.getExtras().getString("Jumlah");
        final String harga = dapat.getExtras().getString("Harga");
        final String img = dapat.getExtras().getString("Image");

        imageView = (ImageView)findViewById(R.id.imageView);
        Picasso.get().load(img).into(imageView);
        txtJudul = (TextView)findViewById(R.id.txtJudul);
        txtJudul.setText(judul);
        txtJumlah = (TextView)findViewById(R.id.txtJumlah);
        txtJumlah.setText("Stok/Jumlah: " + jumlah);
        txtHarga = (TextView)findViewById(R.id.txtHarga);
        txtHarga.setText("Rp. "+ harga);
        pesan = (Button)findViewById(R.id.pesan);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Yang Akan Datang", Toast.LENGTH_LONG).show();
            }
        });
    }

    // MENAMPILKAN MENU DAN ITEM MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // MEMBUAT ITEM MENU MENJADI AKTIF DAN BERPINDAH
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            case R.id.admin:
                Toast.makeText(getApplicationContext(), "Dilarang", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
