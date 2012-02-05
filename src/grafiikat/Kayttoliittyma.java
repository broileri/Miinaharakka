package grafiikat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import toimintalogiikka.Haravalogiikka;
import toimintalogiikka.Ruutu;

// TO DO
// High score?, lippumittari, kuvat liputetuille miinoille?, kuolleen harakan parantelu,
// ajastinjutut, jos vinokulmasta aukeaa nolla, avaa sen ympäristökin
// Selvitä bugit: joskus ei aukea kunnolla avatessa tyhjää 64
/**
 * Luokka luo peli-ikkunan ja suorittaa pelin ja käyttäjän väliseen
 * kanssakäyntiin liittyviä operaatioita.
 *
 * @author Broileri
 */
public class Kayttoliittyma extends JFrame {

    // Napit ja muut
    private JButton iso, pieni, normi, reset;
    private HashMap<String, JButton> napukat;
    private int pelinKoko, ax, yy;
    private int[] xAuki, yAuki;
    private Ajastin aika;
    private ImageIcon harakka, hkummastus, hvoitto, hkuolema, miina, lippu, kysymys,
            yksi, kaksi, kolme, nelja, viisi, kuusi, seitseman, kahdeksan, boom;
    private Haravalogiikka logiikka;
    private JPanel ruudukkopaneeli;
    private boolean onkoEkaKrt = true;

    /**
     * Konstruktori luo pelissä tarvittavat ikonit ja nappulat.
     *
     * Lisäksi se kutsuu metodia, joka luo valikkonapeille tapahtumakuuntelijat
     * ja metodia, joka rakentaa varsinaisen peli-ikkunan.
     *
     * Lopuksi konstruktori laittaa peli-ikkunaan otsikon ja tekee siitä
     * loppuvan ja näkyvän.
     *
     * @see grafiikat.Kayttoliittyma#tapahtumakuuntelijatValikolle()
     * @see grafiikat.Kayttoliittyma#aloitaPeli()
     */
    public Kayttoliittyma() {

        // Nappi-ikonit
        harakka = new ImageIcon(getClass().getResource("Kuvat/miinaharakka2.png"));
        hkummastus = new ImageIcon(getClass().getResource("Kuvat/miinaharakkaiik2.png"));
        hvoitto = new ImageIcon(getClass().getResource("Kuvat/miinaharakkajee2.png"));
        hkuolema = new ImageIcon(getClass().getResource("Kuvat/miinaharakkakuol2.png"));
        miina = new ImageIcon(getClass().getResource("Kuvat/miina2.png"));
        lippu = new ImageIcon(getClass().getResource("Kuvat/lippu.png"));
        kysymys = new ImageIcon(getClass().getResource("Kuvat/kysymys.png"));
        yksi = new ImageIcon(getClass().getResource("Kuvat/yksi.png"));
        kaksi = new ImageIcon(getClass().getResource("Kuvat/kaksi.png"));
        kolme = new ImageIcon(getClass().getResource("Kuvat/kolme.png"));
        nelja = new ImageIcon(getClass().getResource("Kuvat/nelja.png"));
        viisi = new ImageIcon(getClass().getResource("Kuvat/viisi.png"));
        kuusi = new ImageIcon(getClass().getResource("Kuvat/kuusi.png"));
        seitseman = new ImageIcon(getClass().getResource("Kuvat/seitseman.png"));
        kahdeksan = new ImageIcon(getClass().getResource("Kuvat/kahdeksan.png"));
        boom = new ImageIcon(getClass().getResource("Kuvat/rajahdys.png"));

        // Nappulat
        reset = new JButton(harakka);
        reset.setPressedIcon(hkummastus);
        reset.setFocusPainted(false);
        pieni = new JButton("S");
        pieni.setFocusPainted(false);
        normi = new JButton("M");
        normi.setFocusPainted(false);
        iso = new JButton("L");
        iso.setFocusPainted(false);
        //napukat = new HashMap<String, JButton>();
        pelinKoko = 9;

        // Tapahtumakuuntelijoiden asettaminen
        tapahtumakuuntelijatValikolle();

        // Peli-ikkunan luonti        
        aloitaPeli();

        // Asetukset
        this.setTitle("Miinaharakka"); // Otsikko \o/        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Osaa loppua
        this.setVisible(true); // Olio näkyviin
    }

