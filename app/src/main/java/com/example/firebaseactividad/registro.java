package com.example.firebaseactividad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registro extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    Button btnregister, btnlogin;
    EditText txtemail, txtpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnlogin = findViewById(R.id.btnloguearse);
        btnregister = findViewById(R.id.btnregister);

        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpass);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(registro.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtemail.getText().toString();
                String password = txtpassword.getText().toString();

                if (!email.isEmpty() &&!password.isEmpty() ){
                    if (password.length() < 6){
                        Toast.makeText(registro.this, "La contraseña debe ser mayor o igual a 6 caracteres", Toast.LENGTH_LONG).show();
                    }else{
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(registro.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(registro.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(registro.this, "Este usuario existente", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(registro.this, "Registro incorrecto", Toast.LENGTH_LONG).show();
                                    }
                                }

                            }

                        });
                    }

                }else {
                    Toast.makeText(registro.this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });


    }








}