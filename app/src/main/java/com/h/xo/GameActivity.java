package com.h.xo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    int gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameState = 1; // 1 - can play, 2 - gameover, 3 - draw
    }


    public void GameBoardClick(View view) {

        ImageView selectedImage = (ImageView) view;

        int selectedBlock = 0;
        switch (selectedImage.getId()) {

            case R.id.iv_11: selectedBlock = 1; break;
            case R.id.iv_12: selectedBlock = 2; break;
            case R.id.iv_13: selectedBlock = 3; break;

            case R.id.iv_21: selectedBlock = 4; break;
            case R.id.iv_22: selectedBlock = 5; break;
            case R.id.iv_23: selectedBlock = 6; break;

            case R.id.iv_31: selectedBlock = 7; break;
            case R.id.iv_32: selectedBlock = 8; break;
            case R.id.iv_33: selectedBlock = 9; break;
        }
        
        PlayGame(selectedBlock, selectedImage);
    }


    int activePlayer = 1;
    ArrayList<Integer> player1 = new ArrayList<>();
    ArrayList<Integer> player2 = new ArrayList<>();
    private void PlayGame(int selectedBlock, ImageView selectedImage) {
        if (gameState == 1) {
            if (activePlayer == 1) {
                selectedImage.setImageResource(R.drawable.ttt_x);
                player1.add(selectedBlock);
                activePlayer = 2;
                AutoPlay();
            } else if (activePlayer == 2) {
                selectedImage.setImageResource(R.drawable.ttt_o);
                player2.add(selectedBlock);
                activePlayer = 1;
            }
        }

        selectedImage.setEnabled(false);
        CheckWinner();
    }

    private void AutoPlay() {
        ArrayList<Integer> emptyBlocks = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            if ((player1.contains(i) || player2.contains(i))) {
                emptyBlocks.add(i);
            }
        }

        if (emptyBlocks.size() == 0) {
            CheckWinner();
            if (gameState == 1) {
                AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                ShowAlert("Draw");
            }
        } else {
            Random r = new Random();
            int randomIndex = r.nextInt(emptyBlocks.size());
            int selectedBlock = emptyBlocks.get(randomIndex);

            ImageView selectedImage = findViewById(R.id.iv_11);
            switch (selectedBlock) {
                case 1: selectedImage = findViewById(R.id.iv_11);
                case 2: selectedImage = findViewById(R.id.iv_12);
                case 3: selectedImage = findViewById(R.id.iv_13);

                case 4: selectedImage = findViewById(R.id.iv_21);
                case 5: selectedImage = findViewById(R.id.iv_22);
                case 6: selectedImage = findViewById(R.id.iv_23);

                case 7: selectedImage = findViewById(R.id.iv_31);
                case 8: selectedImage = findViewById(R.id.iv_32);
                case 9: selectedImage = findViewById(R.id.iv_33);
            }
            PlayGame(selectedBlock, selectedImage);
        }
    }

    void CheckWinner(){
        int winner = 0;

        /********* for player 1 *********/
        if(player1.contains(1) && player1.contains(2) && player1.contains(3)){ winner = 1; }
        if(player1.contains(4) && player1.contains(5) && player1.contains(6)){ winner = 1; }
        if(player1.contains(7) && player1.contains(8) && player1.contains(9)){ winner = 1; }

        if(player1.contains(1) && player1.contains(4) && player1.contains(7)){ winner = 1; }
        if(player1.contains(2) && player1.contains(5) && player1.contains(8)){ winner = 1; }
        if(player1.contains(3) && player1.contains(6) && player1.contains(9)){ winner = 1; }

        if(player1.contains(1) && player1.contains(5) && player1.contains(9)){ winner = 1; }
        if(player1.contains(3) && player1.contains(5) && player1.contains(7)){ winner = 1; }


        /********* for player 2 *********/
        if(player2.contains(1) && player2.contains(2) && player2.contains(3)){ winner = 2; }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6)){ winner = 2; }
        if(player2.contains(7) && player2.contains(8) && player2.contains(9)){ winner = 2; }

        if(player2.contains(1) && player2.contains(4) && player2.contains(7)){ winner = 2; }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8)){ winner = 2; }
        if(player2.contains(3) && player2.contains(6) && player2.contains(9)){ winner = 2; }

        if(player2.contains(1) && player2.contains(5) && player2.contains(9)){ winner = 2; }
        if(player2.contains(3) && player2.contains(5) && player2.contains(7)){ winner = 2; }



        if(winner != 0 && gameState == 1){
            if(winner == 1){
                ShowAlert("player 1 is winner");
            }else if(winner == 2){
                ShowAlert("player 2 is winner");
            }
            gameState = 2; // gameover
        }
    }

    void ShowAlert(String Title){
        AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);
        b.setTitle(Title)
                .setMessage("Start a new game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetGame();
                    }
                })
                .setNegativeButton("Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    void ResetGame(){
        gameState = 1; // playing
        activePlayer = 1;
        player1 = new ArrayList<Integer>(); // player1.clear()
        player2 = new ArrayList<Integer>();

        ImageView iv;
        iv = (ImageView) findViewById(R.id.iv_11); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_12); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_13); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv_21); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_22); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_23); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv_31); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_32); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_33); iv.setImageResource(0); iv.setEnabled(true);

    }

}
