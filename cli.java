import java.util.*;

public class cli {
    Scanner input = new Scanner(System.in); 
    
    private int xsize = 7;
    private int ysize = 6;
    private int currentPlayer = 1;
    
    private boolean hasWon = false;
    private boolean hasDraw = false;
    private boolean quit = false;
    private boolean newGame = false;
    
    Grid my_grid = new Grid();
    logic my_logic = new logic(my_grid); 

    public cli() {
    }

    public void runtime() {
        System.out.println("player " + currentPlayer + "'s turn");
        display();
        int x = 0;
        try {
            x = input.nextInt();
            if (x <= 0 || x > my_grid.get_xsize()) {
                throw new Exception();
            }
        } catch (Exception exc) {
            System.out.println("not a number between 1 and "
                    + my_grid.get_xsize());
            input.nextLine();
        }
        
        if (x > 0 && x < my_grid.get_xsize() + 1) {
            x--;
            int y = my_grid.find_y(x);
            if (y != -1) {
                
                if (my_logic.set_and_check(x, y, currentPlayer)) {
                    hasWon = true;
                } else if (my_logic.draw_game()) {
                    hasDraw = true;
                } else {
                    
                    currentPlayer = my_grid.changeplayer(currentPlayer, 2);
                }
            } else {
                System.out.println("collumn filled");
            }
        }
    }

    public void showWin() {

        display();
        System.out.println("\nwinner is player " + currentPlayer
                + "\nPlay again?\n"
                + "press 0 for new game\n"
                + "press a letter to quit");
        int choice = -1;
        try {
            choice = input.nextInt();
        } catch (Exception exc) {
            System.out.println("Quitting");
            quit = true;
        }
        if (choice == 0) {
            newGame = true;
        }
    }

    public void showDraw() {
        display();
        System.out.println(
                "\ndraw game"
                + "\nPlay again?\n"
                + "press 0 for new game\n"
                + "press a letter to quit");
        int choice = -1;
        try {
            choice = input.nextInt();
        } catch (Exception exc) {
            System.out.println("Quitting");
            quit = true;
        }
        if (choice == 0) {
            newGame = true;
        }
    }

    public void display() {
        for (int i = -1; i < ysize; i++) {
            for (int j = 0; j < xsize; j++) {
                if (i < 0) {
                    System.out.print(" " + (j + 1) + " ");
                } else {
                    System.out.print("[");
                    if (my_grid.matrix_equals(j, i, 0)) {
                        System.out.print(" ]");
                    } else {
                        int[][] temp_matrix = my_grid.get_matrix();
                        System.out.print(temp_matrix[j][i] + "]");
                    }
                }
            }
            System.out.println();
        }
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public boolean getHasDraw() {
        return hasDraw;
    }

    public boolean getQuit() {
        return quit;
    }

    public boolean getNewGame() {
        return newGame;
    }
}
