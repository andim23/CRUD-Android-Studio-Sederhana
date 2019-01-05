package com.example.wrep.penjualanbarang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

public class UpdateData extends AppCompatActivity{

    // BUTTON
    Button updateData;
    Button deleteData;

    // EDIT TEXT
    EditText inputKode;
    EditText inputNama;
    EditText inputJumlah;
    EditText inputHarga;
    EditText inputNamaGambar;

    // APALAH
    String GetKode;
    String GetNama;
    String GetJumlah;
    String GetHarga;

    // INPUT POST NAME DI PHP
    String kode_barang = "kode_barang";
    String nama_barang = "nama_barang";
    String jumlah_barang = "jumlah_barang";
    String harga_barang = "harga_barang";

    private EditText txtKode, txtNama, txtJumlah, txtHarga;
    private Button update;

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
        setContentView(R.layout.activity_update_data);

        updateData = (Button)findViewById(R.id.buttonUpdate);
        deleteData = (Button)findViewById(R.id.buttonDelete);

        Intent dapat = getIntent();
        final String kode = dapat.getExtras().getString("kode_barang");
        final String judul = dapat.getExtras().getString("nama_barang");
        final String jumlah = dapat.getExtras().getString("jumlah_barang");
        final String harga = dapat.getExtras().getString("harga_barang");

        txtKode = (EditText)findViewById(R.id.inputKode);
        txtKode.setText(kode);
        txtKode.setEnabled(false);
        txtNama = (EditText)findViewById(R.id.inputNama);
        txtNama.setText(judul);
        txtJumlah = (EditText)findViewById(R.id.inputJumlah);
        txtJumlah.setText(jumlah);
        txtHarga = (EditText)findViewById(R.id.inputHarga);
        txtHarga.setText(harga);

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { 
                GetKode = txtKode.getText().toString();
                GetNama = txtNama.getText().toString();
                GetJumlah = txtJumlah.getText().toString();
                GetHarga = txtHarga.getText().toString();
                if(GetNama.trim().equals("") && GetJumlah.trim().equals("") && GetHarga.trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Kolom Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                if(GetNama.trim().equals("") || GetJumlah.trim().equals("") || GetHarga.trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Lengkapi Kolomnya", Toast.LENGTH_SHORT).show();
                }
                if(!GetNama.trim().equals("") && !GetJumlah.trim().equals("") && !GetHarga.trim().equals("")){
                    ubahData();
                }
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetKode = txtKode.getText().toString();
                GetNama = txtNama.getText().toString();
                GetJumlah = txtJumlah.getText().toString();
                GetHarga = txtHarga.getText().toString();

                hapusData();
            }
        });
    }

    public void ubahData(){
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(UpdateData.this,"Sedang Merubah Data","Mohon menunggu",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(UpdateData.this,string1,Toast.LENGTH_SHORT).show();
                Intent back = new Intent(getApplicationContext(), AdminMain.class);
                startActivity(back);
                finish();
            }

            @Override
            protected String doInBackground(Void... params) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(kode_barang, GetKode);
                HashMapParams.put(nama_barang, GetNama);
                HashMapParams.put(jumlah_barang, GetJumlah);
                HashMapParams.put(harga_barang, GetHarga);

                String FinalData = imageProcessClass.ImageHttpRequest("http://iniwahyu.com/android/update.php", HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public void hapusData(){
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(UpdateData.this,"Sedang Menghapus Data","Mohon menunggu",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(UpdateData.this,string1,Toast.LENGTH_SHORT).show();
                Intent back = new Intent(getApplicationContext(), AdminMain.class);
                startActivity(back);
                finish();
            }

            @Override
            protected String doInBackground(Void... params) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(kode_barang, GetKode);

                String FinalData = imageProcessClass.ImageHttpRequest("http://iniwahyu.com/android/delete.php", HashMapParams);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                finish();
                return true;
            case R.id.mainadmin:
                Intent mainadmin = new Intent(getApplicationContext(), AdminMain.class);
                startActivity(mainadmin);
                finish();
                return true;
            case R.id.tambahBarang:
                Intent tambah = new Intent(getApplicationContext(), UpdateData.class);
                startActivity(tambah);
                finish();
                return true;
            case R.id.telepon:
                String phone = "+6285712032051";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                return true;
            case R.id.sms:
                Uri urisms = Uri.parse("smsto:6285712032051");
                Intent sms = new Intent(Intent.ACTION_SENDTO, urisms);
                sms.putExtra("sms_body", "Ini Pesan");
                startActivity(sms);
                return true;
            case R.id.whatsapp:
                Uri uri = Uri.parse("smsto:" + "6285712032051");
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
                return true;
            case R.id.maps:
                Intent maps = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//WREPSHOP,+Jl.+Bukit+Anyelir+II+No.238,+Sendangmulyo,+Tembalang,+Kota+Semarang,+Jawa+Tengah+50272/@-7.0451889,110.4718997,15z/data=!4m8!4m7!1m0!1m5!1m1!1s0x2e708c2d6c04e4d9:0xe4773594010c89ca!2m2!1d110.4718997!2d-7.0451889"));
                startActivity(maps);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}