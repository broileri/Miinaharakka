package toimintalogiikka;

import java.util.Random;

public class Haravalogiikka {

    private int[][] miinakentta;
    private int[] miinatX;
    private int[] miinatY;

    public Haravalogiikka(int koko) {
        luoRuudukko(koko);
        laitaMiinatRuudukkoon(koko);
        laitaNumerotRuudukkoon();
    }

    // Luo uuden ruudukon ja täyttää sen tyhjillä ruuduilla
    private void luoRuudukko(int koko) {

        miinakentta = new int[koko][koko];

        for (int x = 0; x < koko; x++) {
            for (int y = 0; y < koko; y++) {
                miinakentta[x][y] = 0; // 0 tarkoittaa tyhjää ruutua
            }
        }
    }

    // Laskee miinamäärän, laittaa miinat ruudukkoon ja tallentaa niiden sijainnin
    private void laitaMiinatRuudukkoon(int koko) {        
        
        // Miinamäärä
        int miinamaara;

        if (koko == 9) {
            miinamaara = 10;
        } else if (koko == 16) {
            miinamaara = 40;
        } else {
            miinamaara = 99;
        }
        
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

    // Ympäröi miinat numeroilla
    private void laitaNumerotRuudukkoon() {
        int x, y;
        
        for (int i = 0; i < miinatX.length; i++) {
            x = miinatX[i];
            y = miinatY[i];
            
            // Yläruudut
            if (onkoRuudukossa(x-1, y-1) && miinakentta[x-1][y-1] != -1) {
                miinakentta[x-1][y-1] += 1;
            }
            if (onkoRuudukossa(x-1, y) && miinakentta[x-1][y] != -1) {
                miinakentta[x-1][y] += 1;
            }
            if (onkoRuudukossa(x-1, y+1) && miinakentta[x-1][y+1] != -1) {
                miinakentta[x-1][y+1] += 1;
            }
            // Sivuruudut
            if (onkoRuudukossa(x, y-1) && miinakentta[x][y-1] != -1) {
                miinakentta[x][y-1]++;
            }
            if (onkoRuudukossa(x, y+1) && miinakentta[x][y+1] != -1) {
                miinakentta[x][y+1]++;
            }
            // Alaruudut
            if (onkoRuudukossa(x+1, y-1) && miinakentta[x+1][y-1] != -1) {
                miinakentta[x+1][y-1]++;
            }
            if (onkoRuudukossa(x+1, y) && miinakentta[x+1][y] != -1) {
                miinakentta[x+1][y]++;
            }
            if (onkoRuudukossa(x+1, y+1) && miinakentta[x+1][y+1] != -1) {
                miinakentta[x+1][y+1]++;
            }
        }        
    }
    
    // Tutkii, onko ruudukossa kohtaa [x, y]
    public boolean onkoRuudukossa(int x, int y) {
        
        if ( x <= (miinakentta.length-1) && y <= (miinakentta.length-1)
                && x >= 0 && y >= 0) {
            return true;
        }
        return false;
    }
   
    
    // Testimetodi, jolla voi kokeilla, onko ruudukko oikeanlainen
    private void testaaRuudukkoa() {
        
        // Miltä ruudukko näyttää
        String testi = "";
         for (int i = 0; i < miinakentta.length; i++) {
             for (int j = 0; j < miinakentta.length; j++) {
                 testi += miinakentta[i][j] + " ";
                 if (miinakentta[i][j] != -1) {
                     testi += " ";
                 }
                 if (j == miinakentta.length-1) {
                     testi += "\n";
                 }
             }
         }
         System.out.println(testi);
         
         // Miinojen sijainnit (x, y)-koordinaatteina
         System.out.println("Miinat ovat kohdissa:");         
         String kohdat = "";
         
         for (int m = 0; m < miinatX.length;  m++) {
             kohdat += "(" + miinatX[m] + ", " + miinatY[m] + ")";
         }         
         System.out.println(kohdat);
         
         // Toimiiko onkoRuudukossa(x, y)
         System.out.println("False: " + onkoRuudukossa(9,0));
         System.out.println("False: " + onkoRuudukossa(-2,3));
         System.out.println("True: " + onkoRuudukossa(8,0));
         System.out.println("True: " + onkoRuudukossa(0,8));
    }
    
    public static void main(String[] args) {
        Haravalogiikka testaus = new Haravalogiikka(9);
        testaus.testaaRuudukkoa();
    }
      
    
}
