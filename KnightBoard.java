import java.util.*;
import java.io.*;
public class KnightBoard{

  public static void main(String[] args){
    KnightBoard test = new KnightBoard(5,5);
    System.out.println(test + "test");
    System.out.println(test.toStringMoves());
  //  System.out.println(test.solve(0,0));
  ArrayList<int[]> moves = new ArrayList<int[]>();
  test.addKnight(2,2,1);
  test.updateMoves(2,2, moves);
  test.updateMoves(0,1, moves);
  test.updateMoves(2,0, moves);
  // System.out.println(test.countSolutions(2,3));
    System.out.println(test);
    /*  for (int r = 0; r < 3; r++){
    for (int c = 0; c < 10; c++){
    if (test.solve(r,c)){
    System.out.println(test);
    c = 10;
    r = 3;

  }
}*/

}
private int[][] boardSequence;
private int[][] outgoingMoves;
private ArrayList<int[]> boardMoves = new ArrayList<int[]>();

// possible knight boardMoves
private int[] offsets = {1,2, 1,-2, -1,2, -1,-2, 2,1, 2,-1, -2,1, -2,-1};

/*  @throws IllegalArgumentException when either parameter is negative.
*/
public KnightBoard(int startingRows,int startingCols){
  if (startingRows < 0 || startingCols < 0){
    throw new IllegalArgumentException();
  }
  boardSequence = new int[startingRows][startingCols];
  outgoingMoves = new int[startingRows][startingCols];

  // initialize with all 0s
  for (int r = 0; r < boardSequence.length; r++){
      for (int c = 0; c < boardSequence[0].length; c++){
      boardSequence[r][c] = 0;
      outgoingMoves[r][c] = 0;
      // loop through move offsets, see if each is a possible move from the given row and col
    //  System.out.println("("+r+", "+c+")");
      for (int j = 0; j < 15; j+=2){
      //  System.out.println(j);
        if (r + offsets[j] >= 0 && r + offsets[j] < boardSequence.length
        &&  c + offsets[j+1] >= 0 && c + offsets[j+1] < boardSequence[0].length){

          outgoingMoves[r][c]++;
        //  System.out.println("("+r+", "+c+"), move by "+ offsets[j]+", " +offsets[j+1]+": " +outgoingMoves[r][c]);
        }
      }
    }
  }
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

// for the board of outgoing moves
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
public  String toStringBoardMoves(ArrayList<int[]> board){
  String toStr = "";
  for (int i = 0; i < board.size(); i++){
    toStr += Arrays.toString( board.get(i) );
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
  for (int i = 0; i < boardSequence.length; i++){
    if (boardSequence[i][0] != 0){
      throw new IllegalStateException();
    }
  }
  return solveH(startingRow, startingCol, 1);
}
// level is the # of the knight
private boolean solveH(int row ,int col, int level){
/*  System.out.println(KnightBoard.go(1,1));
  System.out.println(this);
  KnightBoard.wait(50); */ //adjust this delay
  // if all levels were reached, board is solveable. Return true
  if (level > boardSequence.length * boardSequence[0].length){
    return true;
  }
  else{
    // check if knight can be added
    if (addKnight(row,col,level)){
      // store possible moves and coordinates from this position
      updateMoves(row, col, boardMoves);
      // recursively call on all possible moves
      for (int i = 0; i < boardMoves.size(); i++){
        if (solveH(boardMoves.get(i)[1],boardMoves.get(i)[2],level+1)){
          return true;
        }
      }
      removeKnight(row, col, 0);
    }

  }

  return false;
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
    updateMoves(row,col, boardMoves);
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
public void updateMoves(int r, int c, ArrayList<int[]> boardMoves_){
    boardMoves_ = new ArrayList<int[]>();
    for (int j = 0; j < 15; j += 2){
      if (r + offsets[j] >= 0 && r + offsets[j] < boardSequence.length
      &&  c + offsets[j+1] >= 0 && c + offsets[j+1] < boardSequence[0].length
      && boardSequence[r + offsets[j]][c + offsets[j+1]] == 0){
        // each array contains number of possible moves from the given offset spot
        // subtract one from outgoing moves because knight can't move back

        outgoingMoves[r + offsets[j]][c + offsets[j+1]]--;

        int[] moveLocation = {outgoingMoves[r + offsets[j]][c + offsets[j+1]], offsets[j],offsets[j+1]};
        System.out.println(Arrays.toString(moveLocation));
       boardMoves_.add(moveLocation);
    }
   }
   System.out.println(toStringMoves());
   System.out.println("unsorted moves "+toStringBoardMoves(boardMoves_));

   // sort the moves
  insertionSort(boardMoves_);
  System.out.println(boardMoves_.size());
  System.out.println("sorted moves "+toStringBoardMoves(boardMoves_));
}

 public static void insertionSort(ArrayList<int[]> data){
   System.out.println("DEBUGGING FOR INSERTIONSORT");
   System.out.println("DATA SIZE: " + data.size());
  if (data.size() >= 2){
    System.out.println("FIRST IF");
    // loop through arrayList, checking number at current index relative to previous numbers
    for (int i = 0; i < data.size(); i++){
      // current number
      int[] current = data.get(i);
      System.out.println("CURRENT: " + Arrays.toString(current));
      int index = i;
      for (int j = i; j >= 0; j--){
        int[] temp = data.get(j);
     // inner loop checks if previous elements (number of moves) are greater than current element
      if (current[0] < data.get(j)[0]){
        System.out.println(current[0] + " < " + data.get(j)[0]);
        // store the previous array
        data.set(j+1, temp);
        System.out.println("TEMP: " + Arrays.toString(temp));

       // if current number of moves for outer loop is less than the previous number(s) of moves
            // previous array moves up a space
            data.set( j+1, temp );
            System.out.println("MOVING TEMP UP: "+Arrays.toString(data.get(j+1)));
            // current array will be put at a smaller index
            index--;
        }
        // set array at index equal to current array (sorted)
        data.set(index, current);
        System.out.println("SETTING INDEX TO ORIGINAL: "+Arrays.toString(data.get(index)));
      }
     }
    }
  }

}
