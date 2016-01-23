import java.awt.Color;

/**
 * This class is for the chess piece Pawn. 
 * 
 */
public class PawnPiece extends ChessPiece {

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
  public PawnPiece(Side side, int row, int column, String label, Color color,
   ChessBoard chessBoard) {
  super(side, row, column, label, color, chessBoard);
 }

/**
 * This methods returns a boolean based on whether move is legal.  
 * 
 * @param row
 * @param column
 * @return boolean true if legal, false if not
 */
 public boolean isLegalMove(int row, int column) {

  if (getSide() == Side.NORTH && this.row > 4) {
   if ((this.row == row) && Math.abs(this.column - column) == 1) {

    return isLegalNonCaptureMove(row, column);
   }
  }
  if (getSide() == Side.SOUTH && this.row < 5) {
   if ((this.row == row) && Math.abs(this.column - column) == 1) {

    return isLegalNonCaptureMove(row, column);
   }
  }

  int oldColumn = this.column + chessBoard.getMaxColumns() * this.row;
  int newColumn = column + chessBoard.getMaxColumns() * row;

  if (getSide() == Side.NORTH) {

   if ((newColumn - oldColumn) == chessBoard.getMaxColumns())
    return isLegalNonCaptureMove(row, column);
  }
  else {
   if ((oldColumn - newColumn) == chessBoard.getMaxColumns())
    return isLegalNonCaptureMove(row, column);

  }

  return false;
 }
}
