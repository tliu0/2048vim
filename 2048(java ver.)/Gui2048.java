//------------------------------------------------------------------//
// Gui2048.java                                                     //
//                                                                  //
// GUI Driver for 2048                                             //
//                                                                  //
// Date:    11/09/16                                                //
//------------------------------------------------------------------//
// Name: Tiffany Liu                                                //
// Sources of Help:                                                 //              
//         Book: Introduction to Java Programming by Y. Daniel Liang//
//                                                                  //
// Program Description:                                             //
//         Graphical user interface design for the game 2048.       //
//------------------------------------------------------------------//


import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

/*
 * Name:    Gui2048
 * Purpose: GUI driver for the 2048 game. 
 */

public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private static final int TILE_WIDTH = 106;

    private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
    private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
                                                 //(128, 256, 512)
    private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
                                                  //(1024, 2048, Higher)

    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(221, 220, 226, 0.35);
    private static final Color COLOR_2 = Color.rgb(245, 252, 252);
    private static final Color COLOR_4 = Color.rgb(224, 240, 247);
    private static final Color COLOR_8 = Color.rgb(198, 208, 233);
    private static final Color COLOR_16 = Color.rgb(217, 184, 225);
    private static final Color COLOR_32 = Color.rgb(204, 151, 179);
    private static final Color COLOR_64 = Color.rgb(179, 132, 157);
    private static final Color COLOR_128 = Color.rgb(238, 184, 192);
    private static final Color COLOR_256 = Color.rgb(229, 177, 185);
    private static final Color COLOR_512 = Color.rgb(238, 184, 192);
    private static final Color COLOR_1024 = Color.rgb(238, 184, 192);
    private static final Color COLOR_2048 = Color.rgb(229, 177, 185);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
                        // For tiles >= 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
                       // For tiles < 8

    private GridPane pane;

    /** Add your own Instance Variables here */
    private int gridSize;
    private Stage stage;
    private static final Color COLOR_HEADER = Color.rgb(255, 255, 255);
    
    private Rectangle[][] square;
    private Text[][] tileText;
    
    private int score; 
    private Text displayScore;
    



 /* 
  * Name:       start
  * Purpose:    Sets up the stage/scene and its components (i.e. 
  *             the general interface design of the game), assigns
  *             tiles 
  * Parameters: Stage primaryStage - the stage 
  * Return:     n/a
  */
    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(184, 177, 182)");
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);

        /** Add your Code for the GUI Here */
        this.gridSize = board.getGrid().length; //gets size of grid
        this.square = new Rectangle[gridSize][gridSize];
        this.tileText = new Text[gridSize][gridSize]; 

        // creates game title text 
        Text title = new Text();
        title.setText("2048");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,
                                  TEXT_SIZE_MID + 2));
        title.setFill(COLOR_HEADER);
        
        // creates text for score value 
        Text score = new Text();
        score.setText("Score:" + this.score);
        score.setFont(Font.font("Verdana", FontWeight.BOLD, TEXT_SIZE_HIGH));
        score.setFill(COLOR_HEADER);
        this.displayScore = score; 
        
        // adds text to pane in proper places 
        pane.add(title, 0, 1, 2, 1);
        GridPane.setHalignment(title, HPos.CENTER); //centers text
        pane.add(score, 2, 1, 2, 1);
        
        
        // adds tiles for each value in grid
        for (int i = 0; i < gridSize; i++)
        {
            for (int j = 0; j < gridSize; j++)
            {
              // creates tile 
              Rectangle tile = new Rectangle();
              tile.setWidth(TILE_WIDTH); // sets dimensions of tile
              tile.setHeight(TILE_WIDTH);
              tile.setFill(Color.BLACK); // sets default color (to
                                         // be changed later)
              
              // creates text
              Text value = new Text();
              value.setFont(Font.font("Verdana", FontWeight.BOLD,
                                      TEXT_SIZE_LOW));
        
              // creates tile/text for every value found on grid
              tileText[i][j] = value;
              square[i][j] = tile;

              // adds tiles onto pane 
              pane.add(tile, j, i + 2);
              pane.add(value, j, i + 2);
              
              // align text to center
              GridPane.setHalignment(value, HPos.CENTER); 
            }
        }
        
        // saves primary stage value
        this.stage = primaryStage;

        // updates the grid pane
        update();
       
        // creates scene 
        Scene scene = new Scene(pane); 
        primaryStage.setTitle("Gui2048");
        scene.setOnKeyPressed(new myKeyHandler());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

 /* 
  * Name:       update
  * Purpose:    Updates the gridpane, assigning color and text to tiles
  *             based on their values. Also dispalys the current score.
  * Parameters: n/a
  * Return:     n/a
  */
    private void update()
    {
        // updates the score
        this.score = board.getScore();
        this.displayScore.setText("Score: " + score);
        
        int[][] grid = this.board.getGrid();
        
        // updates tiles
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid.length; j++)
            {
                if (grid[i][j] == 0)
                {
                    tileText[i][j].setText("");
                    square[i][j].setFill(COLOR_EMPTY);
                }
                
                else if (grid[i][j] < 8)
                {
                  tileText[i][j].setFill(COLOR_VALUE_DARK);
                  tileText[i][j].setText(String.valueOf(grid[i][j]));
                  tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD, 
                                                   TEXT_SIZE_LOW));
                  
                  if (grid[i][j] == 2)
                    square[i][j].setFill(COLOR_2);
                  
                  else 
                    square[i][j].setFill(COLOR_4);
                }
                
                // for tile values greater than or equal to 8
                else if (grid[i][j] > 8 || grid[i][j] == 8)
                {
                  tileText[i][j].setFill(COLOR_VALUE_LIGHT);
                  tileText[i][j].setText(String.valueOf(grid[i][j]));
                  
                  // if value of tile is 8
                  if (grid[i][j] == 8)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                     TEXT_SIZE_LOW));
                    square[i][j].setFill(COLOR_8);
                  }
                  
                  // if value of tile is 16
                  else if (grid[i][j] == 16)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                     TEXT_SIZE_LOW));
                    square[i][j].setFill(COLOR_16);
                  }
                  
                  // if value of tile is 32
                  else if (grid[i][j] == 32)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD, 
                                                     TEXT_SIZE_LOW));
                    square[i][j].setFill(COLOR_32);
                  }
                  
                  // if value of tile is 64
                  else if (grid[i][j] == 64)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                     TEXT_SIZE_LOW));
                    square[i][j].setFill(COLOR_64);
                  }
                  
                  // if value of tile is 128
                  else if (grid[i][j] == 128)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                    TEXT_SIZE_MID));
                    square[i][j].setFill(COLOR_128);
                  }
                  
                  // if value of tile is 256
                  else if (grid[i][j] == 256)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                     TEXT_SIZE_MID));
                    square[i][j].setFill(COLOR_256);
                  }
                  
                  // if value of tile is 512
                  else if (grid[i][j] == 512)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                     TEXT_SIZE_MID));
                    square[i][j].setFill(COLOR_512);
                  }
                  
                  // if value of tile is 1024
                  else if (grid[i][j] == 1024)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                    TEXT_SIZE_HIGH));
                    square[i][j].setFill(COLOR_1024);
                  }
                  
                  // if value of tile is 2048
                  else if (grid[i][j] == 2048)
                  {
                    tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                     TEXT_SIZE_HIGH));
                    square[i][j].setFill(COLOR_2048);
                  }
                }
                
                // for any other number
                else 
                {
                  tileText[i][j].setFill(COLOR_VALUE_LIGHT);
                  square[i][j].setFill(COLOR_OTHER);
                  tileText[i][j].setText(String.valueOf(grid[i][j]));
                  tileText[i][j].setFont(Font.font("Verdana", FontWeight.BOLD,
                                                   TEXT_SIZE_HIGH));
                }
                
                
            }
        }
     }
    
 /* 
  * Name:       updateInput
  * Purpose:    Updates the board after a key press event. Prints "Moving" 
  *             + the direction of the move based on the input, or updates
  *             the board with the gameover screen if it detects a gameover.
  * Parameters: Direction dir - direction value inputted by user
  * Return:     n/a 
  */
    private void updateInput(Direction dir){
      this.board.move(dir);
      this.board.addRandomTile(); 
      update();
      
      double width = stage.getWidth();
      double height = stage.getHeight();
      
      int doubleG = (gridSize * 2); 
      int center = ((gridSize/2) - 1);
      
      // prints out moves when valid
      if (dir == Direction.UP)
        System.out.println("Moving " + Direction.UP.toString());
      
      else if (dir == Direction.DOWN)
        System.out.println("Moving " + Direction.DOWN.toString());
      
      else if (dir == Direction.LEFT)
        System.out.println("Moving " + Direction.LEFT.toString());
      
      else
        System.out.println("Moving " + Direction.RIGHT.toString());
      
      // checks for game over
      if (board.isGameOver())
      {
        Rectangle gameOver = new Rectangle(width, height);
        gameOver.setFill(COLOR_GAME_OVER);
        
        Text over = new Text("Game Over");
        over.setFont(Font.font("Verdana", 
                               FontWeight.BOLD, FontPosture.ITALIC, 
                               TEXT_SIZE_LOW));
        over.setFill(COLOR_VALUE_DARK);
        
        pane.add(gameOver, 0, 0, gridSize, doubleG);
        GridPane.setHalignment(gameOver, HPos.CENTER);
        GridPane.setValignment(gameOver, VPos.CENTER);
        pane.add(over, 0, center, gridSize, gridSize);  
        GridPane.setHalignment(over, HPos.CENTER);
        GridPane.setValignment(gameOver, VPos.CENTER);
      }
    }
    
 /* 
  * Name:       myKeyHandler
  * Purpose:    Reads and implements key press events inputted by the user
  *             as actions into the board. Utilizes the method "updateInput"
  *             to update/implement actions once the events have been read.
  * Parameters: n/a
  * Return:     n/a 
  */
    private class myKeyHandler implements EventHandler<KeyEvent>
    {
      @Override
      public void handle(KeyEvent e)
      {
        if (e.getCode() == KeyCode.UP && board.canMove(Direction.UP))
          updateInput(Direction.UP);
        
        else if (e.getCode() == KeyCode.DOWN && 
                 board.canMove(Direction.DOWN))
          updateInput(Direction.DOWN);
        
        else if (e.getCode() == KeyCode.RIGHT && 
                 board.canMove(Direction.RIGHT))
          updateInput(Direction.RIGHT);
        
        else if (e.getCode() == KeyCode.LEFT &&
                 board.canMove(Direction.LEFT))
          updateInput(Direction.LEFT);
        
        else if (e.getCode() == KeyCode.S)
        {
          try 
          {
            board.saveBoard(outputBoard);
          } 
          catch (IOException i) 
          { 
            System.out.println("saveBoard threw an Exception");
          }
        }
      }
    }

    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
}
