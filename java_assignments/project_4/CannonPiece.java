import java.awt.Color;

/**
 * This class is for the chess piece Cannon. 
 * 
 */
public class CannonPiece extends StraightLineMovePiece {

  /**
 * This constructor intitializes many variables. 
 * 
 * @param side - current side
 * @param row - which row
 * @param column - which column 
 * @param label - which piece
 * @param color - color
 * @param chessBoard - chess board being used 
 * 
 * @return nothing 
 * 
 */
  public CannonPiece(Side side, int row, int column, String label,
   Color color, ChessBoard chessBoard) {
  super(side, row, column, label, color, chessBoard, 0, 0);
 }

 /**
 * This methods checks whether move is legal or not.  
 * 
 * @param row
 * @param column
 * 
 * @return boolean true if legal, false if not 
 * 
 */
 public boolean isLegalMove(int row, int column) {

  if (isLegalCaptureMove(row, column)) {
   setRoadBlockCount(1);
  }

  boolean status = isLegalNonCaptureMove(row, column);
  setRoadBlockCount(0);

  return status;
 }
}
