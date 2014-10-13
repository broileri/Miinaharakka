package game_logic;

import java.util.Random;

/**
 * This class creates a minefield that contains the positions of the mines,
 * numbers and blanks.
 *
 * @author Broileri
 */
public class Minefield {

    
    private final int[][] minefield;
    private final int FIELD_SIDE;
    private int MINE_TILE, BLANK_TILE, MINE_AMOUNT;

    /**
     * Creates a new game.
     *
     * @param size The length of the side of the field. Either 9, 16 or 22.
     * @see game_logic.Minefield#placeMines(int, int)
     * @see game_logic.Minefield#addNumbersOnField(Tile[] mines)
     * @see game_logic.Minefield#amountOfMines(int)
     */
    public Minefield(int size) {
        MINE_TILE = -1;
        BLANK_TILE = 0;
        FIELD_SIDE = size;
        MINE_AMOUNT = amountOfMines(FIELD_SIDE);

        minefield = new int[FIELD_SIDE][FIELD_SIDE];
        placeMines();
    }

    /**
     * Defines the amount of mines based on the size of the minefield.
     *
     * @param size The length of the side of the field.
     * @return The amount of mines.
     */
    private int amountOfMines(int size) {

        if (size == 9) {
            return 10;
        }
        if (size == 16) {
            return 40;
        }
        return 99;
    }

    /**
     * Places mines randomly on the field and returns their positions.
     *
     * @param size The length of the side of the minefield.
     * @param mine_amount The amount of mines.
     */
    private void placeMines() {

        Random mine_dealer = new Random();

        for (int i = MINE_AMOUNT; i > 0; i--) {

            // Ensures that a mine can be placed only to a blank location
            while (true) {
                int x = mine_dealer.nextInt(FIELD_SIDE);
                int y = mine_dealer.nextInt(FIELD_SIDE);

                if (minefield[x][y] != MINE_TILE) {

                    minefield[x][y] = MINE_TILE;
                    addNumbersAroundMine(x, y);
                    break;
                }
            }
        }
    }

    /**
     * Surrounds the mines with numbers, so that each number is surrounded by as
     * many mines as the number indicates.
     *
     * @see game_logic.Minefield#fieldContainsLocation(int, int)
     */
    private void addNumbersAroundMine(int x, int y) {

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

    /**
     * Checks whether the matrix contains location (x, y).
     *
     * @param x coordinate
     * @param y coordinate
     * @return true, if the matrix contains the given location.
     */
    public boolean fieldContainsLocation(int x, int y) {

        return x <= (minefield.length - 1) && y <= (minefield.length - 1) && x >= 0 && y >= 0;
    }

    /**
     * Returns the matrix representation of the minefield that contains mines,
     * blanks, opened tiles and numbers.
     */
    public int[][] getMinematrix() {
        return this.minefield;
    }

    public int getAmountOfMines() {
        return this.MINE_AMOUNT;
    }

    public int getMineValue() {
        return this.MINE_TILE;
    }

    public int getBlankValue() {
        return this.BLANK_TILE;
    }
}
