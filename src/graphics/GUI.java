package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import game_logic.Minefield;
import game_logic.Tile;

/**
 * This class creates a game window and handles the interactions between the
 * user and the game.
 *
 * @author Broileri
 */
public class GUI extends JFrame {

    // Buttons and icons
    private ImageIcon bird_normal, bird_pressed, bird_victory, bird_fainted, mine_whole, egged_tile, unsure, one, two, three, four, five, six, seven, eight, mine_explosion, mine_found;
    private JButton large, small, normal, reset;
    private HashMap<Integer, JButton> field_buttons; // this is where the buttons of the game field are saved
    private Tile[] to_be_opened; // this is where the coordinates of the to-be-opened tiles are saved
    private GameTimer game_timer;
    private Minefield minefield;
    private JPanel button_field_panel;
    private JTextField mine_meter;
    private boolean start_timer, mouse_listener_on;
    private int game_size = 9, unused_eggs;

    /**
     * *
     * @see graphics.GUI#loadIcons()
     * @see graphics.GUI#createMenuButtons()
     * @see graphics.GUI#addEventListenersForMenu()
     * @see graphics.GUI#startGame(boolean)
     */
    public GUI() {

        loadIcons();
        createMenuButtons();
        addEventListenersForMenu();
        startGame(true);
        this.setTitle("Miinaharakka");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Creates the menu buttons.
     */
    private void createMenuButtons() {

        reset = new JButton(bird_normal);
        reset.setPressedIcon(bird_pressed);
        reset.setFocusPainted(false);
        small = new JButton("S");
        small.setFocusPainted(false);
        normal = new JButton("M");
        normal.setFocusPainted(false);
        large = new JButton("L");
        large.setFocusPainted(false);
    }

    /**
     * Loads all icons.
     */
    private void loadIcons() {

        bird_normal = new ImageIcon(getClass().getResource("Icons/miinaharakka2.png"));
        bird_pressed = new ImageIcon(getClass().getResource("Icons/miinaharakkaiik2.png"));
        bird_victory = new ImageIcon(getClass().getResource("Icons/miinaharakkajee2.png"));
        bird_fainted = new ImageIcon(getClass().getResource("Icons/miinaharakkakuol2.png"));
        mine_whole = new ImageIcon(getClass().getResource("Icons/miina2.png"));
        egged_tile = new ImageIcon(getClass().getResource("Icons/lippu4.png"));
        unsure = new ImageIcon(getClass().getResource("Icons/kysymys.png"));
        one = new ImageIcon(getClass().getResource("Icons/yksi.png"));
        two = new ImageIcon(getClass().getResource("Icons/kaksi.png"));
        three = new ImageIcon(getClass().getResource("Icons/kolme.png"));
        four = new ImageIcon(getClass().getResource("Icons/nelja.png"));
        five = new ImageIcon(getClass().getResource("Icons/viisi.png"));
        six = new ImageIcon(getClass().getResource("Icons/kuusi.png"));
        seven = new ImageIcon(getClass().getResource("Icons/seitseman.png"));
        eight = new ImageIcon(getClass().getResource("Icons/kahdeksan.png"));
        mine_explosion = new ImageIcon(getClass().getResource("Icons/rajahdys.png"));
        mine_found = new ImageIcon(getClass().getResource("Icons/miinaok2.png"));
    }

    /**
     * Starts a new game by creating a new instance of Minefield.
     *
     * @see graphics.GUI#createGameWindow()
     * @see graphics.GUI#createButtonFieldPanel()
     * @see game_logic.Minefield#getAmountOfMines()
     * @see graphics.GUI#addMouseListenersForGameField(int)
     * @see game_logic.Minefield#Minefield(int game_size)
     */
    private void startGame(boolean first_game) {

        reset.setIcon(bird_normal);
        mouse_listener_on = true;
        field_buttons = new HashMap<Integer, JButton>();

        if (first_game) {
            createGameWindow();
        }
        else {
            this.remove(button_field_panel);
            button_field_panel = createButtonFieldPanel();
            this.add("Center", button_field_panel);
        }
        
        for (int i = 0; i < game_size * game_size; i++) {
            addMouseListenersForGameField(i);
        }
        // "resetting" variables and doing some "assembling"
        minefield = new Minefield(game_size);
        unused_eggs = minefield.getAmountOfMines();
        mine_meter.setText(Integer.toString(unused_eggs));
        start_timer = true;
        this.pack();
    }

    /**
     * Creates a game window when the game is started for the first time.
     *
     * @see graphics.GUI#createButtonFieldPanel()
     */
    public void createGameWindow() {

        button_field_panel = createButtonFieldPanel();

        BorderLayout layout = new BorderLayout(10, 10);
        layout.setVgap(10);
        JPanel left = new JPanel();
        left.setSize(30, WIDTH);
        JPanel right = new JPanel();
        right.setSize(40, WIDTH);
        JPanel lower = new JPanel();
        lower.setSize(WIDTH, 15);

        this.setLayout(layout);
        this.add("West", left);
        this.add("Center", button_field_panel);
        this.add("East", right);
        this.add("South", lower);
        this.add("North", createMenuPanel());
        this.setBounds(330, 150, 300, 300);
        this.setResizable(false);
        this.setVisible(true);
    }


    /**
     * Called when the player loses or wins the game. 
     * @param defeat True if the player loses.
     * @see graphics.GameTimer#GameTimer(JPanel)
     * @see graphics.GUI#revealField(boolean) 
     */    
    public void endGame(boolean defeat) {
        game_timer.stop();
        revealField(defeat);
        if (defeat) {
            reset.setIcon(bird_fainted);
        }
        else {
            reset.setIcon(bird_victory);
            mine_meter.setText("0");
        }       
    }

    /**
     * Reveals all mines, blanks and number tiles.
     * @param defeat True if player lost.
     * @return Exploding mine icon or null.
     */
    public ImageIcon revealField(boolean defeat) {

        int[][] field = this.minefield.getMinematrix();

        for (int i = 0; i < game_size * game_size; i++) {

            Tile r = createTileByIndex(i);
            int tileType = field[r.getX()][r.getY()];

            // Victory, mine tile
            if (tileType == -1 && !defeat) {
                field_buttons.get(i).setIcon(mine_found);
                field_buttons.get(i).setDisabledIcon(mine_found);
                field_buttons.get(i).setEnabled(false);
            } // Loss, mine tile           
            else if (tileType == -1) {
                field_buttons.get(i).setIcon(mine_whole);
                field_buttons.get(i).setDisabledIcon(mine_whole);
                field_buttons.get(i).setEnabled(false);
            } 
        }
        if (defeat) {
            mouse_listener_on = false;
            return mine_explosion;
        }
        return null;
    }

    /**
     * Checks if the game has been won. Called every time a tile is opened.
     */
    private void checkSituation() {

        int opened = 0;
        for (int i = 0; i < game_size * game_size; i++) {
            if (!field_buttons.get(i).isEnabled()) {
                opened++;
            }
        }
        if (minefield.getAmountOfMines() == game_size * game_size - opened) {
            endGame(false);
        }
    }

    /**
     * When the player opens a tile that doesn't contain a mine or a number, all other empty tiles that surround 
     * that tile get opened, too. This method opens the number tiles that are on the edges of that blank area.
     *
     * @param blanks The X and Y coordinates of the blank tiles.
     */
    public void openEdgesOfBlankArea(Tile[] blanks) {

        int[][] helper_minefield = this.minefield.getMinematrix();
        int size = game_size * game_size * 8; // because eight just works... :)
        int[] hashmap_indexes = new int[size]; 
        for (int i = 0; i < size; i++) {
            hashmap_indexes[i] = -9;
        }
        int index = 0;

        for (int i = 0; i < blanks.length; i++) {

            if (blanks[i].getX() != -9) {

                // Upper
                if (this.minefield.fieldContainsLocation(blanks[i].getX() - 1, blanks[i].getY() - 1)) {
                    if (helper_minefield[blanks[i].getX() - 1][blanks[i].getY() - 1] != 9) {
                        hashmap_indexes[index] = createIndex(blanks[i].getX() - 1, blanks[i].getY() - 1);
                        index++;
                    }
                }
                if (this.minefield.fieldContainsLocation(blanks[i].getX() - 1, blanks[i].getY())) {
                    if (helper_minefield[blanks[i].getX() - 1][blanks[i].getY()] != 9) {
                        hashmap_indexes[index] = createIndex(blanks[i].getX() - 1, blanks[i].getY());
                        index++;
                    }
                }
                if (this.minefield.fieldContainsLocation(blanks[i].getX() - 1, blanks[i].getY() + 1)) {
                    if (helper_minefield[blanks[i].getX() - 1][blanks[i].getY() + 1] != 9) {
                        hashmap_indexes[index] = createIndex(blanks[i].getX() - 1, blanks[i].getY() + 1);
                        index++;
                    }
                }
                // Sides
                if (this.minefield.fieldContainsLocation(blanks[i].getX(), blanks[i].getY() - 1)) {
                    if (helper_minefield[blanks[i].getX()][blanks[i].getY() - 1] != 9) {
                        hashmap_indexes[index] = createIndex(blanks[i].getX(), blanks[i].getY() - 1);
                        index++;
                    }
                }
                if (this.minefield.fieldContainsLocation(blanks[i].getX(), blanks[i].getY() + 1)) {
                    if (helper_minefield[blanks[i].getX()][blanks[i].getY() + 1] != 9) {
                        hashmap_indexes[index] = createIndex(blanks[i].getX(), blanks[i].getY() + 1);
                        index++;
                    }
                }
                // Lower
                if (this.minefield.fieldContainsLocation(blanks[i].getX() + 1, blanks[i].getY() - 1)) {
                    if (helper_minefield[blanks[i].getX() + 1][blanks[i].getY() - 1] != 9) {
                        hashmap_indexes[index] = createIndex(blanks[i].getX() + 1, blanks[i].getY() - 1);
                        index++;
                    }
                }
                if (this.minefield.fieldContainsLocation(blanks[i].getX() + 1, blanks[i].getY())) {
                    if (helper_minefield[blanks[i].getX() + 1][blanks[i].getY()] != 9) {
                        hashmap_indexes[index] = createIndex(blanks[i].getX() + 1, blanks[i].getY());
                        index++;
                    }
                }
                if (this.minefield.fieldContainsLocation(blanks[i].getX() + 1, blanks[i].getY() + 1)) {
                    if (helper_minefield[blanks[i].getX() + 1][blanks[i].getY() + 1] != 9) {
                        hashmap_indexes[index] = createIndex(blanks[i].getX() + 1, blanks[i].getY() + 1);
                        index++;
                    }
                }
            } else {
                setIconsForEdges(hashmap_indexes, helper_minefield, index);
                return;
            }
        }
    }

    /**
     * Sets icons for the tiles defined by openEdgesOfBlankArea
     *
     * @param hashmap_indexes
     * @param helper_minefield
     * @param index    
     * @see graphics.GUI#openEdgesOfBlankArea(game_logic.Tile[])
     */
    public void setIconsForEdges(int[] hashmap_indexes, int[][] helper_minefield, int index) {

        for (int i = 0; i < index; i++) {
            if (hashmap_indexes[i] != -9) {
                JButton button = field_buttons.get(hashmap_indexes[i]);
                Tile r = createTileByIndex(hashmap_indexes[i]);
                if (button.getIcon() == null) {
                    button.setIcon(openingNumberTile(helper_minefield[r.getX()][r.getY()]));
                    button.setEnabled(false);
                    button.setDisabledIcon(openingNumberTile(helper_minefield[r.getX()][r.getY()]));
                }
            }
        }
    }

    /**
     * Returns the proper icon for an opened tile.
     *
     * @param index The HashMap key of the given button.
     * @return Icon for the opened tile.
     * @see graphics.GUI#openEdgesOfBlankArea(game_logic.Tile[])
     * @see graphics.GUI#openingNumberTile(int)
     * @see game_logic.Minefield#findBlanks(int[][], game_logic.Tile)
     * @see game_logic.Minefield#getBlanks() 
     */
    private ImageIcon setIconToOpened(int index) {

        int[][] helper_minefield = this.minefield.getMinematrix();
        Tile t = createTileByIndex(index);
        int tileType = helper_minefield[t.getX()][t.getY()];

        if (tileType == -1) { // It's a mine
            endGame(true);
            return mine_explosion;

        } else if (tileType == 0) { // It's a blank
            minefield.findBlanks(helper_minefield, t);
            to_be_opened = minefield.getBlanks(); // Coordinates of the blanks

            // Opening of the blanks and ruling out the "empty" Tiles (-9) of the Tile table
            for (int i = 0; i < to_be_opened.length; i++) {
                if (to_be_opened[i].getX() != -9) {
                    int button_index = createIndex(to_be_opened[i].getX(), to_be_opened[i].getY());
                    if (field_buttons.get(button_index).getIcon() == null) {
                        field_buttons.get(button_index).setEnabled(false);
                        field_buttons.get(button_index).setIcon(null);
                    }
                }
            }
            openEdgesOfBlankArea(to_be_opened); // Opening the edges of blank tiles
        } else { // Opening a number tile
            return openingNumberTile(tileType);
        }
        return null;
    }

    /**
     * Defines which icon to use.  
     * @param tileNumber The number in the tile. Indicates the amount of mines in the vicinity.
     * @return Number icon.
     * @see graphics.GUI#setIconToOpened(int)
     */
    public ImageIcon openingNumberTile(int tileNumber) {

        if (tileNumber == 1) {
            return one;
        } else if (tileNumber == 2) {
            return two;
        } else if (tileNumber == 3) {
            return three;
        } else if (tileNumber == 4) {
            return four;
        } else if (tileNumber == 5) {
            return five;
        } else if (tileNumber == 6) {
            return six;
        } else if (tileNumber == 7) {
            return seven;
        } else if (tileNumber == 7) {
            return eight;
        }
        return null;
    }

    /**
     * Creates a Tile out of a JButton HashMap key.
     * @param index of JButton Hashmap
     * @return Tile
     */
    public Tile createTileByIndex(int index) {

        int tile_of_field = 0;

        for (int x = 0; x < game_size; x++) {
            for (int y = 0; y < game_size; y++) {
                if (tile_of_field == index) {
                    return new Tile(x, y);
                }
                tile_of_field++;
            }
        }
        return null;
    }

    /**
     * Translates int[] indexes into JButton HashMap keys.
     * @param x X
     * @param y Y
     * @return HashMap key
     */
    public int createIndex(int x, int y) {

        return (x + y + x * (game_size - 1));
    }

    /**
     * Creates mouse listeners for the button field.
     *
     * @param index key value for the JButton HashMapin.
     * @see graphics.GameTimer
     * @see graphics.GUI#checkSituation()
     * @see graphics.GUI#setIconToOpened(int)
     */
    private void addMouseListenersForGameField(final int index) {

        final JButton button = field_buttons.get(index);

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                if (mouse_listener_on) {
                    // If a tile is not egged and MOUSE1, the tile opens 
                    if (e.getButton() == MouseEvent.BUTTON1
                            && button.getIcon() != egged_tile && button.isEnabled()) {

                        if (start_timer) {
                            start_timer = false;
                            game_timer.start();
                        }
                        button.setIcon(setIconToOpened(index));
                        button.setEnabled(false);
                        button.setDisabledIcon(setIconToOpened(index));
                        checkSituation();

                    } // If a tile is not egged and MOUSE3, the tile becomes egged
                    else if (e.getButton() == MouseEvent.BUTTON3 && button.getIcon() == null
                            && button.isEnabled()) {
                        button.setIcon(egged_tile);
                        unused_eggs--;
                        mine_meter.setText(Integer.toString(unused_eggs));

                    } // If the tile is egged and MOUSE3, the tile becomes a question mark 
                    else if (e.getButton() == MouseEvent.BUTTON3 && button.getIcon() == egged_tile
                            && button.isEnabled()) {
                        button.setIcon(unsure);
                        unused_eggs++;
                        mine_meter.setText(Integer.toString(unused_eggs));
                    } // If the tile has a question mark and MOUSE3 the tile becomes empty
                    else if (e.getButton() == MouseEvent.BUTTON3 && button.getIcon() == unsure
                            && button.isEnabled()) {
                        button.setIcon(null);
                    }
                }
            }
        });
    }

    /**
     * Creates event listeners for the menu buttons. 
     * @see graphics.GameTimer
     * @see graphics.GUI#startGame(boolean) 
     * @see graphics.GUI#addEventListenerForSizeButton(int, javax.swing.JButton)
     */
    private void addEventListenersForMenu() {

        // Reset button
        reset.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        reset.setIcon(bird_normal);
                        game_timer.stop();
                        game_timer.reset();
                        startGame(false);
                    }
                });
        // Size buttons
        addEventListenerForSizeButton(9, small);
        addEventListenerForSizeButton(16, normal);
        addEventListenerForSizeButton(22, large);
    }

    /**
     * Sets an event listener for a size button.
     * @param g_size The size of the game.
     * @param button_size S, M or L JButton.
     */
    public void addEventListenerForSizeButton(final int g_size, JButton button_size) {

        button_size.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game_timer.stop();
                        game_timer.reset();
                        game_size = g_size;
                        startGame(false);
                    }
                });
    }

    /**
     * Creates a button minefield panel. 
     * @return button_field_panel
     * @see graphics.GUI#startGame(boolean)
     */
    private JPanel createButtonFieldPanel() {

        button_field_panel = new JPanel(new GridLayout(game_size, game_size));
        createButtonsForField(game_size * game_size);
        for (int i = 0; i < field_buttons.size(); i++) {
            field_buttons.get(i).setEnabled(true);
            button_field_panel.add(field_buttons.get(i));
        }
        return button_field_panel;
    }

    /**
     * Creates and returns the menu panel.
     * @return menu_panel   
     * @see graphics.GUI#startGame(boolean)
     * @see graphics.GameTimer
     */
    private JPanel createMenuPanel() {

        JPanel menu_panel = new JPanel(new FlowLayout());
        Font f = new Font("Helvetica", Font.BOLD, 14);
        mine_meter = new JTextField();
        mine_meter.setFont(f);
        mine_meter.setEditable(false);
        mine_meter.setColumns(2);
        menu_panel.add(new JLabel(mine_whole));
        menu_panel.add(mine_meter);
        menu_panel.add(small);
        menu_panel.add(normal);
        menu_panel.add(large);
        game_timer = new GameTimer(menu_panel);
        menu_panel.add(reset);

        return menu_panel;
    }

    /**
     * Creates JButtons for a HashMap.  
     * @param amount The amount of JButtons to be created.
     */
    private void createButtonsForField(int amount) {

        JButton button;

        for (int i = 0; i < amount; i++) {
            button = new JButton();
            button.setPreferredSize(new Dimension(30, 30));
            button.setFocusPainted(false);
            field_buttons.put(i, button);
        }
    }
}
