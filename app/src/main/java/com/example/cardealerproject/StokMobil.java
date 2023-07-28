package com.example.cardealerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StokMobil extends AppCompatActivity {


    public static final String MOBIL_ID = "com.example.cardealerproject.id";
    public static final String MOBIL_KODE = "com.example.cardealerproject.kd_brg";
    public static final String MOBIL_MERK = "com.example.cardealerproject.merk_mobil";
    public static final String MOBIL_TAHUN_PRODUKSI = "com.example.cardealerproject.tahun_produksi";
    public static final String MOBIL_JENIS = "com.example.cardealerproject.jenis";
    public static final String MOBIL_KETERANGAN = "com.example.cardealerproject.keterangan";
    public static final String MOBIL_JUMLAH_STOK = "com.example.cardealerproject.jumlah_stok";

    //FloatingActionButton fabBtn;

    ListView lvStokMbl;

    //list untuk menyimpan semua data mobil ke firebase database
    List<Mobil> mobils;

    //database reference object
    DatabaseReference databaseMobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_mobil);
        getSupportActionBar().setTitle("Transaksi");


        //mengambil reference dari node mobils
        databaseMobil = FirebaseDatabase.getInstance().getReference("mobils");

        lvStokMbl = (ListView) findViewById(R.id.listViewStokMobil);
        //fabBtn = (FloatingActionButton) findViewById(R.id.fabBtnStokMobil);

        //list untuk menyimpan data mobil ke firebase database
        mobils = new ArrayList<>();

//        fabBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StokMobil.this, FormTambahMobil.class);
//                startActivity(intent);
//            }
//        });

        //untuk menampilkan dialog jual beli
        lvStokMbl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
