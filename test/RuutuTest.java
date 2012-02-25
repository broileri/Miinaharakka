import org.junit.*;
import static org.junit.Assert.*;
import toimintalogiikka.Ruutu;

/**
 * Luokka testaa Ruutu-luokkaa
 * @author Broileri
 */
public class RuutuTest {
    
    public RuutuTest() {
    }
    private Ruutu r, n;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        r = new Ruutu(23, 69);
        n = new Ruutu(1, 2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void haeX() {
        int x1, x2;
        x1 = r.getX();
        x2 = n.getX();
        assertEquals(23, x1);
        assertEquals(1, x2);
    }
    
    @Test
    public void haeY() {
        int y1, y2;
        y1 = r.getY();
        y2 = n.getY();
        assertEquals(69, y1);
        assertEquals(2, y2);
    }
    
    @Test
    public void laitaX() {
        int x1 = 3, x2 = 8, x4, x5;
        r.setX(x1);
        n.setX(x2);
        x4 = r.getX();
        x5 = n.getX();        
        assertEquals(x1, x4);
        assertEquals(x2, x5);
    }
    
    @Test
    public void laitaY() {
        int y1 = 6, y2 = 11, y4, y5;
        r.setY(y1);
        n.setY(y2);
        y4 = r.getY();
        y5 = n.getY();
        assertEquals(y1, y4);
        assertEquals(y2, y5);
    }
}
