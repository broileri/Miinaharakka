package toimintalogiikka;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Luokka luo sijainnit pelikentällä oleville miinoille, numeroille ja tyhjille
 * ruuduille.
 * @author Broileri
 */
public class Haravalogiikka {
   
    private int[][] miinakentta;
    private int[] miinatX, miinatY;
    private int[] tyhjatY, tyhjatX;
    private int ruudukonSivu;
    

    /**
     * Luo uudet pelisijainnit.
     * 
     * @param koko Annettu koko, joka on 9, 16 tai 22.
     * 
     * @see toimintalogiikka.Haravalogiikka#luoRuudukko(int)
     * @see toimintalogiikka.Haravalogiikka#laitaMiinatRuudukkoon(int, maaritaMiinamaara(int))
     * @see toimintalogiikka.Haravalogiikka#laitaNumerotRuudukkoon()
     * @see toimintalogiikka.Haravalogiikka#maaritaMiinamaara(int)
     */
    public Haravalogiikka(int koko) {
        ruudukonSivu = koko;
        luoRuudukko(koko);
        laitaMiinatRuudukkoon(koko, maaritaMiinamaara(koko));
        laitaNumerotRuudukkoon();
        testaaRuudukkoa();
    }

    
    /**
     * Luo uuden halutun kokoisen ruudukon ja täyttää sen nollilla. Nolla
     * tarkoittaa tyhjää ruutua.
     * 
     * @param koko Pelialueen sivun pituus.
     */
    private void luoRuudukko(int koko) {

        miinakentta = new int[koko][koko];

        for (int x = 0; x < koko; x++) {
            for (int y = 0; y < koko; y++) {
                miinakentta[x][y] = 0; // 0 tarkoittaa tyhjää ruutua
            }
        }
    }

    /**
     * Määrittää miinojen lukumäärän ruudukon kokoon perustuen.
     * 
     * @param koko Ruudukon sivun pituus.
     * 
     * @return Miinojen lukumäärä.
     */
    private int maaritaMiinamaara(int koko) {

        int miinamaara;

        if (koko == 9) {
            miinamaara = 10;
        } else if (koko == 16) {
            miinamaara = 40;
        } else {
            miinamaara = 99;
        }
        return miinamaara;
    }

    /**
     * Laittaa miinoja ruudukkoon sattumanvaraisiin kohtiin
     * ja tallettaa miinojen sijainnit.
     * 
     * @param koko Ruudukon sivun pituus.
     * @param miinamaara Miinojen lukumäärä.
     */
    private void laitaMiinatRuudukkoon(int koko, int miinamaara) {

        miinatX = new int[miinamaara];
        miinatY = new int[miinamaara];

        // Miinojen sijainnin arvonta ja asettaminen
        Random arpoja = new Random();
        int talletusindeksi = 0;
        for (int i = miinamaara; i > 0; i--) {
            while (true) { // Pitää huolen, ettei samaan kohtaan tule kahdesti miinaa
                int x = arpoja.nextInt(koko - 1);
                int y = arpoja.nextInt(koko - 1);
                if (miinakentta[x][y] == 0) {
                    miinakentta[x][y] = -1; // -1 tarkoittaa miinaa
                    miinatX[talletusindeksi] = x;
                    miinatY[talletusindeksi] = y;
                    talletusindeksi++;
                    break;
                }
            }
        }
    }

    /**
     * Ympyröi miinat numeroilla niin, että jokaista numeroa ympyröi numeron
     * osoittaman määrän verran miinoja. Pitää myös huolen siitä, ettei yritä
     * laittaa numeroita ruudukon ulkopuolelle.
     * 
     * @see toimintalogiikka.Haravalogiikka#onkoRuudukossa(int, int)
     */
    private void laitaNumerotRuudukkoon() {
        int x, y;

        for (int i = 0; i < miinatX.length; i++) {
            x = miinatX[i];
            y = miinatY[i];

            // Yläruudut
            if (onkoRuudukossa(x - 1, y - 1) && miinakentta[x - 1][y - 1] != -1) {
                miinakentta[x - 1][y - 1] += 1;
            }
            if (onkoRuudukossa(x - 1, y) && miinakentta[x - 1][y] != -1) {
                miinakentta[x - 1][y] += 1;
            }
            if (onkoRuudukossa(x - 1, y + 1) && miinakentta[x - 1][y + 1] != -1) {
                miinakentta[x - 1][y + 1] += 1;
            }
            // Sivuruudut
            if (onkoRuudukossa(x, y - 1) && miinakentta[x][y - 1] != -1) {
                miinakentta[x][y - 1]++;
            }
            if (onkoRuudukossa(x, y + 1) && miinakentta[x][y + 1] != -1) {
                miinakentta[x][y + 1]++;
            }
            // Alaruudut
            if (onkoRuudukossa(x + 1, y - 1) && miinakentta[x + 1][y - 1] != -1) {
                miinakentta[x + 1][y - 1]++;
            }
            if (onkoRuudukossa(x + 1, y) && miinakentta[x + 1][y] != -1) {
                miinakentta[x + 1][y]++;
            }
            if (onkoRuudukossa(x + 1, y + 1) && miinakentta[x + 1][y + 1] != -1) {
                miinakentta[x + 1][y + 1]++;
            }
        }
    }

