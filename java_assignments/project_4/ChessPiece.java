import java.awt.Color;

import javax.swing.Icon;

/**
 * This class has general methods that pertains to all the chess pieces. 
 * 
 */
public class ChessPiece {

 /* row */
 protected int row;
 /* column */
 protected int column;
 /* color */
 protected Color color;
 /* label */
 protected String label;
 /* chess board */
 ChessBoard chessBoard;
 /* side */
 Side side;

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
 public ChessPiece(Side side, int row, int column, String label,
   Color color, ChessBoard chessBoard) {
  this.row = row;
  this.column = column;
  this.label = label;
  this.color = color;
  this.chessBoard = chessBoard;
  this.side = side;
 }

 /**
 * This methods sets the row. 
 * 
 * @param row
 * @return nothing
 * 
 */
 public void setRow(int row) {
  this.row = row;
 }

 /**
 * This methods sets the column. 
 * 
 * @param column
 * @return nothing
 * 
 */
 public void setColumn(int column) {
  this.column = column;
 }

 /**
 * This methods gets the color.
 * 
 * @return color
 * 
 */
 public Color getColor() {
  return color;
 }

 /**
 * This methods gets the label.  
 * 
 * @return label
 * 
 */
 public String getLabel() {
  return label;
 }

 /**
 * This methods gets the icon.
 * 
 * @return icon
 * 
 */
 public Icon getIcon() {
  return null;
 }

 /**
 * This methods gets the chess board.  
 * 
 * @return chess board 
 * 
 */
 public ChessBoard getChessBoard() {
  return chessBoard;
 }

 /**
 * This methods gets the side.  
 * 
 * @return side
 */
 public Side getSide() {
  return side;
 }

 /**
 * This methods gets the row.  
 * 
 * @return row
 * 
 */
 public int getRow() {
  return row;
 }

 /**
 * This methods gets the column.  
 * 
 * @return column
 */
 public int getColumn() {
  return column;
 }

 /**
 * This methods returns a boolean based on whether move is legal.  
 * 
 * @param row
 * @param column
 * 
 * @return boolean, returns true if legal
 * 
 */
 public boolean isLegalMove(int row, int column) {
  return true;
 }

 /**
 * This methods classifies the move as being done.  
 * 
 */
 public void moveDone() {
 }

 /**
 * This methods returns a boolean based on whether move is legal and no capture involved.  
 * 
 * @param row
 * @param column
 * 
 * @return boolean, returns true if legal
 */
 boolean isLegalNonCaptureMove(int row, int column) {
  // Find the place and check the piece there
  ChessPiece chessPiece = chessBoard.getPiece(row, column);
  if (chessPiece != null && chessPiece.getSide() == side)
   return false;
  else
   return true;
 }

 /**
 * This methods returns a boolean based on whether move is legal.  
 * 
 * @return boolean, returns true if legal
 * 
 */
 boolean isLegalCaptureMove(int row, int column) {
  // Find the place and check the piece there
  ChessPiece chessPiece = chessBoard.getPiece(row, column);
  if (chessPiece != null && chessPiece.getSide() != side)
   return true;
  else
   return false;
 }
}
