package tictactoe;

/**
 * Represents a move in Tic-Tac-Toe game.
 * Immutable class representing row and column position.
 */
public class Move implements Comparable<Move> {
    private final int row;
    private final int col;
    
    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Move)) return false;
        Move other = (Move) obj;
        return row == other. row && col == other.col;
    }
    
    @Override
    public int hashCode() {
        return 31 * row + col;
    }
    
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
    
    /**
     * Lexicographic ordering for deterministic tie-breaking
     */
    @Override
    public int compareTo(Move other) {
        if (this.row != other.row) {
            return Integer.compare(this.row, other.row);
        }
        return Integer.compare(this.col, other.col);
    }
}