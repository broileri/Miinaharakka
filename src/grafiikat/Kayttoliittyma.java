package grafiikat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.*;
import toimintalogiikka.Haravalogiikka;
import toimintalogiikka.Ruutu;

// TO DO
// miinan auetessa merkatut miinat ok:ksi

/**
 * Luokka luo peli-ikkunan ja suorittaa pelin ja käyttäjän väliseen
 * kanssakäyntiin liittyviä operaatioita.
 *
 * @author Broileri
 */
public class Kayttoliittyma extends JFrame {

    // Napit ja muut
    private JButton iso, pieni, normi, reset;
    private HashMap<Integer, JButton> napukat;
    private int pelinKoko, ax, yy, lippumaara;
    private int[] xAuki, yAuki;
    private Ajastin aika;
    private ImageIcon harakka, hkummastus, hvoitto, hkuolema, miina, lippu, kysymys,
            yksi, kaksi, kolme, nelja, viisi, kuusi, seitseman, kahdeksan, boom, okmiina;
    private Haravalogiikka logiikka;
    private JPanel ruudukkopaneeli;
    private boolean onkoEkaKrt = true, ekaKlikkaus;
    private JTextField miinamittari;

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
        lippu = new ImageIcon(getClass().getResource("Kuvat/lippu2.png"));
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
        okmiina = new ImageIcon(getClass().getResource("Kuvat/miinaok2.png"));

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
        pelinKoko = 9;

        // Miinamittari
        miinamittari = new JTextField();

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

        reset.setIcon(harakka);
        napukat = new HashMap<Integer, JButton>();