//                Mobil mobil = mobils.get(i);
//                Intent intent2 = new Intent(StokMobil.this, Transaksi.class);
//
//
//
//                intent2.putExtra(MOBIL_ID, mobil.getId());
//                intent2.putExtra(MOBIL_KODE, mobil.getKd_brg());
//                intent2.putExtra(MOBIL_MERK, mobil.getMerk_mobil());
//                intent2.putExtra(MOBIL_TAHUN_PRODUKSI, mobil.getTahun_produksi().toString());
//                intent2.putExtra(MOBIL_JENIS, mobil.getJenis());
//                intent2.putExtra(MOBIL_KETERANGAN, mobil.getKeterangan());
//                intent2.putExtra(MOBIL_JUMLAH_STOK, mobil.getJumlah_stok().toString());
//
//
//                startActivity(intent2);
                Mobil mobil = mobils.get(i);
                Integer posisi = i;
                showJualBeliDialog(mobil.getId(),mobil.getKd_brg(),mobil.getMerk_mobil(), posisi);

            }
        });
        //untuk menampilkan dialog update delete
        lvStokMbl.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                Mobil mobil = mobils.get(i);
                showUpdateDeleteDialog(mobil.getId(),mobil.getKd_brg(), mobil.getMerk_mobil(),mobil.getTahun_produksi(),mobil.getJenis(),mobil.getKeterangan(),mobil.getJumlah_stok());
                return true;
            }
        });
    }
    //untuk menampilkan data mobil dari firebase ke adapter listview yang telah dibuat
    @Override
    protected void onStart() {
        super.onStart();
        //melampirkan value event listener
        databaseMobil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //membersihkan data list mobil sebelumnya
                mobils.clear();

                //iterasi ke semua node
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //get mobil
                    Mobil mobil = postSnapshot.getValue(Mobil.class);
                    //menambahkan mobil ke dalam list
                    mobils.add(mobil);
                }

                //membuat adapter
                MobilList mobilAdapter = new MobilList(StokMobil.this, mobils);
                //memasang adapter ke listview
                lvStokMbl.setAdapter(mobilAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //method untuk menampilkan update delete dialog
    private void showUpdateDeleteDialog(final String id, final String kd_brg, final String merk_mobil, final Integer tahun_produksi, final String jenis, final String keterangan, final Integer jumlah_stok) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText etkdbrg = (EditText) dialogView.findViewById(R.id.ediTextKodeBarangUpdate);
        final EditText etmerkmbl = (EditText) dialogView.findViewById(R.id.ediTextMerkMobilUpdate);
        final EditText etthnprd = (EditText) dialogView.findViewById(R.id.ediTextTahunProduksiUpdate);
        final Spinner spinnerjns = (Spinner) dialogView.findViewById(R.id.spinnerJenisMblUpdate);
        final EditText etket = (EditText) dialogView.findViewById(R.id.ediTextKeteranganUpdate);
        final EditText etjmlstk = (EditText) dialogView.findViewById(R.id.ediTextStokMobilUpdate);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateMbl);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteMbl);

        etkdbrg.setText(kd_brg);
        etmerkmbl.setText(merk_mobil);
        etthnprd.setText(String.valueOf(tahun_produksi));
        //spinnerjns.set
        etket.setText(keterangan);
        etjmlstk.setText(String.valueOf(jumlah_stok));



        dialogBuilder.setTitle(merk_mobil);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        //update data mobil
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = etkdbrg.getText().toString().trim();
                String mm = etmerkmbl.getText().toString().trim();
                Integer tp = Integer.parseInt(String.valueOf(etthnprd.getText()));
                String jns = spinnerjns.getSelectedItem().toString();
                String ket = etket.getText().toString().trim();
                Integer jstk = Integer.parseInt(String.valueOf(etjmlstk.getText()));
                if (!TextUtils.isEmpty(kb)||!TextUtils.isEmpty(mm)||!TextUtils.isEmpty(etthnprd.getText())||!TextUtils.isEmpty(ket)||!TextUtils.isEmpty(etjmlstk.getText())) {
                    updateMobil(id,kb, mm, tp,jns,ket,jstk);
                    b.dismiss();
                }
            }
        });

        //delete data mobil
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteMobil(id);
                b.dismiss();
            }
        });
    }

    //method untuk menampilkan jual beli dialog
    private void showJualBeliDialog(final String id, final String kd_brg, final String merk_mobil,final Integer posisi){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_jualbelidialog, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(merk_mobil);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        final Button buttonJualMobil = (Button) dialogView.findViewById(R.id.buttonJualMbl);
        final Button buttonBeliMobil = (Button) dialogView.findViewById(R.id.buttonBeliMbl);

        //mengambil data mobil untuk ditransfer ke kelas transaksi
        Mobil mobil = mobils.get(posisi);
        final Intent intent2 = new Intent(StokMobil.this, Transaksi.class);
        intent2.putExtra(MOBIL_ID, mobil.getId());
        intent2.putExtra(MOBIL_KODE, mobil.getKd_brg());
        intent2.putExtra(MOBIL_MERK, mobil.getMerk_mobil());
        intent2.putExtra(MOBIL_TAHUN_PRODUKSI, mobil.getTahun_produksi().toString());
        intent2.putExtra(MOBIL_JENIS, mobil.getJenis());
        intent2.putExtra(MOBIL_KETERANGAN, mobil.getKeterangan());
        intent2.putExtra(MOBIL_JUMLAH_STOK, mobil.getJumlah_stok().toString());



        //mengambil data mobil untuk ditransfer ke kelas transaksibeli
        Mobil mobil2 = mobils.get(posisi);
        final Intent intent3 = new Intent(StokMobil.this, TransaksiBeli.class);
        intent3.putExtra(MOBIL_ID, mobil2.getId());
        intent3.putExtra(MOBIL_KODE, mobil2.getKd_brg());
        intent3.putExtra(MOBIL_MERK, mobil2.getMerk_mobil());
        intent3.putExtra(MOBIL_TAHUN_PRODUKSI, mobil2.getTahun_produksi().toString());
        intent3.putExtra(MOBIL_JENIS, mobil2.getJenis());
        intent3.putExtra(MOBIL_KETERANGAN, mobil2.getKeterangan());
        intent3.putExtra(MOBIL_JUMLAH_STOK, mobil2.getJumlah_stok().toString());


        //pindah ke halaman transaksi dan membawa data mobil
        buttonJualMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(intent2);
            }
        });

        //pindah ke halaman transaksibeli dan membawa data mobil
        buttonBeliMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
    }


    //method untuk update mobil
    private boolean updateMobil(String id,String kd_brg, String merk_mobil, Integer tahun_produksi,String jenis, String keterangan, Integer jumlah_stok) {
        //mendapatkan id dari reference mobil firebase
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("mobils").child(id);

        //update mobil
        Mobil mobil = new Mobil(id,kd_brg, merk_mobil, tahun_produksi,jenis,keterangan,jumlah_stok);
        dR.setValue(mobil);
        Toast.makeText(getApplicationContext(), "Mobil Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    //method untuk delete mobil
    private boolean deleteMobil(String id) {
        //mendapatkan id dari reference mobil firebase
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("mobils").child(id);

        //menghapus mobil
        dR.removeValue();

        return true;
    }
}
