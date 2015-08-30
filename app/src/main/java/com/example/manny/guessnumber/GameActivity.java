package com.example.manny.guessnumber;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private TextView mGuessNumberTextView;
    private TextView mResultTextView;
    private TextView mGuessLevel;
    private EditText mInput;
    private Button   mGuessButton;
    private Game     mGame;
    private int Level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Level = intent.getIntExtra("level",0);
        mGuessNumberTextView = (TextView) findViewById(R.id.guess_number_text_view);
        mResultTextView = (TextView) findViewById(R.id.result_text_view);
        mGuessLevel = (TextView) findViewById(R.id.guess_level);
        mInput = (EditText) findViewById(R.id.input);
        mGuessButton = (Button) findViewById(R.id.guess_button);

        mGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mInput.getText().length() == 0){
                    Toast.makeText(GameActivity.this, "Please Input number", Toast.LENGTH_LONG).show();
                    return;
                }else if(Level == 0&&mInput.getText().length() > 1){
                    Toast.makeText(GameActivity.this, "Please Input 0-9 only", Toast.LENGTH_LONG).show();
                    return;
                }else if(Level == 1&&mInput.getText().length() > 2){
                    Toast.makeText(GameActivity.this, "Please Input 0-99 only", Toast.LENGTH_LONG).show();
                    return;
                }

                int guessNumber = Integer.valueOf(mInput.getText().toString());
                mGuessNumberTextView.setText(String.valueOf(guessNumber));
                mGuessLevel.setText(NameLevel(Level));
                checkAnswer(guessNumber);
                mInput.setText("");
            }
        });
        newGame(Level);

    }

    private void checkAnswer(int guessNumber) {
        Game.CompareResult result = mGame.submitGuess(guessNumber);
        if (result == Game.CompareResult.EQUAL){
            mResultTextView.setText(guessNumber + " That's right.");
            mGuessNumberTextView.setBackgroundResource(R.color.correct_guess);
            MediaPlayer mp = MediaPlayer.create(this, R.raw.applause );
            mp.start();
            mp.setVolume(100f , 100f);
            String str = String.format("Your Guess is %d", mGame.getTotalGuesses());
            new AlertDialog.Builder(this).setTitle("Summary").setMessage(str).setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new AlertDialog.Builder(GameActivity.this).setTitle("Levels").setItems(new String[]{"Easy", "Hard"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("MainAcivity", String.valueOf(which));
                           Level = which;
                            newGame(Level);
                        }
                    }).show();

                }
            }).setNegativeButton("back home", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        }else if(result == Game.CompareResult.TOO_BIG){
            mResultTextView.setText(guessNumber +" Number is too much.");
            mGuessNumberTextView.setBackgroundResource(R.color.number_too_much);
        }else{
            mResultTextView.setText(guessNumber +" Number is too less");
            mGuessNumberTextView.setBackgroundResource(R.color.number_too_less);
        }

    }
    private String NameLevel(int level){
        if(level == 0 )
            return "Easy";
        else
            return  "Hard";
    }
    private void newGame(int level){
        mGame = new Game(level);
        if(level == 0) {
            mGuessLevel.setText(NameLevel(Level));
            mGuessNumberTextView.setText("X");
            mGuessNumberTextView.setBackgroundResource(R.color.incorrect_guess);
            mResultTextView.setText("");
        }
        else if(level ==1 ){
            mGuessLevel.setText(NameLevel(Level));
            mGuessNumberTextView.setText("XX");
            mGuessNumberTextView.setBackgroundResource(R.color.incorrect_guess);
            mResultTextView.setText("");
        }
    }

}
