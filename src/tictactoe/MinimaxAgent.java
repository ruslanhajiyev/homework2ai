package tictactoe;

import java. util.Collections;
import java.util.List;

/**
 * Plain Minimax algorithm implementation.
 * Used as oracle for 3×3 boards.
 */
public class MinimaxAgent {
    private int nodesExplored;
    
    public MinimaxAgent() {
        this.nodesExplored = 0;
    }
    
    /**
     * Returns best move using plain Minimax algorithm
     */
    public Move minimax(Board board) {
        nodesExplored = 0;
        List<Move> actions = GameEngine.actions(board);
        
        if (actions. isEmpty()) {
            return null;
        }
        
        // Sort for deterministic tie-breaking
        Collections.sort(actions);
        
        char player = GameEngine.player(board);
        boolean isMaximizing = (player == 'X');
        
        Move bestMove = null;
        double bestValue = isMaximizing ? Double. NEGATIVE_INFINITY : Double. POSITIVE_INFINITY;
        
        for (Move action : actions) {
            Board newBoard = GameEngine.result(board, action);
            double value = minimaxValue(newBoard, ! isMaximizing);
            
            if (isMaximizing) {
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = action;
                }
            } else {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = action;
                }
            }
        }
        
        return bestMove;
    }
    
    /**
     * Recursive minimax value calculation
     */
    private double minimaxValue(Board board, boolean isMaximizing) {
        nodesExplored++;
        
        if (GameEngine.terminal(board)) {
            Integer utility = GameEngine.utility(board);
            return utility != null ? utility : 0;
        }
        
        List<Move> actions = GameEngine. actions(board);
        Collections.sort(actions);
        
        if (isMaximizing) {
            double maxValue = Double.NEGATIVE_INFINITY;
            for (Move action : actions) {
                Board newBoard = GameEngine.result(board, action);
                double value = minimaxValue(newBoard, false);
                maxValue = Math.max(maxValue, value);
            }
            return maxValue;
        } else {
            double minValue = Double.POSITIVE_INFINITY;
            for (Move action : actions) {
                Board newBoard = GameEngine.result(board, action);
                double value = minimaxValue(newBoard, true);
                minValue = Math. min(minValue, value);
            }
            return minValue;
        }
    }
    
    public int getNodesExplored() {
        return nodesExplored;
    }
}