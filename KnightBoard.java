import java.util.*;
import java.io.*;
public class KnightBoard{

  public static void main(String[] args){
    if (args.length == 0){
      System.out.println("3 x 3 BOARD");
      KnightBoard test = new KnightBoard(3,3);
      System.out.println("SOLVEABLE FROM (0,0)?: " +test.solve(0,0)+"\n");
      System.out.println("6 x 6 BOARD");
      KnightBoard test1 = new KnightBoard(6,6);

      System.out.println("SOLVEABLE FROM (0,0)?: " +test1.solve(0,0)+"\n");
      System.out.println("5 x 5 BOARD");
      KnightBoard test2 = new KnightBoard(5,5);
      System.out.println("NUMBER OF SOLUTIONS FROM (0,0): "+ test2.countSolutions(0,0)+"\n");
    }
    else if (args.length >= 2){
      try{
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        KnightBoard a = new KnightBoard(rows, cols);
        KnightBoard b = new KnightBoard(rows, cols);
        System.out.println(rows+" X "+cols+" BOARD");
        System.out.println("SOLVEABLE FROM (0,0)?: "+a.solve(0,0)+"\n");
        if (rows < 6 && cols < 6){
          System.out.println("NUMBER OF SOLUTIONS FROM (0,0): "+b.countSolutions(0,0)+"\n");
        }
      }catch(IllegalStateException e){
        System.out.println("IllegalStateException caught in main");
      }catch(IllegalArgumentException f){
        System.out.println("IllegalArgumentException caught in main");
      }
    }
  }
  // m x n board to show sequence of knight moves
  private int[][] boardSequence;
  // m x n board to show possible outgoing moves from given position
  private int[][] outgoingMoves;
  // possible knight moves
  private int[] offsets = {1,2, 1,-2, -1,2, -1,-2, 2,1, 2,-1, -2,1, -2,-1};

  /*  @throws IllegalArgumentException when either parameter is negative.
  */
  public KnightBoard(int startingRows,int startingCols){
    if (startingRows < 0 || startingCols < 0){
      throw new IllegalArgumentException();
    }
    boardSequence = new int[startingRows][startingCols];
    outgoingMoves = new int[startingRows][startingCols];

    // initialize boardSequence with all 0s
    for (int r = 0; r < boardSequence.length; r++){
      for (int c = 0; c < boardSequence[0].length; c++){
        boardSequence[r][c] = 0;
        outgoingMoves[r][c] = 0;

        // loop through move offsets, see if each is a possible move from the given row and col
        // and update outgoingMoves board
        for (int j = 0; j < 15; j+=2){

          if (r + offsets[j] >= 0 && r + offsets[j] < boardSequence.length
          &&  c + offsets[j+1] >= 0 && c + offsets[j+1] < boardSequence[0].length){

            outgoingMoves[r][c]++;

          }
        }
      }
    }
  }

  // toString for the boardSequence
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

  // toString for the board of outgoing moves
  public String toStringMoves(){
    String boardStr = "";

    // prints out m x n board with number of outgoing moves from each position
    for (int r = 0; r < outgoingMoves.length; r++){
      for (int c = 0; c < outgoingMoves[0].length; c++){
        boardStr += "  " + outgoingMoves[r][c];
        if (c == boardSequence[0].length - 1){
          boardStr += "\n";
        }
      }
    }
    return boardStr;
  }

  // for the arraylist that contains possible moves and coordinates
  public  String toStringBoardMoves(ArrayList<int[]> boardMoves){
    String toStr = "";
    for (int i = 0; i < boardMoves.size(); i++){
      toStr += Arrays.toString( boardMoves.get(i) );
    }
    return toStr;
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
    // throw IllegalArgumentException if given non-positive or ount of bounds parameters
    if (startingRow < 0 || startingCol < 0 || startingRow >= boardSequence.length || startingCol >= boardSequence[0].length){
      throw new IllegalArgumentException();
    }
    // throw IllegalStateException if given non-empty board
    for (int i = 0; i < boardSequence.length; i++){
      if (boardSequence[i][0] != 0){
        throw new IllegalStateException();
      }
    }
    return solveH(startingRow, startingCol, 1);
  }

