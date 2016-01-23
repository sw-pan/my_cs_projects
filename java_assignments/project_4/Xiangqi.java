import java.awt.Color;

 /**
 * This class contains important methods to play the game. 
 * 
 */
public class Xiangqi implements ChessGame {

 /* chess board */
 private ChessBoard chessBoard;
 /* display */
 private XiangqiDisplay display;
 /* current side */
 private Side currentSide;

 /**
 * This constructor sets the pieces in the appropriate sides.  
 * 
 */
 public Xiangqi() {
  int maxRows = 10;
  int maxCols = 9;
  currentSide = Side.NORTH;

  display = new XiangqiDisplay();
  chessBoard = new ChessBoard(maxRows, maxCols, display, this);

  {
   // North side
   Color color = Color.white;
   Side side = Side.NORTH;

   RookPiece rookPiece = new RookPiece(side, 0, 0, "R", color,
     chessBoard);
   chessBoard.addPiece(rookPiece, rookPiece.getRow(),
     rookPiece.getColumn());

   KnightPiece knightPiece = new KnightPiece(side, 0, 1, "N", color,
     chessBoard);
   chessBoard.addPiece(knightPiece, knightPiece.getRow(),
     knightPiece.getColumn());

   ElephantPiece elephantPiece = new ElephantPiece(side, 0, 2, "E",
     color, chessBoard);
   chessBoard.addPiece(elephantPiece, elephantPiece.getRow(),
     elephantPiece.getColumn());

   GuardPiece guardPiece = new GuardPiece(side, 0, 3, "G", color,
     chessBoard);
   chessBoard.addPiece(guardPiece, guardPiece.getRow(),
     guardPiece.getColumn());

   KingPiece kingPiece = new KingPiece(side, 0, 4, "K", color,
     chessBoard);
   chessBoard.addPiece(kingPiece, kingPiece.getRow(),
     kingPiece.getColumn());

   GuardPiece guardPiece1 = new GuardPiece(side, 0, 5, "G", color,
     chessBoard);
   chessBoard.addPiece(guardPiece1, 0, 5);

   ElephantPiece elephantPiece1 = new ElephantPiece(side, 0, 6, "E",
     color, chessBoard);
   chessBoard.addPiece(elephantPiece1, 0, 6);

   KnightPiece knightPiece1 = new KnightPiece(side, 0, 7, "N", color,
     chessBoard);
   chessBoard.addPiece(knightPiece1, 0, 7);

   RookPiece rookPiece1 = new RookPiece(side, 0, 8, "R", color,
     chessBoard);
   chessBoard.addPiece(rookPiece1, 0, 8);

   CannonPiece cannonPiece = new CannonPiece(side, 2, 1, "C", color,
     chessBoard);
   chessBoard.addPiece(cannonPiece, 2, 1);

   CannonPiece cannonPiece1 = new CannonPiece(side, 2, 7, "C", color,
     chessBoard);
   chessBoard.addPiece(cannonPiece1, 2, 7);

   PawnPiece pawnPiece = new PawnPiece(side, 3, 0, "P", color,
     chessBoard);
   chessBoard.addPiece(pawnPiece, 3, 0);

   PawnPiece pawnPiece1 = new PawnPiece(side, 3, 2, "P", color,
     chessBoard);
   chessBoard.addPiece(pawnPiece1, 3, 2);

   PawnPiece pawnPiece2 = new PawnPiece(side, 3, 4, "P", color,
     chessBoard);
   chessBoard.addPiece(pawnPiece2, 3, 4);

   PawnPiece pawnPiece3 = new PawnPiece(side, 3, 6, "P", color,
     chessBoard);
   chessBoard.addPiece(pawnPiece3, 3, 6);

   PawnPiece pawnPiece4 = new PawnPiece(side, 3, 8, "P", color,
     chessBoard);
   chessBoard.addPiece(pawnPiece4, 3, 8);
  }
  {
   // South side
   Color color = Color.red;
   Side side = Side.SOUTH;

   RookPiece rookPiece = new RookPiece(side, maxRows - 1, 0, "R",
     color, chessBoard);
   chessBoard.addPiece(rookPiece, maxRows - 1, 0);

   KnightPiece knightPiece = new KnightPiece(side, maxRows - 1, 1,
     "N", color, chessBoard);
   chessBoard.addPiece(knightPiece, maxRows - 1, 1);

   ElephantPiece elephantPiece = new ElephantPiece(side, maxRows - 1,
     2, "E", color, chessBoard);
   chessBoard.addPiece(elephantPiece, maxRows - 1, 2);

   GuardPiece guardPiece = new GuardPiece(side, maxRows - 1, 3, "G",
     color, chessBoard);
   chessBoard.addPiece(guardPiece, maxRows - 1, 3);

   KingPiece kingPiece = new KingPiece(side, maxRows - 1, 4, "K",
     color, chessBoard);
   chessBoard.addPiece(kingPiece, maxRows - 1, 4);

   GuardPiece guardPiece1 = new GuardPiece(side, maxRows - 1, 5, "G",
     color, chessBoard);
   chessBoard.addPiece(guardPiece1, maxRows - 1, 5);

   ElephantPiece elephantPiece1 = new ElephantPiece(side, maxRows - 1,
     6, "E", color, chessBoard);
   chessBoard.addPiece(elephantPiece1, maxRows - 1, 6);

   KnightPiece knightPiece1 = new KnightPiece(side, maxRows - 1, 7,
     "N", color, chessBoard);
   chessBoard.addPiece(knightPiece1, maxRows - 1, 7);

   RookPiece rookPiece1 = new RookPiece(side, maxRows - 1, 8, "R",
     color, chessBoard);
   chessBoard.addPiece(rookPiece1, maxRows - 1, 8);

   CannonPiece cannonPiece = new CannonPiece(side, maxRows - 1 - 2, 1,
     "C", color, chessBoard);
   chessBoard.addPiece(cannonPiece, maxRows - 1 - 2, 1);

   CannonPiece cannonPiece1 = new CannonPiece(side, maxRows - 1 - 2,
     7, "C", color, chessBoard);
   chessBoard.addPiece(cannonPiece1, maxRows - 1 - 2, 7);

   PawnPiece pawnPiece = new PawnPiece(side, maxRows - 1 - 3, 0, "P",
     color, chessBoard);
   chessBoard.addPiece(pawnPiece, maxRows - 1 - 3, 0);

   PawnPiece pawnPiece1 = new PawnPiece(side, maxRows - 1 - 3, 2, "P",
     color, chessBoard);
   chessBoard.addPiece(pawnPiece1, maxRows - 1 - 3, 2);

   PawnPiece pawnPiece2 = new PawnPiece(side, maxRows - 1 - 3, 4, "P",
     color, chessBoard);
   chessBoard.addPiece(pawnPiece2, maxRows - 1 - 3, 4);

   PawnPiece pawnPiece3 = new PawnPiece(side, maxRows - 1 - 3, 6, "P",
     color, chessBoard);
   chessBoard.addPiece(pawnPiece3, maxRows - 1 - 3, 6);

   PawnPiece pawnPiece4 = new PawnPiece(side, maxRows - 1 - 3, 8, "P",
     color, chessBoard);
   chessBoard.addPiece(pawnPiece4, maxRows - 1 - 3, 8);
  }
 }