    /**
     * Tutkii, onko ruudukossa sijaintia (x, y).
     * 
     * @param x x-koordinaatti 
     * @param y y-koordinaatti
     * @return  true, jos ruudukossa on parametrien mukainen sijainti. Muuten false.
     */
    public boolean onkoRuudukossa(int x, int y) {

        if (x <= (miinakentta.length - 1) && y <= (miinakentta.length - 1)
                && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Palauttaa tämänhetkisen miinakenttäruudukon, jossa
     * miinojen, tyhjien, avattujen ja numeroruutujen sijainnit.
     * @return 
     */
    public int[][] getRuudukko() {
        return miinakentta;
    }
    
    /**
     * Palauttaa tyhjien ruutujen x-koordinaatit.
     * @return 
     */
    public int[] getTyhjatX() {
        return tyhjatX;
    }
    
    /**
     * Palauttaa tyhjien ruutujen y-koordinaatit.
     * @return 
     */
    public int[] getTyhjatY() {
        return tyhjatY;
    } 
    
    /**
     * Palauttaa miinamäärän
     */
    public int getMiinamaara() {
        return(maaritaMiinamaara(ruudukonSivu));
    }
   

    // Jos annettu ruutu on tyhjä, merkitsee sen ja sitä ympäröivät tyhjät
    // ruudut 9:llä. Flood-fill.
    /**
     * Jos annettu ruutu on tyhjä, metodi merkitsee sen ja sitä ympäröivät
     * tyhjät ruudut yhdeksiköillä. 9 tarkoittaa tyhjää, avattavaa ruutua.
     * Toimii kuin flood fill -algoritmi.     * 
     * 
     * @param miinakentta Miinakenttä, int[][]
     * @param r Ruutu-apuluokkaa käyttäen annettu ruudun sijainti.
     * 
     * @see toimintalogiikka.Haravalogiikka#onkoRuudukossa(int, int)
     */
    public void etsiTyhjat(int[][] miinakentta, Ruutu r) {

        int index = 0, koko = miinakentta.length-1;
        tyhjatX = new int[koko*koko];
        tyhjatY = new int[koko*koko];
        
        for (int i = 0; i < tyhjatX.length; i++) {
            tyhjatX[i] = -9;
            tyhjatY[i] = -9;
        }
        
        Queue<Ruutu> jono = new LinkedList<Ruutu>();
        jono.add(r);

        while (!jono.isEmpty()) {
            Ruutu n = jono.poll();
            int x = n.getX();
            int y = n.getY();
            if (miinakentta[x][y] == 0) {
                miinakentta[x][y] = 9;

                // Avattavien tallennus
                tyhjatX[index] = x;
                tyhjatY[index] = y;                 
                index++;

                if (onkoRuudukossa(x, y - 1)) {
                    jono.add(new Ruutu(x, y - 1));
                }
                if (onkoRuudukossa(x, y + 1)) {
                    jono.add(new Ruutu(x, y + 1));
                }
                if (onkoRuudukossa(x - 1, y)) {
                    jono.add(new Ruutu(x - 1, y));
                }
                if (onkoRuudukossa(x + 1, y)) {
                    jono.add(new Ruutu(x + 1, y));
                }
            }
        }
    }

    // Testimetodi ohjelmoinnin helpottamiseksi
    public void testaaRuudukkoa() {

        // Miltä ruudukko näyttää
        String testi = "";
        for (int i = 0; i < miinakentta.length; i++) {
            for (int j = 0; j < miinakentta.length; j++) {
                testi += miinakentta[i][j] + " ";
                if (miinakentta[i][j] != -1) {
                    testi += " ";
                }
                if (j == miinakentta.length - 1) {
                    testi += "\n";
                }
            }
        }
        System.out.println(testi);

        // Miinojen sijainnit (x, y)-koordinaatteina
        System.out.println("Miinat ovat kohdissa:");
        String kohdat = "";

        for (int m = 0; m < miinatX.length; m++) {
            kohdat += "(" + miinatX[m] + ", " + miinatY[m] + ")";
        }
        System.out.println(kohdat);

        // Toimiiko onkoRuudukossa(x, y)
        System.out.println("False: " + onkoRuudukossa(9, 0));
        System.out.println("False: " + onkoRuudukossa(-2, 3));
        System.out.println("True: " + onkoRuudukossa(8, 0));
        System.out.println("True: " + onkoRuudukossa(0, 8));
    }
}
