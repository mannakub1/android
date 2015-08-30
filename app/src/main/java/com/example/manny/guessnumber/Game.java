package com.example.manny.guessnumber;

import android.util.Log;

import java.util.Random;

/**
 * Created by manny on 8/30/2015 AD.
 */
public class Game {

    private static final String TAG = "Game";
    protected enum CompareResult{
        TOO_SMALL , EQUAL , TOO_BIG
    }
    private int mAnswer;
    private int mTotalGuesses;
    private Random random = new Random();

    public Game(int level){
        mTotalGuesses = 0;
        if(level == 0)
            mAnswer = random.nextInt(10);
        else if(level == 1)
            mAnswer = random.nextInt(100);

        Log.d(TAG, "The answer is " + mAnswer);
    }
    public CompareResult submitGuess(int guessNumber){
        mTotalGuesses++;
        if(guessNumber == mAnswer)
            return  CompareResult.EQUAL;
        else if(guessNumber < mAnswer)
            return  CompareResult.TOO_SMALL;
        else
            return CompareResult.TOO_BIG;

    }

    public int getTotalGuesses(){
        return mTotalGuesses;
    }

}
