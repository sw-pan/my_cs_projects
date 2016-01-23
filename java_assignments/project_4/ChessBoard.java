import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class is the default chessboard. 
 * 
 */
public class ChessBoard {
 
 /* buttons on board */ 
 private JButton[][] boardButtons;
 /* chesspieces */
 private ChessPiece[][] chessPieces;
 /* type of board display */
 private ChessBoardDisplay chessBoardDisplay;
 /* type of chess game */
 private ChessGame chessGame;

 /* records last row/column */ 
 private int lastButtonRow = -1;
 private int lastButtonColumn = -1;

 /* keeps track of click */ 
 boolean firstClick = false;
 /* rows */
 private int maxRows;
 /* columns */
 private int maxColumns;

 /**
 * This constructor creates the grid, displays and starts the game. 
 * 
 * @param row
 *        # of rows
 * @param column
 *        # of columns
 * @param chessDisplay
 *        display of chess board
 * @param chessVersion
 *        version of chess
 * 
 */
 public ChessBoard(int rows, int columns, ChessBoardDisplay chessDisplay,
   ChessGame chessVersion) {

  chessBoardDisplay = chessDisplay;
  chessGame = chessVersion;
  maxRows = rows;
  maxColumns = columns;

  JFrame frame = new JFrame("Xiangqi"); // Creates Frame
  JPanel board = new JPanel(new GridLayout(rows, columns));
  frame.getContentPane().add(board, "Center");

  boardButtons = new JButton[rows][columns]; // New button
  chessPieces = new ChessPiece[rows][columns];

  int i, j;
  for (i = 0; i < rows; ++i) { // Sets for each button
   for (j = 0; j < columns; ++j) {
    boardButtons[i][j] = new JButton();
    chessDisplay.displayEmptySquare(boardButtons[i][j], i, j);
    boardButtons[i][j].addActionListener(new HandleActionEvents());

    board.add(boardButtons[i][j]);
    chessPieces[i][j] = null;
   }
  }
  chessPieces = new ChessPiece[rows][columns];

  frame.setSize(400, 400); // Sets size of frame

  frame.setVisible(true);
 }

 /**
 * This methods adds a piece to the specific location. 
 * 
 * @param piece
 * @param row
 * @param column
 * @return nothing 
 * 
 */
 public void addPiece(ChessPiece piece, int row, int column) {
  chessPieces[row][column] = piece;
  chessBoardDisplay.displayFilledSquare(boardButtons[row][column], row,
    column, piece);
 }

 /**
 * This methods removes a piece from the specific location. 
 * 
 * @param row
 * @param column
 * @return chess piece 
 * 
 */
 public ChessPiece removePiece(int row, int column) {
  chessBoardDisplay.displayEmptySquare(boardButtons[row][column], row,
    column);
  ChessPiece piece = chessPieces[row][column];
  chessPieces[row][column] = null;
  return piece;
 }

 /**
 * This method checks if there is a piece. 
 * 
 * @param row
 * @param column
 * @return boolean 
 *         returns true if piece at that location
 * 
 */
 public boolean hasPiece(int row, int column) {
  if (chessPieces[row][column] != null)
   return true;
  else
   return false;
 }

 /**
 * This methods gets the piece. 
 * 
 * @param row
 * @param column
 * @return chess piece
 *         returns chess piece at that location
 */
 public ChessPiece getPiece(int row, int column) {
  if (row < 0 || row > maxRows - 1)
   return null;

  if (column < 0 || column > maxColumns - 1)
   return null;

  return chessPieces[row][column];
 }
 
 /**
 * This methods listens to clicks and highlights square when needed. 
 * 
 * @param row
 * @param column
 * @return nothing 
 * 
 */
 public void buttonPressedAction(int row, int column) {
  if (!firstClick) {
   if ((chessPieces[row][column] != null)
     && (chessGame
       .legalPieceToPlay(chessPieces[row][column]))) {
    lastButtonRow = row;
    lastButtonColumn = column;
    firstClick = true;
    chessBoardDisplay.highlightSquare(true,
      boardButtons[row][column], row, column,
      chessPieces[row][column]);
   }
  }
  else {
   chessBoardDisplay.highlightSquare(false,
     boardButtons[lastButtonRow][lastButtonColumn],
     lastButtonRow, lastButtonColumn,
     chessPieces[lastButtonRow][lastButtonColumn]);
   if (lastButtonRow != row || lastButtonColumn != column) {
    chessGame.makeMove(
      chessPieces[lastButtonRow][lastButtonColumn], row,
      column);
   }
   firstClick = false;
  }

 }

 
 /**
 * This inner class handles events. 
 * 
 */
 class HandleActionEvents implements ActionListener {

  /**
 * This methods overrides the action performed method of ActionListener. 
 * 
 * @param e
 *       event 
 * 
 */
  @Override
  public void actionPerformed(ActionEvent e) {
   // TODO Auto-generated method stub
   if (e.getSource() instanceof JButton) {
    JButton button = (JButton) e.getSource();

    int i, j;
    for (i = 0; i < boardButtons.length; ++i) {
     for (j = 0; j < boardButtons[i].length; ++j) {
      if (button == boardButtons[i][j]) {
       buttonPressedAction(i, j);
       return;
      }
     }
    }
   }
  }
 }

 /**
 * This method gets the maximum rows. 
 * 
 * @return maxRows 
 * 
 */
 public int getMaxRows() {
  return maxRows;
 }

 /**
 * This method gets the maximum columns. 
 * 
 * @return maxRows
 * 
 */
 public int getMaxColumns() {
  return maxColumns;
 }
}
