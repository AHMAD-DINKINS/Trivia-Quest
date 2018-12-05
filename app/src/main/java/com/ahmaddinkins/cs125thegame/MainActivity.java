package com.ahmaddinkins.cs125thegame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button start = findViewById(R.id.startButton);
        start.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, MazeActivity.class));
            }
        });
    }

    public void instructions(View view) {
        startActivity(new Intent(MainActivity.this, InstructionsActivity.class));
    }
}
