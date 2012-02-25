
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import toimintalogiikka.Haravalogiikka;
import toimintalogiikka.Ruutu;

/**
 * Testiluokka Haravalogiikalle
 * 
 * @author Broileri
 */
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
    public void avautuukoTyhjiaOikeinMyosVinottain() {
        int[][] testi = new int[9][9];
        boolean totta = true;

        // matriisi täyteen nollia
        for (int i = 0; i < testi.length; i++) {
            for (int j = 0; j < testi.length; j++) {
                testi[i][j] = 0;
            }
        }
        // testi-matriisin neljänneksi ylin rivi -1:iä täyteen
        for (int i = 0; i < testi.length; i++) {
            testi[3][i] = -1;
        }
        // testi-matriisin kolmannelle riville joka toinen ruutu täyteen -1:iä
        for (int i = 0; i < testi.length; i++) {
            if (i % 2 != 0) {
                testi[2][i] = -1;
            }
        }
        // testimatriisin toiselle riville joka toinen ruutu,
        // jolla parillinen indeksi täyteen -1:iä
        for (int i = 0; i < testi.length; i++) {
            if (i % 2 == 0) {
                testi[1][i] = -1;
            }
        }     
        // "Klikataan" ~keskelle ylintä riviä
        haravapieni.etsiTyhjat(testi, new Ruutu(0, 5));      

        // Jos ruuduista, joiden nyt pitäisi olla auki löytyy jokin muu arvo kuin 9,
        // totta saa arvon false ja testi feilaa
        // 1. rivi, putäisi olla kokonaan auki
        for (int i = 0; i < testi.length; i++) {
            if (testi[0][i] != 9) {
                totta = false;
            }
        }  
        // 2. rivi, parittomien indeksien pitäisi olla auki
        for (int i = 0; i < testi.length; i++) {
            if (i%2 != 0 && testi[1][i] != 9) {
                totta = false;
            }
        }  
        // 3. rivi, parillisten indeksien pitäisi olla auki
        for (int i = 0; i < testi.length; i++) {
            if (i%2 == 0 && testi[2][i] != 9) {
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
        assertTrue(!haravapieni.onkoRuudukossa(9, 9));
    }

    @Test
    public void oikeanKokoinenRuudukkoM() {
        assertTrue(haravanormi.onkoRuudukossa(0, 0));
        assertTrue(haravanormi.onkoRuudukossa(0, 15));
        assertTrue(haravanormi.onkoRuudukossa(15, 0));
        assertTrue(haravanormi.onkoRuudukossa(15, 15));
        assertTrue(!haravanormi.onkoRuudukossa(16, 16));
    }

    @Test
    public void oikeanKokoinenRuudukkoL() {
        assertTrue(haravasuuri.onkoRuudukossa(0, 0));
        assertTrue(haravasuuri.onkoRuudukossa(21, 21));
        assertTrue(haravasuuri.onkoRuudukossa(21, 21));
        assertTrue(haravasuuri.onkoRuudukossa(21, 21));
        assertTrue(!haravasuuri.onkoRuudukossa(22, 22));
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

    /**
     * Apumetodi testeille, jotka tarkastavat ruudukossa olevia numeroita.
     * @param h Testattava Haravalogiikka-ilmentymä
     * @param x Koordinaatti
     * @param y Koordinaatti
     * @return Numeroa ympäröivien miinojen lukumäärä
     */
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
}
