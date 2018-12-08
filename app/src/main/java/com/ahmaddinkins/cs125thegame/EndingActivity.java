package com.ahmaddinkins.cs125thegame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;

public class EndingActivity extends AppCompatActivity {
    private ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        layout = findViewById(R.id.endingConstraint);
        Bundle healthData = getIntent().getExtras();
        int health = healthData.getInt("health");
        if (health == 0) {
            layout.setBackground(getDrawable(R.drawable.pepe_hands));
        } else {
            layout.setBackground(getDrawable(R.drawable.ez_clap));
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}
