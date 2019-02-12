public class KnightBoard{
  private int[][] boardSequence;
/*  @throws IllegalArgumentException when either parameter is negative.
*/
  public KnightBoard(int startingRows,int startingCols){
    if (startingRows < 0 || startingCols < 0){
      throw new IllegalArgumentException();
    }
    boardSequence = new int[startingRows][startingCols];
  }

  public String toString(){
    return "";
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
