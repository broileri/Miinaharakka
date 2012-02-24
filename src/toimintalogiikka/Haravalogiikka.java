package toimintalogiikka;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Luokka luo pelikentän ja sijainnit pelikentällä oleville miinoille, numeroille ja tyhjille
 * ruuduille.
 *
 * @author Broileri
 */
public class Haravalogiikka {

    private int[][] miinakentta;   
    private Ruutu[] tyhjat;
    private int ruudukonSivu;

    /**
     * Luo uuden pelin.
     *
     * @param koko Ruudukon sivun pituus, joka on 9, 16 tai 22.
     *
     * @see toimintalogiikka.Haravalogiikka#luoRuudukko(int)
     * @see toimintalogiikka.Haravalogiikka#laitaMiinatRuudukkoon(int, int)
     * @see toimintalogiikka.Haravalogiikka#laitaNumerotRuudukkoon()
     * @see toimintalogiikka.Haravalogiikka#maaritaMiinamaara(int)
     */
    public Haravalogiikka(int koko) {
        ruudukonSivu = koko;
        luoRuudukko(koko);
        //laitaMiinatRuudukkoon(koko, maaritaMiinamaara(koko));
        laitaNumerotRuudukkoon(laitaMiinatRuudukkoon(koko, maaritaMiinamaara(koko)));
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
     * Laittaa miinoja ruudukkoon sattumanvaraisiin kohtiin ja tallettaa
     * miinojen sijainnit.
     *
     * @param koko Ruudukon sivun pituus.
     * @param miinamaara Miinojen lukumäärä.
     */
    private Ruutu[] laitaMiinatRuudukkoon(int koko, int miinamaara) {
        
        Ruutu[] miinat = new Ruutu[miinamaara];

        // Miinojen sijainnin arvonta ja asettaminen
        Random arpoja = new Random();
        int talletusindeksi = 0;
        for (int i = miinamaara; i > 0; i--) {
            
            while (true) {
                // Pitää huolen, ettei samaan kohtaan tule kahdesti miinaa
                int x = arpoja.nextInt(koko - 1);
                int y = arpoja.nextInt(koko - 1);
                if (miinakentta[x][y] == 0) {
                    miinakentta[x][y] = -1; // -1 tarkoittaa miinaa
                    miinat[talletusindeksi] = new Ruutu(x, y);
                    talletusindeksi++;
                    break;
                }
            }
        }
        return miinat;
    }

    /**
     * Ympyröi miinat numeroilla niin, että jokaista numeroa ympyröi numeron
     * osoittaman määrän verran miinoja. Pitää myös huolen siitä, ettei yritä
     * laittaa numeroita ruudukon ulkopuolelle.
     *
     * @see toimintalogiikka.Haravalogiikka#onkoRuudukossa(int, int)
     */
    private void laitaNumerotRuudukkoon(Ruutu[] miinat) {
        
        int x, y;
        
        for (int i = 0; i < miinat.length; i++) {
            x = miinat[i].getX();
            y = miinat[i].getY();

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
     * @return true, jos ruudukossa on parametrien mukainen sijainti. Muuten
     * false.
     */
    public boolean onkoRuudukossa(int x, int y) {

        if (x <= (miinakentta.length - 1) && y <= (miinakentta.length - 1)
                && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Palauttaa tämänhetkisen miinakenttäruudukon, jossa miinojen, tyhjien,
     * avattujen ja numeroruutujen sijainnit.
     *
     * @return
     */
    public int[][] getRuudukko() {
        return miinakentta;
    }

    /**
     * Palauttaa taulukon tyhjistä ruuduista.
     * @return 
     */
    public Ruutu[] getTyhjat() {
        return tyhjat;
    }

    /**
     * Palauttaa miinamäärän
     */
    public int getMiinamaara() {
        return (maaritaMiinamaara(ruudukonSivu));
    }
 
    /**
     * Jos annettu ruutu on tyhjä, metodi merkitsee sen ja sitä ympäröivät
     * tyhjät ruudut yhdeksiköillä. 9 tarkoittaa tyhjää, avattavaa ruutua.
     * Flood fill -periaate.
     *
     * @param miinakentta Miinakenttä, int[][]
     * @param r Ruutu-apuluokkaa käyttäen annettu ruudun sijainti.
     *
     * @see toimintalogiikka.Haravalogiikka#onkoRuudukossa(int, int)
     */
    public void etsiTyhjat(int[][] miinakentta, Ruutu r) {

        int index = 0, koko = miinakentta.length - 1;        
        tyhjat = new Ruutu[koko*koko];

        for (int i = 0; i < tyhjat.length; i++) {            
            tyhjat[i] = new Ruutu(-9, -9); // -9 tarkoittaa paikkaa, johon ei ole tallennettu mitään
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
                tyhjat[index].setX(x);
                tyhjat[index].setY(y);
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
                if (onkoRuudukossa(x - 1, y - 1)) {
                    jono.add(new Ruutu(x - 1, y - 1));
                }
                if (onkoRuudukossa(x - 1, y + 1)) {
                    jono.add(new Ruutu(x - 1, y + 1));
                }
                if (onkoRuudukossa(x + 1, y - 1)) {
                    jono.add(new Ruutu(x + 1, y - 1));
                }
                if (onkoRuudukossa(x + 1, y + 1)) {
                    jono.add(new Ruutu(x + 1, y + 1));
                }
            }
        }
    }  
}