    /**
     * Metodi aloittaa uuden pelin. Se luo uuden menu-paneelin
     * teeYlapaneeli()-metodin avulla ja JButton-HashMapin, johon
     * teeRuudukkopaneeli() tallentaa ruudukkonapit. Lopuksi aloitaPeli() lisää
     * luodulle peliruudukolle hiirikuuntelijat ja luo uuden ilmentymän
     * Haravalogiikasta.
     *
     * @see grafiikat.Kayttoliittyma#teeRuudukkopaneeli()
     * @see grafiikat.Kayttoliittyma#teeYlapaneeli()
     * @see toimintalogiikka.Haravalogiikka#Haravalogiikka(int)
     */
    private void aloitaPeli() {

        napukat = new HashMap<String, JButton>();

        if (onkoEkaKrt) {
            onkoEkaKrt = false;
            ruudukkopaneeli = teeRuudukkopaneeli();

            // Paneelien lisääminen pelikenttään        
            this.setLayout(new BorderLayout(10, 10));
            this.add("Center", ruudukkopaneeli);
            this.add("North", teeYlapaneeli());
            this.setBounds(300, 300, 200, 200);
            this.setResizable(false);
            this.setVisible(true);

        } else {
            this.remove(ruudukkopaneeli);
            ruudukkopaneeli = teeRuudukkopaneeli();
            this.add("Center", ruudukkopaneeli);
        }
        this.pack();

        for (int i = 0; i < pelinKoko * pelinKoko; i++) {
            hiirikuuntelijatRuudukolle(i);
        }
        logiikka = new Haravalogiikka(pelinKoko);
    }

    /**
     * Tätä metodia kutsutaan, kun pelaaja voittaa pelin. Se asettaa reset-napin
     * kuvaksi voitokkaan harakan ja pysäyttää ajastimen. Lopuksi metodi
     * paljastaa koko pelikentän.
     *
     * @see grafiikat.Ajastin#Ajastin(JPanel)
     */
    public void voittoKotiin() {
        reset.setIcon(hvoitto);
        aika.seis();
        paljastaKentta(1000);
    }

    /**
     * Tätä metodia kutsutaan, kun pelaaja häviää pelin. Se asettaa reset-napin
     * kuvaksi kuolleen harakan ja pysäyttää ajastimen. Lopuksi metodi paljastaa
     * koko pelikentän.
     *
     * @see grafiikat.Ajastin#Ajastin(JPanel)
     */
    public ImageIcon tappio(int avain) {
        reset.setIcon(hkuolema);
        aika.seis();
        return paljastaKentta(avain);
    }

    /**
     * Paljastaa koko pelialueen miinat, tyhjät ja numerot.
     */
    public ImageIcon paljastaKentta(int avainJosTappio) {

        int[][] miinakentta = logiikka.getRuudukko();

        for (int i = 0; i < pelinKoko * pelinKoko; i++) {

            String kohta = Integer.toString(i);
            haeIndeksi(i);
            int mikaRuutu = miinakentta[ax][yy];

            // Miina 
            if (mikaRuutu == -1) {
                napukat.get(kohta).setIcon(miina);
                napukat.get(kohta).setDisabledIcon(miina);
            } // Numero
            else {
                napukat.get(kohta).setIcon(numeroruudunAvaus(mikaRuutu));
                napukat.get(kohta).setDisabledIcon(numeroruudunAvaus(mikaRuutu));
            }
            napukat.get(kohta).setEnabled(false);
        }
        if (avainJosTappio != 1000) {
            return boom;
        }
        return null;
    }

