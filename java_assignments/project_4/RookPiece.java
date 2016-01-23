import java.awt.Color;

/**
 * This class is for the chess piece Rook. 
 * 
 */
public class RookPiece extends StraightLineMovePiece {

  /**
 * This constructor intitializes many variables. 
 * 
 * @param side
 * @param row
 * @param column
 * @param label
 * @param color
 * @param chessBoard
 * 
 */
  public RookPiece(Side side, int row, int column, String label, Color color,
   ChessBoard chessBoard) {
  super(side, row, column, label, color, chessBoard, 0, 0);
 }

/**
 * This methods returns a boolean based on whether move is legal.  
 * 
 * @param row
 * @param column
 * @return boolean true if legal, false if not
 */
 public boolean isLegalMove(int row, int column) {
  return isLegalNonCaptureMove(row, column);
 }
}
