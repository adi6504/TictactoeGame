package com.aditya.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.Color;
import android.content.Intent;

import com.aditya.tictactoe.database.DatabaseHelper;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    private TextView textViewLogin;
    private ProgressBar progressBar;
    private DatabaseHelper dbHelper;
    private EditText editTextUserName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DatabaseHelper(this);

        textViewLogin = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        editTextUserName = findViewById(R.id.editText2);
        editTextEmail = findViewById(R.id.editText1);
        editTextPassword = findViewById(R.id.editText3);
        buttonSignup = findViewById(R.id.ButtonSignup);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save obtained username, email, and password from user input
                String username = editTextUserName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Reset error highlighting
                editTextUserName.setError(null);
                editTextEmail.setError(null);
                editTextPassword.setError(null);

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    // If any field is empty, show dialog box and return
                    new AlertDialog.Builder(Signup.this)
                            .setTitle("Invalid Credentials")
                            .setMessage("Empty credentials. Please try again.")
                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Reset EditText fields
                                    editTextUserName.setText("");
                                    editTextPassword.setText("");
                                    editTextEmail.setText("");
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();
                    return;
                }

                boolean isValid = true;

                if (!isValidEmail(email)) {
                    isValid = false;
                    editTextEmail.setError("Invalid email address");
                }

                if (!isValidPassword(password)) {
                    isValid = false;
                    editTextPassword.setError("Invalid password (min 8 characters, uppercase, lowercase, digit, special character)");
                }

                if (isValid) {
                    // Insert data into the database
                    dbHelper.insertData(username, email, password);
                    progressBar.setVisibility(View.VISIBLE);
                    // Show progress bar while uploading user data
                    buttonSignup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            onBackPressed();
                        }
                    }, 7000);

                    Intent signupIntent = new Intent(Signup.this, TicTacToe.class);
                    startActivity(signupIntent);
                    textViewLogin.setTextColor(Color.BLACK);
                }
            }

        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewLogin.setTextColor(Color.BLUE);
                progressBar.setVisibility(View.VISIBLE);
                textViewLogin.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        onBackPressed();
                    }
                }, 4000); // 3000 milliseconds = 3 seconds

                Intent loginIntent = new Intent(Signup.this, Login.class);
                startActivity(loginIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Check if the text color is blue, then change it to black, and vice versa
        if (textViewLogin.getCurrentTextColor() == Color.BLUE) {
            textViewLogin.setTextColor(Color.BLACK);
        } else {
            textViewLogin.setTextColor(Color.BLUE);
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Clear the EditText fields when the activity is resumed
        editTextUserName.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
    }

}


