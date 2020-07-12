package com.example.quizzlet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestions;
    int mIndex;
    int mQuestion;
    TextView mScoreTv;
    ProgressBar mProgressBar;
    int mScore;


    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true)
    };


    final int PROGRESS_BAR_INC = (int) Math.ceil(100.0 / mQuestionBank.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mScore = savedInstanceState.getInt("Scorekey");
            mIndex = savedInstanceState.getInt("Indexkey");
        }else{
            mScore=0;
            mIndex=0;
        }

        mScoreTv = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);

        mQuestions = (TextView) findViewById(R.id.question_text_view);
        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestions.setText(mQuestion);

        mScoreTv.setText(mScore+"/"+mQuestionBank.length);


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestionBank[mIndex % mQuestionBank.length].isAnswer()) {
                    mScore++;
                }
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "False Pressed", Toast.LENGTH_SHORT).show();
                if (!mQuestionBank[mIndex % mQuestionBank.length].isAnswer()) {

                    mScore++;
                }

                updateQuestion();
            }
        });


    }

    public void updateQuestion() {
        mIndex = (mIndex + 1) % mQuestionBank.length;
        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestions.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INC);
        mScoreTv.setText("Score " + mScore + "/" + mQuestionBank.length);

        if (mIndex == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("bas khatam");
            alert.setCancelable(false);
            alert.setMessage("YOU SCORED " + mScore + " out of " + mQuestionBank.length);
            alert.setPositiveButton("Close application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("Scorekey", mScore);
        outState.putInt("Indexkey", mIndex);


    }

}