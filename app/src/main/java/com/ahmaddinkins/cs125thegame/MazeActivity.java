package com.ahmaddinkins.cs125thegame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MazeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MazeActivity.this, EncounterActivity.class));
            }
        });

        GridLayout mazeGrid = findViewById(R.id.mazeGrid);
        init(mazeGrid);
    }

    private void init(GridLayout gridMaze) {
        for(ArrayList<Cell> row : Maze.getMaze()) {
            for (Cell cell : row) {
                ImageView imageView = new ImageView(MazeActivity.this);
                imageView.setImageDrawable(parseCell(cell));
                gridMaze.addView(imageView, cell.getY());
            }
        }
        System.out.println(gridMaze.getChildCount());
    }

    private Drawable parseCell(Cell cell) {
        System.out.print(cell.toString() + "|");
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
