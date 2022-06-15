package com.example.firebaseactividad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inicio extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    FirebaseAuth mAuth;

    DatabaseReference mDatabase;
    FirebaseFirestore fs;

    EditText txtTask;
    Button btnadd, btntask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        fs = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        btnadd = findViewById(R.id.btnadd);
        btntask = findViewById(R.id.btntask);
        txtTask = findViewById(R.id.txttask);


        btnadd.setOnClickListener(this);
        btntask.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        switch (view.getId()){
            case R.id.btnadd:
                String task = txtTask.getText().toString();

                if (!task.isEmpty()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("task", task);
                    fs.collection("tasks").document(task).set(map);
                    txtTask.setText("");
                    Toast.makeText(Inicio.this, "Registro creado", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btntask:
                Intent i = new Intent(Inicio.this, Tarea.class);
                startActivity(i);
                break;

        }

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_salir) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(Inicio.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

}

