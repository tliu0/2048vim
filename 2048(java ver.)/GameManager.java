//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  Ujjwal Gulecha                                          //
// Date:    10/12/16                                                //
//                                                                  // 
// Name: Tiffany Liu                                                //
// Login: cs8bfch                                                   //
// Sources of Help:                                                 //              
//         Book: Introduction to Java Programming by Y. Daniel Liang//
//                                                                  //
// Program Description:                                             //
//         Manages the game 2048; objective is to slide numbered    //
//         tiles on a grid to combine them and create a tile with   //
//         the number 2048. This version was created so the game    //
//         could be played in the terminal.                         //
//------------------------------------------------------------------//

import java.util.*;
import java.io.*;


/*
 * Name:    GameManager 
 * Purpose: The game manager for 2048. Contains several constructors
 *          and methods, such as loading and saving files as well as 
 *          the main play loop, based on user input. 
 */

public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputFileName; // File to save the board to when exiting

 /* 
  * Name:       GameManager
  * Purpose:    Generates a new game.   
  * Parameters: String boardSize - size of new board
  *             String outputBoard - file to be saved to
  *             Random random - random object in order to add tiles
  * Return:     n/a (constructor)
  */
    public GameManager(int boardSize, String outputBoard, Random random) {
        System.out.println("Generating a New Board");
        
        board = new Board(boardSize, random); //creates new board
        outputFileName = outputBoard; 

    }

 /* 
  * Name:       GameManager
  * Purpose:    Loads a saved game.  
  * Parameters: String inputBoard - file to be opened, inputted by user
  *             String outputBoard - file to be saved to
  *             Random random - random object in order to add tiles
  * Return:     n/a (constructor)
  */
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
        System.out.println("Loading Board from " + inputBoard);
        
        board = new Board (inputBoard, random);
        outputFileName = outputBoard; 
    }

    
 /* 
  * Name:       play 
  * Purpose:    Takes in input from the user to specify move to 
  *             execute. Valid moves are: 
  *                   k - Move Up
  *                   j - Move Down
  *                   h - Move Left
  *                   l - Move Right
  *                   q - Quit and Save Board
  *             If an invalid command is received then controls are
  *             printed to remind the user of the valid moves. 
  * 
  *             Once the player decides to quit or the game is over, 
  *             the game board is saved to a file (outputFileName)
  *             which was set in the constructor as a string and then
  *             returned. 
  * 
  *             In the case of game over, the string "Game Over!" is 
  *             printed to the terminal. 
  *             
  * Parameters: n/a
  * Return:     n/a (void)
  */
    public void play() throws IOException {
      
      Scanner input = new Scanner(System.in);
      
      String direct = "";
      String printBoard = board.toString();
      
      Direction dir = Direction.UP; 
      boolean command = false; 
        
      printControls(); 
      
      System.out.println(printBoard); 
      
      boolean endgame = board.isGameOver();
      
      // if the game is already over, prints Game Over!
      // and saves board to OutputFileName
      if (endgame)
      {
        System.out.println("Game Over!");
        board.saveBoard(outputFileName);
      }
      
      else 
      {
        System.out.println("> (type command)");
        
        while (input.hasNext())
        {
          direct = input.next();
          
          if (direct.equals("k"))
          {
            dir = Direction.UP; 
            command = true; 
          }
          
          else if (direct.equals("j"))
          {
            dir = Direction.DOWN;
            command = true;
          }
          
          else if (direct.equals("h"))
          {
            dir = Direction.LEFT;
            command = true; 
          }
          
          else if (direct.equals("l"))
          {
            dir = Direction.RIGHT;
            command = true;
          }
          
          // if the user decides to quit, saves board and exits
          else if (direct.equals("q"))
          {
            command = false;
            board.saveBoard(outputFileName);
            return;
          }
          
          // if the user inserts an invalid command
          // controls are printed as a reminder
          else 
          {
            command = false;
            printControls();
          }
          
          // assesses validity of command/move 
          // if command is valid, adds a random new tile
          if (command == true)
          {
            board.move(dir);
            board.addRandomTile();
          }
          
          // print the board
          System.out.println(board.toString());
          
          // if game is not over
          if (!endgame)
            System.out.println("> (type command)");
          
          // if game is over
          else
          {
            System.out.println("Game Over!");
            return;
          }
          
        }
      }
    }

    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
