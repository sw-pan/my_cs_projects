import javax.swing.JButton;


/**
 * This interface contains the display of the chess board and the squares. 
 * 
 */
public interface ChessBoardDisplay {

 /**
  * This method displays an empty square.
  * 
  * @param jbutton
  *            button on board
  * @param row
  *            # of rows in board
  * @param column
  *            # of columns in board
  * @return nothing 
  * 
  */
 public void displayEmptySquare(JButton jbutton, int row, int column);

/**
  * This method displays an display filled square.
  * 
  * @param jbutton
  *            button on board
  * @param row
  *            # of rows in board
  * @param column
  *            # of columns in board
  * 
  */
 public void displayFilledSquare(JButton jbutton, int row, int column,
   ChessPiece chessPiece);

/**
  * This method highlights a square.
  * 
  * @param dummy
  *            boolean that stores whether it's highlighted 
  * @param jbutton
  *            button on board
  * @param row
  *            # of rows in board
  * @param column
  *            # of columns in board
  * @param chessPiece
  *            type of chess piece
  * @return nothing
  * 
  */
 public void highlightSquare(Boolean dummy, JButton jbutton, int row,
   int column, ChessPiece chessPiece);

}
