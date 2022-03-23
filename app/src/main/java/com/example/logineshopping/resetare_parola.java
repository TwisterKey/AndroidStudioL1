package com.example.logineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class resetare_parola extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button reset;
    private EditText t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetare_parola);
        mAuth = FirebaseAuth.getInstance();
        viewSetup();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = t1.getText().toString().trim();;

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(resetare_parola.this, "Verificati-va adresa de email", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                Intent intent = new Intent(resetare_parola.this, MainActivity.class);
                resetare_parola.this.startActivity(intent);
            }
        });
    }

    private void viewSetup(){
        reset = findViewById(R.id.button2);
        t1 = findViewById(R.id.editTextTextPersonName2);
    }

}