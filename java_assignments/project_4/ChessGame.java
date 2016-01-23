public interface ChessGame {

 /**
  * This method determines whether the piece is legal to be played.
  * 
  * @param piece
  *            type of chess piece
  * @return boolean true if legal, false if not 
  * 
  */
 boolean legalPieceToPlay(ChessPiece piece);

 /**
  * This method moves the piece.
  * 
  * @param piece
  *            type of chess piece
  * @param row
  *            # of rows in board
  * @param column
  *            # of columns in board
  * @return boolean 
  * 
  */
 boolean makeMove(ChessPiece piece, int row, int column);
}
