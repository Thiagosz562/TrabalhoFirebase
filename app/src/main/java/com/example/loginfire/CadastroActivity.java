package com.example.loginfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginfire.databinding.ActivityCadastroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroActivity extends AppCompatActivity {
    ActivityCadastroBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progress = new ProgressDialog(this);
        progress.setTitle("Crie sua conta");
        progress.setMessage("Aguarde...");

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = binding.editTextNome.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String senha = binding.editTextSenha.getText().toString();

                if (nome.isEmpty()) {
                    binding.editTextNome.setError("Digite seu nome");
                } else if (email.isEmpty()) {
                    binding.editTextEmail.setError("Digite seu email");
                } else if (senha.isEmpty()) {
                    binding.editTextSenha.setError("Digite sua senha");
                } else {
                    progress.show();
                    auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Models models = new Models(nome, email, senha);
                                String id = task.getResult().getUser().getUid();
                                firestore.collection("users").document(id).set(models).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progress.dismiss();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CadastroActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(CadastroActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                progress.dismiss();
                                Toast.makeText(CadastroActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        binding.nextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
