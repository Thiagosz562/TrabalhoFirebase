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

import com.example.loginfire.databinding.ActivityForgetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ForgetActivity extends AppCompatActivity {
    ActivityForgetBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        progress = new ProgressDialog(this);
        progress.setTitle("Crie sua conta");
        progress.setMessage("Aguarde...");

 binding.forgetbutton.setOnClickListener(new View.OnClickListener() {
 @Override
      public void onClick(View v) {
     String email=binding.editEmailAdress.getText().toString();
     progress.dismiss();
     if(email.isEmpty()){
         binding.editEmailAdress.setError("Digite seu email");
         return;
     }else{

      auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful()){
                  progress.dismiss();
                  Toast.makeText(ForgetActivity.this, "Verifique seu email", Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(ForgetActivity.this,LoginActivity.class));
              }else{
                  progress.dismiss();
                  Toast.makeText(ForgetActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
              }






          }
      });


     }

 }
 });


binding.login.setOnClickListener(new View.OnClickListener() {
      @Override
          public void onClick(View v) {
startActivity(new Intent(ForgetActivity.this,LoginActivity.class));
                                     }
                                 });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}