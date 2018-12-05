package com.ahmaddinkins.cs125thegame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
public class CharacterSelectionActivity extends AppCompatActivity {
    private static final String TAG = "filtered";
    private int character = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);
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
        character = R.drawable.boy;
        Log.i(TAG, "boyClick");
    }

    public void girl(View view) {
        character = R.drawable.girl;
        Log.i(TAG, "girlClick");
    }
}
