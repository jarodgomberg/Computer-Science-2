/* 
 Jarod Gomberg - 5106937 - ja545977
 1/25/2022
 COP3503
 Project 2 - tentaizu.java
*/

import java.util.*;

/*

    Played n 7x7 board. Exactly 10 of the 49 squares are each hiding a star
    Other squares in the board provide clues:
        A number in a square indicates how many stars lie next to the square-in other words, how
        many adjacent squares (including diagonally adjacent squares) contain stars. 
        No square with a number in it contains a star, but a star may appear in a square with no adjacent numbers. 

    Each character will be a digit from '0'-'8' or a '.' to indicate an empty square 
        (which may be hiding a star)

*/

public class tentaizu {

    //variables for building initial game board
    final public static int SIZE = 7;
    final public static int MAX = SIZE*SIZE;
    final public static int maxStars = 10;
    private static char[] board;
    
    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        int numCases = stdin.nextInt();

        for(int loop = 1; loop <= numCases; loop++) {

            //declare variables to use per testcase
            String input = "";
            board = new char[MAX];

            //use string to store entire board
            for(int i = 0; i < SIZE; i++) {
                input+= stdin.next();
            }

            //then pull each individual character from that string into 1d array of size MAX
            for(int i = 0; i < MAX; i++) {
                board[i] = input.charAt(i);
            }

            //Find solution and print
            getSoln(0,0);
            System.out.println("Tentaizu Board #" + loop + ":");
            printBoard(board);
            System.out.println();
        }
    }

    /* 
        The main recursive function to solve board.
        Check all conditions and recurse to "solve" one space at a time
    */
    public static boolean getSoln(int space, int curStars) {
        
        /*
            check intial base cases and ones "doomed to fail"
            if not all stars are placed but on last spot
            if stars placed is more than 10
        */

        if(space == MAX && curStars != maxStars)
            return false;
    
        if(curStars > maxStars)
            return false;
       
        //if the number of adjacent stars does not satisfy
        if(checkAdj(space, curStars) == false)
            return false;
    
        //Now all bombs have been placed, valid, return true
        if(curStars == maxStars)
            return true; 

        //check if space is already taken, if false, recurse
        if(!canPlace(space)) {
            return getSoln(space+1, curStars);
        }
        else {

            //That spot is open so placing bomb may work
            board[space] = '*';
            
            //recurse to test if valid, return true
            if(getSoln(space+1, curStars+1)== true)
                return true;

            //if not, reset and recurse
            board[space] = '.';
            return getSoln(space+1, curStars);
        }
    }

    //Function to check if location is in the designated board
    public static boolean outOfBounds(int x, int y) {
		return (x < 0 || y < 0 || x >= SIZE || y >= SIZE);
	}

    //Function to test the availability of the current space
    public static boolean canPlace(int space) {
        //obtain initial coordinates of space by dividing the board

        /*
            divide and mod the current space by size of the board. In this case 7
            using integer division
        */
        int x = space/7;
        int y = space%7;

        /* 
            now go through board for location and check if outOfBounds
            if not, then get that index location and check if it's number value.
            return false if it is a number, as can't place bomb there.
            now just check if spot is empty, if not, bomb is probably there so false
        */

		for (int i = x-1; i <= x+1; i++) {
			for (int j = y-1; j <= y+1; j++) {
				if (!outOfBounds(i, j)) {
					if (board[SIZE*i+j] == '0')
						return false;
                    if(board[space] != '.')
                        return false;
				}
			}
		}

        //spot is available
		return true;
    }

    //Function to find the number of bombs around current to satisfy requirement of numbered spaces
    public static boolean checkAdj(int space, int curStars) {

        //Go through board
        for (int i=0; i<MAX; i++) {
            //check if digit from 1-8
			if (board[i] >= '0' && board[i] <= '8') {

                /*
                    use integer division to get space location
                    x, y fashion
                */
				int x = i/7, y = i%7, adj = 0;

                //go through spaces adjacent current space and check for stars
                //similar fashion to canPlace function
				for (int j = x-1; j <= x+1; j++) {
					for (int k = y-1; k <= y+1; k++) {
						if(!outOfBounds(j,k)) {
                            if(board[SIZE*j+k] == '*') { 
                                //star matched increment adjacent
                                adj++;
                            }
                        }      
					}
				}
               
                //begin checking conditions of adjacent stars

                /*
                    if at end of board and adjacent doesn't match up to current space
                    or current stars is already 10
                */
				if ((space == MAX || curStars == maxStars) && (char)(adj + '0') != board[i]) 
                    return false;

                //if too many adjacent stars
				if ((char)(adj + '0') > board[i]) 
                    return false;
                
                /*
                    Now to check If the board isn't finished and the adjacent stars don't add up
                    this is a last check to make sure the boards are being answered appropriately 
                */
				if (i < space-SIZE-1 && (char)(adj + '0') != board[i]) 
                    return false;
			}
		}
        //so far valid board
		return true;

    }

    //Print the array, 7 elements per line (per output specifications)
    public static void printBoard(char[] spaces) {
        for(int i = 0; i < MAX; i++) {
            System.out.print(spaces[i]);
            if((i+1)%SIZE == 0) System.out.println();
        }
    }
}
