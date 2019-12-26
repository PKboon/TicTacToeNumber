/** This is Project 1: Tic Tac Toe Number
 * Has a main function, int functions, boolean functions, and void functions that work together
 * Also has reasonable global variables.
 *
 */
package tictactoenum;

import java.util.Scanner;

/**
 * Tic Tac Toe Game that allows a player to win if the player makes the sum of
 * either a row or a column be 15.
 *
 * @author Pikulkaew Boonpeng
 */
public class TicTacToeNum {

    // Global constant variables
    final static int SIZE_ROW = 3,
            SIZE_COL = 3,
            MARG_FIRST = 1,
            MARG_LAST = 3,
            NUM_MIN = 1,
            NUM_MAX = 9,
            BINGO = 15;

    // Global 2D array board
    private static int[][] board = new int[SIZE_ROW][SIZE_COL];

    public static Scanner input = new Scanner(System.in);

    /**
     * Main method. Prints the rule and loops the game.
     *
     * @param args
     */
    public static void main(String[] args) {

        printRule();

        while (true) {
            // Asks if the user wants to play or quit the game
            System.out.print("\n\n\tTo Start enter S"
                    + "\n\tTo Quit  enter Q: ");
            String command = input.next();
            char gameStatus = command.charAt(0);

            // If s is entered, the game will begin
            switch (gameStatus) {
                case 's':
                case 'S':
                    clearBoard(); // initialize the board to zero
                    gameOn();
                    break;
                case 'q':
                case 'Q':
                    System.exit(0);
                default:
                    System.out.print("Invalid command.");
                    break;
            }
        }
    }

    /**
     * This void function prints the game's rules.
     */
    public static void printRule() {
        int counter = 0;

        System.out.print("     Welcome to Tic Tac Toe Number");

        System.out.println(
                "\n\t     -------------"
                + "\n\t     | T | T | T |"
                + "\n\t     -------------"
                + "\n\t     | N | U | M |"
                + "\n\t     -------------"
                + "\n\t     | B | E | R |"
                + "\n\t     -------------");

        System.out.println("Rules: "
                + "\n" + ++counter + ". Enter an amount of players, 1 or 2."
                + "\n" + ++counter + ". If you play with the Computer, enter 1 player, it will always play Odd number."
                + "\n" + ++counter + ". The players, Odd and Even, take turn filling a cell with their numbers."
                + "\n" + ++counter + ". The ODD number player goes FIRST by selecting a row and a column [1, 2, or 3],"
                + "\n" + "   then an odd number [1, 3, 5, 7, or 9]."
                + "\n" + ++counter + ". Follow by the EVEN number player doing the same thing,"
                + "\n" + "   except the set of even numbers is [2, 4, 6, and 8]."
                + "\n" + ++counter + ". Who reach the sum of \"" + BINGO + "\" in a row,"
                + "\n" + "   may be Vertical, Horizontal, or Diagonal, first will be a WINNER!");
    }

    /**
     * This void function is called when gameStatus is s or S.
     */
    public static void gameOn() {
        String player1 = "Odd number player", player2 = "Even number player";
        int numberOfPlayer;

        System.out.print("\n\t    Start!\n");

        //Print the board that is initialized by clearBoard(); in main function
        printBoard();

        System.out.print("\nHow many players?: ");
        numberOfPlayer = input.nextInt();
        // If the user enters number other than 1 and 2, this while loop will ask the user to input just 1 or 2
        while (numberOfPlayer < 1 || numberOfPlayer > 2) {
            System.out.print("Invalid amount of players. Please enter 1 or 2: ");
            numberOfPlayer = input.nextInt();
        }

        // If 1 is entered, the user will be playing with the computer
        if (numberOfPlayer == 1) {
            do {
                turnOfComp();
                if (isBingo()) {
                    printLineBingo();
                    System.out.print(" The Computer has WON!");
                    break;
                } else if (isFull()) {
                    System.out.print("\nIt's a DRAW! Game Over!");
                    break;
                }

                turnOfEven(player2);
                if (isBingo()) {
                    printLineBingo();
                    System.out.print(" You have WON!");
                    break;
                }
            } while (isBingo() == false);
        } // If 2 is entered, there will be two players
        else if (numberOfPlayer == 2) {
            do {
                turnOfOdd(player1);
                if (isBingo()) {
                    printLineBingo();
                    System.out.print(" " + player1 + " has WON!");
                    break;
                } else if (isFull()) {
                    System.out.print("\nIt's a DRAW! Game Over!");
                    break;
                }

                turnOfEven(player2);
                if (isBingo()) {
                    printLineBingo();
                    System.out.print(" " + player2 + " has WON!");
                    break;
                }
            } while (isBingo() == false);
        }
    }

