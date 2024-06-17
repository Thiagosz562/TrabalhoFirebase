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

import com.example.loginfire.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
ActivityLoginBinding binding;

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        progress = new ProgressDialog(this);
        progress.setTitle("Crie sua conta");
        progress.setMessage("Aguarde...");

        binding.newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,CadastroActivity.class));
            }
        });

binding.loginButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String email=binding.editEmailAdress.getText().toString();
        String senha=binding.editPassword.getText().toString();

        if(email.isEmpty()){
            binding.editEmailAdress.setError("Digite seu email");

        }else if(senha.isEmpty()) {
            binding.editPassword.setError("Digite sua senha");
        }else{

            progress.show();

            auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progress.dismiss();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }else{
                        progress.dismiss();
                        Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
});

        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetActivity.class));
            }
        });





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}