package com.ahmaddinkins.cs125thegame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.util.Log;

import java.util.Timer;

public class EncounterActivity extends AppCompatActivity {
    private static final String TAG = "test";

    private RadioGroup radioAnswerGroup;
    private RadioButton radioAnswer;
    private Button answerButton;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encounter);

        result = (TextView) findViewById(R.id.result);
        addListenerButton();

        Log.i(TAG, "onCreate");
    }

    public void addListenerButton() {
        radioAnswerGroup = findViewById(R.id.answerGroup);
        answerButton = findViewById(R.id.submitButton);

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answerId = radioAnswerGroup.getCheckedRadioButtonId();

                if (answerId == -1) {
                    result.setText("Select an answer.");
                    return;
                }

                radioAnswer = (RadioButton) findViewById(answerId);

                if (radioAnswer.getText().equals("1")) {
                    result.setText("Correct");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(EncounterActivity.this, MazeActivity.class));
                        }
                    }, 250);
                } else {
                    result.setText("Incorrect");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }
}
