package com.hope.nessa.tictactoev2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private static ImageView[] possibleSpaces = new ImageView[9];
    public int player = 0;
    int resId = 0;

    TextView scoreMessage;
    TextView p1GamesCount, p2GamesCount;
    int p1GamesCounter = 0, p2GamesCounter = 0;

   static final String winner = "You won the game!";
   static final String loser = "Oops! Try Again!";
   static final String draw = "It's a Draw!";

   Boolean playerFlag = false;

   TextView p1Message, p2Message;
   private static symbols[] playerSymbol = {symbols.DEFAULT, symbols.DEFAULT};

   static int spaceCounter = 0;

   int[] playerChoice = {0,0,0,0,0,0,0,0,0};
   int[][] winningPositions =
           {{0,1,2}, {3,4,5}, {6,7,8}, //horizontal
           {0,3,6}, {1,4,7}, {2,5,8}, //vertical
           {0,4,8}, {2,4,6}}; //diagonal


    public enum symbols{
        CIRCLE, CROSS, DEFAULT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        playGame();
        connectButtons();
        scoreMessage = findViewById(R.id.textViewScore);


    }

    //initialize the players
    void playGame(){
        playerSymbol[0] = symbols.CROSS;
        playerSymbol[1] = symbols.CIRCLE;}

    private void connectButtons() {

        //for loop to connect each ImageView based on its selected position
        for (int i = 0; i < 9; i++) {

            resId = getResources().getIdentifier("space_" + (i), "id", getPackageName());
            possibleSpaces[i] = findViewById(resId);

            final int finalPosition = i;

            possibleSpaces[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchTurns(finalPosition);

                }
            });
        }
        final Button btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 9; i++) {

                    //reset all mutable values
                    playerFlag = false;
                    player = 0;
                    playerChoice[i] = 0;
                    p1Message.setText("");
                    p2Message.setText("");
                    spaceCounter = 0;
                    possibleSpaces[i].setImageDrawable(null);
                    possibleSpaces[i].setEnabled(true);
                    scoreMessage.setText(getString(R.string.score_count));

                }
                playGame();
            }
        });


        Button btnQuit = findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    void switchTurns(int position){

        if (playerFlag == false) {
            possibleSpaces[position].setImageResource(R.drawable.cross);

            //saves the player's position
                playerChoice[position] = 1;
                playerFlag = true;

        } else if (playerFlag == true){
            possibleSpaces[position].setImageResource(R.drawable.circle);

                playerChoice[position] = 2;
                playerFlag = false;

        }
        possibleSpaces[position].setEnabled(false);
        spaceCounter++;

        if(weHaveAWinner()) {
            for (int i = 0; i < 9; i++) {
                Log.i("SwitchTurns:", "Got here" + Arrays.deepToString(winningPositions));

                possibleSpaces[i].setEnabled(false);

            }
        }

        if (spaceCounter == 9){

            scoreMessage.setText(draw);
        }
    }


    public boolean weHaveAWinner(){

        p1Message = findViewById(R.id.playerOneMessage);
        p2Message = findViewById(R.id.playerTwoMessage);
        p1GamesCount = findViewById(R.id.playerOneTotalGame);
        p2GamesCount = findViewById(R.id.playerTwoTotalGame);

        //player 2 wins
        if(playerFlag == false) {
            for (int i = 0; i < winningPositions.length; i++) {
                if (playerChoice[winningPositions[i][0]] != 0) {
                    if (playerChoice[winningPositions[i][0]] == playerChoice[winningPositions[i][1]] &&
                            playerChoice[winningPositions[i][0]] == playerChoice[winningPositions[i][2]]) {

                        p1Message.setText(loser);
                        p2Message.setText(winner);

                        p2GamesCounter++;
                        p2GamesCount.setText(getString(R.string.total_games_won) + p2GamesCounter);

                        return true;
                    }
                }
            }
            //player 1 wins
        }else if(playerFlag == true){
            for (int i = 0; i < winningPositions.length; i++) {
                if (playerChoice[winningPositions[i][0]] != 0) {
                    if (playerChoice[winningPositions[i][0]] == playerChoice[winningPositions[i][1]] &&
                            playerChoice[winningPositions[i][0]] == playerChoice[winningPositions[i][2]]) {

                        p1Message.setText(winner);
                        p2Message.setText(loser);

                        p1GamesCounter++;
                        p1GamesCount.setText(getString(R.string.total_games_won) + p1GamesCounter);

                        return true;
                    }
                }
            }
        }
return false;
    }



}