        // Käynnistyksen yhteydessä säädettävät asetukset
        if (onkoEkaKrt) {
            onkoEkaKrt = false;
            ruudukkopaneeli = teeRuudukkopaneeli();

            // Paneelien lisääminen pelikenttään             
            BorderLayout leiautti = new BorderLayout(10, 10);
            leiautti.setVgap(10);
            JPanel vas = new JPanel();
            vas.setSize(30, WIDTH);
            JPanel oik = new JPanel();
            oik.setSize(40, WIDTH);
            JPanel ala = new JPanel();
            ala.setSize(WIDTH, 15); 
            this.setLayout(leiautti);            

            this.add("West", vas);
            this.add("Center", ruudukkopaneeli);
            this.add("East", oik);
            this.add("South", ala);
            this.add("North", teeYlapaneeli());
            this.setBounds(330, 150, 300, 300);            
            this.setResizable(false);
            this.setVisible(true);
        } 
        // Uusi peli käynnistyksen jälkeen
        else {
            this.remove(ruudukkopaneeli);
            ruudukkopaneeli = teeRuudukkopaneeli();
            this.add("Center", ruudukkopaneeli);
        }
        // Ruudukon hiirikuuntelijoiden asennus
        for (int i = 0; i < pelinKoko * pelinKoko; i++) {
            hiirikuuntelijatRuudukolle(i);
        }
        // Muuttujien "nollaus" ja kaiken kokoon kursiminen
        logiikka = new Haravalogiikka(pelinKoko);
        lippumaara = logiikka.getMiinamaara();
        miinamittari.setText(Integer.toString(lippumaara));
        ekaKlikkaus = true;
        this.pack();        
    }

    /**
     * Tätä metodia kutsutaan, kun pelaaja voittaa pelin. Se asettaa reset-napin
     * kuvaksi voitokkaan harakan ja pysäyttää ajastimen. Lopuksi metodi
     * paljastaa koko pelikentän.
     *
     * @see grafiikat.Ajastin#Ajastin(JPanel)
     */
    public void voitto() {
        reset.setIcon(hvoitto);
        aika.seis();
        paljastaKentta(1000);
        miinamittari.setText("0");
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

            haeIndeksi(i);
            int mikaRuutu = miinakentta[ax][yy];

            // Miina             
            if (mikaRuutu == -1 && avainJosTappio == 1000) {
                napukat.get(i).setIcon(okmiina);
                napukat.get(i).setDisabledIcon(okmiina);
            } 
            else if (mikaRuutu == -1 ) {
                napukat.get(i).setIcon(miina);
                napukat.get(i).setDisabledIcon(miina);
            }// Numero
            else {
                napukat.get(i).setIcon(numeroruudunAvaus(mikaRuutu));
                napukat.get(i).setDisabledIcon(numeroruudunAvaus(mikaRuutu));
            }            
            napukat.get(i).setEnabled(false);
        }
        if (avainJosTappio != 1000) {
            return boom;
        }
        return null;
    }

    /**
     * Tarkastaa, onko peli voitettu. 
     * Jos ruutuja on auki pelin ruutujen määrä - miinamäärä, peli on voitettu.
     */
    private void tilannekatsaus() {

        int auki = 0;
        for (int i = 0; i < pelinKoko * pelinKoko; i++) {
            if (!napukat.get(i).isEnabled()) {
                auki++;
            }
        }
        if (logiikka.getMiinamaara() == pelinKoko * pelinKoko - auki) {
            voitto();
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
        int[] hashMapIndeksit = new int[pelinKoko*pelinKoko*8];
        for (int i = 0; i < pelinKoko*pelinKoko*8; i++) {
            hashMapIndeksit[i] = -9;
        }
        int indeksi = 0;

        for (int i = 0; i < x.length; i++) {

            if (x[i] != -9) {
                                
                // Ylä
                if (logiikka.onkoRuudukossa(x[i] - 1, y[i] - 1)) {
                    if (miinakentta[x[i] - 1][y[i] - 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(x[i] - 1, y[i] - 1);
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
                JButton nappi = napukat.get(hashMapIndeksit[i]);
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
    private ImageIcon asetaKuvaAvattuun(int indeksi) {
        
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
                    napukat.get(nappiIndeksi).setEnabled(false);
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

        final JButton nappula = napukat.get(indeksi);

        nappula.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                // Jos ruudussa ei lippua ja HIIRI1, se aukeaa
                if (e.getButton() == MouseEvent.BUTTON1
                        && nappula.getIcon() != lippu && nappula.isEnabled()) {

                    if (ekaKlikkaus) {
                        ekaKlikkaus = false;
                        aika.aloita();
                    }
                    nappula.setIcon(asetaKuvaAvattuun(indeksi));
                    nappula.setEnabled(false);
                    nappula.setDisabledIcon(asetaKuvaAvattuun(indeksi));
                    tilannekatsaus();

                } // Jos ruutu tyhjä ja HIIRI3, niin ruutuun lippu
                else if (e.getButton() == MouseEvent.BUTTON3 && nappula.getIcon() == null) {
                    nappula.setIcon(lippu);
                    lippumaara--;
                    miinamittari.setText(Integer.toString(lippumaara));

                } // Jos ruudussa lippu ja HIIRI3, ruutuun ? 
                else if (e.getButton() == MouseEvent.BUTTON3 && nappula.getIcon() == lippu) {
                    nappula.setIcon(kysymys);
                    lippumaara++;
                    miinamittari.setText(Integer.toString(lippumaara));
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
            napukat.get(i).setEnabled(true);
            ruudukkopaneeli.add(napukat.get(i));
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
        Font f = new Font("Helvetica", Font.BOLD, 14);
        miinamittari.setFont(f);
        miinamittari.setEditable(false);
        miinamittari.setColumns(2);
        ylapaneeli.add(new JLabel(miina));
        ylapaneeli.add(miinamittari);
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
        
        JButton nappula;

        for (int i = 0; i < lkm; i++) {            
            nappula = new JButton();
            nappula.setPreferredSize(new Dimension(30, 30));
            nappula.setFocusPainted(false);
            napukat.put(i, nappula);
        }
    }
}
