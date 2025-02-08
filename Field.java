import java.util.Scanner;

public class Field {
    private int currentX;
    private int currentY;
    private int height;
    private int width;
    private int[][] field;
    private int level;
    private int freeCells;
    private int mines;
    private boolean firstOpened;

    Field(int height, int width, int[][] field, int mines, int level) {
        currentY = 0;
        currentX = 0;
        firstOpened = false;

        this.height = height;
        this.width = width;
        this.field = field;
        this.level = level;
        this.mines = mines;
        this.freeCells = width * height - mines;
    }

    public void printField() {
        Tools.clearConsole(); // opened field for developer
//        for (int h = 0; h < height; h++) {
//            for (int w = 0; w < width; w++) {
//                System.out.print(field[h][w] + "\t");
//            }
//            System.out.println();
//        }
        for (int h = 0; h < getHeight(); h++) {
            for (int w = 0; w < getWidth(); w++) {
                if (h == getCurrentY() && w == getCurrentX()) {
                    System.out.print("X   ");
                }
                else {
                    if (field[h][w] > 10 && field[h][w] < 19) {
                        System.out.print(getField()[h][w] - 10 + "   ");
                    } else if (field[h][w] == 10) {
                        System.out.print("-   ");
                    } else if (field[h][w] >= 19) {
                        System.out.print("F   ");
                    }
                    else {
                        System.out.print("*   ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("Write \"l\" to move left, r - right, u - up and d - down");
        System.out.println("Write \"f\" to put or remove flag on current cell");
        System.out.println("Write \"o\" to open current cell");
        System.out.println("Write \"e\" to end the game");
        System.out.println(freeCells + " " + mines);
    }

    public void startGame() {
        setHeight(height);
        setWidth(width);
        Tools.clearConsole();
        printField();

        Scanner scan = new Scanner(System.in);
        String move = askForMove(scan);
        move(move);
        if (move.equals("o")) {
            openCell();
        } else if (move.equals("f")) {
            putFlag();
        }
        Tools.clearConsole();
        printField();
        while (!move.equals("e")) {
            Tools.clearConsole();
            printField();
            move = askForMove(scan);
            if (move.equals("o")) {
                openCell();
            } else if (move.equals("f")) {
                putFlag();
            } else {
                move(move);
            }
        }
    }

    public String askForMove(Scanner scan) {
        String move = scan.nextLine();
        while (!"lrudfeo".contains(move)) {
            Tools.clearConsole();
            printField();
            System.out.println();
            System.out.println("Error, wrong command! Try again");
            move = scan.nextLine();
        }
        return move;
    }
    public void move(String nextMove) {
        int x = getCurrentX();
        int y = getCurrentY();
        switch (nextMove) {
            case "l" -> {
                if (x != 0) {
                    setCurrentX(--x);
                }
            }
            case "r" -> {
                if (x != getWidth() - 1) {
                    setCurrentX(++x);
                }
            }
            case "d" -> {
                if (y != getHeight() - 1) {
                    setCurrentY(++y);
                }
            }
            case "u" -> {
                if (y != 0) {
                    setCurrentY(--y);
                }
            }
        }
    }

    public void openCell() {
        if (!firstOpened) {
             firstOpened = true;
             changeMineLocation();
             field[currentY][currentX] = 0;
        }
            int currentCell = getField()[getCurrentY()][getCurrentX()];
            if (currentCell == -1) { // cell with bomb
                lost();
            } else if (currentCell >= 0 && currentCell <= 8) { // free cell
                field[getCurrentY()][getCurrentX()] = currentCell + 10;
                setFreeCells(getFreeCells() - 1);
                if (getFreeCells() == 0 && getMines() == 0) {
                    win();
                }
            } else if (currentCell >= 19) {
                field[getCurrentY()][getCurrentX()] = currentCell - 20;
                setFreeCells(getFreeCells() - 1);
                if (field[getCurrentY()][getCurrentX()] == -1) {
                    lost();
                }
            }
        }

    public void changeMineLocation() {
        for (int h = height - 1; h >= 0 && field[currentY][currentX] == -1; h--) { // fill with 0's
            for (int w = width - 1; w >= 0 && field[currentY][currentX] == -1; w--) {
                if ((h != currentY || w != currentX) && field[h][w] != -1) {
                    field[h][w] = -1;

                }
            }
        }
    }

    public void putFlag() {
        int currentCell = getField()[getCurrentY()][getCurrentX()];
        if (currentCell >= 19) {
            getField()[getCurrentY()][getCurrentX()] = currentCell - 20;
        } else if (currentCell == - 1) {
            getField()[getCurrentY()][getCurrentX()] = currentCell + 20;
            setMines(getMines() - 1);
            if (getMines() == 0 && getFreeCells() == 0) {
                win();
            }
        } else if (currentCell <= 8 && currentCell >= 0) {
            getField()[getCurrentY()][getCurrentX()] = currentCell + 20;
            setFreeCells(getFreeCells() - 1);
            if (getMines() == 0 && getFreeCells() == 0) {
                win();
            }
        }
    }

    public void win() {
        Scanner lastWords = new Scanner(System.in);
        if (level == 1) {
            System.out.println("Congratulations!!! You are a winner! Uou have passed lvl 1, press enter to continue!");
            lastWords.nextLine();
            Game game = new Game();
            game.startStoryMode(2);
        } else if (level == 2) {
            System.out.println("Congratulations!!! You are a winner! You have passed lvl 2, press enter to continue!");
            lastWords.nextLine();
            Game game = new Game();
            game.startStoryMode(3);
        } else  if (level == 3) {
            System.out.println("Congratulations!!! You are mega winner! I can believe you even exist");
            lastWords.nextLine();
            Game game = new Game();
            game.play();
        } else {
            Tools.clearConsole();
            System.out.println("Congratulations!!! You are a winner! press enter to continue!");
            lastWords.nextLine();
            Game game = new Game();
            game.play();
        }
    }
    public void lost() {
        System.out.println("You have lost :(, send anything to get in main menu");
        Scanner lastWords = new Scanner(System.in);
        lastWords.nextLine();
        Game game = new Game();
        game.play();
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int[][] getField() {
        return field;
    }

    public int getFreeCells() {
        return freeCells;
    }

    public void setFreeCells(int freeCells) {
        this.freeCells = freeCells;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public boolean getFirstOpened() {
        return firstOpened;
    }

    public void setFirstOpened(boolean status) {
        this.firstOpened = status;
    }
}
