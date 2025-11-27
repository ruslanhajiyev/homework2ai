package tictactoe;

import org.junit.Test;
import static org.junit. Assert.*;
import java.util.List;

public class GameEngineTest {

    @Test
    public void testInitialState() {
        System.out.println("Game Engine Test 1 - Initial State:");

        Board board = GameEngine.initialState(3, 3);

        System. out.println("  Board created: " + (board != null ? "Yes" : "No"));
        System.out.println("  Current player: " + GameEngine.player(board));
        System.out.println("  Is terminal: " + GameEngine.terminal(board));
        System.out.println("  Winner: " + GameEngine.winner(board));

        assertNotNull(board);
        assertEquals('X', GameEngine.player(board));
        assertFalse(GameEngine.terminal(board));
        assertNull(GameEngine.winner(board));

        System.out.println("  ✓ Initial state correct");
    }

    @Test
    public void testActions() {
        System.out. println("\nGame Engine Test 2 - Action Generation:");

        Board board = GameEngine.initialState(3, 3);
        List<Move> actions = GameEngine.actions(board);

        System.out. println("  Empty 3x3 board actions: " + actions.size());
        assertEquals(9, actions.size());

        Move move = new Move(1, 1);
        Board newBoard = GameEngine.result(board, move);
        List<Move> newActions = GameEngine.actions(newBoard);

        System.out.println("  After one move, actions: " + newActions.size());
        assertEquals(8, newActions.size());

        System.out.println("  ✓ Action generation correct");
    }

    @Test
    public void testHorizontalWin() {
        System.out. println("\nGame Engine Test 3 - Horizontal Win Detection:");

        Board board = GameEngine.initialState(3, 3);
        board = board.makeMove(new Move(0, 0)); // X
        board = board.makeMove(new Move(1, 0)); // O
        board = board.makeMove(new Move(0, 1)); // X
        board = board.makeMove(new Move(1, 1)); // O
        board = board.makeMove(new Move(0, 2)); // X wins

        System.out. println("  Board state:");
        System.out.println("    X X X  ← Horizontal win!");
        System.out.println("    O O _");
        System.out.println("    _ _ _");

        System.out.println("  Is terminal: " + GameEngine.terminal(board));
        System.out.println("  Winner: " + GameEngine.winner(board));
        System.out.println("  Utility: " + GameEngine.utility(board));

        assertTrue(GameEngine.terminal(board));
        assertEquals(Character.valueOf('X'), GameEngine. winner(board));
        assertEquals(Integer.valueOf(1), GameEngine.utility(board));

        System.out. println("  ✓ Horizontal win detected correctly");
    }

    @Test
    public void testVerticalWin() {
        System.out.println("\nGame Engine Test 4 - Vertical Win Detection:");

        Board board = GameEngine.initialState(3, 3);
        board = board.makeMove(new Move(0, 0)); // X
        board = board.makeMove(new Move(0, 1)); // O
        board = board.makeMove(new Move(1, 0)); // X
        board = board.makeMove(new Move(1, 1)); // O
        board = board.makeMove(new Move(2, 0)); // X wins

        System.out.println("  Board state:");
        System.out.println("    X O _");
        System.out.println("    X O _");
        System.out. println("    X _ _  ← Vertical win!");

        System.out.println("  Is terminal: " + GameEngine.terminal(board));
        System.out.println("  Winner: " + GameEngine.winner(board));

        assertTrue(GameEngine.terminal(board));
        assertEquals(Character. valueOf('X'), GameEngine.winner(board));

        System.out.println("  ✓ Vertical win detected correctly");
    }

    @Test
    public void testDiagonalWin() {
        System.out.println("\nGame Engine Test 5 - Diagonal Win Detection:");

        Board board = GameEngine.initialState(3, 3);
        board = board.makeMove(new Move(0, 0)); // X
        board = board. makeMove(new Move(0, 1)); // O
        board = board.makeMove(new Move(1, 1)); // X
        board = board.makeMove(new Move(0, 2)); // O
        board = board.makeMove(new Move(2, 2)); // X wins

        System.out.println("  Board state:");
        System.out. println("    X O O");
        System.out.println("    _ X _");
        System.out. println("    _ _ X  ← Diagonal win!");

        System.out.println("  Is terminal: " + GameEngine.terminal(board));
        System.out.println("  Winner: " + GameEngine.winner(board));

        assertTrue(GameEngine.terminal(board));
        assertEquals(Character.valueOf('X'), GameEngine.winner(board));

        System.out.println("  ✓ Diagonal win detected correctly");
    }

    @Test
    public void testDraw() {
        System.out.println("\nGame Engine Test 6 - Draw Detection:");

        Board board = GameEngine. initialState(3, 3);
        // Create a draw scenario
        board = board.makeMove(new Move(0, 0)); // X
        board = board.makeMove(new Move(0, 1)); // O
        board = board.makeMove(new Move(0, 2)); // X
        board = board.makeMove(new Move(1, 0)); // O
        board = board.makeMove(new Move(1, 1)); // X
        board = board.makeMove(new Move(2, 0)); // O
        board = board.makeMove(new Move(1, 2)); // X
        board = board.makeMove(new Move(2, 2)); // O
        board = board.makeMove(new Move(2, 1)); // X

        System.out.println("  Board state:");
        System. out.println("    X O X");
        System.out.println("    O X X");
        System.out.println("    O X O  ← Draw!");

        System.out.println("  Is terminal: " + GameEngine.terminal(board));
        System.out.println("  Winner: " + GameEngine.winner(board));
        System.out.println("  Utility: " + GameEngine.utility(board));

        assertTrue(GameEngine.terminal(board));
        assertNull(GameEngine.winner(board));
        assertEquals(Integer.valueOf(0), GameEngine.utility(board));

        System. out.println("  ✓ Draw detected correctly");
    }

    @Test
    public void test4x4With3InRow() {
        System.out. println("\nGame Engine Test 7 - Generalized k-in-a-row (4x4, k=3):");

        Board board = GameEngine.initialState(4, 3);

        System.out.println("  Board: 4x4, Win condition: 3-in-a-row");

        board = board.makeMove(new Move(0, 0)); // X
        board = board.makeMove(new Move(1, 0)); // O
        board = board.makeMove(new Move(0, 1)); // X
        board = board.makeMove(new Move(1, 1)); // O
        board = board.makeMove(new Move(0, 2)); // X wins with 3-in-a-row

        System.out. println("  Board state:");
        System.out.println("    X X X _  ← 3-in-a-row wins!");
        System.out.println("    O O _ _");
        System.out.println("    _ _ _ _");
        System.out.println("    _ _ _ _");

        System.out.println("  Is terminal: " + GameEngine. terminal(board));
        System. out.println("  Winner: " + GameEngine.winner(board));

        assertTrue(GameEngine.terminal(board));
        assertEquals(Character. valueOf('X'), GameEngine.winner(board));

        System.out.println("  ✓ Generalized k-in-a-row works correctly");
    }
}