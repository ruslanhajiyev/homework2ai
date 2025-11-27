package tictactoe;

/**
 * Heuristic evaluation function for non-terminal board positions.
 * Used for depth-limited search on larger boards.
 */
public class HeuristicEvaluator {
    
    /**
     * Evaluates board position from X's perspective. 
     * Positive values favor X, negative values favor O. 
     * 
     * Considers:
     * - Potential winning lines
     * - Threats (k-1 in a row)
     * - Partial patterns (2-in-a-row, 3-in-a-row, etc.)
     */
    public static double evaluate(Board board) {
        Character winner = GameEngine.winner(board);
        if (winner != null) {
            return (winner == 'X') ? 10000 : -10000;
        }
        if (board.isFull()) {
            return 0;
        }
        
        int m = board.getSize();
        int k = board.getWinCondition();
        
        double score = 0;
        
        // Evaluate all possible lines
        // Rows
        for (int i = 0; i < m; i++) {
            score += evaluateLine(board, i, 0, 0, 1, k);
        }
        
        // Columns
        for (int j = 0; j < m; j++) {
            score += evaluateLine(board, 0, j, 1, 0, k);
        }
        
        // Diagonals (top-left to bottom-right)
        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= m - k; j++) {
                score += evaluateLine(board, i, j, 1, 1, k);
            }
        }
        
        // Diagonals (top-right to bottom-left)
        for (int i = 0; i <= m - k; i++) {
            for (int j = k - 1; j < m; j++) {
                score += evaluateLine(board, i, j, 1, -1, k);
            }
        }
        
        return score;
    }
    
    /**
     * Evaluates a single line (row, column, or diagonal segment of length k)
     */
    private static double evaluateLine(Board board, int startRow, int startCol,
                                       int dRow, int dCol, int k) {
        int m = board.getSize();
        int xCount = 0;
        int oCount = 0;
        int emptyCount = 0;
        
        int row = startRow;
        int col = startCol;
        
        for (int i = 0; i < k; i++) {
            if (row < 0 || row >= m || col < 0 || col >= m) {
                return 0;
            }
            
            char cell = board.getCell(row, col);
            if (cell == 'X') xCount++;
            else if (cell == 'O') oCount++;
            else emptyCount++;
            
            row += dRow;
            col += dCol;
        }
        
        // If both players have marks in this line, it's not valuable
        if (xCount > 0 && oCount > 0) {
            return 0;
        }
        
        // Score based on potential
        if (xCount > 0) {
            return Math.pow(10, xCount);
        } else if (oCount > 0) {
            return -Math.pow(10, oCount);
        }
        
        return 0;
    }
}