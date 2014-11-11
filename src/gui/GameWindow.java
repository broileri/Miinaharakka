package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates the game window that contains the menu buttons and the minefield
 * 
 * @author Broileri
 */
public class GameWindow extends JFrame {

    private JPanel game_board;
    private JButton large, small, medium, reset;
    private GameTimer timer;
    private ImageIcon bird_normal;
    private JTextField mine_meter;
    private GameGrid game_grid;

    public GameWindow() {

        game_grid = new GameGrid();
        bird_normal = new ImageIcon(getClass().getResource("Icons/miinaharakka2.png"));
        createWindow();
        game_grid.setResetButtonForGameGrid(reset);
        game_grid.setMineMeterForGameGrid(mine_meter);
        game_grid.setTimerForGameGrid(timer);        
    }
    
    private void createWindow() {

        BorderLayout layout = new BorderLayout(10, 10);
        layout.setVgap(10);
        this.setLayout(layout);

        JPanel left = new JPanel();
        left.setSize(30, WIDTH);
        this.add("West", left);

        JPanel right = new JPanel();
        right.setSize(30, WIDTH);
        this.add("East", right);

        JPanel lower = new JPanel();
        lower.setSize(WIDTH, 15);
        this.add("South", lower);

        game_board = game_grid.createGameBoard();
        this.add("Center", game_board);

        this.add("North", createWindowMenuPanel());

        Toolkit t = Toolkit.getDefaultToolkit();
        this.setSize((t.getScreenSize().height) / 2, (t.getScreenSize().width) / 3);

        this.setResizable(false);
        this.setTitle("Miinaharakka");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    private JPanel createWindowMenuPanel() {

        createMenuButtons();

        JPanel menu_panel = new JPanel(new FlowLayout());
        mine_meter = new JTextField();
        mine_meter.setFont(new Font("Helvetica", Font.BOLD, 14));
        mine_meter.setEditable(false);
        mine_meter.setColumns(2);
        mine_meter.setText(Integer.toString(game_grid.getUnusedEggs()));
        menu_panel.add(new JLabel(new ImageIcon(getClass().getResource("Icons/miina2.png"))));
        menu_panel.add(mine_meter);
        menu_panel.add(small);
        menu_panel.add(medium);
        menu_panel.add(large);
        timer = new GameTimer(menu_panel);
        menu_panel.add(reset);

        return menu_panel;
    }

    private void createMenuButtons() {

        reset = new JButton(bird_normal);
        reset.setPressedIcon(new ImageIcon(getClass().getResource("Icons/miinaharakkaiik2.png")));
        reset.setFocusPainted(false);

        small = new JButton("S");
        small.setFocusPainted(false);

        medium = new JButton("M");
        medium.setFocusPainted(false);

        large = new JButton("L");
        large.setFocusPainted(false);

        // Reset button
        reset.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent tapahtuma) {
                reset.setIcon(bird_normal);
                timer.stop();
                timer.reset();
                resetGame();
            }
        });
        // Size buttons
        addEventListenerForSizeButton(9, small);
        addEventListenerForSizeButton(16, medium);
        addEventListenerForSizeButton(22, large);
    }
    
    private void addEventListenerForSizeButton(final int side, JButton size_button) {

        size_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timer.reset();
                game_grid.setGridSide(side);
                resetGame();
            }
        });
    }

    public void resetGame() {

        reset.setIcon(bird_normal);
        game_grid.newGame();
        mine_meter.setText(Integer.toString(game_grid.getUnusedEggs()));
        
        this.remove(game_board);
        game_board = game_grid.createGameBoard();
        this.add("Center", game_board);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}