package gui;

import game_logic.Minefield;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates and maintains the button grid that acts as a minefield.
 * 
 * @author Broileri
 */
public class GameGrid {

    private ImageIcon bird_clicked, bird_victory, bird_fainted, bird_normal;
    private ImageIcon mine_whole, egged_tile, unsure, mine_explosion, mine_found;
    private ImageIcon[] numbers;
    private JButton[][] button_grid;
    private JButton reset;
    private boolean GRID_IS_CLICKABLE, PLAYER_LOSES;
    private int[][] minematrix;
    private int MINE_TILE, BLANK_TILE, TILES_LEFT_TO_OPEN, GRID_SIDE, UNUSED_EGGS, MINE_COUNT;
    private boolean START_TIMER = true;
    private Minefield minefield;
    private JTextField mine_meter;
    private GameTimer timer;

    public GameGrid() {

        GRID_SIDE = 9;

        minefield = new Minefield(GRID_SIDE);
        minematrix = minefield.getMinematrix();

        MINE_TILE = minefield.getMineValue();
        BLANK_TILE = minefield.getBlankValue();

        MINE_COUNT = minefield.getAmountOfMines();
        UNUSED_EGGS = MINE_COUNT;
        TILES_LEFT_TO_OPEN = GRID_SIDE * GRID_SIDE - MINE_COUNT;

        PLAYER_LOSES = false;
        GRID_IS_CLICKABLE = true;

        loadIcons();
    }

