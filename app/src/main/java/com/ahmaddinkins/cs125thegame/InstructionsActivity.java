package com.ahmaddinkins.cs125thegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class InstructionsActivity extends AppCompatActivity {
    private static final String TAG = "filtered";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    public void back(View view) {
        Log.i(TAG, "backClick");
        finish();
    }
}
