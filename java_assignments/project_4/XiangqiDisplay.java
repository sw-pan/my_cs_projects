import java.awt.Color;

import javax.swing.JButton;

 /**
 * This class implements the specified interface. 
 * 
 */
public class XiangqiDisplay implements ChessBoardDisplay {

 /**
 * This method overrides the displayEmptySquare method from ChessBoardDisplay. 
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
 @Override
 public void displayEmptySquare(JButton jbutton, int row, int column) {
  jbutton.setFocusPainted(true);
  jbutton.setBackground(java.awt.Color.gray);
  jbutton.setText("");
  jbutton.setIcon(null);
  jbutton.setFocusPainted(false);

 }

/**
 * This method overrides the displayFilledSquare method from ChessBoardDisplay. 
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
 @Override
 public void displayFilledSquare(JButton jbutton, int row, int column,
   ChessPiece chessPiece) {
  jbutton.setFocusPainted(true);
  jbutton.setBackground(chessPiece.getColor());
  jbutton.setText(chessPiece.getLabel());
  jbutton.setIcon(chessPiece.getIcon());
  jbutton.setFocusPainted(false);

 }

/**
 * This method overrides the highlightSquare method from ChessBoardDisplay. 
 * 
 * @param method
 *            boolean for highlighting 
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
 @Override
 public void highlightSquare(Boolean method, JButton jbutton, int row,
   int column, ChessPiece chessPiece) {
  if (method) {
   jbutton.setFocusPainted(true);
   jbutton.setBackground(java.awt.Color.yellow);
   jbutton.setFocusPainted(false);
  }
  else {
   if (chessPiece == null) {
    displayEmptySquare(jbutton, row, column);
   }
   else {
    displayFilledSquare(jbutton, row, column, chessPiece);
   }

  }
 }
}
