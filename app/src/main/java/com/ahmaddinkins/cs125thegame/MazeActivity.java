package com.ahmaddinkins.cs125thegame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.ahmaddinkins.cs125thegame.Maze.maze;

public class MazeActivity extends AppCompatActivity {
    private static final String TAG = "filtered";
    private Drawable character;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivityForResult(new Intent(MazeActivity.this, CharacterSelectionActivity.class), 1);
        setContentView(R.layout.activity_maze);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MazeActivity.this, EncounterActivity.class));
            }
        });
    }

    private void init(final GridLayout gridMaze, final GridLayout characterGridMaze) {
        Log.i(TAG, "init");
        for(ArrayList<Cell> row : Maze.getMaze()) {
            for (Cell cell : row) {
                ImageView imageView = new ImageView(MazeActivity.this);
                imageView.setImageDrawable(parseCell(cell));
                gridMaze.addView(imageView, cell.getIndex());
                if (cell == Maze.start) {
                    ImageView characterImageView = new ImageView(MazeActivity.this);
                    characterImageView.setImageDrawable(character);
                    characterGridMaze.addView(characterImageView, cell.getIndex());
                } else {
                    ImageView characterImageView = new ImageView(MazeActivity.this);
                    characterImageView.setImageDrawable(getDrawable(R.drawable.clear));
                    characterGridMaze.addView(characterImageView, cell.getIndex());
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "result");
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                character = getDrawable(data.getIntExtra("selectedCharacter", R.drawable.alien));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                character = getDrawable(R.drawable.alien);
            }
        }
        GridLayout mazeGrid = findViewById(R.id.mazeGrid);
        GridLayout characterMazeGrid = findViewById(R.id.characterMazeGrid);
        init(mazeGrid, characterMazeGrid);
    }

    private void update() {
    }

    public void upClick(View view) {
        Log.i(TAG, "upClick");
    }

    public void rightClick(View view) {
        Log.i(TAG, "rightClick");
    }

    public void leftClick(View view) {
        Log.i(TAG, "leftClick");
    }

    public void downClick(View view) {
        Log.i(TAG, "downClick");
    }

    private Drawable parseCell(Cell cell) {
        switch (cell.toString()) {
            case "L":
                return getDrawable(R.drawable.left);
            case "T":
                return getDrawable(R.drawable.top);
            case "R":
                return getDrawable(R.drawable.right);
            case "B":
                return getDrawable(R.drawable.bottom);
            case "LT":
                return getDrawable(R.drawable.left_top);
            case "LR":
                return getDrawable(R.drawable.left_right);
            case "LB":
                return getDrawable(R.drawable.left_bottom);
            case "TR":
                return getDrawable(R.drawable.top_right);
            case "TB":
                return getDrawable(R.drawable.top_bottom);
            case "RB":
                return getDrawable(R.drawable.right_bottom);
            case "LRB":
                return getDrawable(R.drawable.left_right_bottom);
            case "LTB":
                return getDrawable(R.drawable.left_top_bottom);
            case "LTR":
                return getDrawable(R.drawable.left_top_right);
            case "TRB":
                return getDrawable(R.drawable.top_right_bottom);
        }
        return getDrawable(R.drawable.fail);
    }
}
