package com.example.cardealerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Terjual extends AppCompatActivity {

    DatabaseReference databaseTransaksi;
    ListView lvTransaksi;

    List<Trans> transs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terjual);
        getSupportActionBar().setTitle("Terjual");

        databaseTransaksi = FirebaseDatabase.getInstance().getReference("transs");

        lvTransaksi = (ListView) findViewById(R.id.listViewTransaksi);

        transs = new ArrayList<>();
    }

    //menampilkan listview terjual
    @Override
    protected void onStart() {
        super.onStart();
        //melampirkan value event listener
        databaseTransaksi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //membersihkan data list transaksi sebelumnya
                transs.clear();

                //iterasi ke semua node
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //get transaksi
                    Trans trans = postSnapshot.getValue(Trans.class);
                    //menambahkan transaksi ke dalam list
                    transs.add(trans);
                }

                //membuat adapter
                TransaksiList transAdapter = new TransaksiList(Terjual.this, transs);
                //memasang adapter ke listview
                lvTransaksi.setAdapter(transAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
