package com.example.wrep.penjualanbarang;

import android.content.Intent;
import android.net.Uri;
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
    private TextView txtKode, txtJudul, txtJumlah, txtHarga;
    private String judul, jumlah, harga;
    private Button pesan, pesanWhatsapp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        Intent dapat = getIntent();
        final String kode = dapat.getExtras().getString("Kode");
        final String judul = dapat.getExtras().getString("Judul");
        final String jumlah = dapat.getExtras().getString("Jumlah");
        final String harga = dapat.getExtras().getString("Harga");
        final String img = dapat.getExtras().getString("Image");

        imageView = (ImageView)findViewById(R.id.imageView);
        Picasso.get().load(img).into(imageView);
        txtKode = (TextView)findViewById(R.id.txtKode);
        txtKode.setText("Kode: "+kode);
        txtJudul = (TextView)findViewById(R.id.txtJudul);
        txtJudul.setText(judul);
        txtJumlah = (TextView)findViewById(R.id.txtJumlah);
        txtJumlah.setText("Stok: " + jumlah);
        txtHarga = (TextView)findViewById(R.id.txtHarga);
        txtHarga.setText("Rp. "+ harga);
        pesan = (Button)findViewById(R.id.pesan);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(DetailItem.this, Keranjang.class);
                pindah.putExtra("kode_barang", kode.toString());
                pindah.putExtra("nama_barang", judul.toString());
                pindah.putExtra("jumlah_barang", jumlah.toString());
                pindah.putExtra("harga_barang", harga.toString());
                startActivity(pindah);
                finish();
            }
        });
        pesanWhatsapp = (Button)findViewById(R.id.pesanWhatsapp);
        pesanWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String text = "Halo Saya Tertarik dengan Produk *"+judul+"*. Apakah Tersedia?";// Replace with your message.
                    String toNumber = "6285712032051"; // Replace with mobile phone number without +Sign or leading zeros.
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(DetailItem.this, "Whatsapp Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                }
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
