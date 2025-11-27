package tictactoe;

import org.junit. Test;
import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testInitialBoard() {
        System.out.println("Board Test 1 - Initial Board Creation:");

        Board board = new Board(3, 3);

        System.out.println("  Board size: " + board.getSize());
        System.out.println("  Win condition: " + board.getWinCondition());
        System.out.println("  Current player: " + board.getCurrentPlayer());
        System.out.println("  Move count: " + board.getMoveCount());

        assertEquals(3, board.getSize());
        assertEquals(3, board. getWinCondition());
        assertEquals('X', board.getCurrentPlayer());
        assertEquals(0, board.getMoveCount());
        assertTrue(board.isEmpty(0, 0));

        System.out.println("  ✓ Initial board created correctly");
    }

    @Test
    public void testMakeMove() {
        System. out.println("\nBoard Test 2 - Making Moves:");

        Board board = new Board(3, 3);
        Move move = new Move(1, 1);

        System.out.println("  Original board player: " + board.getCurrentPlayer());
        System.out.println("  Making move at " + move);

        Board newBoard = board.makeMove(move);

        System. out.println("  New board cell (1,1): " + newBoard.getCell(1, 1));
        System.out.println("  New board player: " + newBoard.getCurrentPlayer());
        System.out.println("  New board move count: " + newBoard.getMoveCount());
        System.out.println("  Original board cell (1,1): " + board.getCell(1, 1) + " (should be empty)");

        assertEquals('X', newBoard.getCell(1, 1));
        assertEquals('O', newBoard.getCurrentPlayer());
        assertEquals(1, newBoard.getMoveCount());
        assertTrue(board.isEmpty(1, 1)); // Original unchanged

        System.out. println("  ✓ Move applied correctly (immutability preserved)");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMove() {
        System. out.println("\nBoard Test 3 - Invalid Move Detection:");

        Board board = new Board(3, 3);
        Move move = new Move(1, 1);

        System.out.println("  Making first move at " + move);
        Board newBoard = board.makeMove(move);

        System. out.println("  Attempting to make same move again (should throw exception).. .");
        newBoard.makeMove(move); // Should throw

        System.out.println("  ✗ Exception not thrown (test failed)");
    }

    @Test
    public void testBoardEquality() {
        System.out. println("\nBoard Test 4 - Board Equality:");

        Board board1 = new Board(3, 3);
        Board board2 = new Board(3, 3);

        System.out.println("  Two empty boards equal: " + board1. equals(board2));
        assertEquals(board1, board2);

        Move move = new Move(0, 0);
        Board board3 = board1.makeMove(move);
        Board board4 = board2.makeMove(move);

        System.out.println("  Two boards with same move equal: " + board3.equals(board4));
        assertEquals(board3, board4);

        System.out.println("  ✓ Board equality works correctly");
    }

    @Test
    public void testLargerBoard() {
        System.out.println("\nBoard Test 5 - Larger Board (5x5):");

        Board board = new Board(5, 4);

        System.out.println("  Board size: " + board.getSize());
        System.out.println("  Win condition: " + board.getWinCondition());

        assertEquals(5, board.getSize());
        assertEquals(4, board.getWinCondition());

        // Make some moves
        board = board.makeMove(new Move(2, 2)); // Center
        board = board.makeMove(new Move(0, 0)); // Corner
        board = board.makeMove(new Move(2, 3));

        System.out. println("  Moves made: 3");
        System.out.println("  Current player: " + board.getCurrentPlayer());

        assertEquals(3, board.getMoveCount());
        assertEquals('O', board.getCurrentPlayer());

        System.out.println("  ✓ Larger board works correctly");
    }
}