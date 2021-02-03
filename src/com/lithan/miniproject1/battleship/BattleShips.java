package com.lithan.miniproject1.battleship;

import java.util.Scanner;

public class BattleShips {

    public static int rowsNum = 10;
    public static int colsNum = 10;
    public static int playerShips;
    public static int computerShips;
    public static char[][] grid = new char[rowsNum][colsNum];

    public static void main(String[] args) {
        System.out.println("+-+-+-+-+-+- Welcome to the Battle Ships Game! +-+-+-+-+-+-");
        System.out.println("Right now, the sea is empty.\n");

        //Step 1 - Create the ocean map
        theOceanMap();

        //Step 2 - Deploy the player's Ship
        deployPlayerShips();

        //Step 3 - Deploy the computer's Ship
        deployComputerShips();

        //Step 4 - Battle
        do {
            Battle();
        } while (playerShips != 0 && computerShips != 0);

        //Step 5 - Game Over!
        gameOver();
    }

    //Create the Ocean map
    public static void theOceanMap(){
        //First section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < colsNum; i++ ) {
            System.out.print(i);
        }
        System.out.println();

        //Middle section of the Ocean Map
        for(int i = 0; i < grid.length; i++){
            System.out.print(i + "|");

            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = 0;
                System.out.print(" ");
            }
            System.out.println("|" + i);
        }

        //Last section of Ocean Map
        System.out.print("  ");
        for (int i = 0; i < colsNum; i++){
            System.out.print(i);
        }
        System.out.println();
    }

    //Deploy the player Ship
    public static void deployPlayerShips(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nDeploy your Ship!");
        //Delpoying 5 ships for player
        playerShips = 5;
        for (int i = 1; i <= playerShips;) {
            System.out.print("Enter X coordinate for your " + i + " ship: ");
            int x = input.nextInt();
            System.out.print("Enter Y coordinate for your " + i + " ship: ");
            int y = input.nextInt();

            if ((x >= 0 && x < rowsNum) && (y >= 0 && y < colsNum) && (grid[x][y] == 0)) {
                grid[x][y] = 1;
                i++;
            } else if ((x >= 0 && x < rowsNum) && (y >= 0 && y < colsNum) && (grid[x][y] == 1)) {
                System.out.println("You cannot place two or more ships on the same coordinate!");
            } else if ((x < 0 || x >= rowsNum) || (y < 0 || y >= colsNum)) {
                System.out.println("You cannot place the ships outside the " + rowsNum + " by " + colsNum + " grid!");
            }
        }
        printDeployedOceanMap();
    }

    //Deploy the player Ship
    public static void deployComputerShips(){
        System.out.println("\nComputer is deploying ships");
        //Deploying 5 Computer's Ships
        computerShips = 5;
        for (int i = 1; i <= computerShips;) {
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);

            if((x > 0 && x < rowsNum) && (y > 0 && y < colsNum) && (grid[x][y] == 0)) {
                grid[x][y] = 2;
                System.out.println(i + ". ship DEPLOYED");
                i++;
            } else if ((x >= 0 && x < rowsNum) && (y >= 0 && y < colsNum) && (grid[x][y] == 2)) {
                System.out.println("You cannot place two or more ships on the same coordinate!");
            } else if ((x < 0 || x >= rowsNum) || (y < 0 || y >= colsNum)) {
                System.out.println("You cannot place the ships outside the " + rowsNum + " by " + colsNum + " grid!");
            }
        }
        printDeployedOceanMap();
    }

    public static void Battle() {
        playerPlay();
        computerPlay();

        printDeployedOceanMap();

        System.out.println();
        System.out.println("Your ships: " + playerShips + " | Computer ships: " + computerShips);
        System.out.println();
    }

    private static void playerPlay() {
        System.out.println("\n---------- YOUR TURN! ----------");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter X Coordinate: ");
            x = input.nextInt();
            System.out.print("Enter Y Coordinate: ");
            y = input.nextInt();

            if ((x >= 0 && x < rowsNum) && (y >= 0 && y < colsNum)) { // Valid guess
                switch (grid[x][y]) {
                    case 1:
                        System.out.println("Oh No, You sunk your own ship :(");
                        --playerShips;
                        grid[x][y] = 5;
                        break;
                    case 2:
                        System.out.println("Boom! You sunk the ship!");
                        --computerShips;
                        grid[x][y] = 3;
                        break;
                    case 3:
                    case 5:
                        System.out.println("The ship is already sunk!");
                        break;
                    default:
                        System.out.println("Sorry, you missed");
                        grid[x][y] = 4;
                        break;
                }
            } else if ((x < 0 || x >= rowsNum ) || (y < 0 || y >= colsNum)) {// Invalid Guess
                System.out.println("You cannot place the bombs outside the " + rowsNum + " by " + colsNum + " grid!");
            }
        }while((x < 0 || x >= rowsNum ) || (y < 0 || y >= colsNum)); //keep rolling the game until valid guess
    }

    private static void computerPlay() {
        System.out.println("\n---------- COMPUTER'S TURN! ----------");

        //Guess the coordinates
        int x = -1, y= -1;
        do {
            x = (int) (Math.random() * 10);
            y = (int) (Math.random() * 10);

            if ((x > 0  && x < rowsNum ) && (y > 0 && y < colsNum )) { // Valid Guess
                switch (grid[x][y]) {
                    case 1:
                        System.out.println("Oh no! The Computer sunk one of your ships! ");
                        --playerShips;
                        grid[x][y] = 5;
                        break;
                    case 2:
                        System.out.println("Yeah! The Computer sunk one of its own ships!");
                        --computerShips;
                        grid[x][y] = 3;
                        break;
                    case 3:
                    case 5:
                        System.out.println("The ship is already sunk!");
                        break;
                    default:
                        System.out.println("The Computer missed!");
                        break;
                }
            } else if ((x < 0 || x >= rowsNum ) || (y < 0 || y >= colsNum)) {// Invalid Guess
                System.out.println("You cannot place the bombs outside the " + rowsNum + " by " + colsNum + " grid!");
            }
        } while ((x < 0 || x >= rowsNum ) || ( y < 0 || y >= colsNum ));
    }

    public static void gameOver() {
        System.out.println("Your Ships: " + playerShips + " | Computer Ships: " + computerShips);
        if (playerShips > 0 && computerShips <= 0) {
            System.out.println("Hooray! You win the battle :)");
        } else {
            System.out.println("Sorry... You lose :(");
        }
        System.out.println();
    }

    private static void printDeployedOceanMap() {
        System.out.println();
        //First section of Ocean Map
        System.out.print("  ");
        for (int i = 0; i < rowsNum; i++) {
            System.out.print(i);
        }
        System.out.println();

        //Middle section of Ocean Map
        for (int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++) {
                switch (grid[x][y]) {
                    case 1:
                        System.out.print("@");
                        break;
//                    case 2:
//                        System.out.print("C");
//                        break;
                    case 3:
                        System.out.print("!");
                        break;
                    case 4:
                        System.out.print("-");
                        break;
                    case 5:
                        System.out.print("x");
                        break;
                    default:
                        System.out.print(" ");
                        break;
                }
            }

            System.out.println("|" + x);
        }

        //Last section of Ocean Map
        System.out.print("  ");
        for (int i = 0; i < colsNum; i++) {
            System.out.print(i);
        }
        System.out.println();
    }
}
