package com.aditya.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.aditya.tictactoe.database.DatabaseHelper;

public class Login extends AppCompatActivity {

    private Button buttonLogin;
    private ProgressBar progressBar2;
    private DatabaseHelper dbHelper;
    private EditText editTextUserName;
    private EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        editTextUserName = findViewById(R.id.editText2);
        editTextPassword = findViewById(R.id.editText3);

        buttonLogin = findViewById(R.id.ButtonSignup);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar2.setVisibility(View.VISIBLE);
                buttonLogin.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar2.setVisibility(View.GONE);
                    }
                }, 4000); // 2000 milliseconds = 2 seconds
                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {

                    progressBar2.setVisibility(View.VISIBLE);
                    //Show progress bar while uploading user data
                    buttonLogin.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2.setVisibility(View.GONE);
                        }
                    }, 4000);

                    // Check data into the database
                    if (dbHelper.checkCredentials(editTextUserName.getText().toString(), editTextPassword.getText().toString()) == true) {
                        Intent loginIntent = new Intent(Login.this, TicTacToe.class);
                        startActivity(loginIntent);
                    } else {
                        // Invalid credentials, show an error message and reset the EditText fields
                        new AlertDialog.Builder(Login.this)
                                .setTitle("Invalid Credentials")
                                .setMessage("Wrong credentials. Please try again.")
                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Reset EditText fields
                                        editTextUserName.setText("");
                                        editTextPassword.setText("");
                                        dialog.dismiss();
                                    }
                                })
                                .setCancelable(false) // Prevent dialog dismissal on outside touch or back button
                                .show();
                    }
                }
                else {
                    // Invalid credentials, show an error message and reset the EditText fields
                    new AlertDialog.Builder(Login.this)
                            .setTitle("Invalid Credentials")
                            .setMessage("Empty credentials. Please try again.")
                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Reset EditText fields
                                    editTextUserName.setText("");
                                    editTextPassword.setText("");
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false) // Prevent dialog dismissal on outside touch or back button
                            .show();
                }
            }
        });

    }
}