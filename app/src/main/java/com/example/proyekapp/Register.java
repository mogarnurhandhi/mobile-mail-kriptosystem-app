package com.example.proyekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //paling sedikit 1 digit
                    "(?=.*[a-z])" +         //paling sedikit 1 lowercase karakter
                    "(?=.*[A-Z])" +         //paling sedikit 1 upercase karakter
                    "(?=.*[@#$%^&+=])" +    //paling sedikit 1 spesial karakter
                    "(?=.*[\\s+$])." +      //tanpa spasi
                    "{6,}" +                //paling sedikit 6 upercase karakter
                    "$");

    private EditText inputEmail, inputPassword, inputNama, inputTanggal, inputjnsKelamin, inputNoTelepon;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnRegis;
//    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        databaseReference=FirebaseDatabase.getInstance().getReference("Pengguna/");

        auth = FirebaseAuth.getInstance();
        inputNama = (EditText) findViewById(R.id.nama1);
        inputTanggal = (EditText) findViewById(R.id.tanggal1);
        inputjnsKelamin = (EditText) findViewById(R.id.jnsKelamin1);
        inputNoTelepon = (EditText) findViewById(R.id.noTelepon);
        inputEmail = (EditText) findViewById(R.id.email1);
        inputPassword = (EditText) findViewById(R.id.password1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        btnRegis = (Button) findViewById(R.id.btn_register);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nama = inputNama.getText().toString();
                final String tgl = inputTanggal.getText().toString();
                final String jk = inputjnsKelamin.getText().toString();
                final String noHp = inputNoTelepon.getText().toString();
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(nama)){
                    Toast.makeText(getApplicationContext(), "Masukan nama", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!nama.matches("[a-zA-Z]+")){
                    Toast.makeText(getApplicationContext(), "Masukan hanya alfabet", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tgl)){
                    Toast.makeText(getApplicationContext(), "Masukan tanggal lahir", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!tgl.matches("\\d{4}-\\d{2}-\\d{2}")){
                    Toast.makeText(getApplicationContext(), "Masukan hanya angka format YYYY-MM-DD", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jk)){
                    Toast.makeText(getApplicationContext(), "Masukan jenis kelamin", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!jk.matches("(?:pria|Pria|wanita|Wanita|PRIA|WANITA|Not prefer to say)$")){
                    Toast.makeText(getApplicationContext(), "Masukan hanya alfabet format pria/wanita/PRIA/WANITA/", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(noHp)){
                    Toast.makeText(getApplicationContext(), "Masukan nomor telepon", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!noHp.matches("^[+][0-9]{10,13}$")){
                    Toast.makeText(getApplicationContext(), "Masukan hanya angka format +62xxxxxxxxxxx", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Masukan email", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "Masukan email dengan valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Masukan password", Toast.LENGTH_SHORT).show();
                    return;
//                }else if (!PASSWORD_PATTERN.matcher(password).matches()){
//                    Toast.makeText(getApplicationContext(), "Password terlalu lemah", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password harus lebih dari 6 karakter", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                FirebaseDatabase.getInstance().getReference("Pengguna/" +
                                                FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                        setValue(new Pengguna(inputNama.getText().toString(),
                                                inputTanggal.getText().toString(),
                                                inputjnsKelamin.getText().toString(),
                                                inputNoTelepon.getText().toString(),
                                                inputEmail.getText().toString(),
                                                inputPassword.getText().toString()));

                                Toast.makeText(getApplicationContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                startActivity(new Intent(getApplicationContext(), Login.class));
                                if (!task.isSuccessful()){
                                    Toast.makeText(Register.this, "Maaf registrasi gagal" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    startActivity(new Intent(Register.this, Login.class));
                                }
                            }
                        });
            }
        });
    }
}