package com.example.logineshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private Button register;
    private Button login;
    private Button reset;
    private FirebaseAuth mAuth;
    private EditText t1;
    private EditText t2;
    private Button galerie;
    //private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        viewSetup();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RegisterActivity.class);
                Login.this.startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = t1.getText().toString().trim();
                String parola = t2.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, parola)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Succes", Toast.LENGTH_LONG).show();
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(Login.this, ListaProduseActivity.class);
                                    Login.this.startActivity(intent);
                                } else {
                                    Toast.makeText(Login.this, "Failure", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ResetareParolaActivity.class);
                Login.this.startActivity(intent);
            }
        });

        galerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ListaProduseActivity.class);
                Login.this.startActivity(intent);
            }
        });
    }

    private void viewSetup() {
        galerie = findViewById(R.id.button112);
        reset = findViewById(R.id.button);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        t1 = findViewById(R.id.editTextTextEmailAddress);
        t2 = findViewById(R.id.editTextTextPassword);
    }

    private void updateUI(FirebaseUser user) {

    }
}