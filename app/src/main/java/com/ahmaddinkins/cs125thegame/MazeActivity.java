package com.ahmaddinkins.cs125thegame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.ahmaddinkins.cs125thegame.Maze.maze;

/**
 * Class that will
 */
public class MazeActivity extends AppCompatActivity {
    private Random random = new Random();
    private GridLayout characterMazeGrid;
    private TextView healthView;
    private TextView levelView;
    private static Drawable character;
    private int position;
    private static final int[] ENEMIES = {R.drawable.slime, R.drawable.ghost, R.drawable.pyron};
    private static final int NUM_LEVELS = 5;
    private static int currentLevel = 0;
    private static int health = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (character == null) {
            startActivityForResult(new Intent(MazeActivity.this, CharacterSelectionActivity.class), 1);
        } else {
            GridLayout mazeGrid = findViewById(R.id.mazeGrid);
            characterMazeGrid = findViewById(R.id.characterMazeGrid);
            healthView = findViewById(R.id.healthView);
            healthView.setText("Health: " + health);
            levelView = findViewById(R.id.levelView);
            levelView.setText("Level: " + currentLevel);
            init(mazeGrid);
        }
    }

    private void init(final GridLayout gridMaze) {
        for(ArrayList<Cell> row : Maze.getMaze()) {
            for (Cell cell : row) {
                ImageView imageView = new ImageView(MazeActivity.this);
                imageView.setImageDrawable(parseCell(cell));
                gridMaze.addView(imageView, cell.getIndex());
                if (cell == Maze.start) {
                    if (cell.getEnemy()) {
                        cell.markEnemy();
                    }
                    ImageView characterImageView = new ImageView(MazeActivity.this);
                    characterImageView.setBackground(character);
                    characterMazeGrid.addView(characterImageView, cell.getIndex());
                    position = cell.getIndex();
                } else if (cell.getEnemy()) {
                    ImageView enemyImageView = new ImageView(MazeActivity.this);
                    int selectedEnemy = ENEMIES[random.nextInt(ENEMIES.length)];
                    cell.setImageId(selectedEnemy);
                    enemyImageView.setBackground(getDrawable(selectedEnemy));
                    characterMazeGrid.addView(enemyImageView, cell.getIndex());
                } else {
                    ImageView clearImageView = new ImageView(MazeActivity.this);
                    clearImageView.setBackground(getDrawable(R.drawable.clear));
                    characterMazeGrid.addView(clearImageView, cell.getIndex());
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                character = getDrawable(data.getIntExtra("selectedCharacter", R.drawable.alien));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                character = getDrawable(R.drawable.alien);
            }
            GridLayout mazeGrid = findViewById(R.id.mazeGrid);
            characterMazeGrid = findViewById(R.id.characterMazeGrid);
            healthView = findViewById(R.id.healthView);
            healthView.setText("Health: " + health);
            levelView = findViewById(R.id.levelView);
            levelView.setText("Level: " + currentLevel);
            init(mazeGrid);
        } else if (requestCode == 2) {
            health -= data.getIntExtra("damage", 0);
            healthView.setText("Health: " + health);
            //Will decide what happens to player here
            if (Cell.numEnemies == 0) {
                currentLevel++;
                if (currentLevel < NUM_LEVELS) {
                    Toast.makeText(this, "Loading Next Maze...", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MazeActivity.this, MazeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                } else {
                    currentLevel = 0;
                    character = null;
                    health = 20;
                    Toast.makeText(this, "You Win!!!", Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 750);
                }
            }
        }
    }

    public static Drawable getCharacter() {
        return character;
    }

    private void update(final int newPosition) {

        boolean enemy = false;
        Cell newCell = null;
        for(ArrayList<Cell> row : maze) {
            for (Cell cell : row) {
                if (cell.getIndex() == newPosition) {
                    newCell = cell;
                    enemy = cell.getEnemy();
                    characterMazeGrid.getChildAt(position).setBackground(getDrawable(R.drawable.clear));
                    characterMazeGrid.getChildAt(cell.getIndex()).setBackground(character);
                    position = newPosition;
                }
            }
        }
        if (enemy) {
            Intent encounter = new Intent(MazeActivity.this, EncounterActivity.class);
            encounter.putExtra("enemyImage", newCell.getImageId());
            startActivityForResult(encounter, 2);
            newCell.markEnemy();
        }
    }

    public void upClick(View view) {
        if (maze.get(position / Maze.SIZE).get(position % Maze.SIZE).getWalls()[1]){
            return;
        }
        update(position - Maze.SIZE);
    }

    public void rightClick(View view) {
        if (maze.get(position / Maze.SIZE).get(position % Maze.SIZE).getWalls()[2]){
            return;
        }
        update(position + 1);
    }

    public void leftClick(View view) {
        if (maze.get(position / Maze.SIZE).get(position % Maze.SIZE).getWalls()[0]){
            return;
        }
        update(position - 1);
    }

    public void downClick(View view) {
        if (maze.get(position / Maze.SIZE).get(position % Maze.SIZE).getWalls()[3]){
            return;
        }
        update(position + Maze.SIZE);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); Commented to disable what normally happens when back button is pressed.
        Toast.makeText(this, "Back Button Disabled", Toast.LENGTH_SHORT).show();
    }
}
