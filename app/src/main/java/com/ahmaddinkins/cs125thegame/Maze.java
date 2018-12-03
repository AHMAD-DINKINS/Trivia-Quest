package com.ahmaddinkins.cs125thegame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that produces a maze filled with enemies and the player at a random starting
 * */
public class Maze {
    /**The size of the Maze.**/
    static final int SIZE = 10;
    /**Random object used to choose a neighbor at random**/
    private static Random random = new Random();
    /**The Maze**/
    static ArrayList<ArrayList<Cell>> maze;
    /**
     * Return a new Maze.
     * @return a freshly generated maze.
     */
    static ArrayList<ArrayList<Cell>> getMaze() {
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
        if (!cell.isVisited()) {
            cell.setVisited();
            stack.add(cell);
        }
        ArrayList<Cell> nextPossibleCells = new ArrayList<>();
        for (Cell neighbor : cell.getNeighbors()) {
            if (neighbor != null && !neighbor.isVisited()) {
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
            cell.getWalls()[cell.getNeighbors().indexOf(next)] = false;
            next.getWalls()[next.getNeighbors().indexOf(cell)] = false;
            generate(next, stack);
            return;
        }
        Cell next = nextPossibleCells.get(random.nextInt(nextPossibleCells.size() - 1));
        cell.getWalls()[cell.getNeighbors().indexOf(next)] = false;
        next.getWalls()[next.getNeighbors().indexOf(cell)] = false;
        generate(next, stack);
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
