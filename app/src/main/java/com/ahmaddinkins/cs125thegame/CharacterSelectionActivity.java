package com.ahmaddinkins.cs125thegame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class CharacterSelectionActivity extends AppCompatActivity {
    private static final String TAG = "filtered";
    private int character = -1;
    private ImageButton boyButton;
    private ImageButton girlButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);
        boyButton = findViewById(R.id.boyButton);
        girlButton = findViewById(R.id.girlButton);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void ready(View view) {
        Intent intent = new Intent();
        if (character != -1) {
            intent.putExtra("selectedCharacter", character);
            setResult(Activity.RESULT_OK, intent);
        } else {
            setResult(Activity.RESULT_CANCELED, intent);
        }
        finish();
    }

    public void boy(View view) {
        girlButton.setBackground(getDrawable(R.drawable.temp_girl_button));
        boyButton.setBackgroundColor(0xffffffff);
        boyButton.setImageDrawable(getDrawable(R.drawable.temp_boy_button));
        character = R.drawable.boy;
        Log.i(TAG, "boyClick");
    }

    public void girl(View view) {
        boyButton.setBackground(getDrawable(R.drawable.temp_boy_button));
        girlButton.setBackgroundColor(0xffffffff);
        girlButton.setImageDrawable(getDrawable(R.drawable.temp_girl_button));
        character = R.drawable.girl;
        Log.i(TAG, "girlClick");
    }
}
