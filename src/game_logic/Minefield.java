package game_logic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * This class creates a minefield that contains the positions of the mines, numbers and blanks.
 * 0 indicates a completely empty Tile, 1-8 indicate how many mines surround a certain Tile, -1 indicates a mine. 
 *
 * @author Broileri
 */
public class Minefield {
    
    /**
     * Variable "blanks" is a table, that is used to save the indexes of the blank Tiles that are opened.
     */
    private Tile[] blanks;
    private int[][] minefield;   
    private final int side_of_field;

    /**
     * Creates a new game.
     *
     * @param size The length of the side of the field. Either 9, 16 or 22.
     *
     * @see game_logic.Minefield#createField(int)
     * @see game_logic.Minefield#placeMines(int, int)
     * @see game_logic.Minefield#addNumbersOnField(Tile[] mines)
     * @see game_logic.Minefield#amountOfMines(int)
     */
    public Minefield(int size) {
        side_of_field = size;
        createField(size); // sets value to private variable "minefield"
        addNumbersOnField(placeMines(size, amountOfMines(size)));
    }

    /**
     * Creates a new minefield that is filled with zeros. Zeros are used to indicate an empty tile.
     *
     * @param size The length of the side of the minefield.
     */
    private void createField(int size) {

        minefield = new int[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                minefield[x][y] = 0;
            }
        }
    }

    /**
     * Defines the amount of mines based on the size of the minefield.
     *
     * @param size The length of the side of the field.
     *
     * @return The amount of mines.
     */
    private int amountOfMines(int size) {

        int amount_of_mines;

        if (size == 9) {
            amount_of_mines = 10;
        } else if (size == 16) {
            amount_of_mines = 40;
        } else {
            amount_of_mines = 99;
        }
        return amount_of_mines;
    }

    /**
     * Places mines randomly on the field and returns their positions.
     *
     * @param size The length of the side of the minefield.
     * @param mine_amount The amount of mines.
     */
    private Tile[] placeMines(int size, int mine_amount) {
        
        Tile[] mines = new Tile[mine_amount];

        // Drawing out the positions of the mines
        Random mine_dealer = new Random();
        int index = 0;
        
        // Placing the mines on the field
        for (int i = mine_amount; i > 0; i--) {
            
            // Ensures that a mine can be placed only to a blank location
            while (true) {                
                int x = mine_dealer.nextInt(size);
                int y = mine_dealer.nextInt(size);                
                if (minefield[x][y] == 0) {
                    minefield[x][y] = -1; // -1 is used to indicate a mine
                    mines[index] = new Tile(x, y);
                    index++;
                    break;
                }
            }
        }
        return mines;
    }

    /**
     * Surrounds the mines with numbers, so that each number is surrounded by as many mines as the number indicates. 
     *
     * @see game_logic.Minefield#fieldContainsLocation(int, int)
     */
    private void addNumbersOnField(Tile[] mines) {
        
        int x, y;
        
        for (int i = 0; i < mines.length; i++) {
            x = mines[i].getX();
            y = mines[i].getY();

            // Upper tiles
            if (fieldContainsLocation(x - 1, y - 1) && minefield[x - 1][y - 1] != -1) {
                minefield[x - 1][y - 1] += 1;
            }
            if (fieldContainsLocation(x - 1, y) && minefield[x - 1][y] != -1) {
                minefield[x - 1][y] += 1;
            }
            if (fieldContainsLocation(x - 1, y + 1) && minefield[x - 1][y + 1] != -1) {
                minefield[x - 1][y + 1] += 1;
            }
            // Side tiles
            if (fieldContainsLocation(x, y - 1) && minefield[x][y - 1] != -1) {
                minefield[x][y - 1]++;
            }
            if (fieldContainsLocation(x, y + 1) && minefield[x][y + 1] != -1) {
                minefield[x][y + 1]++;
            }
            // Lower tiles
            if (fieldContainsLocation(x + 1, y - 1) && minefield[x + 1][y - 1] != -1) {
                minefield[x + 1][y - 1]++;
            }
            if (fieldContainsLocation(x + 1, y) && minefield[x + 1][y] != -1) {
                minefield[x + 1][y]++;
            }
            if (fieldContainsLocation(x + 1, y + 1) && minefield[x + 1][y + 1] != -1) {
                minefield[x + 1][y + 1]++;
            }
        }
    }

    /**
     * Checks whether the matrix contains location (x, y).
     *
     * @param x coordinate
     * @param y coordinate
     * @return true, if the matrix contains the given location.
     */
    public boolean fieldContainsLocation(int x, int y) {

        if (x <= (minefield.length - 1) && y <= (minefield.length - 1)
                && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the ready minefield that contains the locations for mines, blanks, opened tiles and numbers.
     */
    public int[][] getMinematrix() {
        return minefield;
    }

    /**
     * Returns the table of empty Tiles.
     */
    public Tile[] getBlanks() {
        return blanks;
    }

    /**
     * Returns the amount of mines.
     */
    public int getAmountOfMines() {
        return (amountOfMines(side_of_field));
    }
 
    /**
     * Puts a 9 into the given blank Tile and every blank Tile that is surrounding it. 9 is used to indicate a blank Tile that is to be opened. Flood fill principle.
     *
     * @param minefield Minefield, int[][]
     * @param t A location given as a Tile.
     *
     * @see game_logic.Minefield#fieldContainsLocation(int, int)
     */
    public void findBlanks(int[][] minefield, Tile t) {

        int index = 0, size = minefield.length - 1;        
        blanks = new Tile[size*size];

        for (int i = 0; i < blanks.length; i++) {            
            blanks[i] = new Tile(-9, -9); // -9 is used to indicate a position that is blank
        }

        Queue<Tile> queue = new LinkedList<Tile>();
        queue.add(t);

        while (!queue.isEmpty()) {
            Tile n = queue.poll();
            int x = n.getX();
            int y = n.getY();
            if (minefield[x][y] == 0) {
                minefield[x][y] = 9;

                // Saving the to-be-opened Tiles                
                blanks[index].setX(x);
                blanks[index].setY(y);
                index++;

                if (fieldContainsLocation(x, y - 1)) {
                    queue.add(new Tile(x, y - 1));
                }
                if (fieldContainsLocation(x, y + 1)) {
                    queue.add(new Tile(x, y + 1));
                }
                if (fieldContainsLocation(x - 1, y)) {
                    queue.add(new Tile(x - 1, y));
                }
                if (fieldContainsLocation(x + 1, y)) {
                    queue.add(new Tile(x + 1, y));
                }
                if (fieldContainsLocation(x - 1, y - 1)) {
                    queue.add(new Tile(x - 1, y - 1));
                }
                if (fieldContainsLocation(x - 1, y + 1)) {
                    queue.add(new Tile(x - 1, y + 1));
                }
                if (fieldContainsLocation(x + 1, y - 1)) {
                    queue.add(new Tile(x + 1, y - 1));
                }
                if (fieldContainsLocation(x + 1, y + 1)) {
                    queue.add(new Tile(x + 1, y + 1));
                }
            }
        }
    }  
}
