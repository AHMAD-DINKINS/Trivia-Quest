package com.ahmaddinkins.cs125thegame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

    private RadioGroup radioAnswerGroup;
    private RadioButton radioAnswer;
    private Button answerButton;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encounter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        result = findViewById(R.id.result);
        addListenerButton();
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
                            finish();
                        }
                    }, 250);
                } else {
                    result.setText("Incorrect");
                }
            }
        });
    }

}
