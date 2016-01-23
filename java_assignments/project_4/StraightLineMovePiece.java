import java.awt.Color;

 /**
 * This class incorporates methods that involve pieces that have straight movement. 
 * 
 */
public class StraightLineMovePiece extends ChessPiece {
 /* max it will go */ 
 int maxRoadBlocks;
 /* pieces in the way */
 int maxMoveLimit;

 /**
 * This constructor intitializes many variables. 
 * 
 * @param side
 * @param row
 * @param column
 * @param label
 * @param color
 * @param chessBoard
 * @param maxMoveLimit
 * 
 */
 public StraightLineMovePiece(Side side, int row, int column, String label,
   Color color, ChessBoard chessBoard, int maxMoveLimit,
   int maxRoadBlocks) {
  super(side, row, column, label, color, chessBoard);
  this.maxRoadBlocks = maxRoadBlocks;
  this.maxMoveLimit = maxMoveLimit;
 }

 boolean isLegalNonCaptureMove(int row, int column) {
  boolean horizontalMove = false;
  boolean verticalMove = false;
  ChessPiece chessPiece;
  int i;

  if (this.row == row)
   horizontalMove = true;

  if (this.column == column)
   verticalMove = true;

  if (!horizontalMove && !verticalMove)
   return false;

  // Checks the max Limit to move
  if (maxMoveLimit != 0) {
   if (horizontalMove) {
    if (Math.abs(column - this.column) > maxMoveLimit) {
     return false;
    }
   }
   else {
    if (Math.abs(row - this.row) > maxMoveLimit) {
     return false;
    }
   }
  }

  int roadBlocks = maxRoadBlocks;

  if (horizontalMove) {

   if (column > this.column) {
    for (i = this.column + 1; i < column; ++i) {
     chessPiece = chessBoard.getPiece(row, i);
     if (chessPiece != null) {
      if (roadBlocks == 0)
       return false;
      roadBlocks--;
     }
    }
   }
   else if (column < this.column) {
    for (i = column + 1; i < this.column; ++i) {
     chessPiece = chessBoard.getPiece(row, i);
     if (chessPiece != null) {
      if (roadBlocks == 0)
       return false;
      roadBlocks--;
     }
    }
   }
  }
  else {
   if (row > this.row) {
    for (i = this.row + 1; i < row; ++i) {
     chessPiece = chessBoard.getPiece(i, column);
     if (chessPiece != null) {
      if (roadBlocks == 0)
       return false;
      roadBlocks--;
     }
    }
   }
   else if (row < this.row) {
    for (i = row + 1; i < this.row; ++i) {
   
  chessPiece = chessBoard.getPiece(i, column);
     if (chessPiece != null)
      if (roadBlocks == 0)
       return false;
     roadBlocks--;
    }
   }
  }

  chessPiece = chessBoard.getPiece(row, column);
  if (chessPiece != null && chessPiece.getSide() == side)
   return false;
  else
   return true;

 }

 /**
 * This method counts road blocks. 
 * 
 * @param count
 * @return nothing
 * 
 */
 void setRoadBlockCount(int count) {
  maxRoadBlocks = count;
 }
}
