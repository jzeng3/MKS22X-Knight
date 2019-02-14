public class KnightBoard{

  public static void main(String[] args){
    KnightBoard test = new KnightBoard(4,4);
    System.out.println(test);
    test.solve(0,0);
    System.out.println(test);
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
/*  @throws IllegalArgumentException when either parameter is negative.
*/
  public KnightBoard(int startingRows,int startingCols){
    if (startingRows < 0 || startingCols < 0){
      throw new IllegalArgumentException();
    }
    boardSequence = new int[startingRows][startingCols];
    // initialize with all 0s
    for (int r = 0; r < startingRows; r++){
      for (int c = 0; c < startingCols; c++){
        boardSequence[r][c] = 0;
      }
    }
  }

  public String toString(){
    String boardStr = "";
    for (int r = 0; r < boardSequence.length; r++){
      for (int c = 0; c < boardSequence[0].length; c++){
        // add extra space if number is less than 10 for nice formatting
        if (boardSequence[r][c] < 10){
          boardStr += "  " + boardSequence[r][c];
        }
        else{
          boardStr += " " + boardSequence[r][c];
        }
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
    return solveH(0,0,1);
  }
  public int countSolutions(int startingRow, int startingCol){
    return 0;
  }
  // level is the # of the knight
  private boolean solveH(int row ,int col, int level){
    System.out.println(KnightBoard.go(1,1));
System.out.println(this);
KnightBoard.wait(5); //adjust this delay

if (level == boardSequence.length * boardSequence[0].length){
  // System.out.println("All levels reached");
  return true;
}
else{
  // check if knight can be added
if (addKnight(row,col,level)){
  // System.out.println("i: "+i);
//  System.out.println("added knight to "+ (row+i) + " "+col+" at level " + level);
//  System.out.println("level "+level);
boardSequence[row][col] = level;
// recursively call on all possible moves
if (solveH(row+1,col+2,level+1)){
  return true;
}
if (solveH(row+1,col-2,level+1)){
  return true;
}
if (solveH(row-1,col-2,level+1)){
  return true;
}
if (solveH(row-1,col+2,level+1)){
  return true;
}
if (solveH(row+2,col+1,level+1)){
  return true;
}
if (solveH(row+2,col-1,level+1)){
  return true;
}
if (solveH(row-2,col+1,level+1)){
  return true;
}
if (solveH(row-2,col-1,level+1)){
  return true;
}
removeKnight(row, col, 0);
}


}
    // System.out.println("Not all levels were reached");
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
}
