//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  Ujjwal Gulecha                                          //
// Date:    10/25/16                                                //
//                                                                  //
// Name: Tiffany Liu                                                //
// Login: cs8bfch                                                   //
// Sources of Help:                                                 //              
//         Book: Introduction to Java Programming by Y. Daniel Liang//
//                                                                  //
// Program Description:                                             //
//         Contains the constructors for the board class used in    //
//         the 2048 game.                                           //
//------------------------------------------------------------------//

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

/*
 * Name:    Board
 * Purpose: Creates a board for the 2048 game. Contains several
 *          useful methods such as save, rotate, etc. 
 */

public class Board {
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random;
    private int[][] grid;
    private int score;

 /* 
  * Name:       Board
  * Purpose:    Constructs a fresh board with random tiles.
  * Parameters: int boardSize - size of board
  *             Random random - random object in order to add tiles
  * Return:     n/a (constructor, not method--contains board object)
  */
    public Board(int boardSize, Random random) {
        this.random = random;
        GRID_SIZE = boardSize; //sets size to input boardSize
        grid = new int[GRID_SIZE][GRID_SIZE]; //creates grid array
          
        score = 0; //sets starting score to zero
        
        //adds starter tiles
        for (int i = 0; i < NUM_START_TILES; i++)
             addRandomTile(); 
    }

 /* 
  * Name:       Board
  * Purpose:    Constructs a board based off of an input file.
  * Parameters: String inputBoard - board file inputted by user
  *             Random random - random object in order to add tiles
  * Return:     n/a (constructor, not method--contains board object)
  */
    public Board(String inputBoard, Random random) throws IOException {
        this.random = random; 
        
        // reads sourcefile
        Scanner file = new Scanner(new File(inputBoard)); 
        
        GRID_SIZE = file.nextInt(); 
        score = file.nextInt();
        
        //creates new grid
        grid = new int[GRID_SIZE][GRID_SIZE]; 
        
        while (file.hasNext())
        {
          for (int i = 0; i < GRID_SIZE; i++)
          {
            for (int m = 0; m < GRID_SIZE; m++)
              {
              int nextNum = file.nextInt();
              grid[i][m] = nextNum; 
              }
          }
               
        }
        
    }

 /* 
  * Name:       saveBoard
  * Purpose:    Saves the current board to a file.
  * Parameters: String outputBoard - file name to save the board to
  * Return:     n/a (void)
  */
    public void saveBoard(String outputBoard) throws IOException {
      
      // creating printwriter
      java.io.PrintWriter output = new java.io.PrintWriter(outputBoard);
      
      // saves values
      output.println(GRID_SIZE);
      output.println(score); 
    
      for (int i = 0; i < GRID_SIZE; i++)
      {
        for (int m = 0; m < GRID_SIZE; m++)
        {
          output.print(grid[i][m] + " ");
        }
        output.println(); 
      }
      output.close(); //closes printwriter
    
      }

 /* 
  * Name:       addRandomTile
  * Purpose:    Adds a random tile (of value 2 or 4) to a random
  *             empty space on the board. If there are no open tiles,
  *             returns without saving the board. 
  * Parameters: n/a
  * Return:     n/a (void)
  */
    public void addRandomTile() {
      
      int count = 0; 
      
      for (int i = 0; i < GRID_SIZE; i++)
      {
        for (int m = 0; m < GRID_SIZE; m++)
        {
          // counts the tiles on the board
          // and finds the available spaces
          if (grid[i][m] == 0)
            count ++;
        }
      }
      
      // if count = 0, then exit
      if (count == 0)
        return;
     
      
      if (count != 0)
      {
        int location = random.nextInt(count);
        int value = random.nextInt(100);
        int place = 0; // new count #, keeps track of empty spaces
        
        for (int i = 0; i < GRID_SIZE; i++)
        {
          for (int m = 0; m < GRID_SIZE; m++)
          {
            if (grid[i][m] == 0)
            {
              if (place == location)
              {
                if (value < TWO_PROBABILITY)
                {
                  grid[i][m] = 2; 
                }
              
                else
                  grid[i][m] = 4; 
                
                return; 
              }
              
              else 
                place++; 
            } 
          }
        }
        
      }

    }

