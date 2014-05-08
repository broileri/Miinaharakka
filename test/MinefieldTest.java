
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import game_logic.Minefield;
import game_logic.Tile;

/**
 * Testing Minefield class
 * @author Broileri
 */
public class MinefieldTest {

    public MinefieldTest() {
    }
    private Minefield small_field;
    private Minefield normal_field;
    private Minefield large_field;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        small_field = new Minefield(9);
        normal_field = new Minefield(16);
        large_field = new Minefield(22);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void areBlanksOpenedCorrectly() {
        int[][] test = new int[9][9];
        boolean should_be_true = true;

        // matrix is filled with zeros
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test.length; j++) {
                test[i][j] = 0;
            }
        }
        // the third row of the test matrix is filled with -1
        for (int i = 0; i < test.length; i++) {
            test[2][i] = -1;
        }
        // "Clicking" in the middle of the second row
        small_field.findBlanks(test, new Tile(1, 5));

        // If the first two rows contain value other than 9, should_be_true becomes false and test fails
        for (int i = 0; i < test.length; i++) {
            if (test[0][i] != 9 || test[1][i] != 9) {
                System.out.println(test[0][i] + " " + test[1][i]);
                should_be_true = false;
            }
        }
        assertTrue(should_be_true);
    }

    @Test
    public void areBlanksOpenedCorrectlyDiagonally() {
        int[][] test = new int[9][9];
        boolean should_be_true = true;

        // matrix is filled with zeros
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test.length; j++) {
                test[i][j] = 0;
            }
        }
        // the fourth row of the test matrix is filled with -1
        for (int i = 0; i < test.length; i++) {
            test[3][i] = -1;
        }
        // every other tile of the 3rd row of the test matrix is set as -1
        for (int i = 0; i < test.length; i++) {
            if (i % 2 != 0) {
                test[2][i] = -1;
            }
        }
        // every tile with an even index of the 2nd row of the test matrix is set as -1
        for (int i = 0; i < test.length; i++) {
            if (i % 2 == 0) {
                test[1][i] = -1;
            }
        }     
        // "clicking" in the middle of the first row
        small_field.findBlanks(test, new Tile(0, 5));      

        // If any of the opened tiles contains a value other than 9, should_be_true becomes false and test fails
        // 1st row should be opened completely
        for (int i = 0; i < test.length; i++) {
            if (test[0][i] != 9) {
                should_be_true = false;
            }
        }  
        // 2nd row, uneven indexes should be open
        for (int i = 0; i < test.length; i++) {
            if (i%2 != 0 && test[1][i] != 9) {
                should_be_true = false;
            }
        }  
        // 3rd row, even indexes should be open
        for (int i = 0; i < test.length; i++) {
            if (i%2 == 0 && test[2][i] != 9) {
                should_be_true = false;
            }
        }         
        assertTrue(should_be_true);
    }

    @Test
    public void fieldSizeRightS() {
        assertTrue(small_field.fieldContainsLocation(0, 0));
        assertTrue(small_field.fieldContainsLocation(0, 8));
        assertTrue(small_field.fieldContainsLocation(8, 0));
        assertTrue(small_field.fieldContainsLocation(8, 8));
        assertTrue(!small_field.fieldContainsLocation(9, 9));
    }

    @Test
    public void fieldSizeRightM() {
        assertTrue(normal_field.fieldContainsLocation(0, 0));
        assertTrue(normal_field.fieldContainsLocation(0, 15));
        assertTrue(normal_field.fieldContainsLocation(15, 0));
        assertTrue(normal_field.fieldContainsLocation(15, 15));
        assertTrue(!normal_field.fieldContainsLocation(16, 16));
    }

    @Test
    public void fieldSizeRightL() {
        assertTrue(large_field.fieldContainsLocation(0, 0));
        assertTrue(large_field.fieldContainsLocation(21, 21));
        assertTrue(large_field.fieldContainsLocation(21, 21));
        assertTrue(large_field.fieldContainsLocation(21, 21));
        assertTrue(!large_field.fieldContainsLocation(22, 22));
    }

    @Test
    public void rightAmountOfMinesInS() {
        int[][] small = small_field.getMinematrix();
        int mines = 0;
        for (int x = 0; x < small.length; x++) {
            for (int y = 0; y < small.length; y++) {
                if (small[x][y] == -1) {
                    mines++;
                }
            }
        }
        assertEquals(mines, 10);
    }

    @Test
    public void rightAmountOfMinesInM() {
        int[][] normal = normal_field.getMinematrix();
        int mines = 0;
        for (int x = 0; x < normal.length; x++) {
            for (int y = 0; y < normal.length; y++) {
                if (normal[x][y] == -1) {
                    mines++;
                }
            }
        }
        assertEquals(mines, 40);
    }

    @Test
    public void rightAmountOfMinesInL() {
        int[][] large = large_field.getMinematrix();
        int mines = 0;
        for (int x = 0; x < large.length; x++) {
            for (int y = 0; y < large.length; y++) {
                if (large[x][y] == -1) {
                    mines++;
                }
            }
        }
        assertEquals(mines, 99);
    }

    @Test
    public void areNumbersSetCorrectlyS() {

        int[][] test = small_field.getMinematrix();
        int number;

        for (int x = 0; x < test.length; x++) {
            for (int y = 0; y < test.length; y++) {
                if (test[x][y] != -1) {
                    number = test[x][y];
                    assertEquals(number, countSurroundingMines(small_field, x, y));
                }
            }
        }
    }

    @Test
    public void areNumbersSetCorrectlyM() {

        int[][] test = normal_field.getMinematrix();
        int number;

        for (int x = 0; x < test.length; x++) {
            for (int y = 0; y < test.length; y++) {
                if (test[x][y] != -1) {
                    number = test[x][y];
                    assertEquals(number, countSurroundingMines(normal_field, x, y));
                }
            }
        }
    }

    @Test
    public void areNumbersSetCorrectlyL() {

        int[][] test = large_field.getMinematrix();
        int number;

        for (int x = 0; x < test.length; x++) {
            for (int y = 0; y < test.length; y++) {
                if (test[x][y] != -1) {
                    number = test[x][y];
                    assertEquals(number, countSurroundingMines(large_field, x, y));
                }
            }
        }
    }

    /**
     * An assist method for tests that check the numbers in the field
     * @param m Minefield instance to be tested
     * @param x coordinate
     * @param y coordinate
     * @return The number of mines that surround the number.
     */
    public int countSurroundingMines(Minefield m, int x, int y) {

        int surrounding_mines;
        int[][] test = m.getMinematrix();

        surrounding_mines = 0;

        // Upper tiles
        if (m.fieldContainsLocation(x - 1, y - 1) && test[x - 1][y - 1] == -1) {
            surrounding_mines++;
        }
        if (m.fieldContainsLocation(x - 1, y) && test[x - 1][y] == -1) {
            surrounding_mines++;
        }
        if (m.fieldContainsLocation(x - 1, y + 1) && test[x - 1][y + 1] == -1) {
            surrounding_mines++;
        }
        // Side tiles
        if (m.fieldContainsLocation(x, y - 1) && test[x][y - 1] == -1) {
            surrounding_mines++;
        }
        if (m.fieldContainsLocation(x, y + 1) && test[x][y + 1] == -1) {
            surrounding_mines++;
        }
        // Lower tiles
        if (m.fieldContainsLocation(x + 1, y - 1) && test[x + 1][y - 1] == -1) {
            surrounding_mines++;
        }
        if (m.fieldContainsLocation(x + 1, y) && test[x + 1][y] == -1) {
            surrounding_mines++;
        }
        if (m.fieldContainsLocation(x + 1, y + 1) && test[x + 1][y + 1] == -1) {
            surrounding_mines++;
        }
        return surrounding_mines;
    }
}
