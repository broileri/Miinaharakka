import org.junit.*;
import static org.junit.Assert.*;
import game_logic.Tile;

/**
 * Testing Tile class
 * @author Broileri
 */
public class TileTest {
    
    public TileTest() {
    }
    private Tile t, s;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        t = new Tile(23, 69);
        s = new Tile(1, 2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getX() {
        int x1, x2;
        x1 = t.getX();
        x2 = s.getX();
        assertEquals(23, x1);
        assertEquals(1, x2);
    }
    
    @Test
    public void getY() {
        int y1, y2;
        y1 = t.getY();
        y2 = s.getY();
        assertEquals(69, y1);
        assertEquals(2, y2);
    }
    
    @Test
    public void setX() {
        int x1 = 3, x2 = 8, x4, x5;
        t.setX(x1);
        s.setX(x2);
        x4 = t.getX();
        x5 = s.getX();        
        assertEquals(x1, x4);
        assertEquals(x2, x5);
    }
    
    @Test
    public void setY() {
        int y1 = 6, y2 = 11, y4, y5;
        t.setY(y1);
        s.setY(y2);
        y4 = t.getY();
        y5 = s.getY();
        assertEquals(y1, y4);
        assertEquals(y2, y5);
    }
}
