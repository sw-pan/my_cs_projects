import java.awt.Color;

/**
 * This class is for the chess piece Knight. 
 * 
 */
public class KnightPiece extends ChessPiece {

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
  public KnightPiece(Side side, int row, int column, String label,
   Color color, ChessBoard chessBoard) {
  super(side, row, column, label, color, chessBoard);
  // TODO Auto-generated constructor stub
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
  ChessPiece chessPiece;
  int firstSquareRow = -1;
  int firstSquareColumn = -1;

  if (row > this.row) {
   // The move is upwards so checks the first square
   // occupancy
   if ((row - this.row) == 1)
    firstSquareRow = this.row;
   else if ((row - this.row) == 2)
    firstSquareRow = this.row + 1;
  }
  else {
   // Downwards move
   if ((this.row - row) == 1)
    firstSquareRow = this.row;
   else if ((row - this.row) == 2)
    firstSquareRow = this.row - 1;
  }

  if (column > this.column) {
   if ((column - this.column) == 1)
    firstSquareColumn = this.column;
   else if ((column - this.column) == 2)
    firstSquareColumn = this.column + 1;
  }
  else {
   if ((this.column - column) == 1)
    firstSquareColumn = this.column;
   else if ((this.column - column) == 2)
    firstSquareColumn = this.column - 1;
  }
  chessPiece = chessBoard.getPiece(firstSquareRow, firstSquareColumn);
  if (chessPiece != null)
   return false;

  int oldColumn = firstSquareColumn + chessBoard.getMaxColumns()
    * firstSquareRow;
  int newColumn = column + chessBoard.getMaxColumns() * row;

  if (Math.abs(chessBoard.getMaxColumns()
    - Math.abs(oldColumn - newColumn)) == 1) {

   return isLegalNonCaptureMove(row, column);
  }

  return false;
 }
}
