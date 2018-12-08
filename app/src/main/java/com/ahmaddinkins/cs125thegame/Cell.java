package com.ahmaddinkins.cs125thegame;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

/**
 * Nested class that represents the cells of a maze.
 */
public class Cell  {
    /** x-coordinate of cell.*/
    private int x;
    /** y-coordinate of cell.*/
    private int y;
    /** visited state of cell.*/
    private boolean visited;
    private int imageId = -1;
    /** walls of cell.*/
    private boolean[] walls = {true, true, true, true};
    /** neighboring cells.*/
    private ArrayList<Cell> neighbors;
    /**The enemy within the cell*/
    private boolean enemy = false;
    /** The number of enemies on the map*/
    static int numEnemies = 0;
    /**
     * Constuctor for Cells objects.
     * @param setX The row the cell is within.
     * @param setY The column the cell is within.
     */
    Cell(final int setX, final int setY) {
        x = setX;
        y = setY;
        /* Random object for determining wether or not an enemy spawns*/
        Random random = new Random();
        int spawnChance = random.nextInt(Maze.SIZE * Maze.SIZE);
        if (spawnChance >= 0 && spawnChance <= 9) {
            enemy = true;
            numEnemies++;
        }
    }
    /**
     * Function that finds all of the horizontal and adjacent neighboring cells.
     */
    void findNeighbors() {
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        for (int index = 0; index < 4; index++) {
            int xPosition = 0; // horizonatal neighbor
            int yPosition = 0; // vertical neighbor
            if (index == 0) { // left neighbor
                yPosition--;
            } else if (index == 1) { // top neighbor
                xPosition--;
            } else if (index == 2) { // right neighbor
                yPosition++;
            } else { // bottom neighbor
                xPosition++;
            }
            try {
                adjacentCells.add(index, Maze.maze.get(x + xPosition).get(y%Maze.SIZE + yPosition));
            } catch (Exception e) {
                adjacentCells.add(index, null);
            }
        }
        neighbors = adjacentCells;
    }
    /**
     * Getter for the neighbors of the cell.
     * @return The neighbors of the cell.
     */
    ArrayList<Cell> getNeighbors() {
        return neighbors;
    }
    /**
     * Getter for the cell's walls.
     * @return The walls of the cell.
     */
    boolean[] getWalls() {
        return walls;
    }

    /**
     * Returns the enemy.
     * @return boolean representing if an enemy has spawned.
     */
    boolean getEnemy() {
        return enemy;
    }
    /**
     * getter for the y coordinate
     * @return The index the cell would be at in a one dimensional array of length Maze.SIZE * Maze.SIZE
     */
    int getIndex() {
        return y;
    }
    /**
     * Returns if the cell has been visited or not.
     * @return The visited state of the cell.
     */
    boolean isVisited() {
        return visited;
    }

    /**
     * Method used to mark that an enemy has been defeated.
     */
    void markEnemy() {
        enemy = false;
        numEnemies--;
    }
    /**Checks if the cell has been visited
    boolean isVisited() {
        return visited;
    }

    /**
     * Marks the cell as visited;
     */
    void setVisited() {
        visited = true;
    }
    /**
     * Function that represents a cell as a String.
     * -----------
     * KEY
     * ----------
     * L: Left wall
     * T: Top wall
     * R: Right wall
     * B: Bottom wall
     * @return A string that indicates which walls the cell has up.
     */
    @NonNull
    public String toString() {
        StringBuilder output  = new StringBuilder();
        for (int index = 0; index < walls.length; index++) {
            if (index == 0 && walls[index]) {
                output.append("L");
            } else if (index == 1 && walls[index]) {
                output.append("T");
            } else if (index == 2 && walls[index]) {
                output.append("R");
            } else if (walls[index]){
                output.append("B");
            }
        }
        return output.toString();
    }

    int getImageId() {
        return imageId;
    }

    void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
