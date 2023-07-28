package com.example.cardealerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Home");


    }
    //pindah halaman ke form tambah mobil
    public void stockMobilPage(View view){
        Intent intent=new Intent(Home.this,FormTambahMobil.class);
        startActivity(intent);
    }
    //pindah ke halaman terjual
    public void terjualPage(View view){
        Intent intent=new Intent(Home.this,Terjual.class);
        startActivity(intent);
    }
    //pindah ke halaman about
    public void aboutPage(View view){
        Intent intent=new Intent(Home.this,About.class);
        startActivity(intent);
    }
    //pindah ke halaman stok mobil
    public void transaksipage(View view){
        Intent intent = new Intent(Home.this,StokMobil.class);
        startActivity(intent);
    }


    //untuk keluar dari aplikasi
    public void keluar(View view){
        finish();
        System.exit(0);
    }

    //mematikan tombol back
    @Override
    public void onBackPressed() { }
}