    public JPanel createGameBoard() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(GRID_SIDE, GRID_SIDE));

        button_grid = new JButton[GRID_SIDE][GRID_SIDE];

        for (int x = 0; x < GRID_SIDE; x++) {
            for (int y = 0; y < GRID_SIDE; y++) {
                button_grid[x][y] = new JButton();
                addMouseListenerForGridButton(button_grid[x][y], x, y);
                button_grid[x][y].setFocusPainted(false);
                button_grid[x][y].setPreferredSize(new Dimension(35, 35));
                panel.add(button_grid[x][y]);
            }
        }
        return panel;
    }

    private void addMouseListenerForGridButton(final JButton b, final int x, final int y) {


        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (GRID_IS_CLICKABLE) {
                    reset.setIcon(bird_clicked);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (GRID_IS_CLICKABLE) {
                    reset.setIcon(bird_normal);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                if (GRID_IS_CLICKABLE && b.isEnabled()) {
                    // If a tile is not egged and MOUSE1, the tile opens 
                    if (e.getButton() == MouseEvent.BUTTON1 && b.getIcon() != egged_tile) {

                        b.removeMouseListener(this);

                        if (START_TIMER) {
                            START_TIMER = false;
                            timer.start();
                        }
                        openTile(x, y);
                        if (!PLAYER_LOSES && TILES_LEFT_TO_OPEN == 0) {
                            reset.setIcon(bird_victory);
                            GRID_IS_CLICKABLE = false;
                            revealGameBoard();
                        }

                    } // If a tile is not egged and MOUSE3, the tile becomes egged
                    else if (e.getButton() == MouseEvent.BUTTON3 && b.getIcon() == null) {
                        b.setIcon(egged_tile);
                        UNUSED_EGGS--;
                        mine_meter.setText(Integer.toString(UNUSED_EGGS));

                    } // If the tile is egged and MOUSE3, the tile becomes a question mark 
                    else if (e.getButton() == MouseEvent.BUTTON3 && b.getIcon() == egged_tile) {
                        b.setIcon(unsure);
                        UNUSED_EGGS++;
                        mine_meter.setText(Integer.toString(UNUSED_EGGS));
                    } // If the tile has a question mark and MOUSE3 the tile becomes empty
                    else if (e.getButton() == MouseEvent.BUTTON3 && b.getIcon() == unsure) {
                        b.setIcon(null);
                    }
                }
            }
        });
    }

    private void openTile(int x, int y) {

        int tileType = minematrix[x][y];

        if (tileType == MINE_TILE) {
            button_grid[x][y].setEnabled(false);
            button_grid[x][y].setIcon(mine_explosion);
            button_grid[x][y].setDisabledIcon(mine_explosion);
            gameOver();
        } else if (tileType == BLANK_TILE) {
            openBlankArea(x, y);
        } else {
            button_grid[x][y].setEnabled(false);
            TILES_LEFT_TO_OPEN--;
            button_grid[x][y].setIcon(numbers[tileType - 1]);
            button_grid[x][y].setDisabledIcon(numbers[tileType - 1]);
        }
    }

    private void gameOver() {
        
        PLAYER_LOSES = true;
        GRID_IS_CLICKABLE = false;
        reset.setIcon(bird_fainted);
        timer.stop();
        revealGameBoard();
    }

    public void newGame() {

        minefield = new Minefield(GRID_SIDE);
        minematrix = minefield.getMinematrix();

        PLAYER_LOSES = false;
        GRID_IS_CLICKABLE = true;
        START_TIMER = true;
        MINE_COUNT = minefield.getAmountOfMines();
        UNUSED_EGGS = MINE_COUNT;
        TILES_LEFT_TO_OPEN = GRID_SIDE * GRID_SIDE - MINE_COUNT;
    }

    /**
     * Opens all tiles when game ends.
     */
    private void revealGameBoard() {

        for (int x = 0; x < GRID_SIDE; x++) {
            for (int y = 0; y < GRID_SIDE; y++) {
                if (button_grid[x][y].isEnabled() && button_grid[x][y].getIcon() == null) {

                    button_grid[x][y].setEnabled(false);
                    if (minematrix[x][y] == BLANK_TILE) { // blank tile
                    } else if (minematrix[x][y] > MINE_TILE) { // number tile
                        button_grid[x][y].setIcon(numbers[minematrix[x][y] - 1]);
                        button_grid[x][y].setDisabledIcon(numbers[minematrix[x][y] - 1]);
                    } else { // mine tile
                        button_grid[x][y].setIcon(mine_whole);
                        button_grid[x][y].setDisabledIcon(mine_whole);
                    }

                } else if (button_grid[x][y].isEnabled()) {

                    button_grid[x][y].setEnabled(false);

                    if (button_grid[x][y].getIcon() == egged_tile) { // egged tile
                        if (minematrix[x][y] == MINE_TILE) {
                            button_grid[x][y].setIcon(mine_found);
                            button_grid[x][y].setDisabledIcon(mine_found);
                        } else {
                            button_grid[x][y].setDisabledIcon(egged_tile);
                        }
                    } else { // unsure tile
                        if (minematrix[x][y] == BLANK_TILE) { // was a blank
                            button_grid[x][y].setIcon(null);
                            button_grid[x][y].setDisabledIcon(null);
                        }

                        if (minematrix[x][y] > MINE_TILE) { // was a number
                            button_grid[x][y].setIcon(numbers[minematrix[x][y] - 1]);
                            button_grid[x][y].setDisabledIcon(numbers[minematrix[x][y] - 1]);

                        } else { // was a mine
                            button_grid[x][y].setIcon(mine_whole);
                            button_grid[x][y].setDisabledIcon(mine_whole);

                        }
                    }
                }
            }
        }
    }

    /**
     * Opens all blank tiles surrounding the clicked blank tile.
     */
    public void openBlankArea(int x, int y) {

        if (x >= 0 && x < GRID_SIDE && y >= 0 && y < GRID_SIDE) {

            if (minematrix[x][y] > 0) {

                if (button_grid[x][y].getIcon() == null) {
                    button_grid[x][y].setEnabled(false);
                    TILES_LEFT_TO_OPEN--;
                    button_grid[x][y].setIcon(numbers[minematrix[x][y] - 1]);
                    button_grid[x][y].setDisabledIcon(numbers[minematrix[x][y] - 1]);
                }
                return;
            }

            if (minematrix[x][y] == 0 && button_grid[x][y].isEnabled()) {

                button_grid[x][y].setIcon(null);
                button_grid[x][y].setEnabled(false);
                TILES_LEFT_TO_OPEN--;

                openBlankArea(x - 1, y);
                openBlankArea(x + 1, y);
                openBlankArea(x, y - 1);
                openBlankArea(x, y + 1);
                openBlankArea(x - 1, y - 1);
                openBlankArea(x - 1, y + 1);
                openBlankArea(x + 1, y - 1);
                openBlankArea(x + 1, y + 1);
            }
        }
    }

    private void loadIcons() {

        numbers = new ImageIcon[8];
        numbers[0] = new ImageIcon(getClass().getResource("Icons/yksi.png"));
        numbers[1] = new ImageIcon(getClass().getResource("Icons/kaksi.png"));
        numbers[2] = new ImageIcon(getClass().getResource("Icons/kolme.png"));
        numbers[3] = new ImageIcon(getClass().getResource("Icons/nelja.png"));
        numbers[4] = new ImageIcon(getClass().getResource("Icons/viisi.png"));
        numbers[5] = new ImageIcon(getClass().getResource("Icons/kuusi.png"));
        numbers[6] = new ImageIcon(getClass().getResource("Icons/seitseman.png"));
        numbers[7] = new ImageIcon(getClass().getResource("Icons/kahdeksan.png"));

        bird_normal = new ImageIcon(getClass().getResource("Icons/miinaharakka2.png"));
        bird_victory = new ImageIcon(getClass().getResource("Icons/miinaharakkajee2.png"));
        bird_clicked = new ImageIcon(getClass().getResource("Icons/miinaharakkaklik2.png"));
        bird_fainted = new ImageIcon(getClass().getResource("Icons/miinaharakkakuol2.png"));

        egged_tile = new ImageIcon(getClass().getResource("Icons/lippu4.png"));
        unsure = new ImageIcon(getClass().getResource("Icons/kysymys.png"));

        mine_whole = new ImageIcon(getClass().getResource("Icons/miina2.png"));
        mine_explosion = new ImageIcon(getClass().getResource("Icons/rajahdys.png"));
        mine_found = new ImageIcon(getClass().getResource("Icons/miinaok2.png"));
    }

    public void setGridSide(int side) {
        GRID_SIDE = side;
    }
    
    public int getUnusedEggs() {
        return UNUSED_EGGS;
    }

    public void setResetButtonForGameGrid(JButton reset) {
        this.reset = reset;
    }

    public void setMineMeterForGameGrid(JTextField mine_meter) {
        this.mine_meter = mine_meter;
    }

    public void setTimerForGameGrid(GameTimer timer) {
        this.timer = timer;
    }
}
