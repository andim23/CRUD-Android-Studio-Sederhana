package com.example.wrep.penjualanbarang;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

public class TambahData extends AppCompatActivity{

    Button GetImageFromGalleryButton, UploadImageOnServerButton;

    ImageView ShowSelectedImage;

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
    String GetNamaGambar;

    Bitmap FixBitmap;

    // INPUT POST NAME DI PHP
    String kode_barang = "kode_barang";
    String nama_barang = "nama_barang";
    String jumlah_barang = "jumlah_barang";
    String harga_barang = "harga_barang";
    String ImageTag = "image_tag" ;
    String ImageName = "image_data" ;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        GetImageFromGalleryButton = (Button)findViewById(R.id.buttonSelect);
        UploadImageOnServerButton = (Button)findViewById(R.id.buttonUpload);

        ShowSelectedImage = (ImageView)findViewById(R.id.imageView);

        inputKode = (EditText)findViewById(R.id.inputKode);
        inputNama = (EditText)findViewById(R.id.inputNama);
        inputJumlah = (EditText)findViewById(R.id.inputJumlah);
        inputHarga = (EditText)findViewById(R.id.inputHarga);
        //inputNamaGambar =(EditText)findViewById(R.id.namaGambar);

        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetKode = inputKode.getText().toString();
                GetNama = inputNama.getText().toString();
                GetJumlah = inputJumlah.getText().toString();
                GetHarga = inputHarga.getText().toString();
                //GetNamaGambar = inputNamaGambar.getText().toString();

                UploadImageToServer();
            }
        });

        if (ContextCompat.checkSelfPermission(TambahData.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera" };
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ShowSelectedImage.setImageBitmap(FixBitmap);
                    UploadImageOnServerButton.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(TambahData.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            ShowSelectedImage.setImageBitmap(FixBitmap);
            UploadImageOnServerButton.setVisibility(View.VISIBLE);
        }
    }

    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(TambahData.this,"Image Sedang diunggah","Mohon menunggu",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(TambahData.this,string1,Toast.LENGTH_LONG).show();
                Intent back = new Intent(getApplicationContext(), TambahData.class);
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
//                HashMapParams.put(ImageTag, GetNamaGambar);

                HashMapParams.put(ImageName, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest("http://iniwahyu.com/android/tambahdata2.php", HashMapParams);

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //
            }
            else {
                Toast.makeText(TambahData.this, "Gagal Membuka Kamera. Mohon Buka Perizinan Terlebih Dahulu", Toast.LENGTH_LONG).show();
            }
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
                Intent tambah = new Intent(getApplicationContext(), TambahData.class);
                startActivity(tambah);
                //finish();
                return true;
            case R.id.bantuan:
                // Exit option clicked.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}