import org.junit.*;
import static org.junit.Assert.*;
import toimintalogiikka.Ruutu;


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
}
