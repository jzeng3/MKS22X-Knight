import java.util.*;
import java.io.*;
public class KnightBoard{

  public static void main(String[] args){
    KnightBoard test = new KnightBoard(5,4);
    System.out.println(test + "test");
    System.out.println(test.toStringMoves());
    System.out.println(test.countSolutions(0,0));
    System.out.println(test);
    /*  for (int r = 0; r < 3; r++){
    for (int c = 0; c < 10; c++){
    if (test.solve(r,c)){
    System.out.println(test);
    c = 10;
    r = 3;

  }
}*/
/*  test.moveKnight(0,0,2,1);
System.out.println(test);
test.moveKnight(2,1,-2,-1);
System.out.println(test);
test.moveKnight(0,0,1,2);
System.out.println(test);
test.moveKnight(1,2,1,-2);
System.out.println(test);*/

}
private int[][] boardSequence;
private int[][] outgoingMoves;
private ArrayList<int[]> boardMoves;

/*  @throws IllegalArgumentException when either parameter is negative.
*/
public KnightBoard(int startingRows,int startingCols){
  if (startingRows < 0 || startingCols < 0){
    throw new IllegalArgumentException();
  }
  boardSequence = new int[startingRows][startingCols];
  outgoingMoves = new int[startingRows][startingCols];
  // initialize with all 0s
  for (int r = 0; r < startingRows; r++){
    for (int c = 0; c < startingCols; c++){
      boardSequence[r][c] = 0;
      if (startingRows == 1 || startingCols == 1
          || (startingRows == startingCols && startingRows < 3)){
        outgoingMoves[r][c] = 0;
      }
      // 2 possible from the corners
      if (startingRows >= 3 && startingCols >= 3 ){
        if ( (r == 0 || r == outgoingMoves.length - 1)
            && (c == 0 || c == outgoingMoves[0].length - 1)
            ){
            outgoingMoves[r][c] = 2;
        }
      }

    }
  }
  boardMoves = new ArrayList<int[]>();
}

public String toString(){
  String boardStr = "";
  for (int r = 0; r < boardSequence.length; r++){
    for (int c = 0; c < boardSequence[0].length; c++){
      // add extra space if number is less than 10 for nice formatting
      if (boardSequence[r][c] == 0){
        boardStr += " " + "__";
      }
      else if (boardSequence[r][c] < 10){
        boardStr += "  " + boardSequence[r][c];
      }
      else if (boardSequence[r][c] >= 10){
        boardStr += " " + boardSequence[r][c];
      }
      if (c == boardSequence[0].length - 1){
        boardStr += "\n";
      }
    }
  }
  return boardStr;
}
public String toStringMoves(){
  String boardStr = "";

for (int r = 0; r < outgoingMoves.length; r++){
    for (int c = 0; c < outgoingMoves[0].length; c++){
      // add extra space if number is less than 10 for nice formatting
      boardStr += "  " + outgoingMoves[r][c];
      if (c == boardSequence[0].length - 1){
        boardStr += "\n";
      }
    }
  }
  return boardStr;
}
// taken from Mr. K's website for debugging
public static String go(int x,int y){
  return ("\033[" + x + ";" + y + "H");
}
public static void wait(int millis){
  try {
    Thread.sleep(millis);
  }
  catch (InterruptedException e) {
  }
}

/*  Modifies the board by labeling the moves from 1 (at startingRow,startingCol) up to the area of the board in proper knight move steps.
@throws IllegalStateException when the board contains non-zero values.
@throws IllegalArgumentException when either parameter is negative
or out of bounds.
@returns true when the board is solvable from the specified starting position
public boolean solve(int startingRow, int startingCol)
/*  @throws IllegalStateException when the board contains non-zero values.
@throws IllegalArgumentException when either parameter is negative
or out of bounds.*/
public boolean solve(int startingRow, int startingCol){
  for (int i = 0; i < boardSequence.length; i++){
    if (boardSequence[i][0] != 0){
      throw new IllegalStateException();
    }
  }
  return solveH(startingRow, startingCol, 1);
}
// count number of possible solutions from starting row, column.
// throws IllegalStateException if board is in a non-zero state
public int countSolutions(int startingRow, int startingCol){
  for (int i = 0; i < boardSequence.length; i++){
    if (boardSequence[i][0] != 0){
      throw new IllegalStateException();
    }
  }
  // return possible solutions, excluding duplicates
  return countSolutionsH(startingRow, startingCol, 1) / 8;
}
private int countSolutionsH(int row, int col, int level){
  /* System.out.println(KnightBoard.go(1,1));
  System.out.println(this);
  KnightBoard.wait(1);*/

  // if all levels were reached, add 1 to number of solutions
  if (level > boardSequence.length * boardSequence[0].length){
    return 1;
  }
  int numSolns = 0;
  // if knight can be added, try different possibilities for next level
  if (addKnight(row, col, level)){
    numSolns += countSolutionsH(row+1,col+2,level+1);

    numSolns += countSolutionsH(row+1,col-2,level+1);

    numSolns += countSolutionsH(row-1,col+2,level+1);

    numSolns += countSolutionsH(row-1, col-2, level+1);

    numSolns += countSolutionsH(row+2,col+1,level+1);

    numSolns += countSolutionsH(row+2,col-1,level+1);

    numSolns += countSolutionsH(row-2,col+1,level+1);

    numSolns += countSolutionsH(row-2,col-1,level+1);

    removeKnight(row, col, 0);
  }
  // return number of possible solutions
  return numSolns;
}
// level is the # of the knight
private boolean solveH(int row ,int col, int level){
  /*System.out.println(KnightBoard.go(1,1));
  System.out.println(this);
  KnightBoard.wait(50);*/ //adjust this delay
  // if all levels were reached, board is solveable. Return true
  if (level > boardSequence.length * boardSequence[0].length){
    return true;
  }
  else{
    // check if knight can be added
    if (addKnight(row,col,level)){
      boardSequence[row][col] = level;
      // recursively call on all possible moves
      if ( solveH(row+1,col+2,level+1)
      || solveH(row+1,col-2,level+1)
      || solveH(row-1,col+2,level+1)
      || solveH(row-1, col-2, level+1)
      || solveH(row+2,col+1,level+1)
      || solveH(row+2,col-1,level+1)
      || solveH(row-2,col+1,level+1)
      || solveH(row-2,col-1,level+1) ){
        return true;
      }
      removeKnight(row, col, 0);
    }

  }

  return false;
}

// move knight by specified x (left/right) and y (up/down)
private boolean addKnight(int row, int col, int level){
  // if move is in range of the board
  if (
  row >= 0 && row < boardSequence.length &&
  col >= 0 && col < boardSequence[0].length &&
  boardSequence[row][col] == 0){
    // mark spot as next number in the sequence
    boardSequence[row][col] = level;
    return true;
  }
  return false;
}
private boolean removeKnight(int row, int col, int level){
  // if move is in range of the board
  if (
  row >= 0 && row < boardSequence.length &&
  col >= 0 && col < boardSequence[0].length &&
  boardSequence[row][col] != 0){
    // mark spot as next number in the sequence
    boardSequence[row][col] = level;
    return true;
  }
  return false;
}
// fill out board with number of possible outgoing moves from each position
private void outgoingMoves(){
  for (int i = 0; i < boardSequence.length; i++){

  }
}


}
