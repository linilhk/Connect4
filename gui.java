
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class gui {

    private JFrame frame;
    private JLabel[][] slots;
    private JButton[] buttons;
    
    private int xsize = 7;
    private int ysize = 6;
    private int currentPlayer = 1;
    
    private boolean hasWon = false;
    private boolean hasDraw = false;
    private boolean quit = false;
    private boolean newGame = false;
    
    Grid my_grid = new Grid();
    logic my_logic = new logic(my_grid); 

    public gui() {

        frame = new JFrame("connect four");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(new GridLayout(xsize, ysize + 1));

        slots = new JLabel[xsize][ysize];
        buttons = new JButton[xsize];

        for (int i = 0; i < xsize; i++) {
            buttons[i] = new JButton("" + (i + 1));
            buttons[i].setActionCommand("" + i);
            buttons[i].addActionListener(
                    new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            int a = Integer.parseInt(e.getActionCommand());
                            int y = my_grid.find_y(a);
                            if (y != -1) {
                                
                                if (my_logic.set_and_check(a, y, currentPlayer)) {
                                    hasWon = true;
                                } else if (my_logic.draw_game()) {
                                    hasDraw = true;
                                } else {
                                    
                                    currentPlayer = my_grid.changeplayer(currentPlayer, 2);
                                    frame.setTitle("connect four - player " + currentPlayer + "'s turn");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "choose another one", "column is filled", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });
            panel.add(buttons[i]);
        }
        for (int column = 0; column < ysize; column++) {
            for (int row = 0; row < xsize; row++) {
                slots[row][column] = new JLabel();
                slots[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                slots[row][column].setBorder(new LineBorder(Color.black));
                panel.add(slots[row][column]);
            }
        }

        
        frame.setContentPane(panel);
        frame.setSize(
                700, 600);
        frame.setVisible(
                true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateBoard() {
        for (int row = 0; row < xsize; row++) {
            for (int column = 0; column < ysize; column++) {
                if (my_grid.matrix_equals(row, column, 1)) {
                    slots[row][column].setOpaque(true);
                    slots[row][column].setBackground(Color.red);
                }
                if (my_grid.matrix_equals(row, column, 2)) {
                    slots[row][column].setOpaque(true);
                    slots[row][column].setBackground(Color.blue);
                }
            }
        }
    }

    public void showWon() {
        String winner = "player " + currentPlayer + " wins";
        int n = JOptionPane.showConfirmDialog(
                frame,
                "new game?",
                winner,
                JOptionPane.YES_NO_OPTION);
        if (n < 1) {
            frame.dispose();
            newGame = true;
        } else {
            frame.dispose();
            quit = true;
        }
    }

    public void showDraw() {
        String winner = "draw game";
        int n = JOptionPane.showConfirmDialog(
                frame,
                "new game?",
                winner,
                JOptionPane.YES_NO_OPTION);
        if (n < 1) {
            frame.dispose();
            newGame = true;
        } else {
            frame.dispose();
            quit = true;
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

    public static void main(String[] args) {
        gui Gui = new gui();
    }
}
