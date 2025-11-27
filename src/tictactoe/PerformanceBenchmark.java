package tictactoe;

/**
 * Performance benchmarking for comparing algorithms and move ordering.
 */
public class PerformanceBenchmark {
    
    public static void runBenchmarks() {
        System.out.println("=== Performance Benchmarks ===\n");
        
        benchmark3x3();
        benchmark4x4();
        testEquivalence();
    }
    
    private static void benchmark3x3() {
        System.out.println("--- 3×3 Board (k=3) ---");
        Board board = GameEngine.initialState(3, 3);
        
        // Minimax
        MinimaxAgent minimax = new MinimaxAgent();
        long start = System.currentTimeMillis();
        Move minimaxMove = minimax.minimax(board);
        long minimaxTime = System.currentTimeMillis() - start;
        int minimaxNodes = minimax.getNodesExplored();
        
        // Alpha-Beta
        AlphaBetaAgent alphaBeta = new AlphaBetaAgent();
        start = System.currentTimeMillis();
        Move abMove = alphaBeta.alphaBeta(board);
        long abTime = System.currentTimeMillis() - start;
        int abNodes = alphaBeta.getNodesExplored();
        
        System.out.println("Minimax:");
        System.out.println("  Move: " + minimaxMove);
        System.out.println("  Time: " + minimaxTime + "ms");
        System.out.println("  Nodes: " + minimaxNodes);
        
        System.out.println("\nAlpha-Beta:");
        System.out.println("  Move: " + abMove);
        System.out.println("  Time: " + abTime + "ms");
        System.out.println("  Nodes: " + abNodes);
        
        System.out.println("\nSpeedup: " + String.format("%.2fx", (double) minimaxNodes / abNodes));
        System.out.println("Moves match: " + minimaxMove.equals(abMove));
        System.out.println();
    }
    
    private static void benchmark4x4() {
        System.out.println("--- 4×4 Board (k=3) ---");
        Board board = GameEngine.initialState(4, 4);
        
        // Without move ordering
        AlphaBetaAgent abNoOrdering = new AlphaBetaAgent(6, false);
        long start = System. currentTimeMillis();
        Move moveNoOrdering = abNoOrdering.alphaBeta(board);
        long timeNoOrdering = System.currentTimeMillis() - start;
        int nodesNoOrdering = abNoOrdering.getNodesExplored();
        
        // With move ordering
        AlphaBetaAgent abWithOrdering = new AlphaBetaAgent(6, true);
        start = System.currentTimeMillis();
        Move moveWithOrdering = abWithOrdering.alphaBeta(board);
        long timeWithOrdering = System.currentTimeMillis() - start;
        int nodesWithOrdering = abWithOrdering.getNodesExplored();
        
        System.out.println("Alpha-Beta without move ordering:");
        System.out. println("  Move: " + moveNoOrdering);
        System. out.println("  Time: " + timeNoOrdering + "ms");
        System.out.println("  Nodes: " + nodesNoOrdering);
        
        System.out.println("\nAlpha-Beta with move ordering:");
        System.out.println("  Move: " + moveWithOrdering);
        System.out.println("  Time: " + timeWithOrdering + "ms");
        System.out.println("  Nodes: " + nodesWithOrdering);
        
        System. out.println("\nReduction: " + String.format("%.2f%%", 
            100.0 * (nodesNoOrdering - nodesWithOrdering) / nodesNoOrdering));
        System.out.println();
    }
    
    private static void testEquivalence() {
        System.out.println("--- Equivalence Test (3×3) ---");
        Board board = GameEngine.initialState(3, 3);
        
        MinimaxAgent minimax = new MinimaxAgent();
        AlphaBetaAgent alphaBeta = new AlphaBetaAgent();
        
        int testCount = 0;
        int matchCount = 0;
        
        // Test on multiple positions
        for (int i = 0; i < 3 && ! GameEngine.terminal(board); i++) {
            Move mmMove = minimax.minimax(board);
            Move abMove = alphaBeta.alphaBeta(board);
            
            testCount++;
            if (mmMove.equals(abMove)) {
                matchCount++;
            }
            
            board = GameEngine.result(board, mmMove);
        }
        
        System.out.println("Tests: " + testCount);
        System.out.println("Matches: " + matchCount);
        System.out.println("Pass: " + (testCount == matchCount));
        System.out.println();
    }
}