 /* 
  * Name:       rotate
  * Purpose:    Rotates the board by 90 degrees clockwise or 90 degrees
  *             counter-clockwise. If rotateClockwise == true, rotates
  *             the board clockwise, else rotates the board counter-clockwise.
  * Parameters: boolean rotateClockwise - determines whether to rotate
  *                                       clockwise or counter-clockwise
  * Return:     n/a (void)
  */
    public void rotate(boolean rotateClockwise) {
      
      // creates a new board for rotation
      int[][] rotate = new int[GRID_SIZE][GRID_SIZE];
      int length = GRID_SIZE - 1; 
      
      // rotates the board "rotate" 90 degrees clockwise
      if (rotateClockwise == true)
      {
        for (int r = 0; r < GRID_SIZE; r++)
        {
          for (int c = 0; c < GRID_SIZE; c++)
          {
            rotate[r][length - c] = grid[c][r];
          }
        }
      }
      
      // rotates the board "rotate" 90 degrees counter-clockwise
      else
      {
        for (int r = 0; r < GRID_SIZE; r++)
        {
          for (int c = 0; c < GRID_SIZE; c++)
          {
            rotate[length - r][c] = grid[c][r];
          }
        }
      }
      
      // copies new grid "rotate" to original
      for(int i = 0; i < GRID_SIZE; i++)
      {
        for(int m = 0; m < GRID_SIZE; m++)
        {
         grid[i][m] = rotate[i][m]; 
        }
      }

    }

