package tictactoe;

import java.util.Scanner;

/**
 * Main game class for interactive play and AI vs AI matches.
 */
public class Game {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System. in);

        System.out.println("=== Generalized Tic-Tac-Toe ===");
        System.out.println("1. Play vs AI");
        System.out.println("2. AI vs AI");
        System.out.println("3. Run Performance Benchmark");
        System.out. print("Choose option: ");

        int option = scanner.nextInt();

        if (option == 1) {
            playVsAI(scanner);
        } else if (option == 2) {
            aiVsAI();
        } else if (option == 3) {
            PerformanceBenchmark.runBenchmarks();
        }

        scanner.close();
    }

    private static void playVsAI(Scanner scanner) {
        System. out.print("Board size (m): ");
        int m = scanner.nextInt();
        System. out.print("Win condition (k): ");
        int k = scanner.nextInt();

        System.out.print("Play as X or O? ");
        char humanPlayer = scanner.next().toUpperCase().charAt(0);

        Board board = GameEngine.initialState(m, k);
        AlphaBetaAgent agent = new AlphaBetaAgent(m > 3 ? 6 : Integer.MAX_VALUE, true);

        System.out.println("\n*** Board positions are numbered from 1 to " + m + " ***");
        System. out.println("*** Example: For a 3x3 board, enter '1 1' for top-left, '3 3' for bottom-right ***\n");

        while (! GameEngine.terminal(board)) {
            System.out.println("\nCurrent board:");
            printBoardWithIndices(board);

            if (GameEngine.player(board) == humanPlayer) {
                System.out.print("Your move (row col, from 1 to " + m + "): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                // Convert from 1-based (user input) to 0-based (array index)
                int arrayRow = row - 1;
                int arrayCol = col - 1;

                // Validate input
                if (arrayRow < 0 || arrayRow >= m || arrayCol < 0 || arrayCol >= m) {
                    System.out.println("Invalid position! Row and column must be between 1 and " + m);
                    continue;
                }

                Move move = new Move(arrayRow, arrayCol);

                if (! GameEngine.actions(board). contains(move)) {
                    System.out.println("Invalid move! That cell is already occupied.");
                    continue;
                }

                board = GameEngine.result(board, move);
                System.out.println("You played at position (" + row + ", " + col + ")");
            } else {
                System.out.println("AI is thinking...");
                long start = System.currentTimeMillis();
                Move aiMove = agent.alphaBeta(board);
                long time = System.currentTimeMillis() - start;

                // Convert from 0-based (array index) to 1-based (user display)
                int displayRow = aiMove.getRow() + 1;
                int displayCol = aiMove.getCol() + 1;

                System.out.println("AI chose: (" + displayRow + ", " + displayCol + ") " +
                        "(time: " + time + "ms, nodes: " + agent.getNodesExplored() + ")");
                board = GameEngine.result(board, aiMove);
            }
        }

        System.out.println("\nFinal board:");
        printBoardWithIndices(board);

        Character winner = GameEngine.winner(board);
        if (winner != null) {
            if (winner == humanPlayer) {
                System.out.println("🎉 Congratulations! You win!");
            } else {
                System.out.println("😅 AI wins!  Better luck next time.");
            }
        } else {
            System.out.println("🤝 It's a draw!");
        }
    }

    /**
     * Prints the board with row/column indices for user reference
     */
    private static void printBoardWithIndices(Board board) {
        int m = board.getSize();

        // Print column numbers
        System.out.print("   ");
        for (int j = 1; j <= m; j++) {
            System.out.print(" " + j + " ");
            if (j < m) System.out.print(" ");
        }
        System. out.println();

        // Print separator
        System.out.print("   ");
        for (int j = 0; j < m; j++) {
            System.out.print("---");
            if (j < m - 1) System.out.print("-");
        }
        System.out.println();

        // Print rows with row numbers
        for (int i = 0; i < m; i++) {
            System.out.print((i + 1) + " |");  // Row number (1-based)
            for (int j = 0; j < m; j++) {
                char cell = board.getCell(i, j);
                System.out.print(" " + cell + " ");
                if (j < m - 1) System.out.print("|");
            }
            System.out.println("|");

            // Print separator between rows
            if (i < m - 1) {
                System.out.print("  |");
                for (int j = 0; j < m; j++) {
                    System.out.print("---");
                    if (j < m - 1) System. out.print("+");
                }
                System. out.println("|");
            }
        }

        // Print bottom border
        System.out.print("   ");
        for (int j = 0; j < m; j++) {
            System. out.print("---");
            if (j < m - 1) System.out.print("-");
        }
        System.out.println();
    }

    private static void aiVsAI() {
        Board board = GameEngine.initialState(3, 3);
        AlphaBetaAgent agentX = new AlphaBetaAgent();
        AlphaBetaAgent agentO = new AlphaBetaAgent();

        System.out.println("AI vs AI (3x3)");

        while (!GameEngine.terminal(board)) {
            System.out.println("\n" + board);

            AlphaBetaAgent currentAgent = (GameEngine.player(board) == 'X') ? agentX : agentO;
            Move move = currentAgent.alphaBeta(board);

            // Display in 1-based indexing
            System.out.println(GameEngine.player(board) + " plays: (" +
                    (move.getRow() + 1) + ", " + (move.getCol() + 1) + ")");

            board = GameEngine.result(board, move);
        }

        System.out.println("\nFinal board:");
        System.out.println(board);

        Character winner = GameEngine.winner(board);
        if (winner != null) {
            System.out.println(winner + " wins!");
        } else {
            System.out. println("Draw!");
        }
    }
}