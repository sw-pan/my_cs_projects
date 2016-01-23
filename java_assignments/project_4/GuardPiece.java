import java.awt.Color;

/**
 * This class is for the chess piece Guard. 
 * 
 */
public class GuardPiece extends DiagonalMovePiece {

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
  public GuardPiece(Side side, int row, int column, String label,
   Color color, ChessBoard chessBoard) {
  super(side, row, column, label, color, chessBoard, 1, 0);
 }

 /**
 * This methods returns a boolean based on whether move is legal.  
 * 
 * @param row
 * @param column
 * @return boolean true if legal, false if not
 * 
 */
 public boolean isLegalMove(int row, int column) {

  if (column < 3 || column > 5)
   return false;

  if (getSide() == Side.NORTH && row > 2)
   return false;

  if (getSide() == Side.SOUTH && (chessBoard.getMaxRows() - 3) > row)
   return false;

  return isLegalNonCaptureMove(row, column);

 }
}
