package com.example.wrep.penjualanbarang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Keranjang extends AppCompatActivity {

    private EditText inputKode, inputNama, inputHarga, inputNamaPembeli, inputJumlahPesanan, inputAlamat;
    private Button buttonTambah;

    // APALAH
    String GetKode, GetNama, GetHarga, GetNamaPembeli, GetJumlahPesanan, GetAlamat;

    // INPUT POST NAME DI PHP
    String kode_barang = "kode_barang";
    String nama_barang = "nama_barang";
    String harga_barang = "harga_barang";
    String nama_pembeli = "nama_pembeli";
    String jumlah_pesanan = "jumlah_pesanan";
    String alamat_pembeli = "alamat_pembeli";

    ProgressDialog progressDialog ;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        buttonTambah = (Button)findViewById(R.id.buttonTambah);

        Intent dapat = getIntent();
        final String kode = dapat.getExtras().getString("kode_barang");
        final String judul = dapat.getExtras().getString("nama_barang");
        final String harga = dapat.getExtras().getString("harga_barang");

        inputKode = (EditText)findViewById(R.id.inputKode);
        inputKode.setText(kode);
        inputKode.setEnabled(false);
        inputNama = (EditText)findViewById(R.id.inputNama);
        inputNama.setText(judul);
        inputNama.setEnabled(false);
        inputHarga = (EditText)findViewById(R.id.inputHarga);
        inputHarga.setText(String.valueOf(harga));
        inputHarga.setEnabled(false);
        inputNamaPembeli = (EditText)findViewById(R.id.inputNamaPembeli);
        inputJumlahPesanan = (EditText)findViewById(R.id.inputJumlahPesanan);
        inputAlamat = (EditText)findViewById(R.id.inputAlamat);

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetKode = inputKode.getText().toString();
                GetNama = inputNama.getText().toString();
                GetHarga = inputHarga.getText().toString();
                GetNamaPembeli = inputNamaPembeli.getText().toString();
                GetJumlahPesanan = inputJumlahPesanan.getText().toString();
                GetAlamat = inputAlamat.getText().toString();

                int hargas = Integer.parseInt(GetHarga);
                int pesanan = Integer.parseInt(GetJumlahPesanan);

                if(GetNama.trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Kolom Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                if(!GetNama.trim().equals("") && !GetHarga.trim().equals("")){
                    Intent pindah = new Intent(Keranjang.this, Transaksi.class);
                    pindah.putExtra("kode_barang", kode.toString());
                    pindah.putExtra("nama_barang", judul.toString());
                    pindah.putExtra("harga_barang_int", hargas);
                    pindah.putExtra("nama_pembeli", GetNamaPembeli.toString());
                    pindah.putExtra("jumlah_pesanan_int", pesanan);
                    pindah.putExtra("alamat_pembeli", GetAlamat.toString());
                    startActivity(pindah);
                    finish();
                    tambahKeranjang();
                }
            }
        });
    }

    public void tambahKeranjang(){
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Keranjang.this,"Proses Menambahkan ke Keranjang","Mohon menunggu",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(Keranjang.this,string1,Toast.LENGTH_SHORT).show();
//                Intent transaksi = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(transaksi);
//                finish();
            }

            @Override
            protected String doInBackground(Void... params) {
                Keranjang.ImageProcessClass imageProcessClass = new Keranjang.ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(kode_barang, GetKode);
                HashMapParams.put(nama_barang, GetNama);
                HashMapParams.put(harga_barang, GetHarga);
                HashMapParams.put(nama_pembeli, GetNamaPembeli);
                HashMapParams.put(jumlah_pesanan, GetJumlahPesanan);
                HashMapParams.put(alamat_pembeli, GetAlamat);

                String FinalData = imageProcessClass.ImageHttpRequest("http://iniwahyu.com/android/keranjang.php", HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                url = new URL(requestURL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter( new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(bufferedWriterDataFN(PData));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReader.readLine()) != null){
                        stringBuilder.append(RC2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check) {
                    check = false;
                } else {
                    stringBuilder.append("&");
                }
                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilder.toString();
        }
    }
}
