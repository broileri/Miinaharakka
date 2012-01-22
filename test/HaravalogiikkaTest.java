
import org.junit.*;
import static org.junit.Assert.*;
import toimintalogiikka.Haravalogiikka;

public class HaravalogiikkaTest {

    public HaravalogiikkaTest() {
    }
    private Haravalogiikka haravapieni;
    private Haravalogiikka haravanormi;
    private Haravalogiikka haravasuuri;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        haravapieni = new Haravalogiikka(9);
        haravanormi = new Haravalogiikka(16);
        haravasuuri = new Haravalogiikka(22);

    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void oikeanKokoinenRuudukkoS() {
       assertTrue(haravapieni.onkoRuudukossa(0, 0));
       assertTrue(haravapieni.onkoRuudukossa(0, 8));
       assertTrue(haravapieni.onkoRuudukossa(8, 0));
       assertTrue(haravapieni.onkoRuudukossa(8, 8));       
    }
    
    @Test
    public void oikeanKokoinenRuudukkoM() {
       assertTrue(haravanormi.onkoRuudukossa(0, 0));
       assertTrue(haravanormi.onkoRuudukossa(0, 15));
       assertTrue(haravanormi.onkoRuudukossa(15, 0));
       assertTrue(haravanormi.onkoRuudukossa(15, 15));  
    }
    
    @Test
    public void oikeanKokoinenRuudukkoL() {
       assertTrue(haravasuuri.onkoRuudukossa(0, 0));
       assertTrue(haravasuuri.onkoRuudukossa(21, 21));
       assertTrue(haravasuuri.onkoRuudukossa(21, 21));
       assertTrue(haravasuuri.onkoRuudukossa(21, 21)); 
    }   
    

    @Test
    public void oikeaMiinamaaraPieniRuudukkossa() {
        int[][] pieni = haravapieni.getRuudukko();
        int miinoja = 0;
        for (int x = 0; x < pieni.length; x++) {
            for (int y = 0; y < pieni.length; y++) {
                if (pieni[x][y] == -1) {
                    miinoja++;
                }
            }
        }
        assertEquals(miinoja, 10);
    }
    @Test
    public void oikeaMiinamaaraNormiRuudukkossa() {
        int[][] normi = haravanormi.getRuudukko();
        int miinoja = 0;
        for (int x = 0; x < normi.length; x++) {
            for (int y = 0; y < normi.length; y++) {
                if (normi[x][y] == -1) {
                    miinoja++;
                }
            }
        }
        assertEquals(miinoja, 40);
    }
    
    @Test
    public void oikeaMiinamaaraSuuriRuudukkossa() {
        int[][] suuri = haravasuuri.getRuudukko();
        int miinoja = 0;
        for (int x = 0; x < suuri.length; x++) {
            for (int y = 0; y < suuri.length; y++) {
                if (suuri[x][y] == -1) {
                    miinoja++;
                }
            }
        }
        assertEquals(miinoja, 99);
    }

    @Test
    public void ovatkoRuudukonNumerotOikeinS() {
        int[][] testi = haravapieni.getRuudukko();
        int numero;

        for (int x = 0; x < testi.length; x++) {
            for (int y = 0; y < testi.length; y++) {
                if (testi[x][y] != -1) {
                    numero = testi[x][y];
                    assertEquals(numero, laskeViereisetMiinat(haravapieni, x, y));
                }
            }
        }
    }

    @Test
    public void ovatkoRuudukonNumerotOikeinM() {
        int[][] testi = haravanormi.getRuudukko();
        int numero;

        for (int x = 0; x < testi.length; x++) {
            for (int y = 0; y < testi.length; y++) {
                if (testi[x][y] != -1) {
                    numero = testi[x][y];
                    assertEquals(numero, laskeViereisetMiinat(haravanormi, x, y));
                }
            }
        }
    }

    @Test
    public void ovatkoRuudukonNumerotOikeinL() {
        int[][] testi = haravasuuri.getRuudukko();
        int numero;

        for (int x = 0; x < testi.length; x++) {
            for (int y = 0; y < testi.length; y++) {
                if (testi[x][y] != -1) {
                    numero = testi[x][y];
                    assertEquals(numero, laskeViereisetMiinat(haravasuuri, x, y));
                }
            }
        }
    }

    public int laskeViereisetMiinat(Haravalogiikka h, int x, int y) {

        int miinojaVieressa;
        int[][] testi = h.getRuudukko();

        miinojaVieressa = 0;

        // Yläruudut
        if (h.onkoRuudukossa(x - 1, y - 1) && testi[x - 1][y - 1] == -1) {
            miinojaVieressa++;
        }
        if (h.onkoRuudukossa(x - 1, y) && testi[x - 1][y] == -1) {
            miinojaVieressa++;
        }
        if (h.onkoRuudukossa(x - 1, y + 1) && testi[x - 1][y + 1] == -1) {
            miinojaVieressa++;
        }
        // Sivuruudut
        if (h.onkoRuudukossa(x, y - 1) && testi[x][y - 1] == -1) {
            miinojaVieressa++;
        }
        if (h.onkoRuudukossa(x, y + 1) && testi[x][y + 1] == -1) {
            miinojaVieressa++;
        }
        // Alaruudut
        if (h.onkoRuudukossa(x + 1, y - 1) && testi[x + 1][y - 1] == -1) {
            miinojaVieressa++;
        }
        if (h.onkoRuudukossa(x + 1, y) && testi[x + 1][y] == -1) {
            miinojaVieressa++;
        }
        if (h.onkoRuudukossa(x + 1, y + 1) && testi[x + 1][y + 1] == -1) {
            miinojaVieressa++;
        }
        return miinojaVieressa;
    }
    /*
     * private String lintu1 = "nokka"; private String lintu2 = "nokka";
     *
     * // Harjoitustesti @Test public void hello() { assertTrue(2+1==3); } //
     * Harjoitustesti @Test public void samatStringitBoolean() {
     * assertTrue(lintu1 == lintu2); } // Harjoitustesti @Test public void
     * samatStringitObject() { assertEquals(lintu1, lintu2); }
     *
     */
}
