package com.example.cardealerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FormTambahMobil extends AppCompatActivity {
    EditText etKodeBarang,etMerkMobil,etTahunProduksi,etKeterangan,etStok;
    Spinner spinnerJenis;
    Button btnAddMobil;

    //list untuk menyimpan data mobil ke firebase database
    List<Mobil> mobils;

    //database reference object
    DatabaseReference databaseMobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tambah_mobil);
        getSupportActionBar().setTitle("Form Entry Mobil");

        //mengambil reference dari node mobils
        databaseMobil = FirebaseDatabase.getInstance().getReference("mobils");

        //deklarasi objek
        etKodeBarang = (EditText) findViewById(R.id.ediTextKodeBarang);
        etMerkMobil = (EditText) findViewById(R.id.ediTextMerkMobil);
        etTahunProduksi = (EditText) findViewById(R.id.ediTextTahunProduksi);
        etKeterangan = (EditText) findViewById(R.id.ediTextKeterangan);
        spinnerJenis = (Spinner) findViewById(R.id.spinnerJenisMbl);
        etStok = (EditText) findViewById(R.id.ediTextStokMobil);

        btnAddMobil = (Button) findViewById(R.id.tombolAdd);

        //list untuk menyimpan data mobils
        mobils = new ArrayList<>();

        //klik tombol add mobil
        btnAddMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memanggil method tambah mobil
                addMobil();
            }
        });
    }

    //method menambahkan mobil
    private void addMobil(){
        //deklarasi objek
        String etKB = etKodeBarang.getText().toString().trim();
        String etMM = etMerkMobil.getText().toString().trim();
        Integer etTP = Integer.parseInt(String.valueOf(etTahunProduksi.getText()));
        String etSpinner = spinnerJenis.getSelectedItem().toString();
        String etKet = etKeterangan.getText().toString().trim();
        Integer etStk = Integer.parseInt(String.valueOf(etStok.getText()));

        //kondisu untuk input mobils
        if(!TextUtils.isEmpty(etKB)||!TextUtils.isEmpty(etMM) || etTahunProduksi.equals("") || !TextUtils.isEmpty(etKet) || etTahunProduksi.equals("")){
            String id = databaseMobil.push().getKey();

            Mobil mobil = new Mobil(id,etKB, etMM, etTP, etSpinner, etKet,etStk);

            databaseMobil.child(id).setValue(mobil);

            etKodeBarang.setText("");
            etMerkMobil.setText("");
            etTahunProduksi.setText("");
            etKeterangan.setText("");
            etStok.setText("");

            Toast.makeText(this,"Mobil added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Empty field!", Toast.LENGTH_LONG).show();
        }
    }

}
