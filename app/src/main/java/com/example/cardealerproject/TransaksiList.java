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

//membuat adapter untuk Transaksi
public class TransaksiList extends ArrayAdapter<Trans> {

    private Activity context;
    List<Trans> transs;

    public TransaksiList(Activity context, List<Trans> transs) {
        super(context, R.layout.activity_transaksi_list,transs);
        this.context = context;
        this.transs = transs;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_transaksi_list,null,true);

        TextView tvkodebarangtrans = (TextView)listViewItem.findViewById(R.id.textViewTransaksiKodeBarangTrans);
        TextView tvtgltrans = (TextView) listViewItem.findViewById(R.id.textViewTransaksiTanggalTrans);
        TextView tvnofaktrans = (TextView) listViewItem.findViewById(R.id.textViewTransaksiNoFakturTrans);
        TextView tvjmltrans = (TextView) listViewItem.findViewById(R.id.textViewTransaksiJumlahJualTrans);
        TextView tvhargajualtrans = (TextView) listViewItem.findViewById(R.id.textViewTransaksiHargaJualTrans);
        TextView tvtotaljualtrans = (TextView) listViewItem.findViewById(R.id.textViewTransaksiTotalJualTrans);
        TextView tvstatustrans = (TextView) listViewItem.findViewById(R.id.textViewTransaksiStatusTrans);

        Trans trans = transs.get(position);
        tvkodebarangtrans.setText(trans.getKdbrgtrans());
        tvtgltrans.setText(trans.getTgl_jual());
        tvnofaktrans.setText(trans.no_faktur);
        tvjmltrans.setText(String.valueOf(trans.jml_jual));
        tvhargajualtrans.setText(String.valueOf(trans.hrg_jual));
        tvtotaljualtrans.setText(String.valueOf(trans.total_jual));
        tvstatustrans.setText(trans.status);

        return listViewItem;
    }
}
