
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import toimintalogiikka.Haravalogiikka;
import toimintalogiikka.Ruutu;

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
    public void avautuukoTyhjiaOikein() {
        int[][] testi = new int[9][9];
        boolean totta = true;
        
        // matriisi täyteen nollia
        for (int i = 0; i < testi.length; i++) {
            for (int j = 0; j < testi.length; j++) {
                testi[i][j] = 0;
            }
        }        
        // testi-matriisin kolmanneksi ylin rivi -1:iä täyteen
        for (int i = 0; i < testi.length; i++) {
            testi[2][i] = -1;
        }
        // "Klikataan" ~keskelle toisiksi ylintä riviä
        haravapieni.etsiTyhjat(testi, new Ruutu(1, 5));
        
        // Jos kahdelta ensimmäiseltä riviltä löytyy jokin muu arvo kuin 9,
        // totta saa arvon false ja testi feilaa
        for (int i = 0; i < testi.length; i++) {
          if (testi[0][i] != 9 || testi[1][i] != 9) {
              System.out.println(testi[0][i] + " " + testi[1][i]);
              totta = false;
          }  
        }
        assertTrue(totta);
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
