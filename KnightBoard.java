public class KnightBoard{

  public static void main(String[] args){
    KnightBoard test = new KnightBoard(3,3);
    System.out.println(test);
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
    return false;
  }
}
