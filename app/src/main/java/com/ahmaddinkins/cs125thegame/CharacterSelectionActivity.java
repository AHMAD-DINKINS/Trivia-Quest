package com.ahmaddinkins.cs125thegame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CharacterSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button ready = findViewById(R.id.readyButton);
        ready.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(CharacterSelectionActivity.this, MazeActivity.class));
            }
        });
    }
}
