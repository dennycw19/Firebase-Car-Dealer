package com.example.cardealerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

//membuat adapter untuk mobil
public class MobilList extends ArrayAdapter<Mobil> {


    private Activity context;
    List<Mobil> mobils;

    public MobilList(Activity context, List<Mobil> mobils) {
        super(context, R.layout.activity_mobil_list,mobils);
        this.context = context;
        this.mobils = mobils;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_mobil_list,null,true);

        TextView tvkodebarang = (TextView)listViewItem.findViewById(R.id.textViewKodeBarang);
        TextView tvmerkmobil = (TextView) listViewItem.findViewById(R.id.textViewMerkMobil);
        TextView tvtahunproduksi = (TextView) listViewItem.findViewById(R.id.textViewTahunProduksi);
        TextView tvjenis = (TextView) listViewItem.findViewById(R.id.textViewJenis);
        TextView tvketerangan = (TextView) listViewItem.findViewById(R.id.textViewKeterangan);
        TextView tvstok = (TextView) listViewItem.findViewById(R.id.textViewStok);

        Mobil mobil = mobils.get(position);
        tvkodebarang.setText(mobil.getKd_brg());
        tvmerkmobil.setText(mobil.getMerk_mobil());
        tvtahunproduksi.setText(String.valueOf(mobil.getTahun_produksi()));
        tvjenis.setText(mobil.getJenis());
        tvketerangan.setText(mobil.getKeterangan());
        tvstok.setText(String.valueOf(mobil.getJumlah_stok()));

        return listViewItem;
    }
}
