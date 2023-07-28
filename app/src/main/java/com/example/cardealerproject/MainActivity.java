package com.example.cardealerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //deklarasi objek
        etUsername = (EditText) findViewById(R.id.editTextUsername);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = ((Button) findViewById(R.id.buttonLogin));

        //klik tombol login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deklarasi objek
                String etUser = etUsername.getText().toString();
                String etPass = etPassword.getText().toString();

                //cek kondisi untuk login
                if(etUser.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Kosong", Toast.LENGTH_SHORT).show();
                }
                else if(etPass.equals("")){
                    Toast.makeText(getApplicationContext(),"Password Kosong", Toast.LENGTH_SHORT).show();
                }
                //username dan password cocok lalu pindah ke halaman home
                else if (etUser.equals("admin") && etPass.equals("admin")){
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    finish();
                    startActivity(intent);

                }
                //jika username/password salah
                else{
                    Toast.makeText(getApplicationContext(),"Username / Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