    //Complete this method ONLY if you want to attempt at getting the extra credit
    //Returns true if the file to be read is in the correct format, else return
    //false
    public static boolean isInputFileCorrectFormat(String inputFile) {
        //The try and catch block are used to handle any exceptions
        //Do not worry about the details, just write all your conditions inside the
        //try block
        try {
            //write your code to check for all conditions and return true if it satisfies
            //all conditions else return false
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
 /* 
  * Name:       canMove
  * Purpose:    Determines if the board can move in the passed direction.
  * Parameters: Direction direction - direction to be moved 
  * Return:     true if able to be moved, false if not
  */
    public boolean canMove(Direction direction)
    {
      if (direction == Direction.LEFT)
      {
        return canMoveLeft();
      }
      
      else if (direction == Direction.RIGHT)
      {
        return canMoveRight();
      }
      
      else if (direction == Direction.UP)
      {
        return canMoveUp();
      }
      
      else if (direction == Direction.DOWN)
      {
        return canMoveDown(); 
      }
      
      else
        return false;
    }
    
   // - - - - - - - - - - - - HELPER METHODS - - - - - - - - - - - - \\
   //                   ( for the canMove method )
    
  /* 
  * Name:       canMoveLeft
  * Purpose:    Determines if the board can move left.
  * Parameters: N/A 
  * Return:     true if able to be moved, false if not
  */
    private boolean canMoveLeft()
    {
      for (int i = 0; i < GRID_SIZE; i++)
      {
        for (int m = 1; m < GRID_SIZE; m++)
        {
          // checking for empty spaces
          if (grid[i][m] > 0) 
          {
            // returns true if there is a 0 to the left
            if (grid[i][m - 1] == 0)
              return true; 
            
            // checks for same value that can be combined
            else if (grid[i][m] == grid[i][m - 1])
              return true;
          }
        }
      }
      //returns false if none of the conditions are met
      return false;
    }
    
 /* 
  * Name:       canMoveRight
  * Purpose:    Determines if the board can move right.
  * Parameters: N/A 
  * Return:     true if able to be moved, false if not
  */
    private boolean canMoveRight()
    {
      for (int i = 0; i < GRID_SIZE; i++)
      {
        for (int m = 0; m < GRID_SIZE - 1; m++)
        {
          // checking for empty spaces
          if (grid[i][m] > 0) 
          {
            // returns true if there is a 0 to the right
            if (grid[i][m + 1] == 0)
              return true; 
            
            // checks for same value that can be combined
            else if (grid[i][m] == grid[i][m + 1])
              return true;
          }
        }
      }
      //returns false if none of the conditions are met
      return false;
    }
    
 /* 
  * Name:       canMoveUp
  * Purpose:    Determines if the board can move up.
  * Parameters: N/A 
  * Return:     true if able to be moved, false if not
  */
    private boolean canMoveUp()
    {
      for (int i = 1; i < GRID_SIZE; i++)
      {
        for (int m = 0; m < GRID_SIZE; m++)
        {
          // checking for empty spaces
          if (grid[i][m] > 0) // != 0?? 
          {
            // returns true if there is a 0 above it
            if (grid[i - 1][m] == 0)
              return true; 
            
            // checks for same value that can be combined
            else if (grid[i][m] == grid[i - 1][m])
              return true;
          }
        }
      }
      //returns false if none of the conditions are met
      return false;
    }
    
 /* 
  * Name:       canMoveDown
  * Purpose:    Determines if the board can move down.
  * Parameters: N/A 
  * Return:     true if able to be moved, false if not
  */
    private boolean canMoveDown()
    {
      for (int i = 0; i < GRID_SIZE - 1; i++)
      {
        for (int m = 0; m < GRID_SIZE; m++)
        {
          // checking for empty spaces
          if (grid[i][m] > 0) // != 0?? 
          {
            // returns true if there is a 0 below 
            if (grid[i + 1][m] == 0)
              return true; 
            
            // checks for same value that can be combined
            else if (grid[i][m] == grid[i + 1][m])
              return true;
          }
        }
      }
      //returns false if none of the conditions are met
      return false;
    }

 // - - - - - - - - - - - - - MOVE METHODS - - - - - - - - - - - - - \\
 /* 
  * Name:       move
  * Purpose:    Performs a move in one of four directions after 
  *             confirming that the move is possible through the
  *             canMove method.
  * Parameters: Direciton direction - direction to be moved in
  * Return:     true if move was successful, false if not
  */
    public boolean move(Direction direction) {
      
      if (direction == Direction.LEFT && canMove(Direction.LEFT))
      {
        moveLeft();
        return true;
      }
      
      else if (direction == Direction.RIGHT && canMove(Direction.RIGHT))
      {
        moveRight();
        return true;
      }
      
      else if (direction == Direction.UP && canMove(Direction.LEFT))
      {
        moveUp();
        return true;
      }
      
      else if (direction == Direction.DOWN && canMove(Direction.DOWN))
      {
        moveDown();
        return true;
      }
      
      else
        return false;
    }
    
    // - - - - - - - - - - - - - HELPER METHODS - - - - - - - - - - - - - \\
    //                       ( for the move method)
    
 /* 
  * Name:       mergeDir
  * Purpose:    Performs a merge in the given direction for 
  *             tiles that meet the conditions.
  * Parameters: String direction - direction as inputted by user,
  *                                either "up" or "down"
  * Return:     n/a
  */
    public void mergeDir(String direction)
    { 
       // if parameter is set to "up," executes the following
      if (direction.equals("up"))
      {
        for (int m = 0; m < GRID_SIZE; m++)
        {
          for (int i = 0; i < GRID_SIZE; i++)
          {
            if (grid[i][m] > 0)
            {
              // finds first non-zero tile below current tile
              for (int j = i + 1; j < GRID_SIZE; j++)
              {
                // checks for same value that can be combined
                if (grid[j][m] > 0 && grid[j][m] == grid[i][m])
                {
                  // if both conditions are met, merges tiles 
                  // and increases score
                  score = score + (grid[j][m] * 2);
                  grid[i][m] = grid[j][m] * 2;
                  grid[j][m] = 0;
                }
                break;
              }
            } 
          }
        }
      }
      
      // if parameter is set to "down," executes the following
      else if (direction.equals("down"))
      {
        for (int m = 0; m < GRID_SIZE; m++)
        {
          // starts from the bottom of the grid
          for (int i = GRID_SIZE - 1; i >= 0; i--)
          {
            if (grid[i][m] > 0)
            {
              // finds first non-zero tile below current tile
              for (int j = i - 1; j >= 0; j--)
              {
                // checks for same value that can be combined
                if (grid[j][m] > 0 && (grid[j][m] == grid[i][m]))
                {
                  // if both conditions are met, merges tiles
                  // and increases score
                  score = score + (grid[j][m] * 2);
                  grid[i][m] = grid[j][m] * 2;
                  grid[j][m] = 0;
                }
                break;
              }
            }
          } 
        }
      }
      
      else
      {
        System.out.println("Not a valid parameter. Available list of inputs:");
        System.out.println("up");
        System.out.println("down");
      }
      
    }
    
 /* 
  * Name:       moveLeft
  * Purpose:    Performs a move in the left direction. Employs
  *             a combination of rotate and moveDown methods to
  *             simplify code.
  * Parameters: n/a
  * Return:     n/a
  */
    private void moveLeft()
    {
      rotate(false); // counterclockwise 90 degree shift
      moveDown(); // merges down
      rotate(true); // rotates back to original position
    }
    
 /* 
  * Name:       moveRight
  * Purpose:    Performs a move in the right direction. Employs
  *             a combination of rotate and moveUp methods to
  *             simplify code.
  * Parameters: n/a
  * Return:     n/a
  */
    private void moveRight()
    {
      rotate(false); // counterclockwise 90 degree shift
      moveUp(); // merges up
      rotate(true); // rotates back to original position 
    }
     
 /* 
  * Name:       moveUp
  * Purpose:    Performs a move in the upwards direction. 
  * Parameters: n/a
  * Return:     n/a
  */
    private void moveUp()
    {
      mergeDir("up");
      
      // creates a new arraylist to store updated grid
      ArrayList<Integer> newGrid = new ArrayList<Integer>();
        
      
      // loops through grid and gets all non-zero tiles,
      // adds them to arrayList new Grid
      for (int m = 0; m < GRID_SIZE; m++) //columns
      {
        // clears each new row before copying elements over
        newGrid.clear();
        for (int i = 0; i < GRID_SIZE; i++) //rows
        {
          if (grid[i][m] > 0)
          {
            Integer value = new Integer(grid[i][m]);
            newGrid.add(value);
          }
        }
        
        // if newGrid is different from grid, updates grid
        if (GRID_SIZE != (GRID_SIZE - newGrid.size()))
        {
          for (int w = 0; w < GRID_SIZE; w++)
          {
            if (w < (GRID_SIZE - (GRID_SIZE - newGrid.size())))
              grid[w][m] = newGrid.get(w);
            
            else
              grid[w][m] = 0;
          }
        }
        }
    }
 
 /* 
  * Name:       moveDown
  * Purpose:    Performs a move in the downwards direction.
  * Parameters: n/a
  * Return:     n/a
  */ 
    private void moveDown()
    {
      mergeDir("down");
      
      // creates a new arraylist to store updated grid
      ArrayList<Integer> newGrid = new ArrayList<Integer>();
      
      // loops through grid and gets all non-zero tiles,
      // adds them to arrayList new Grid
      for (int m = 0; m < GRID_SIZE; m++) //columns
      {
        // clears each new row before copying elements over
        newGrid.clear();
        
        for (int i = 0; i < GRID_SIZE; i++) //rows
        {
          if (grid[i][m] > 0)
          {
            Integer value = new Integer(grid[i][m]);
            newGrid.add(value); 
          } 
        }
        
        // if newGrid is different from grid, updates grid
        if (GRID_SIZE != (GRID_SIZE - newGrid.size()))
        {
          for (int w = 0; w < GRID_SIZE; w++)
          {
            if (w < (GRID_SIZE - newGrid.size()))
              grid[w][m] = 0;
            
            else
              grid[w][m] = newGrid.get(w - (GRID_SIZE - newGrid.size()));
          }
        }
      }
    }

 /* 
  * Name:       isGameOver
  * Purpose:    Checks to see if we have a game over.
  * Parameters: n/a
  * Return:     true if game is over, false if not
  */
    public boolean isGameOver() {
      
      if (canMove(Direction.UP))
        return false;
      
      else if (canMove(Direction.DOWN))
        return false;
      
      else if (canMove(Direction.LEFT))
        return false; 
      
      else if (canMove(Direction.RIGHT))
        return false;
      
      else
        return true;
    }

 /* 
  * Name:       getGrid
  * Purpose:    Return the reference to the 2048 Grid
  * Parameters: n/a
  * Return:     n/a
  */
    public int[][] getGrid() {
        return grid;
    }

 /* 
  * Name:       getScore
  * Purpose:    Return the score.
  * Parameters: n/a
  * Return:     n/a
  */
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
