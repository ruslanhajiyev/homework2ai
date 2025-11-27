package tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util. Comparator;
import java.util.List;

/**
 * Minimax with Alpha-Beta pruning implementation.
 * Supports depth-limited search with heuristic evaluation for larger boards.
 */
public class AlphaBetaAgent {
    private int nodesExplored;
    private boolean useMoveOrdering;
    private int maxDepth;
    
    public AlphaBetaAgent() {
        this(Integer.MAX_VALUE, true);
    }
    
    public AlphaBetaAgent(int maxDepth, boolean useMoveOrdering) {
        this.maxDepth = maxDepth;
        this.useMoveOrdering = useMoveOrdering;
        this.nodesExplored = 0;
    }
    
    /**
     * Returns best move using Alpha-Beta pruning
     */
    public Move alphaBeta(Board board) {
        nodesExplored = 0;
        List<Move> actions = GameEngine.actions(board);
        
        if (actions.isEmpty()) {
            return null;
        }
        
        char player = GameEngine.player(board);
        boolean isMaximizing = (player == 'X');
        
        // Apply move ordering
        if (useMoveOrdering) {
            actions = orderMoves(board, actions);
        } else {
            Collections.sort(actions);
        }
        
        Move bestMove = null;
        double bestValue = isMaximizing ?  Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        
        for (Move action : actions) {
            Board newBoard = GameEngine.result(board, action);
            double value = alphaBetaValue(newBoard, 1, alpha, beta, !isMaximizing);
            
            if (isMaximizing) {
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = action;
                }
                alpha = Math.max(alpha, bestValue);
            } else {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = action;
                }
                beta = Math.min(beta, bestValue);
            }
        }
        
        return bestMove;
    }
    
    /**
     * Recursive alpha-beta value calculation with depth limit
     */
    private double alphaBetaValue(Board board, int depth, double alpha, double beta, 
                                   boolean isMaximizing) {
        nodesExplored++;
        
        // Terminal or depth limit reached
        if (GameEngine. terminal(board)) {
            Integer utility = GameEngine.utility(board);
            return utility != null ? utility : 0;
        }
        
        if (depth >= maxDepth) {
            return HeuristicEvaluator.evaluate(board);
        }
        
        List<Move> actions = GameEngine.actions(board);
        
        // Apply move ordering
        if (useMoveOrdering) {
            actions = orderMoves(board, actions);
        } else {
            Collections.sort(actions);
        }
        
        if (isMaximizing) {
            double maxValue = Double. NEGATIVE_INFINITY;
            for (Move action : actions) {
                Board newBoard = GameEngine. result(board, action);
                double value = alphaBetaValue(newBoard, depth + 1, alpha, beta, false);
                maxValue = Math.max(maxValue, value);
                alpha = Math.max(alpha, maxValue);
                if (beta <= alpha) {
                    break; // Beta cutoff
                }
            }
            return maxValue;
        } else {
            double minValue = Double.POSITIVE_INFINITY;
            for (Move action : actions) {
                Board newBoard = GameEngine.result(board, action);
                double value = alphaBetaValue(newBoard, depth + 1, alpha, beta, true);
                minValue = Math.min(minValue, value);
                beta = Math.min(beta, minValue);
                if (beta <= alpha) {
                    break; // Alpha cutoff
                }
            }
            return minValue;
        }
    }
    
    /**
     * Order moves to improve pruning efficiency. 
     * Strategy: center first, then by heuristic value
     */
    private List<Move> orderMoves(Board board, List<Move> moves) {
        int m = board.getSize();
        int center = m / 2;
        
        List<MoveScore> scoredMoves = new ArrayList<>();
        
        for (Move move : moves) {
            double score = 0;
            
            // Prefer center
            int distToCenter = Math.abs(move.getRow() - center) + Math.abs(move.getCol() - center);
            score -= distToCenter;
            
            // Quick heuristic evaluation
            Board newBoard = GameEngine.result(board, move);
            score += HeuristicEvaluator.evaluate(newBoard) * 0.1;
            
            scoredMoves.add(new MoveScore(move, score));
        }
        
        // Sort by score (descending), then lexicographically for ties
        Collections.sort(scoredMoves, new Comparator<MoveScore>() {
            @Override
            public int compare(MoveScore ms1, MoveScore ms2) {
                int scoreComp = Double.compare(ms2.score, ms1.score);
                if (scoreComp != 0) return scoreComp;
                return ms1.move.compareTo(ms2.move);
            }
        });
        
        List<Move> orderedMoves = new ArrayList<>();
        for (MoveScore ms : scoredMoves) {
            orderedMoves.add(ms.move);
        }
        
        return orderedMoves;
    }
    
    public int getNodesExplored() {
        return nodesExplored;
    }
    
    /**
     * Helper class for move ordering
     */
    private static class MoveScore {
        Move move;
        double score;
        
        MoveScore(Move move, double score) {
            this.move = move;
            this.score = score;
        }
    }
}