  // level is the # of the knight
  private boolean solveH(int row ,int col, int level){
    // if all levels were reached, board is solveable. Return true
    if (level > boardSequence.length * boardSequence[0].length){
      return true;
    }
    // otherwise
    else{

      // check if knight can be added
      if (addKnight(row,col,level)){
        // store possible moves and coordinates from this position into an arrayList
        ArrayList<int[]> boardMoves = new ArrayList<int[]>();
        boardMoves = updateMoves(row, col, boardMoves);

        // recursively call on all possible moves
        for (int i = 0; i < boardMoves.size(); i++){
          if (solveH(row+boardMoves.get(i)[1],col+boardMoves.get(i)[2],level+1)){
            return true;
          }
          else{
            // add 1 back to possible outgoing moves if board isn't solveable from that position
            outgoingMoves[row+boardMoves.get(i)[1]][col+boardMoves.get(i)[2]]++;
          }
        }
        /* if there are no possible moves but board is solveable from given position,
        return true*/
        if (boardMoves.size() == 0){
          if (solveH(row,col,level+1)){
            return true;
          }
        }
      }
      // reset value at given position to 0 if knight can't be added
      removeKnight(row, col, 0);
    }
    return false;
  }

  // count number of possible solutions from starting row, column.
  // throws IllegalStateException if board is in a non-zero state
  // throws IllegalArgumentException if given non-positive or out of bounds parameters
  public int countSolutions(int startingRow, int startingCol){
    if (startingRow < 0 || startingCol < 0 || startingRow >= boardSequence.length || startingCol >= boardSequence[0].length){
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < boardSequence.length; i++){
      if (boardSequence[i][0] != 0){
        throw new IllegalStateException();
      }
    }
    // return possible solutions, excluding duplicates
    return countSolutionsH(startingRow, startingCol, 1) / 8;
  }

  // level is the knight move #
  private int countSolutionsH(int row, int col, int level){

    // if all levels were reached, add 1 to number of solutions
    if (level > boardSequence.length * boardSequence[0].length){
      return 1;
    }
    // variable to keep track of number of solutions
    int numSolns = 0;
    // if knight can be added, try different possibilities for next level
    if (addKnight(row, col, level)){
      for (int i = 0; i < 15; i+= 2){
        numSolns += countSolutionsH(row+offsets[i],col+offsets[i+1],level+1);
      }

      removeKnight(row, col, 0);
    }
    // return number of possible solutions
    return numSolns;
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

  // remove knight from given row, column
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
  public ArrayList<int[]> updateMoves(int r, int c, ArrayList<int[]> boardMoves){
    boardMoves = new ArrayList<int[]>();
    // loop through all the possible moves
    for (int j = 0; j < 15; j += 2){
      /* if move is in range of the board and
      the knight has not traveled to that position */
      if (r + offsets[j] >= 0 && r + offsets[j] < boardSequence.length
      &&  c + offsets[j+1] >= 0 && c + offsets[j+1] < boardSequence[0].length
      && boardSequence[r + offsets[j]][c + offsets[j+1]] == 0){
        // if next move can be made, add the int array to the list of possible moves
        if (outgoingMoves[r + offsets[j]][c + offsets[j+1]] >= 0){
          // subtract one from outgoing moves because knight can't move to previous move
          outgoingMoves[r + offsets[j]][c + offsets[j+1]]--;
          // each array contains the number of outgoing moves, the row offset, and the column offset
          int[] moveLocation = {outgoingMoves[r + offsets[j]][c + offsets[j+1]], offsets[j],offsets[j+1]};
          boardMoves.add(moveLocation);
        }
      }
    }
    // sort the moves by ascending order by number of outgoing moves
    insertionSort(boardMoves);
    return boardMoves;
  }

  // insertion sort to sort the given arraylist
  public static void insertionSort(ArrayList<int[]> data){

    if (data.size() >= 2){

      // loop through arrayList, checking number of outgoing moves for array at current index relative to those of previous arrays
      for (int i = 0; i < data.size(); i++){
        // current array and index
        int[] current = data.get(i);
        int index = i;

        for (int j = i; j >= 0; j--){
          // temporary array
          int[] temp = data.get(j);
          // inner loop checks if previous number of outgoing moves are greater than current number of outgoing moves
          if (current[0] < data.get(j)[0]){
            // store the previous array into temp
            data.set(j+1, temp);
            /* if current number of outgoing moves for outer loop is less than the previous number(s) of moves
            / previous array moves up a space*/
            data.set( j+1, temp );
            // current array will be put at a smaller index
            index--;
          }
          // set array at index equal to current array (sorted)
          data.set(index, current);
        }
      }
    }
  }

}
