package com.example.logineshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logineshopping.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button b0;
    private Button reg;
    private EditText t1;
    private EditText t2;
    private EditText t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        viewSetup();

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(intent);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = t2.getText().toString().trim();
                String nume = t1.getText().toString().trim();
                String parola = t3.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email, parola)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(nume, email);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegisterActivity.this, "Clientul s-a inregistrat cu succes", Toast.LENGTH_LONG).show();

                                                        finish();
                                                    } else {
                                                        Toast.makeText(RegisterActivity.this, "Datele nu sunt introduse corespunzator", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Verificati conectarea la internet", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(intent);
            }
        });
    }

    private void viewSetup() {
        mAuth = FirebaseAuth.getInstance();
        reg = findViewById(R.id.register1);
        b0 = findViewById(R.id.b0);
        t1 = findViewById(R.id.editTextTextPersonName);
        t2 = findViewById(R.id.editTextTextEmailAddress3);
        t3 = findViewById(R.id.editTextTextPassword3);
    }
}