 /**
 * This methods overrides ChessGame's method legalPieceToPlay. 
 * 
 * @param piece
 * @return boolean, returns true if legal 
 * 
 */
 @Override
 public boolean legalPieceToPlay(ChessPiece piece) {
  if (piece.getSide() == currentSide) {
   return true;
  }

  return false;
 }

  /**
 * This methods overrides ChessGame's method makeMove. 
 * 
 * @param piece
 * @param row
 * @param column
 * @return boolean 
 * 
 */
 @Override
 public boolean makeMove(ChessPiece piece, int row, int column) {
  if (currentSide == Side.SOUTH)
   currentSide = Side.NORTH;
  else
   currentSide = Side.SOUTH;
  if (piece.isLegalMove(row, column)) {
   chessBoard.removePiece(piece.getRow(), piece.getColumn());
   piece.setRow(row);
   piece.setColumn(column);
   chessBoard.addPiece(piece, row, column);
   piece.moveDone();
   return true;
  }
  else
   return false;
 }

 /**
 * This method returns the chess board. 
 * 
 * @return chessBoard
 */
 ChessBoard getChessBoard() {
  return chessBoard;
 }
 
 /**
 * This method returns the display.
 * 
 * @return display
 */
 XiangqiDisplay getDisplay() {
  return display;
 }

 /**
 * This is a main method so the game could run as a stand alone program. 
 * 
 */
 public static void main(String[] args) {
  Xiangqi xiangqi = new Xiangqi();
 }
}
