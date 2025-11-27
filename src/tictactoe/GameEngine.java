package tictactoe;

import java.util. ArrayList;
import java.util. List;

/**
 * Game engine implementing all required game rules and logic.
 * Provides methods for state transitions, move generation, and terminal state detection.
 */
public class GameEngine {
    
    /**
     * Creates initial game state
     */
    public static Board initialState(int m, int k) {
        return new Board(m, k);
    }
    
    /**
     * Returns current player ('X' or 'O')
     */
    public static char player(Board board) {
        return board.getCurrentPlayer();
    }
    
    /**
     * Returns list of all legal moves
     */
    public static List<Move> actions(Board board) {
        List<Move> moves = new ArrayList<>();
        int m = board.getSize();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (board.isEmpty(i, j)) {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }
    
    /**
     * Returns new board state after applying action
     */
    public static Board result(Board board, Move action) {
        return board.makeMove(action);
    }
    
    /**
     * Returns winner ('X', 'O', or null if no winner)
     */
    public static Character winner(Board board) {
        int m = board.getSize();
        int k = board.getWinCondition();
        
        // Check rows
        for (int i = 0; i < m; i++) {
            Character rowWinner = checkLine(board, i, 0, 0, 1, k);
            if (rowWinner != null) return rowWinner;
        }
        
        // Check columns
        for (int j = 0; j < m; j++) {
            Character colWinner = checkLine(board, 0, j, 1, 0, k);
            if (colWinner != null) return colWinner;
        }
        
        // Check diagonals (all possible k-length diagonals)
        // Top-left to bottom-right diagonals
        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= m - k; j++) {
                Character diagWinner = checkLine(board, i, j, 1, 1, k);
                if (diagWinner != null) return diagWinner;
            }
        }
        
        // Top-right to bottom-left diagonals
        for (int i = 0; i <= m - k; i++) {
            for (int j = k - 1; j < m; j++) {
                Character diagWinner = checkLine(board, i, j, 1, -1, k);
                if (diagWinner != null) return diagWinner;
            }
        }
        
        return null;
    }
    
    /**
     * Helper method to check a line for k-in-a-row
     */
    private static Character checkLine(Board board, int startRow, int startCol, 
                                       int dRow, int dCol, int k) {
        int m = board.getSize();
        int row = startRow;
        int col = startCol;
        
        char first = board.getCell(row, col);
        if (first == ' ') return null;
        
        int count = 1;
        for (int i = 1; i < k; i++) {
            row += dRow;
            col += dCol;
            if (row < 0 || row >= m || col < 0 || col >= m) return null;
            if (board. getCell(row, col) != first) return null;
            count++;
        }
        
        return (count == k) ? first : null;
    }
    
    /**
     * Returns true if game is over (win, loss, or draw)
     */
    public static boolean terminal(Board board) {
        return winner(board) != null || board.isFull();
    }
    
    /**
     * Returns utility: +1 if X wins, -1 if O wins, 0 if draw, null otherwise
     */
    public static Integer utility(Board board) {
        Character w = winner(board);
        if (w != null) {
            return (w == 'X') ? 1 : -1;
        }
        if (board.isFull()) {
            return 0;
        }
        return null;
    }
}