package com.example.wrep.penjualanbarang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class Laporan extends AppCompatActivity {

    private TextView namaproduk, jumlahproduk, hargaproduk;
    private Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        Intent dapat = getIntent();
        final String nama_produk = dapat.getExtras().getString("nama_produk");
        final String jumlah_produk = dapat.getExtras().getString("jumlah_produk");
        final String harga_produk = dapat.getExtras().getString("harga_produk");

        namaproduk = (TextView) findViewById(R.id.namaproduk);
        namaproduk.setText(nama_produk);
        jumlahproduk = (TextView) findViewById(R.id.jumlahproduk);
        jumlahproduk.setText("Sebanyak: "+jumlah_produk);
        hargaproduk = (TextView)findViewById(R.id.hargaproduk);
        hargaproduk.setText("Rp. "+harga_produk);
        kembali = (Button)findViewById(R.id.kembali);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(kembali);
                finish();
            }
        });

    }
}
