package com.example.proyekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MessageModel> arrayList;
    FirebaseAuth auth;
    Calendar c;
    SwipeRefreshLayout swipeRefreshLayout;
    AESEncrypt aesEncrypt = new AESEncrypt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list_of_messages);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        c=Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("d/M/yyyy h:m:s a");
        final String waktu = date.format(c.getTime());
        swipeRefreshLayout=findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayMessages();
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), Login.class));
        }else {
            Toast.makeText(this,
                            "Haloo" + FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getEmail(),
                            Toast.LENGTH_SHORT)
                    .show();
            onLoading();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        auth = FirebaseAuth.getInstance();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputS = (EditText) findViewById(R.id.inputsub);
                EditText inputM = (EditText) findViewById(R.id.inputmail);
                if (inputS.length()==0){
                    Toast.makeText(getApplicationContext(), "Masukan subyek anda", Toast.LENGTH_SHORT).show();
                    return;
                }else if (inputM.length()==0){
                    Toast.makeText(getApplicationContext(), "Masukan pesan anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseDatabase.getInstance()
                        .getReference("Pesan")
                        .push()
                        .setValue(new MessageModel(aesEncrypt.AESEncrypt
                                (inputS.getText().toString()), aesEncrypt.AESEncrypt(inputM.getText().toString()),
                                FirebaseAuth.getInstance().getCurrentUser()
                                .getEmail(), waktu));
                inputS.setText("");
                inputM.setText("");
            }
        });


    }

    private void onLoading(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                displayMessages();
            }
        });
    }

    private void displayMessages(){
        recyclerView.setAdapter(null);
        swipeRefreshLayout.setRefreshing(true);
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Pesan");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot1 : Snapshot.getChildren()){
                    MessageModel data = dataSnapshot1.getValue(MessageModel.class);
                    arrayList.add(data);
                }
                MessageAdapter adapterMassage = new MessageAdapter(getApplicationContext(), arrayList);
                recyclerView.setAdapter(adapterMassage);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError Error) {
                Toast.makeText(getApplicationContext(),"Data gagal dimuat", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", Error.getDetails()+ " " + Error.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout){
            auth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
        return true;
    }
}