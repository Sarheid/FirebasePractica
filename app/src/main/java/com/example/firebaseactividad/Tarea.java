package com.example.firebaseactividad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Tarea extends AppCompatActivity {
    ListView listView;
    FirebaseFirestore fs;
    ProgressDialog progress;

    private List<String> mLista = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        fs = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.listView);
        progress = new ProgressDialog(this);

        getTasks();
    }

    public void getTasks(){
        progress.setMessage("Obteniendo datos");
        progress.show();
        fs.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                mLista.add(String.valueOf(document.getId()));
//                                Log.d("TAG", document.getId() + " => " + document.getData().get);
                                mAdapter = new ArrayAdapter<String>(Tarea.this, android.R.layout.simple_list_item_1, mLista);

                                listView.setAdapter(mAdapter);
                                progress.dismiss();

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

}