    /**
     * Prints the updated board.
     */
    public static void printBoard() {
        System.out.println("\t-------------");
        for (int row = 0; row < SIZE_ROW; row++) {
            System.out.print("\t| ");
            for (int col = 0; col < SIZE_COL; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println("\n\t-------------");
        }
    }

    /**
     * This void function is called to clear or initialize the board (for the
     * new game).
     */
    public static void clearBoard() {
        for (int row = 0; row < SIZE_ROW; row++) {
            for (int col = 0; col < SIZE_COL; col++) {
                board[row][col] = 0;
            }
        }
    }

    /**
     * This void function is called for Odd number player's turn
     *
     * @param player Player's name
     */
    public static void turnOfOdd(String player) {
        int row, col, num;

        System.out.print("\n" + player + "'s turn."
                + "\nChoose a row: ");
        row = getCell();
        System.out.print("Choose a column: ");
        col = getCell();
        while (isTakenCell(row, col)) {
            // This while loop checks if the cell has been taken by calling isTanekCell function.
            // It will ask the player to input another row and col when the function returns true.
            System.out.print("Invalid cell. Please choose another one."
                    + "\nChoose a row: ");
            row = getCell();
            System.out.print("Choose a column: ");
            col = getCell();
        }

        System.out.print(player + " enter a number: ");
        num = getOdd(); // getOdd is called to check if num is odd
        while (isUsedNumber(num)) {
            // This while loop checks if the num passed is being entered again by calling isUsedNumber function.
            // It will ask the player to enter another when the function returns true.
            System.out.print("Invalid number. Please enter another one: ");
            num = getOdd();
        }
        board[row - 1][col - 1] = num;
        printBoard(); // Print the update board
    }

    /**
     * This void function is called for Even number player's turn. Works the
     * exactly same way as the turnOfOdd function EXCEPT that it checks for even
     * number.
     *
     * @param player Player's name
     */
    public static void turnOfEven(String player) {
        int row, col, num;

        System.out.print("\n" + player + "'s turn."
                + "\nChoose a row: ");
        row = getCell();
        System.out.print("Choose a column: ");
        col = getCell();
        while (isTakenCell(row, col)) {
            System.out.print("Invalid cell. Please choose another one."
                    + "\nChoose a row: ");
            row = getCell();
            System.out.print("Choose a column: ");
            col = getCell();
        }

        System.out.print(player + " enter a number: ");
        num = getEven(); // getEven is called to check if num is even
        while (isUsedNumber(num)) {
            System.out.print("Invalid number. Please enter another one: ");
            num = getEven();
        }

        board[row - 1][col - 1] = num;
        printBoard();
    }

    /**
     * This void function is called for Computer's turn and always play Odd
     * numbers.
     */
    public static void turnOfComp() {
        int row, col, num;

        row = getRandCell();
        col = getRandCell();
        while (isTakenCell(row, col)) {
            row = getRandCell();
            col = getRandCell();
        }

        num = getRandOdd();
        while (isUsedNumber(num)) {
            num = getRandOdd();
        }
        board[row - 1][col - 1] = num;
        System.out.print("\nComputer's turn.\n"); // Print to tell the user that the board below is from computer's turn
        printBoard();
    }

    /**
     * This boolean function checks 8 possible bingo lines. Every cell in the
     * bingo line has to be greater than zero and the sum of them must equal to
     * BINGO(15)
     *
     * @return true if BINGO
     */
    public static boolean isBingo() {
        if (((board[0][0] + board[0][1] + board[0][2] == BINGO)
                && board[0][0] > 0 && board[0][1] > 0 && board[0][2] > 0)
                || ((board[1][0] + board[1][1] + board[1][2] == BINGO)
                && board[1][0] > 0 && board[1][1] > 0 && board[1][2] > 0)
                || ((board[2][0] + board[2][1] + board[2][2] == BINGO)
                && board[2][0] > 0 && board[2][1] > 0 && board[2][2] > 0)
                || ((board[0][0] + board[1][0] + board[2][0] == BINGO)
                && board[0][0] > 0 && board[1][0] > 0 && board[2][0] > 0)
                || ((board[0][1] + board[1][1] + board[2][1] == BINGO)
                && board[0][1] > 0 && board[1][1] > 0 && board[2][1] > 0)
                || ((board[0][2] + board[1][2] + board[2][2] == BINGO)
                && board[0][2] > 0 && board[1][2] > 0 && board[2][2] > 0)
                || ((board[0][0] + board[1][1] + board[2][2] == BINGO)
                && board[0][0] > 0 && board[1][1] > 0 && board[2][2] > 0)
                || ((board[0][2] + board[1][1] + board[2][0] == BINGO)
                && board[0][2] > 0 && board[1][1] > 0 && board[2][0] > 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Announces which line is the BINGO one. Also, prints the calculation of
     * that line by checking if every cell in the bingo line has to be greater
     * than zero and the sum of them must equal to BINGO(15).
     */
    public static void printLineBingo() {
        if ((board[0][0] + board[0][1] + board[0][2] == BINGO)
                && board[0][0] > 0 && board[0][1] > 0 && board[0][2] > 0) {
            System.out.print("\nThe first row: "
                    + board[0][0] + " + " + board[0][1] + " + " + board[0][2] + " = " + BINGO + ".");
        } else if ((board[1][0] + board[1][1] + board[1][2] == BINGO)
                && board[1][0] > 0 && board[1][1] > 0 && board[1][2] > 0) {
            System.out.print("\nThe middle row: "
                    + board[1][0] + " + " + board[1][1] + " + " + board[1][2] + " = " + BINGO + ".");
        } else if ((board[2][0] + board[2][1] + board[2][2] == BINGO)
                && board[2][0] > 0 && board[2][1] > 0 && board[2][2] > 0) {
            System.out.print("\nThe last row: "
                    + board[2][0] + " + " + board[2][1] + " + " + board[2][2] + " = " + BINGO + ".");
        } else if ((board[0][0] + board[1][0] + board[2][0] == BINGO)
                && board[0][0] > 0 && board[1][0] > 0 && board[2][0] > 0) {
            System.out.print("\nThe left column: "
                    + board[0][0] + " + " + board[1][0] + " + " + board[2][0] + " = " + BINGO + ".");
        } else if ((board[0][1] + board[1][1] + board[2][1] == BINGO)
                && board[0][1] > 0 && board[1][1] > 0 && board[2][1] > 0) {
            System.out.print("\nThe middle column: "
                    + board[0][1] + " + " + board[1][1] + " + " + board[2][1] + " = " + BINGO + ".");
        } else if ((board[0][2] + board[1][2] + board[2][2] == BINGO)
                && board[0][2] > 0 && board[1][2] > 0 && board[2][2] > 0) {
            System.out.print("\nThe right column: "
                    + board[0][2] + " + " + board[1][2] + " + " + board[2][2] + " = " + BINGO + ".");
        } else if ((board[0][0] + board[1][1] + board[2][2] == BINGO)
                && board[0][0] > 0 && board[1][1] > 0 && board[2][2] > 0) {
            System.out.print("\nThe opposite of diagonal: "
                    + board[0][0] + " + " + board[1][1] + " + " + board[2][2] + " = " + BINGO + ".");
        } else if ((board[0][2] + board[1][1] + board[2][0] == BINGO)
                && board[0][2] > 0 && board[1][1] > 0 && board[2][0] > 0) {
            System.out.print("\nThe diagonal: "
                    + board[0][2] + " + " + board[1][1] + " + " + board[2][0] + " = " + BINGO + ".");
        }
    }

    /**
     * Checks if all the cells are occupied.
     *
     * @return true if all the cell are used
     */
    public static boolean isFull() {
        return (board[0][0] > 0 && board[0][1] > 0 && board[0][2] > 0
                && board[1][0] > 0 && board[1][1] > 0 && board[1][2] > 0
                && board[2][0] > 0 && board[2][1] > 0 && board[2][2] > 0
                && board[0][0] > 0 && board[1][0] > 0 && board[2][0] > 0
                && board[0][1] > 0 && board[1][1] > 0 && board[2][1] > 0
                && board[0][2] > 0 && board[1][2] > 0 && board[2][2] > 0
                && board[0][0] > 0 && board[1][1] > 0 && board[2][2] > 0
                && board[0][2] > 0 && board[1][1] > 0 && board[2][0] > 0);
    }

    /**
     * Checks if the cell is taken.
     *
     * @param row The row that is called
     * @param col The column that is called
     * @return true if the cell is taken
     */
    public static boolean isTakenCell(int row, int col) {
        return (board[row - 1][col - 1] > 0);
    }

    /**
     * Checks if the number is used.
     *
     * @param num The checked number
     * @return true if the number is used
     */
    public static boolean isUsedNumber(int num) {
        for (int row = 0; row < SIZE_ROW; row++) {
            for (int col = 0; col < SIZE_COL; col++) {
                if (board[row][col] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * For Computer's turn. Stores input in random cell. Must called twice for
     * row and column.
     *
     * @return a workable cell
     */
    public static int getRandCell() {
        int num = (int) (Math.random() * 10); // Generate random numbers
        int randCell = 1;
        if (num >= MARG_FIRST && num <= MARG_LAST) { // Set boundaries
            randCell = num;
        }
        return randCell;
    }

    /**
     * For Computer's turn. Gets random Odd number.
     *
     * @return a workable odd number
     */
    public static int getRandOdd() {
        int randOdd = (int) (Math.random() * 10); // Generate random numbers
        if ((randOdd % 2) == 0) {
            return randOdd + 1; // Turn even to odd
        } else {
            return randOdd;
        }
    }

    /**
     * Gets a cell from players. Must called twice for row and column.
     *
     * @return a workable cell
     */
    public static int getCell() {
        int cell;

        cell = input.nextInt();
        // While enter other numbers out of the range of [1,3], the players will be asked to enter 1, 2, or 3
        while (cell < MARG_FIRST || cell > MARG_LAST) { // Set boundaries
            System.out.print("Please enter 1, 2, or 3: ");
            cell = input.nextInt();
        }
        return cell;
    }

    /**
     * Checks if the number entered is even.
     *
     * @return even number
     */
    public static int getEven() {
        int even = getValidNum();

        while ((even % 2) == 1) {
            System.out.print("Please enter an EVEN number: ");
            even = getValidNum();
        }
        return even;
    }

    /**
     * Checks if the number entered is odd.
     *
     * @return odd number
     */
    public static int getOdd() {
        int odd = getValidNum();

        while ((odd % 2) == 0) {
            System.out.print("Please enter an ODD number: ");
            odd = getValidNum();
        }
        return odd;
    }

    /**
     * Checks if the number entered is in [1,9].
     *
     * @return the checked number
     */
    public static int getValidNum() {
        int num;

        num = input.nextInt();
        while (num < NUM_MIN || num > NUM_MAX) {
            System.out.print("Please enter a valid number from " + NUM_MIN + " to " + NUM_MAX + ": ");
            num = input.nextInt();
        }
        return num;
    }
}
