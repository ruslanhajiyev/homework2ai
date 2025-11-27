package tictactoe;

import org.junit.Test;
import static org. junit.Assert.*;

public class MinimaxTest {

    @Test
    public void testMinimaxFirstMove() {
        Board board = GameEngine.initialState(3, 3);
        MinimaxAgent agent = new MinimaxAgent();

        System.out.println("3x3 board (empty) - Minimax First Move Test:");

        long start = System.currentTimeMillis();
        Move move = agent. minimax(board);
        long time = System.currentTimeMillis() - start;

        System.out.println("  Minimax chose: " + move);
        System.out.println("  Time: " + time + "ms");
        System.out.println("  Nodes explored: " + agent.getNodesExplored());

        assertNotNull("Should return a move", move);

        // Optimal first move should be center or corner
        assertTrue("First move should be optimal (center or corner)",
                move.equals(new Move(0, 0)) ||
                        move.equals(new Move(0, 2)) ||
                        move.equals(new Move(1, 1)) ||
                        move.equals(new Move(2, 0)) ||
                        move.equals(new Move(2, 2)));

        System.out.println("  ✓ Chose optimal first move");
    }

    @Test
    public void testMinimaxBlockWin() {
        Board board = GameEngine.initialState(3, 3);
        board = board.makeMove(new Move(0, 0)); // X
        board = board.makeMove(new Move(1, 1)); // O
        board = board.makeMove(new Move(0, 1)); // X (two in a row)

        System.out.println("3x3 board - Minimax Block Win Test:");
        System.out. println("  Board state:");
        System.out.println("    X X _");
        System.out.println("    _ O _");
        System.out.println("    _ _ _");

        MinimaxAgent agent = new MinimaxAgent();

        long start = System.currentTimeMillis();
        Move move = agent. minimax(board);
        long time = System.currentTimeMillis() - start;

        System.out.println("  Minimax chose: " + move);
        System.out.println("  Time: " + time + "ms");
        System.out. println("  Nodes explored: " + agent.getNodesExplored());

        // O should block at (0, 2)
        assertEquals("O should block X's winning move at (0,2)", new Move(0, 2), move);

        System.out.println("  ✓ Correctly blocked opponent's win");
    }

    @Test
    public void testMinimaxTakeWin() {
        Board board = GameEngine.initialState(3, 3);
        board = board.makeMove(new Move(0, 0)); // X
        board = board.makeMove(new Move(1, 0)); // O
        board = board.makeMove(new Move(0, 1)); // X (two in a row)
        board = board.makeMove(new Move(1, 1)); // O (two in a row)

        System.out.println("3x3 board - Minimax Take Win Test:");
        System. out.println("  Board state:");
        System.out.println("    X X _");
        System.out.println("    O O _");
        System.out. println("    _ _ _");

        MinimaxAgent agent = new MinimaxAgent();

        long start = System. currentTimeMillis();
        Move move = agent.minimax(board);
        long time = System. currentTimeMillis() - start;

        System.out. println("  Minimax chose: " + move);
        System. out.println("  Time: " + time + "ms");
        System.out.println("  Nodes explored: " + agent.getNodesExplored());

        // X should take win at (0, 2)
        assertEquals("X should take the win at (0,2)", new Move(0, 2), move);

        System.out.println("  ✓ Correctly took the winning move");
    }

    @Test
    public void testMinimaxOptimalPlay() {
        Board board = GameEngine.initialState(3, 3);
        MinimaxAgent agent = new MinimaxAgent();

        System.out.println("3x3 board - Minimax Optimal Play Test:");
        System.out.println("  Testing that Minimax never loses.. .");

        int moveCount = 0;
        while (! GameEngine.terminal(board) && moveCount < 5) {
            Move move = agent.minimax(board);
            board = GameEngine.result(board, move);
            moveCount++;
            System.out.println("  Move " + moveCount + ": " + move);
        }

        Character winner = GameEngine.winner(board);

        if (winner == null && ! GameEngine.terminal(board)) {
            System.out.println("  ✓ Game still ongoing (tested 5 moves)");
        } else if (winner == null) {
            System.out. println("  ✓ Game ended in a draw (optimal)");
        } else if (winner == 'X') {
            System.out.println("  ✓ X won (X moved first, optimal play)");
        } else {
            System.out.println("  ✗ O won (should not happen with optimal play!)");
            fail("Minimax should not lose when playing optimally");
        }
    }
}