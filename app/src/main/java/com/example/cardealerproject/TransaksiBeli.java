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

public class TransaksiBeli extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvBeliDateResult;
    private Button btBeliDatePicker;

    EditText transBeliNoFaktur,transBeliJumlahBeli,transBeliHargaBeli;
    TextView transBeliKodeBarang,tvBeliTransTotalBeli;
    Button btnBelihitung,btnBelitransaksi;

    //list untuk menyimpan semua data transaksi ke firebase database
    List<Trans> transs;

    //database reference object
    DatabaseReference databaseTransaksi;
    DatabaseReference databaseMobil;

    Integer tempstok2,stokcuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_beli);

        getSupportActionBar().setTitle("Transaksi Beli");

        final Intent intent = getIntent();
        //final Intent intent2 = getIntent();




        databaseTransaksi = FirebaseDatabase.getInstance().getReference("transs");
        databaseMobil = FirebaseDatabase.getInstance().getReference("mobils");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        transBeliKodeBarang = (TextView) findViewById(R.id.textViewBeliTransKodeBarang);
        tvBeliDateResult = (TextView) findViewById(R.id.tv_dateresultBeli);
        transBeliNoFaktur = (EditText) findViewById(R.id.ediTextBeliTransNoFaktur);
        transBeliJumlahBeli = (EditText) findViewById(R.id.ediTextBeliTransJumlahBeli);
        transBeliHargaBeli = (EditText) findViewById(R.id.ediTextBeliTransHargaBeli);
        tvBeliTransTotalBeli = (TextView) findViewById(R.id.textViewBeliTransTotalBeli);
        btnBelihitung = (Button) findViewById(R.id.buttonHitungBeli);
        btBeliDatePicker = (Button) findViewById(R.id.bt_datepickerBeli);

        btnBelitransaksi = (Button) findViewById(R.id.buttonTransaksiBeli);

        transs = new ArrayList<>();

        transBeliKodeBarang.setText(intent.getStringExtra(StokMobil.MOBIL_KODE));
        tempstok2 = Integer.parseInt(String.valueOf(intent.getStringExtra(StokMobil.MOBIL_JUMLAH_STOK)));

        //tombol untuk memanggil method showDateDialog()
        btBeliDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        //tombol untuk menghitung total harga dan jumlah stok yang baru
        btnBelihitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer jml = Integer.parseInt(String.valueOf(transBeliJumlahBeli.getText()));
                Integer harga = Integer.parseInt(String.valueOf(transBeliHargaBeli.getText()));
                //final Intent intent = getIntent();

                Integer totalhrg = jml*harga;

                tvBeliTransTotalBeli.setText(String.valueOf(totalhrg));

                Integer stokbaru = tempstok2+jml;

                stokcuy=stokbaru;


            }
        });
        //tombol untuk memanggil method addTransaksiBeli()
        btnBelitransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaksiBeli();
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
                tvBeliDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    //method untuk menambahkan transaksi beli ke dalam firebase database
    private void addTransaksiBeli(){
        String kodebarang = transBeliKodeBarang.getText().toString().trim();
        String tanggal = tvBeliDateResult.getText().toString().trim();
        String nofaktur = transBeliNoFaktur.getText().toString().trim();
        Integer jmlbeli = Integer.parseInt(String.valueOf(transBeliJumlahBeli.getText()));
        Integer hargabeli = Integer.parseInt(String.valueOf(transBeliHargaBeli.getText()));
        Integer totalbeli = Integer.parseInt(String.valueOf(tvBeliTransTotalBeli.getText()));


        if(!TextUtils.isEmpty(kodebarang)||!TextUtils.isEmpty(tanggal) || transBeliJumlahBeli.equals("") || !TextUtils.isEmpty(nofaktur) || transBeliHargaBeli.equals("") || tvBeliTransTotalBeli.equals("")){
            final Intent intent = getIntent();
            String id = databaseTransaksi.push().getKey();
            //String id2 = databaseMobil.push().getKey();
            String id2 = intent.getStringExtra(StokMobil.MOBIL_ID);
            String strBeli = "Beli";



            Trans trans = new Trans(id,kodebarang, tanggal, nofaktur, jmlbeli, hargabeli,totalbeli,strBeli);
            Mobil mobil = new Mobil(id2, kodebarang,intent.getStringExtra(StokMobil.MOBIL_MERK),Integer.parseInt(String.valueOf(intent.getStringExtra(StokMobil.MOBIL_TAHUN_PRODUKSI))),intent.getStringExtra(StokMobil.MOBIL_JENIS),intent.getStringExtra(StokMobil.MOBIL_KETERANGAN),stokcuy);

            databaseTransaksi.child(id).setValue(trans);
            databaseMobil.child(id2).setValue(mobil);

            transBeliKodeBarang.setText("");
            tvBeliDateResult.setText("");
            transBeliNoFaktur.setText("");
            transBeliJumlahBeli.setText("");
            transBeliHargaBeli.setText("");
            tvBeliTransTotalBeli.setText("");

            Toast.makeText(this,"Transaksi added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Empty field!", Toast.LENGTH_LONG).show();
        }
    }
}
