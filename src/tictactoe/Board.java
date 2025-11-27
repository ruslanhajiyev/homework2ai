package tictactoe;

import java.util. Arrays;

/**
 * Immutable board representation for Tic-Tac-Toe.
 * Supports m×m boards with k-in-a-row win condition.
 */
public class Board {
    private final int m; // board size
    private final int k; // win condition
    private final char[][] grid;
    private final char currentPlayer;
    private final int moveCount;
    
    /**
     * Creates initial empty board
     */
    public Board(int m, int k) {
        this. m = m;
        this. k = k;
        this. grid = new char[m][m];
        for (int i = 0; i < m; i++) {
            Arrays.fill(grid[i], ' ');
        }
        this.currentPlayer = 'X';
        this.moveCount = 0;
    }
    
    /**
     * Private constructor for creating new board states
     */
    private Board(int m, int k, char[][] grid, char currentPlayer, int moveCount) {
        this.m = m;
        this.k = k;
        this.grid = new char[m][m];
        for (int i = 0; i < m; i++) {
            this.grid[i] = Arrays. copyOf(grid[i], m);
        }
        this. currentPlayer = currentPlayer;
        this.moveCount = moveCount;
    }
    
    /**
     * Returns a new board with the move applied
     */
    public Board makeMove(Move move) {
        if (grid[move.getRow()][move.getCol()] != ' ') {
            throw new IllegalArgumentException("Invalid move: cell already occupied");
        }
        
        char[][] newGrid = new char[m][m];
        for (int i = 0; i < m; i++) {
            newGrid[i] = Arrays.copyOf(grid[i], m);
        }
        newGrid[move.getRow()][move.getCol()] = currentPlayer;
        
        char nextPlayer = (currentPlayer == 'X') ?  'O' : 'X';
        return new Board(m, k, newGrid, nextPlayer, moveCount + 1);
    }
    
    public int getSize() {
        return m;
    }
    
    public int getWinCondition() {
        return k;
    }
    
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public char getCell(int row, int col) {
        return grid[row][col];
    }
    
    public int getMoveCount() {
        return moveCount;
    }
    
    public boolean isEmpty(int row, int col) {
        return grid[row][col] == ' ';
    }
    
    public boolean isFull() {
        return moveCount == m * m;
    }
    
    /**
     * Creates a deep copy of the grid
     */
    public char[][] getGridCopy() {
        char[][] copy = new char[m][m];
        for (int i = 0; i < m; i++) {
            copy[i] = Arrays.copyOf(grid[i], m);
        }
        return copy;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(grid[i][j]);
                if (j < m - 1) sb.append("|");
            }
            sb.append("\n");
            if (i < m - 1) {
                for (int j = 0; j < m; j++) {
                    sb.append("-");
                    if (j < m - 1) sb.append("+");
                }
                sb. append("\n");
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Board)) return false;
        Board other = (Board) obj;
        return m == other.m && k == other.k && 
               currentPlayer == other.currentPlayer &&
               Arrays.deepEquals(grid, other. grid);
    }
    
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid) * 31 + currentPlayer;
    }
}