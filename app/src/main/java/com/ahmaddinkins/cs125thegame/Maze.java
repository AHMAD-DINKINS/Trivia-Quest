package com.ahmaddinkins.cs125thegame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that produces a maze filled with enemies and the player at a random starting
 * */
public class Maze {
    /**The size of the Maze.**/
    private static final int SIZE = 5;
    /**Random object used to choose a neighbor at random**/
    private static Random random = new Random();
    /**The Maze**/
    private static ArrayList<ArrayList<Cell>> maze;
    /**
     * Return a new Maze.
     * @return a freshly generated maze.
     */
    public static ArrayList<ArrayList<Cell>> getMaze() {
        generateMaze();
        return maze;
    }
    /**
     * Creates a grid and then starts creating the paths.
     */
    private static void generateMaze() {
        ArrayList<ArrayList<Cell>> newMaze = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            newMaze.add(new ArrayList<Cell>());
            for (int column = 0; column < SIZE; column++) {
                newMaze.get(row).add(new Cell(row, row * SIZE + column));
            }
        }
        maze = newMaze;
        for (List<Cell> cells : maze) {
            for (Cell cell : cells) {
                cell.findNeighbors();
            }
        }
        ArrayList<Cell> stack = new ArrayList<>();
        Cell start = maze.get(random.nextInt(SIZE - 1)).get(random.nextInt(SIZE - 1));
        generate(start, stack);
    }
    /**
     * Recursive function that generates a maze from a grid.
     * @param cell The current celll within the stack.
     * @param stack The list that is used to keep track of a path of cells.
     */
    private static void generate(final Cell cell, final ArrayList<Cell> stack) {
        if (!cell.visited) {
            cell.visited = true;
            stack.add(cell);
        }
        ArrayList<Cell> nextPossibleCells = new ArrayList<>();
        for (Cell neighbor : cell.neighbors) {
            if (neighbor != null && !neighbor.visited) {
                nextPossibleCells.add(neighbor);
            }
        }
        if (nextPossibleCells.size() == 0) {
            if (stack.size() > 0) {
                generate(stack.remove(stack.size() - 1), stack);
            }
            return;
        } else if (nextPossibleCells.size() == 1) {
            Cell next = nextPossibleCells.get(0);
            cell.walls[cell.neighbors.indexOf(next)] = false;
            next.walls[next.neighbors.indexOf(cell)] = false;
            generate(next, stack);
            return;
        }
        Cell next = nextPossibleCells.get(random.nextInt(nextPossibleCells.size() - 1));
        cell.walls[cell.neighbors.indexOf(next)] = false;
        next.walls[next.neighbors.indexOf(cell)] = false;
        generate(next, stack);
    }
    /**
     * Nested class that represents the cells of a maze.
     */
    private static class Cell  {
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
        private void findNeighbors() {
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
                    adjacentCells.add(index, maze.get(x + xPosition).get(y%SIZE + yPosition));
                } catch (Exception e) {
                    adjacentCells.add(index, null);
                }
            }
            neighbors = adjacentCells;
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
            return output + " ";
        }
    }

    public static void main(String[] args) {
        getMaze();
        for (List<Cell> cells : Maze.maze) {
            for (Cell cell : cells) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
