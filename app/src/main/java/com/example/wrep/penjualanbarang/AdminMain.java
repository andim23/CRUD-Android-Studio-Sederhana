package com.example.wrep.penjualanbarang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminMain extends AppCompatActivity {

    private final String URL_DATA = "http://iniwahyu.com/android/data.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<AdminListItem> listItems;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText et_name,et_email,et_update_name,et_update_email;
    Button add,btn_update,btn_cancel;
    String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.konten);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        registerForContextMenu(recyclerView);
        listItems = new ArrayList<>();

        loadData();
    }

    private void loadData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for (int i = 0; i<array.length(); i++){
                        JSONObject data = array.getJSONObject(i);
                        AdminListItem item = new AdminListItem(
                                data.getString("url_file"),
                                data.getString("nama_barang"),
                                data.getString("harga_barang"),
                                data.getString("jumlah_barang"),
                                data.getString("kode_barang")
                        );
                        listItems.add(item);
                    }
                    adapter = new AdminAdapter(listItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue antrian = Volley.newRequestQueue(this);
        antrian.add(stringRequest);
    }

    // MENAMPILKAN MENU DAN ITEM MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu_admin, menu);
        return true;
    }

    // MEMBUAT ITEM MENU MENJADI AKTIF DAN BERPINDAH
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
//                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // MENAHAN KEMBALI
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan Dua Kali Untuk Keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
