import java.util.Scanner;
import java.util.Random;

public class Game {
    public void play() {
        Tools.clearConsole();
        System.out.println("Welcome to the Minesweeper game!");
        System.out.println("1. \"Step by step\" mode");
        System.out.println("2. Generate random level");
        System.out.println("3. Custom field size");
        System.out.println("4. How to play");
        System.out.println("Enter the number to begin");
        Scanner scan = new Scanner(System.in);
        int number = scan.nextInt();
        while (number < 1  || number > 4) {
            System.out.println("Oooops, wrong number, repeat please :(");
            number = scan.nextInt();
        }
        if (number == 1) {
            Tools.clearConsole();
            System.out.println("In story game you have to pass 3 levels in a row, difficulty will be growing every level. You want to continue?");
            System.out.println("1. Yes\n2. No");
            int answer = scan.nextInt();
            if (answer == 1) {
                startStoryMode(1);
            } else {
                play();
            }
        } else if (number == 4) {
            printInformation();
            System.out.println();
            System.out.println("Press enter to get back.");
            scan.nextLine();
            scan.nextLine();
            play();
        } else if (number == 3) {
            System.out.println("Enter the width of game field");
            int newWidth = scan.nextInt();
            System.out.println("Enter the height of game field");
            int newHeight = scan.nextInt();
            System.out.println("Enter amount of mines on the field. Recommended: " + (int) (newHeight * newWidth * 0.15));
            int newMines = scan.nextInt();
            while (newMines < 0 || newMines >= (newHeight * newWidth * 0.25)) {
                System.out.println("Wrong amount. Repeat pleace.");
                newMines = scan.nextInt();
            };
            createField(newWidth, newHeight, newMines, 0);
        } else  {
            Random rand = new Random(); // generate random size
            int newWidth = rand.nextInt(5, 31);
            int newHeight = rand.nextInt(5, 31);
            int newMines = (int) (newHeight * newWidth * 0.25);
            createField(newWidth, newHeight, newMines, 0);
        }
    }

    public void startStoryMode(int level) {
        Tools.clearConsole();
        if (level == 1) {
            createField(9, 9, 10, 1);
        } else if (level == 2) {
            createField(16, 16, 40, 2);
        } else if (level == 3) {
            createField(9, 9, 10, 3);
        }

    }

    public void printInformation() {
        System.out.println("1. The goal of the game is to find all the safe cells on the field without hitting any mines.\n" +
                "2. Playing field: Consists of square cells, some of which contain mines.\n" +
                "3. Start of the game: The player clicks on any cell to open it.\n" +
                "4. Numbers on the cells: When a cell is open, it can contain a number from 1 to 8 indicating the number of mines in the neighboring cells. (including diagonally)\n" +
                "5. Opening cells: The player can open neighboring cells based on numbers. \n" +
                "6. Closing the cells: The player can close the cells with flags to mark suspicious places with mines.\n" +
                "7. End of the game: The game ends when the player opens a cell with a mine (loss) or opens all safe cells, and marks dangerous ones with a flag (victory).");
    }

    public void createField(int width, int height, int mines, int level) {
        int[][] field = new int[height][width];
        for (int h = 0; h < height; h++) { // fill with 0's
            for (int w = 0; w < width; w++) {
                field[h][w] = 0;
            }
        }
        Random rand = new Random();
        int randomNumberX, randomNumberY;
        int startValueOfMines = mines;
        while (mines > 0) { // placing mines
            randomNumberX = rand.nextInt(width - 1);
            randomNumberY = rand.nextInt(height - 1);
            if (field[randomNumberY][randomNumberX] != -1) {
                field[randomNumberY][randomNumberX] = -1;
                mines--;
            }
        }

        for (int h = 0; h < height; h++) { // checking for neighbors-mines
            for (int w = 0; w < width; w++) {
                if (field[h][w] != -1) {
                    if (w != width - 1) {
                        if (field[h][w + 1] == -1) {
                            field[h][w] += 1;
                        }
                    }
                    if (w != 0) {
                        if (field[h][w - 1] == -1) {
                            field[h][w] += 1;
                        }
                    }
                    if (h != 0) {
                        if (field[h - 1][w] == -1) {
                            field[h][w] += 1;
                        }
                    }
                    if (h != height - 1) {
                        if (field[h + 1][w] == -1) {
                            field[h][w] += 1;
                        }
                    }
                    if (h != height - 1 && w != width - 1) {
                        if (field[h + 1][w + 1] == -1) {
                            field[h][w] += 1;
                        }
                    }
                    if (h != 0 && w != 0) {
                        if (field[h - 1][w - 1] == -1) {
                            field[h][w] += 1;
                        }
                    }
                    if (h != height - 1 && w != 0) {
                        if (field[h + 1][w - 1] == -1) {
                            field[h][w] += 1;
                        }
                    }
                    if (h != 0 && w != width - 1) {
                        if (field[h - 1][w + 1] == -1) {
                            field[h][w] += 1;
                        }
                    }
                }
            }
            System.out.println();
        }

        Field newField = new Field(height, width, field, startValueOfMines, level);
        newField.startGame();
    }
}