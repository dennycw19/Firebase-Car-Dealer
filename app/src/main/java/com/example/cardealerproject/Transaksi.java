package com.example.cardealerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Transaksi extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private Button btDatePicker;

    EditText transNoFaktur,transJumlahJual,transHargaJual;
    TextView transKodeBarang,tvTransTotalJual;
    Button btnhitung,btntransaksi;

    //list untuk menyimpan semua data transaksi ke firebase database
    List<Trans> transs;

    //database reference object
    DatabaseReference databaseTransaksi;
    DatabaseReference databaseMobil;

    Integer tempstok2,stokcuy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        getSupportActionBar().setTitle("Transaksi Jual");

        final Intent intent = getIntent();
        //final Intent intent2 = getIntent();




        databaseTransaksi = FirebaseDatabase.getInstance().getReference("transs");
        databaseMobil = FirebaseDatabase.getInstance().getReference("mobils");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        transKodeBarang = (TextView) findViewById(R.id.textViewTransKodeBarang);
        tvDateResult = (TextView) findViewById(R.id.tv_dateresult);
        transNoFaktur = (EditText) findViewById(R.id.ediTextTransNoFaktur);
        transJumlahJual = (EditText) findViewById(R.id.ediTextTransJumlahJual);
        transHargaJual = (EditText) findViewById(R.id.ediTextTransHargaJual);
        tvTransTotalJual = (TextView) findViewById(R.id.textViewTransTotalJual);
        btnhitung = (Button) findViewById(R.id.buttonHitung);
        btDatePicker = (Button) findViewById(R.id.bt_datepicker);

        btntransaksi = (Button) findViewById(R.id.buttonTransaksi);

        transs = new ArrayList<>();

        transKodeBarang.setText(intent.getStringExtra(StokMobil.MOBIL_KODE));
        tempstok2 = Integer.parseInt(String.valueOf(intent.getStringExtra(StokMobil.MOBIL_JUMLAH_STOK)));

        //tombol untuk memanggil method showDateDialog()
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        //tombol untuk menghitung total harga dan jumlah stok yang baru
        btnhitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer jml = Integer.parseInt(String.valueOf(transJumlahJual.getText()));
                Integer harga = Integer.parseInt(String.valueOf(transHargaJual.getText()));
                //final Intent intent = getIntent();

                Integer totalhrg = jml*harga;

                tvTransTotalJual.setText(String.valueOf(totalhrg));

                Integer stokbaru = tempstok2-jml;

                stokcuy=stokbaru;


            }
        });
        //tombol untuk memanggil method addTransaksi()
        btntransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaksi();
            }
        });
    }

    //method untuk menampilkan dialog kalender
    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                //tvDateResult.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()));
                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    //method untuk menambahkan transaksi jual ke dalam firebase database
    private void addTransaksi(){
        String kodebarang = transKodeBarang.getText().toString().trim();
        String tanggal = tvDateResult.getText().toString().trim();
        String nofaktur = transNoFaktur.getText().toString().trim();
        Integer jmljual = Integer.parseInt(String.valueOf(transJumlahJual.getText()));
        Integer hargajual = Integer.parseInt(String.valueOf(transHargaJual.getText()));
        Integer totaljual = Integer.parseInt(String.valueOf(tvTransTotalJual.getText()));


        if(!TextUtils.isEmpty(kodebarang)||!TextUtils.isEmpty(tanggal) || transJumlahJual.equals("") || !TextUtils.isEmpty(nofaktur) || transHargaJual.equals("") || tvTransTotalJual.equals("")){
            final Intent intent = getIntent();
            String id = databaseTransaksi.push().getKey();
            //String id2 = databaseMobil.push().getKey();
            String id2 = intent.getStringExtra(StokMobil.MOBIL_ID);
            String strJual = "Jual";



            Trans trans = new Trans(id,kodebarang, tanggal, nofaktur, jmljual, hargajual,totaljual,strJual);
            Mobil mobil = new Mobil(id2, kodebarang,intent.getStringExtra(StokMobil.MOBIL_MERK),Integer.parseInt(String.valueOf(intent.getStringExtra(StokMobil.MOBIL_TAHUN_PRODUKSI))),intent.getStringExtra(StokMobil.MOBIL_JENIS),intent.getStringExtra(StokMobil.MOBIL_KETERANGAN),stokcuy);

            databaseTransaksi.child(id).setValue(trans);
            databaseMobil.child(id2).setValue(mobil);

            transKodeBarang.setText("");
            tvDateResult.setText("");
            transNoFaktur.setText("");
            transJumlahJual.setText("");
            transHargaJual.setText("");
            tvTransTotalJual.setText("");

            Toast.makeText(this,"Transaksi added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Empty field!", Toast.LENGTH_LONG).show();
        }
    }
}
