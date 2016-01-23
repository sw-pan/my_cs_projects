import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class XiangqiTest {
 private Xiangqi xiangqi = null;
 private XiangqiDisplay display;
 private ChessBoard chessBoard;

 @Before
 public void setUp() throws Exception {
  xiangqi = new Xiangqi();
  display = xiangqi.getDisplay();
  chessBoard = xiangqi.getChessBoard();
 }

 @Test
 public void testXiangqi() {
  /*
   * Test 1: Constructor test: Manually verifies that the board gets displayed.
   * This also verifies addPiece() method of the ChessBoard class and
   * displayFilledSquare() of the ChessBoardDisplay interface. 
   */
  char charInput = 0;
  try {
   System.out
     .println("Did you see a Xianggi board display with all pieces?");
   System.out.println("Press Y/N key to continue");

   charInput = (char) System.in.read();
  }
  catch (IOException e) {
   e.printStackTrace();
  }
  assertEquals(charInput, 'Y');
 }

 @Test
 public void testGetPiece() {
  String label;
  ChessPiece chessPiece;
  /*
   * Test 2: Verifies the position of each piece set in the constructor.
   * Matches example board displayed in the homework. This 
   * verifies the getPiece() method of ChessBoard and also the getLabel()
   * method of ChessPiece. 
   * 
   * 2.1 - Verify in low row and column position 
   * 2.2 - Verify at random places 
   * 2.3 - Verify in middle
   * 2.4 - Verify in max row and max column
   */
  chessPiece = chessBoard.getPiece(0, 0);
  label = chessPiece.getLabel();
  assertEquals(label, "R");

  chessPiece = chessBoard.getPiece(0, 2);
  label = chessPiece.getLabel();
  assertEquals(label, "E");

  chessPiece = chessBoard.getPiece(0, 5);
  label = chessPiece.getLabel();
  assertEquals(label, "G");

  chessPiece = chessBoard.getPiece(3, 2);
  label = chessPiece.getLabel();
  assertEquals(label, "P");

  chessPiece = chessBoard.getPiece(7, 1);
  label = chessPiece.getLabel();
  assertEquals(label, "C");

  chessPiece = chessBoard.getPiece(9, 8);
  label = chessPiece.getLabel();
  assertEquals(label, "R");

 }

 @Test
 public void testAddRemovePiece() throws IOException {
  /*
   * Test 3: Tests adding and removing pieces. 
   * 3.1 : Remove piece at location 0,0
   * 3.2 : Remove piece from middle 
   * 3.3 : Remove randomly 
   * 3.4 : Remove at max row column
   * 3.5 : After removal add all pieces back
   */
  ChessPiece chessPiece;

  chessPiece = chessBoard.getPiece(0, 0);
  chessBoard.removePiece(0, 0);
  System.out.println("Check a Piece is removed from (0,0) ?");
  System.in.read();
  chessBoard.addPiece(chessPiece, 0, 0);

  chessPiece = chessBoard.getPiece(3, 4);
  chessBoard.removePiece(3, 4);
  System.out.println("Check a Piece is removed from (3,4) ?");
  System.in.read();
  chessBoard.addPiece(chessPiece, 3, 4);

  chessPiece = chessBoard.getPiece(9, 8);
  chessBoard.removePiece(9, 8);
  System.out.println("Check a Piece is removed from (9,8) ?");
  System.in.read();
  chessBoard.addPiece(chessPiece, 9, 8);

 }

 @Test
 public void testLegalPieceToPlay() {
  /*
   * Test 4: Checks whether each user gets to play in the 
   * correct order. 
   */
  ChessPiece chessPiece;
  Boolean result;

  chessPiece = chessBoard.getPiece(0, 4);

  result = xiangqi.legalPieceToPlay(chessPiece);
  assertTrue(result);

  chessBoard.buttonPressedAction(0, 4);
  chessBoard.buttonPressedAction(1, 4);
  result = xiangqi.legalPieceToPlay(chessPiece);
  assertFalse(result);

  chessPiece = chessBoard.getPiece(9, 4);

  chessBoard.buttonPressedAction(9, 4);
  chessBoard.buttonPressedAction(8, 4);

  result = xiangqi.legalPieceToPlay(chessPiece);
  assertFalse(result);

 }

 @Test
 public void testKingMakeMove() {
  /*
   * Test 5: Test King's move 
   * 5.1 - Check King's legal one forward move 
   * 5.2 - Check King's legal horizontal move 
   * 5.3 - Check illegal diagonal move 
   * 5.4 - Check illegal move beyond three central columns
   * 5.5 - Check illegal move beyond top/bottom three rows
   * 5.6 - Check kings capture move, make sure that it doesn't capture its own piece
   */

  ChessPiece chessPiece, chessPieceStore;
  String label;

  chessPiece = chessBoard.getPiece(0, 4);
  xiangqi.makeMove(chessPiece, 1, 4);
  chessPiece = chessBoard.getPiece(1, 4);
  label = chessPiece.getLabel();
  assertEquals(label, "K");

  xiangqi.makeMove(chessPiece, 1, 5);
  chessPiece = chessBoard.getPiece(1, 5);
  label = chessPiece.getLabel();
  assertEquals(label, "K");

  xiangqi.makeMove(chessPiece, 1, 4);
  xiangqi.makeMove(chessPiece, 0, 4);
  chessPiece = chessBoard.getPiece(0, 4);
  label = chessPiece.getLabel();
  assertEquals(label, "K");

  chessPiece = chessBoard.getPiece(0, 4);
  xiangqi.makeMove(chessPiece, 1, 3);
  chessPiece = chessBoard.getPiece(1, 3);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("K"));
  }

  chessPieceStore = chessBoard.getPiece(0, 4);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  xiangqi.makeMove(chessPieceStore, 1, 3);
  xiangqi.makeMove(chessPieceStore, 1, 2);
  chessPiece = chessBoard.getPiece(1, 2);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("K"));
  }
  xiangqi.makeMove(chessPieceStore, 1, 4);
  xiangqi.makeMove(chessPieceStore, 0, 4);

  chessPieceStore = chessBoard.getPiece(0, 4);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  xiangqi.makeMove(chessPieceStore, 2, 4);
  xiangqi.makeMove(chessPieceStore, 3, 4);
  chessPiece = chessBoard.getPiece(3, 4);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("K"));
  }
  xiangqi.makeMove(chessPieceStore, 2, 4);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  xiangqi.makeMove(chessPieceStore, 0, 4);

  // Creates one extra piece of its own side for testing capture
  RookPiece rookPiece = new RookPiece(Side.NORTH, 1, 4, "R", Color.white,
    chessBoard);
  chessBoard.addPiece(rookPiece, rookPiece.getRow(),
    rookPiece.getColumn());
  chessPieceStore = chessBoard.getPiece(0, 4);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  chessPiece = chessBoard.getPiece(1, 4);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("K"));
  }
  chessBoard.removePiece(1, 4);

  // Creates one extra piece of its opposite side for testing capture
  RookPiece rookPiece1 = new RookPiece(Side.SOUTH, 1, 4, "R", Color.red,
    chessBoard);
  chessBoard.addPiece(rookPiece1, rookPiece1.getRow(),
    rookPiece1.getColumn());
  chessPieceStore = chessBoard.getPiece(0, 4);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  chessPiece = chessBoard.getPiece(1, 4);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertEquals(label, "K");
  }
  xiangqi.makeMove(chessPieceStore, 0, 4);
 }

 @Test
 public void testGuardMakeMove() {
  /*
   * Test 6: Tests Guard's movement
   * 6.1 - Check Guard's legal move one place diagonal 
   * 6.2 - Check illegal diagonal move
   * 6.3 - Check illegal move beyond three central columns
   * 6.4 - Check illegal move beyond top/bottom three rows
   * 6.5 - Check capture move, make sure that it doesn't capture its own piece
   */

  ChessPiece chessPiece, chessPieceStore;
  String label;

  chessPiece = chessBoard.getPiece(0, 3);
  xiangqi.makeMove(chessPiece, 1, 4);
  chessPiece = chessBoard.getPiece(1, 4);
  label = chessPiece.getLabel();
  assertEquals(label, "G");
  xiangqi.makeMove(chessPiece, 0, 3);

  chessPiece = chessBoard.getPiece(0, 3);
  xiangqi.makeMove(chessPiece, 1, 2);
  chessPiece = chessBoard.getPiece(1, 2);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("G"));
  }

  // removes piece at 4,3 to test this
  chessBoard.removePiece(4, 3);

  chessPieceStore = chessBoard.getPiece(0, 3);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  xiangqi.makeMove(chessPieceStore, 2, 5);
  xiangqi.makeMove(chessPieceStore, 3, 4);
  xiangqi.makeMove(chessPieceStore, 4, 5);
  chessPiece = chessBoard.getPiece(4, 5);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("G"));
  }
  xiangqi.makeMove(chessPieceStore, 2, 5);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  xiangqi.makeMove(chessPieceStore, 0, 3);

  // Creates one extra piece of its own side for testing capture
  RookPiece rookPiece = new RookPiece(Side.NORTH, 1, 4, "R", Color.white,
    chessBoard);
  chessBoard.addPiece(rookPiece, rookPiece.getRow(),
    rookPiece.getColumn());
  chessPieceStore = chessBoard.getPiece(0, 3);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  chessPiece = chessBoard.getPiece(1, 4);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("G"));
  }
  chessBoard.removePiece(1, 4);

  // Creates one extra piece of its opposite side for testing capture
  RookPiece rookPiece1 = new RookPiece(Side.SOUTH, 1, 4, "R", Color.red,
    chessBoard);
  chessBoard.addPiece(rookPiece1, rookPiece1.getRow(),
    rookPiece1.getColumn());
  chessPieceStore = chessBoard.getPiece(0, 3);
  xiangqi.makeMove(chessPieceStore, 1, 4);
  chessPiece = chessBoard.getPiece(1, 4);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertEquals(label, "G");
  }
  xiangqi.makeMove(chessPieceStore, 0, 3);
 }

 @Test
 public void testElephantMakeMove() {
  /*
   * Test 7: Tests Elephant's movement
   * 7.1 - Check legal move 
   * 7.2 - Check illegal move, it can't jump over
   * 7.3 - Check capture move
   * 7.4 - Check that it can't cross center of the field
   */
  ChessPiece chessPiece, chessPieceStore;
  String label;

  chessPiece = chessBoard.getPiece(0, 2);
  xiangqi.makeMove(chessPiece, 2, 4);
  chessPiece = chessBoard.getPiece(2, 4);
  label = chessPiece.getLabel();
  assertEquals(label, "E");
  xiangqi.makeMove(chessPiece, 0, 2);

  // Creates one extra piece to test
  RookPiece rookPiece = new RookPiece(Side.NORTH, 1, 3, "R", Color.red,
    chessBoard);
  chessBoard.addPiece(rookPiece, rookPiece.getRow(),
    rookPiece.getColumn());
  chessPieceStore = chessBoard.getPiece(0, 2);
  xiangqi.makeMove(chessPieceStore, 2, 4);
  chessPiece = chessBoard.getPiece(2, 4);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("E"));
  }
  chessBoard.removePiece(1, 3);

  // Creates one extra piece of its opposite side for testing capture
  RookPiece rookPiece1 = new RookPiece(Side.SOUTH, 2, 4, "R", Color.red,
    chessBoard);
  chessBoard.addPiece(rookPiece1, rookPiece1.getRow(),
    rookPiece1.getColumn());
  chessPieceStore = chessBoard.getPiece(0, 2);
  xiangqi.makeMove(chessPieceStore, 2, 4);
  chessPiece = chessBoard.getPiece(2, 4);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertEquals(label, "E");
  }
  xiangqi.makeMove(chessPieceStore, 0, 2);

  chessPiece = chessBoard.getPiece(0, 2);
  xiangqi.makeMove(chessPiece, 2, 4);
  xiangqi.makeMove(chessPiece, 4, 6);
  xiangqi.makeMove(chessPiece, 6, 8);
  chessPiece = chessBoard.getPiece(6, 8);
  label = chessPiece.getLabel();
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("E"));
  }
  xiangqi.makeMove(chessPiece, 2, 4);
  xiangqi.makeMove(chessPiece, 0, 2);
 }

 @Test
 public void testKnightMakeMove() {
  /*
   * Test 8: Tests Knight's movement
   * 8.1 - Check legal move 
   * 8.2 - Check illegal move, it can't jump over 
   * 8.3 - Check capture move
   */
  ChessPiece chessPiece, chessPieceStore;
  String label;

  chessPiece = chessBoard.getPiece(0, 1);
  xiangqi.makeMove(chessPiece, 2, 2);
  chessPiece = chessBoard.getPiece(2, 2);
  label = chessPiece.getLabel();
  assertEquals(label, "N");
  xiangqi.makeMove(chessPiece, 0, 1);

  // Creates one extra piece to test
  RookPiece rookPiece = new RookPiece(Side.NORTH, 1, 1, "R", Color.red,
    chessBoard);
  chessBoard.addPiece(rookPiece, rookPiece.getRow(),
    rookPiece.getColumn());
  chessPieceStore = chessBoard.getPiece(0, 1);
  xiangqi.makeMove(chessPieceStore, 2, 2);
  chessPiece = chessBoard.getPiece(2, 2);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("N"));
  }
  chessBoard.removePiece(1, 1);

  // Creates one extra piece of its opposite side for testing capture
  RookPiece rookPiece1 = new RookPiece(Side.SOUTH, 2, 2, "R", Color.red,
    chessBoard);
  chessBoard.addPiece(rookPiece1, rookPiece1.getRow(),
    rookPiece1.getColumn());
  chessPieceStore = chessBoard.getPiece(0, 1);
  xiangqi.makeMove(chessPieceStore, 2, 2);
  chessPiece = chessBoard.getPiece(2, 2);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertEquals(label, "N");
  }
  xiangqi.makeMove(chessPieceStore, 0, 1);

 }

 @Test
 public void testRookMakeMove() {
  /*
   * Test 9: Tests Rooks's movement
   * 9.1 - Check legal move
   * 9.2 - Check illegal move, it can't jump over 
   * 9.3 - Check capture move
   */
  ChessPiece chessPiece, chessPieceStore;
  String label;

  chessPiece = chessBoard.getPiece(0, 0);
  xiangqi.makeMove(chessPiece, 2, 0);
  chessPiece = chessBoard.getPiece(2, 0);
  label = chessPiece.getLabel();
  assertEquals(label, "R");
  xiangqi.makeMove(chessPiece, 0, 0);

  chessPiece = chessBoard.getPiece(0, 0);
  xiangqi.makeMove(chessPiece, 4, 0);
  chessPiece = chessBoard.getPiece(4, 0);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("R"));
  }

  // Creates one extra piece of its opposite side for testing capture
  RookPiece rookPiece1 = new RookPiece(Side.SOUTH, 2, 0, "R", Color.red,
    chessBoard);
  chessBoard.addPiece(rookPiece1, rookPiece1.getRow(),
    rookPiece1.getColumn());
  chessPiece = chessBoard.getPiece(0, 0);
  xiangqi.makeMove(chessPiece, 2, 0);
  chessPiece = chessBoard.getPiece(2, 0);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertEquals(label, "R");
  }
  xiangqi.makeMove(chessPiece, 0, 0);

 }

 @Test
 public void testCanonMakeMove() {
   /*
   * Test 10: Tests Cannon's movement
   * 10.1 - Check legal move 
   * 10.2 - If the cannon is not capturing a piece, all the squares on its move must be empty
   * 10.3 - Check capture move jumping over one piece only
   */
  ChessPiece chessPiece, chessPieceStore;
  String label;

  chessPiece = chessBoard.getPiece(2, 1);
  xiangqi.makeMove(chessPiece, 2, 6);
  chessPiece = chessBoard.getPiece(2, 6);
  label = chessPiece.getLabel();
  assertEquals(label, "C");
  xiangqi.makeMove(chessPiece, 2, 1);

  // Creates one extra piece on the way
  RookPiece rookPiece1 = new RookPiece(Side.NORTH, 2, 4, "R", Color.red,
    chessBoard);
  chessBoard.addPiece(rookPiece1, rookPiece1.getRow(),
    rookPiece1.getColumn());
  chessPiece = chessBoard.getPiece(2, 1);
  xiangqi.makeMove(chessPiece, 2, 6);
  chessPiece = chessBoard.getPiece(2, 6);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("C"));
  }

  chessPiece = chessBoard.getPiece(2, 1);
  xiangqi.makeMove(chessPiece, 9, 1);
  chessPiece = chessBoard.getPiece(9, 1);
  label = chessPiece.getLabel();
  assertEquals(label, "C");
  xiangqi.makeMove(chessPiece, 2, 1);

 }

 @Test
 public void testPawnMakeMove() {
  /*
   * Test 11: Test pawn's move 
   * 11.1 - Check legal move forward
   * 11.2 - Check illegal move, move backward
   * 11.3 - Check illegal move, horizontal before center point
   * 11.5 - Horizontal move OK after center
   */
  ChessPiece chessPiece, chessPieceStore;
  String label;

  chessPiece = chessBoard.getPiece(3, 2);
  xiangqi.makeMove(chessPiece, 4, 2);
  chessPiece = chessBoard.getPiece(4, 2);
  label = chessPiece.getLabel();
  assertEquals(label, "P");
  xiangqi.makeMove(chessPiece, 3, 2);

  chessPiece = chessBoard.getPiece(3, 4);
  xiangqi.makeMove(chessPiece, 2, 4);
  chessPiece = chessBoard.getPiece(2, 4);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("P"));
  }

  chessPiece = chessBoard.getPiece(3, 4);
  xiangqi.makeMove(chessPiece, 3, 5);
  chessPiece = chessBoard.getPiece(3, 5);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertFalse(label.equals("P"));
  }

  chessPiece = chessBoard.getPiece(3, 4);
  xiangqi.makeMove(chessPiece, 4, 4);
  xiangqi.makeMove(chessPiece, 5, 4);
  xiangqi.makeMove(chessPiece, 5, 5);
  chessPiece = chessBoard.getPiece(5, 5);
  if (chessPiece != null) {
   label = chessPiece.getLabel();
   assertEquals(label, "P");
  }

 }

 @After
 public void tearDown() {
  xiangqi = null;
  display = null;
  chessBoard = null;
 }

}
