public class KnightBoard{

  public static void main(String[] args){
    KnightBoard test = new KnightBoard(3,3);
    System.out.println(test);
    test.solveH(0,0,1);
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
          boardStr += " " + boardSequence[r][c];
        }
        else{
          boardStr += boardSequence[r][c];
        }
        if (c == boardSequence[0].length - 1){
          boardStr += "\n";
        }
      }
    }
    return boardStr;
  }
/*  @throws IllegalStateException when the board contains non-zero values.
  @throws IllegalArgumentException when either parameter is negative
  or out of bounds.*/
  public int countSolutions(int startingRow, int startingCol){
    return 0;
  }
  // level is the # of the knight
  private boolean solveH(int row ,int col, int level){
if (level == boardSequence.length * boardSequence[0].length){
  return true;
}
else if (row >= 0 && row < boardSequence.length && col >= 0 && col < boardSequence[0].length){
boardSequence[row][col] = level;
solveH(row+1,col+2,level+1);
solveH(row+1,col-2,level+1);
solveH(row-1,col-2,level+1);
solveH(row-1,col+2,level+1);
solveH(row+2,col+1,level+1);
solveH(row+2,col-1,level+1);
solveH(row-2,col+1,level+1);
solveH(row-2,col-1,level+1);
}
    return false;
  }
  // move knight by specified x (left/right) and y (up/down)
  private boolean moveKnight(int row, int col, int y, int x){
    // if move is in range of the board
    if (col + x < boardSequence[0].length && col + x >= 0 &&
        row + y < boardSequence.length && row + y >= 0){
          // if move is a valid knight move
          if (Math.abs(x) == 1 && Math.abs(y) == 2
             || Math.abs(x) == 2 && Math.abs(y) == 1){
               // mark spot as next number in the sequence
              boardSequence[row + y][col + x] = boardSequence[row][col] + 1;
              return true;
             }
    }
    return false;
  }
}
