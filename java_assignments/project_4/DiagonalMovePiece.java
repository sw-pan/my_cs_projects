import java.awt.Color;

 /**
 * This class incorporates methods that involve pieces that have diagnonal movement. 
 * 
 */
public class DiagonalMovePiece extends ChessPiece {
 /* max it will go */ 
  int maxLimit;
 /* pieces in the way */
 int maxRoadBlock;

 /**
 * This constructor intitializes many variables. 
 * 
 * @param side
 * @param row
 * @param column
 * @param label
 * @param color
 * @param chessBoard
 * @param maxLimit
 * @param maxRoadBlock
 * 
 */
 public DiagonalMovePiece(Side side, int row, int column, String label,
   Color color, ChessBoard chessBoard, int maxLimit, int maxRoadBlock) {
  super(side, row, column, label, color, chessBoard);
  this.maxLimit = maxLimit;
        this.maxRoadBlock = maxRoadBlock;
 }

 /**
 * This method checks whether the move is legal.  
 * 
 * @param row
 * @param column
 * @return boolean true if legal, false if not
 * 
 */
 boolean isLegalNonCaptureMove(int row, int column) {
  if (maxRoadBlock != 0) {
   ChessPiece chessPiece;

   chessPiece = chessBoard.getPiece(row + 1, column - 1);
   if (chessPiece != null)
    return false;

   chessPiece = chessBoard.getPiece(row + 1, column + 1);
   if (chessPiece != null)
    return false;

   chessPiece = chessBoard.getPiece(row - 1, column - 1);
   if (chessPiece != null)
    return false;

   chessPiece = chessBoard.getPiece(row - 1, column + 1);
   if (chessPiece != null)
    return false;
  }

  int oldColumn = this.column + chessBoard.getMaxColumns() * this.row;
  int newColumn = column + chessBoard.getMaxColumns() * row;
  int diff = Math
    .abs((maxLimit * Math.abs(chessBoard.getMaxColumns()) - Math
      .abs(oldColumn - newColumn)));
  boolean check = false;
  if (maxLimit == 1 && diff == 1) {
   check = true;
  }
  else if (maxLimit == 2 && (diff == 1 || diff == 2)) {
   check = true;
  }
  if (check) {
   ChessPiece chessPiece = chessBoard.getPiece(row, column);
   if (chessPiece != null && chessPiece.getSide() == side)
    return false;
   else
    return true;
  }

  return false;
 }
}
