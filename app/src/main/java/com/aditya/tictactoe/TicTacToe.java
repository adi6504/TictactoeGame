package com.aditya.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class TicTacToe extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private int currentPlayer = 0; // 0 for Player X, 1 for Player O

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        // ... initializing buttons
        buttons[0][0] = findViewById(R.id.button1);
        buttons[0][1] = findViewById(R.id.button2);
        buttons[0][2] = findViewById(R.id.button3);
        buttons[1][0] = findViewById(R.id.button4);
        buttons[1][1] = findViewById(R.id.button5);
        buttons[1][2] = findViewById(R.id.button6);
        buttons[2][0] = findViewById(R.id.button7);
        buttons[2][1] = findViewById(R.id.button8);
        buttons[2][2] = findViewById(R.id.button9);
        try {
            // Set OnClickListener for each button
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Button button = (Button) view;
                            // Check if the button is empty
                            if (button.getText().toString().isEmpty()) {
                                // Set the button text based on the current player's turn
                                if (currentPlayer == 0) {
                                    button.setText("O");
                                } else {
                                    button.setText("X");
                                }
                                // Toggle player turn
                                currentPlayer = (currentPlayer + 1) % 2;
                                // Check for a win or a draw and handle it
                                checkGameResult();
                            }
                        }
                    });
                }
            }
        }catch (Exception e){
            // Handle exceptions by showing an error message popup
            showErrorDialog("Error", "An error occurred: " + e.getMessage());
        }

    }
    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
    private void checkGameResult() {
        // Check rows, columns, and diagonals for a win
        if (checkRows() || checkColumns() || checkDiagonals()) {
            // There's a winner, handle the game result (e.g., show a win message)
            showResultMessage("Player " + ((currentPlayer == 0) ? "X" : "O") + " wins!");
        } else if (isBoardFull()) {
            // The board is full, but there's no winner (draw)
            showResultMessage("It's a draw!");
        }
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (checkThree(buttons[i][0], buttons[i][1], buttons[i][2])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (checkThree(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return (checkThree(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                checkThree(buttons[0][2], buttons[1][1], buttons[2][0]));
    }

    private boolean checkThree(Button button1, Button button2, Button button3) {
        return !button1.getText().toString().isEmpty() &&
                button1.getText().toString().equals(button2.getText().toString()) &&
                button2.getText().toString().equals(button3.getText().toString());
    }

    private boolean isBoardFull() {
        // Check if all buttons are filled
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    return false; // There's an empty button, the board is not full
                }
            }
        }
        return true; // All buttons are filled, the board is full
    }

    private void showResultMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetGame(); // Call resetGame() to restart the game
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish(); // Exit the game
                    }
                })
                .show();
    }

    private void resetGame() {
        // Clear button texts
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        // Reset current player to X (Player 0)
        currentPlayer = 0;
    }

}
