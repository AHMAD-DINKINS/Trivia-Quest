package com.ahmaddinkins.cs125thegame;

import java.util.ArrayList;

/**
 * Nested class that represents the cells of a maze.
 */
public class Cell  {
    private int x;
    private int y;
    private boolean visited;
    private boolean[] walls = {true, true, true, true};
    private ArrayList<Cell> neighbors;
    /**
     * Constuctor for Cells objects.
     * @param setX The row the cell is within.
     * @param setY The column the cell is within.
     */
    Cell(final int setX, final int setY) {
        x = setX;
        y = setY;
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
    ArrayList<Cell> getNeighbors() {
        return neighbors;
    }
    boolean[] getWalls() {
        return walls;
    }
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }
    boolean isVisited() {
        return visited;
    }
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
    public String toString() {
        String output  = "";
        for (int index = 0; index < walls.length; index++) {
            if (index == 0 && walls[index]) {
                output += "L";
            } else if (index == 1 && walls[index]) {
                output += "T";
            } else if (index == 2 && walls[index]) {
                output += "R";
            } else if (walls[index]){
                output += "B";
            }
        }
        return output;
    }
}
