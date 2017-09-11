package com.example.user.numlogin;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button logBtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logBtn = (Button) findViewById(R.id.LogBtn);

        //when push button logBtn
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

            private void login() {
                final String email = ((EditText)findViewById(R.id.Emailedittxt)).getText().toString();
                final String password = ((EditText)findViewById(R.id.Passwdedittxt)).getText().toString();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    register(email, password);
                                }
                            }
                        });
            }

            private void register(final String email, final String password) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("登入問題")
                        .setMessage("無此帳號，是否要以此帳號密碼註冊?")
                        .setPositiveButton("註冊",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which){
                                        createUser(email, password);
                                    }
                                })
                        .setNeutralButton("cancel", null)
                        .show();
            }

            private void createUser(String email, String password) {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        String message = task.isComplete() ? "Success" : "Failed";
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setMessage(message)
                                                .setPositiveButton("OK",null)
                                                .show();
                                    }
                                });

            }
        });
    }
}