    /**
     * Tarkastaa, onko peli voitettu.
     */
    public void tilannekatsaus() {

        // Muuttujasta ehkä luokkakohtainen...?
        int auki = 0;
        for (int i = 0; i < pelinKoko * pelinKoko; i++) {
            if (!napukat.get(Integer.toString(i)).isEnabled()) {
                auki++;
            }
        }
        if (logiikka.getMiinamaara() == pelinKoko * pelinKoko - auki) {
            voittoKotiin();
        }
    }

    /**
     * Kun pelaaja avaa tyhjän kenttäruudun, myös kaikki sitä ympäröivät tyhjät
     * painikkeet avautuvat. Tämä metodi avaa tyhjän alueen reunoilta numerot
     * piilottavat painikkeet ja asettaa sen jälkeen avattujen painikkeiden
     * disabled-ikoniksi oikean numeron.
     *
     * @param x Tyhjien ruutujen x-koordinaatit.
     * @param y Tyhjien ruutujen y-koordinaatit.
     */
    public void avaaTyhjienReunat(int[] x, int[] y) {

        int[][] miinakentta = logiikka.getRuudukko();
        int[] hashMapIndeksit = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            hashMapIndeksit[i] = -9;
        }
        int indeksi = 0;

        for (int i = 0; i < x.length; i++) {

            if (x[i] != -9) {

                // Ylä
                if (logiikka.onkoRuudukossa(x[i] - 1, y[i] - 1)) {
                    if (miinakentta[x[i] - 1][y[i] - 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i] - 1, y[i] - 1);
                        //HUOM - virhe x[] ja y[] saattavat hakea indeksistä -1!!!
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(x[i] - 1, y[i])) {
                    if (miinakentta[x[i] - 1][y[i]] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i] - 1, y[i]);
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(x[i] - 1, y[i] + 1)) {
                    if (miinakentta[x[i] - 1][y[i] + 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i] - 1, y[i] + 1);
                        indeksi++;
                    }
                }
                // Sivut
                if (logiikka.onkoRuudukossa(x[i], y[i] - 1)) {
                    if (miinakentta[x[i]][y[i] - 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i], y[i] - 1);
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(x[i], y[i] + 1)) {
                    if (miinakentta[x[i]][y[i] + 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i], y[i] + 1);
                        indeksi++;
                    }
                }
                // Ala
                if (logiikka.onkoRuudukossa(x[i] + 1, y[i] - 1)) {
                    if (miinakentta[x[i] + 1][y[i] - 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i] + 1, y[i] - 1);
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(x[i] + 1, y[i])) {
                    if (miinakentta[x[i] + 1][y[i]] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i] + 1, y[i]);
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(x[i] + 1, y[i] + 1)) {
                    if (miinakentta[x[i] + 1][y[i] + 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i] + 1, y[i] + 1);
                        indeksi++;
                    }
                }
            }
        }
        for (int i = 0; i < indeksi; i++) {
            if (hashMapIndeksit[i] != -9) {
                JButton nappi = napukat.get(Integer.toString(hashMapIndeksit[i]));
                haeIndeksi(hashMapIndeksit[i]);
                nappi.setIcon(numeroruudunAvaus(miinakentta[ax][yy]));
                nappi.setEnabled(false);
                nappi.setDisabledIcon(numeroruudunAvaus(miinakentta[ax][yy]));
            }
        }
    }

    /**
     * Metodi palauttaa oikeanlaisen kuvan käyttäjän avaamaan ruutuun ja kutsuu
     * tarvittaessa tyhjien ruutujen ja numeroruutujen avaamiseen tarkoitettuja
     * metodeja.
     *
     * @param indeksi Annetun napin HashMap-avain.
     * @param nappi Käyttäjän painama nappi.
     * @return Ikoni (kuva tai tyhjä) avattuun ruutuun.
     *
     * @see grafiikat.Kayttoliittyma#avaaTyhjienReunat(int[],int[])
     * @see grafiikat.Kayttoliittyma#numeroruudunAvaus(int)
     */
    private ImageIcon asetaKuvaAvattuun(int indeksi, JButton nappi) {
        int[][] miinakentta = logiikka.getRuudukko();
        haeIndeksi(indeksi);
        int mikaRuutu = miinakentta[ax][yy];

        // Miinan avaus
        if (mikaRuutu == -1) {
            return tappio(indeksi);

            // Tyhjän ruudun avaus    
        } else if (mikaRuutu == 0) {
            logiikka.etsiTyhjat(miinakentta, (new Ruutu(ax, yy)));
            // Tyhjien koordinaatit
            xAuki = logiikka.getTyhjatX();
            yAuki = logiikka.getTyhjatY();

            // Tyhjien avaaminen ja int-taulukoiden tyhjien kenttien (-9) poissulkeminen
            for (int i = 0; i < xAuki.length; i++) {
                if (xAuki[i] != -9 && yAuki[i] != -9) {
                    int nappiIndeksi = teeIndeksi(xAuki[i], yAuki[i]);
                    napukat.get(Integer.toString(nappiIndeksi)).setEnabled(false);
                }
            }
            // Tyhjien ruutujen reunojen avaus
            avaaTyhjienReunat(xAuki, yAuki);
        } // Numeroruudun avaus 
        else {
            return numeroruudunAvaus(mikaRuutu);
        }
        return null;
    }

    /**
     * Metodi määrittää, minkä numerokuvan sitä kutsuva asetaKuvaRuutuun()-
     * metodi palauttaa avatun napin ikoniksi.
     *
     * @param mikaRuutu Avatussa ruudussa oleva lähiruutujen miinamäärää
     * ilmoittava numero.
     *
     * @return Numeroikoni.
     */
    public ImageIcon numeroruudunAvaus(int mikaRuutu) {

        if (mikaRuutu == 1) {
            return yksi;
        } else if (mikaRuutu == 2) {
            return kaksi;
        } else if (mikaRuutu == 3) {
            return kolme;
        } else if (mikaRuutu == 4) {
            return nelja;
        } else if (mikaRuutu == 5) {
            return viisi;
        } else if (mikaRuutu == 6) {
            return kuusi;
        } else if (mikaRuutu == 7) {
            return seitseman;
        } else if (mikaRuutu == 7) {
            return kahdeksan;
        }
        return null;
    }

    /**
     * Metodi muuttaa JButton-HashMapin avaimen int[]-indekseiksi
     *
     * @param indeksi HashMap-avain
     */
    public void haeIndeksi(int indeksi) {

        int kentanRuutu = 0;

        for (int x = 0; x < pelinKoko; x++) {
            for (int y = 0; y < pelinKoko; y++) {
                if (kentanRuutu == indeksi) {
                    ax = x;
                    yy = y;
                    return;
                }
                kentanRuutu++;
            }
        }
    }

    /**
     * int[]-indeksien muuttaminen JButton-HashMapin avaimeksi
     *
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     * @return HashMap-avain
     */
    public int teeIndeksi(int x, int y) {

        return (x + y + x * (pelinKoko - 1));
    }

    /**
     * Metodi luo peliruudukolle hiirikuuntelijat.
     *
     * @param indeksi JButton-HashMapin avain
     */
    private void hiirikuuntelijatRuudukolle(final int indeksi) {

        final JButton nappula = napukat.get(Integer.toString(indeksi));

        nappula.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                // Jos ruudussa ei lippua ja HIIRI1, se aukeaa
                if (e.getButton() == MouseEvent.BUTTON1
                        && nappula.getIcon() != lippu && nappula.isEnabled()) {

                    nappula.setIcon(asetaKuvaAvattuun(indeksi, nappula));
                    nappula.setEnabled(false);
                    nappula.setDisabledIcon(asetaKuvaAvattuun(indeksi, nappula));
                    tilannekatsaus();

                } // Jos ruutu tyhjä ja HIIRI3, niin ruutuun lippu
                else if (e.getButton() == MouseEvent.BUTTON3 && nappula.getIcon() == null) {
                    nappula.setIcon(lippu);

                } // Jos ruudussa lippu ja HIIRI3, ruutuun ? 
                else if (e.getButton() == MouseEvent.BUTTON3 && nappula.getIcon() == lippu) {
                    nappula.setIcon(kysymys);
                } // Jos ruudussa ? ja HIIRI3, ruutu tyhjäksi
                else if (e.getButton() == MouseEvent.BUTTON3 && nappula.getIcon() == kysymys) {
                    nappula.setIcon(null);
                }

            }
        });
    }

    /**
     * Metodi luo valikon napeille tapahtumakuuntelijat.
     */
    private void tapahtumakuuntelijatValikolle() {

        reset.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        reset.setIcon(harakka);
                        aika.seis();
                        aika.resetoi();
                        // Lisää tähän vielä uuden pelin luonti
                        aloitaPeli();
                    }
                });

        pieni.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        aika.seis();
                        aika.resetoi();
                        pelinKoko = 9;
                        aloitaPeli();
                    }
                });

        normi.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        aika.seis();
                        aika.resetoi();
                        pelinKoko = 16;
                        aloitaPeli();
                    }
                });

        iso.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        aika.seis();
                        aika.resetoi();
                        pelinKoko = 22;
                        aloitaPeli();
                    }
                });
    }

    /**
     * Metodi luo peliruudukkopaneelin ja palauttaa sen. Metodia käyttää
     * aloitaPeli().
     *
     * @return ruudukkopaneeli
     * @see grafiikat.Kayttoliittyma#aloitaPeli()
     */
    private JPanel teeRuudukkopaneeli() {

        ruudukkopaneeli = new JPanel(new GridLayout(pelinKoko, pelinKoko));
        teeNappeja(pelinKoko * pelinKoko);
        for (int i = 0; i < napukat.size(); i++) {
            String avain = Integer.toString(i);
            napukat.get(avain).setEnabled(true);
            ruudukkopaneeli.add(napukat.get(avain));
        }
        return ruudukkopaneeli;
    }

    /**
     * Metodi luo valikkopaneelin ja palauttaa sen.
     *
     * @return yläpaneeli
     * @see grafiikat.Kayttoliittyma#aloitaPeli()
     */
    private JPanel teeYlapaneeli() {

        JPanel ylapaneeli = new JPanel(new FlowLayout());
        ylapaneeli.add(pieni);
        ylapaneeli.add(normi);
        ylapaneeli.add(iso);
        aika = new Ajastin(ylapaneeli);
        ylapaneeli.add(reset);

        return ylapaneeli;
    }

    /**
     * Metodi tekee annetun määrän JButtoneita HashMapiin.
     *
     * @param lkm Haluttu JButtonien määrä
     */
    private void teeNappeja(int lkm) {
        String avain;
        JButton nappula;

        for (int i = 0; i < lkm; i++) {
            avain = Integer.toString(i);
            nappula = new JButton();
            nappula.setPreferredSize(new Dimension(30, 30));
            nappula.setFocusPainted(false);
            napukat.put(avain, nappula);
        }
    }

    /**
     * Metodi palauttaa pelinKoko-muuttujan.
     *
     * @return pelinKoko
     */
    public int getPelinKoko() {
        return pelinKoko;
    }

    /**
     * Metodi palauttaa JButton-HashMapin.
     *
     * @return napukat
     */
    public HashMap getNapit() {
        return napukat;
    }
}
