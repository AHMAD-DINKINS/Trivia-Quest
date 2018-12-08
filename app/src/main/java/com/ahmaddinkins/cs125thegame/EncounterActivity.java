package com.ahmaddinkins.cs125thegame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EncounterActivity extends AppCompatActivity {

    private RadioGroup radioAnswerGroup;
    private RadioButton radioAnswer;
    private TextView result;
    private TextView temp;
    private RequestQueue requestQueue;
    private RadioButton[] answers;
    private String correctAnswer;
    private int damage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encounter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestQueue = Volley.newRequestQueue(this);
        temp = findViewById(R.id.questionView);
        RadioButton answerOne = findViewById(R.id.answerOne);
        RadioButton answerTwo = findViewById(R.id.answerTwo);
        RadioButton answerThree = findViewById(R.id.answerThree);
        RadioButton answerFour = findViewById(R.id.answerFour);
        damage = 0;
        ImageView characterAvatar = findViewById(R.id.characterAvatar);
        ImageView enemyAvatar = findViewById(R.id.enemyAvatar);
        characterAvatar.setImageDrawable(MazeActivity.getCharacter());
        Bundle extraData = getIntent().getExtras();
        int enemyId = extraData.getInt("enemyImage");
        enemyAvatar.setImageDrawable(getDrawable(enemyId));

        answers = new RadioButton[]{answerOne, answerTwo, answerThree, answerFour};

        result = findViewById(R.id.result);
        addListenerButton();

        startAPICall();
    }

    public void addListenerButton() {
        radioAnswerGroup = findViewById(R.id.answerGroup);
        Button answerButton = findViewById(R.id.submitButton);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answerId = radioAnswerGroup.getCheckedRadioButtonId();

                if (answerId == -1) {
                    result.setText(getString(R.string.no_answer));
                    return;
                }

                radioAnswer = findViewById(answerId);

                if (radioAnswer.getText().equals(correctAnswer)) {
                    result.setText(getString(R.string.correct_answer));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent result = new Intent();
                            result.putExtra("damage", damage);
                            setResult(Activity.RESULT_OK, result);
                            finish();
                        }
                    }, 250);
                } else {
                    result.setText(getString(R.string.wrong_answer));
                    damage++;
                }
            }
        });
    }

    public void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://opentdb.com/api.php?amount=1&category=18&type=multiple",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            parseJson(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    temp.setText(error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseJson(JSONObject json) {
        try {
            String question = json.getJSONArray("results").getJSONObject(0).getString("question");
            question = question.replaceAll("&quot;", "\"");
            question = question.replaceAll("&#039;", "'");
            temp.setText(question);
            int correct = (int)(Math.random() * 4);
            Integer incorrectNum = 0;
            for (int i = 0; i < answers.length; i++) {
                System.out.print(answers[i]);
                if (i == correct) {
                    correctAnswer = json.getJSONArray("results").
                            getJSONObject(0).getString("correct_answer");
                    correctAnswer = correctAnswer.replaceAll("&quot;", "\"");
                    correctAnswer = correctAnswer.replaceAll("&#039;", "'");
                    answers[i].setText(correctAnswer);
                } else {
                    String incorrectAnswer = json.getJSONArray("results").getJSONObject(0)
                            .getJSONArray("incorrect_answers").getString(incorrectNum);
                    incorrectAnswer = incorrectAnswer.replaceAll("&quot;", "\"");
                    incorrectAnswer = incorrectAnswer.replaceAll("&#039;", "'");
                    answers[i].setText(incorrectAnswer);
                    incorrectNum = incorrectNum + 1;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); Disabled back button
        Toast.makeText(this, "Back Button Disabled", Toast.LENGTH_LONG).show();
    }
}
