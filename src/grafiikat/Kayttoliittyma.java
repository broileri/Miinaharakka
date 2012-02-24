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
// päivitä javadoc, luokkakaavio, sekvenssikaavio, testidokki

/**
 * Luokka luo peli-ikkunan ja suorittaa pelin ja käyttäjän väliseen
 * kanssakäyntiin liittyviä operaatioita.
 *
 * @author Broileri
 */
public class Kayttoliittyma extends JFrame {

    // Napit ja muut
    private ImageIcon harakka, hkummastus, hvoitto, hkuolema, miina, lippu, kysymys,
            yksi, kaksi, kolme, nelja, viisi, kuusi, seitseman, kahdeksan, boom, okmiina;
    private JButton iso, pieni, normi, reset;
    private HashMap<Integer, JButton> napukat;
    private Ruutu[] auki;
    private Ajastin aika;
    private Haravalogiikka logiikka;
    private JPanel ruudukkopaneeli;
    private JTextField miinamittari;
    private boolean ajastinKayntiin;
    private int pelinKoko = 9, ax, yy, lippumaara;

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
        lataaIkonit();

        // Nappulat
        lataaNapit();

        // Tapahtumakuuntelijoiden asettaminen
        tapahtumakuuntelijatValikolle();

        // Peli-ikkunan luonti        
        aloitaPeli(true);

        // Asetukset
        this.setTitle("Miinaharakka"); // Otsikko \o/        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Osaa loppua
        this.setVisible(true); // Olio näkyviin
    }

    /**
     * Metodi luo valikon napit ja asettaa niihin tekstit ja kuvat.
     */
    private void lataaNapit() {

        reset = new JButton(harakka);
        reset.setPressedIcon(hkummastus);
        reset.setFocusPainted(false);
        pieni = new JButton("S");
        pieni.setFocusPainted(false);
        normi = new JButton("M");
        normi.setFocusPainted(false);
        iso = new JButton("L");
        iso.setFocusPainted(false);
    }

    /**
     * Metodi määrittelee ikoinien kuvat.
     */
    private void lataaIkonit() {

        harakka = new ImageIcon(getClass().getResource("Kuvat/miinaharakka2.png"));
        hkummastus = new ImageIcon(getClass().getResource("Kuvat/miinaharakkaiik2.png"));
        hvoitto = new ImageIcon(getClass().getResource("Kuvat/miinaharakkajee2.png"));
        hkuolema = new ImageIcon(getClass().getResource("Kuvat/miinaharakkakuol2.png"));
        miina = new ImageIcon(getClass().getResource("Kuvat/miina2.png"));
        lippu = new ImageIcon(getClass().getResource("Kuvat/lippu4.png"));
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
    private void aloitaPeli(boolean onkoEkaKrt) {

        reset.setIcon(harakka);
        napukat = new HashMap<Integer, JButton>();

        // Käynnistyksen yhteydessä säädettävät asetukset
        if (onkoEkaKrt) {
            ekaPeli();
        } // Uusi peli (ensimmäisen käynnistyksen jälkeen)
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
        ajastinKayntiin = true;
        this.pack();
    }

    /**
     * Kun peli käynnistetään ensimmäistä kertaa, tämä metodi rakentaa
     * peli-ikkunan.
     */
    public void ekaPeli() {

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
        paljastaKentta(1000); // 1000 tarkoittaa tyhjää avainta
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

            Ruutu r = haeIndeksi(i);
            int mikaRuutu = miinakentta[r.getX()][r.getY()];

            // Miina 
            // Voitto
            if (mikaRuutu == -1 && avainJosTappio == 1000) {
                napukat.get(i).setIcon(okmiina);
                napukat.get(i).setDisabledIcon(okmiina);
            } // Total fail
            else if (mikaRuutu == -1) {
                napukat.get(i).setIcon(miina);
                napukat.get(i).setDisabledIcon(miina);
            } // Numero
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
     * Tarkastaa, onko peli voitettu. Jos ruutuja on auki pelin ruutujen määrä
     * miinus miinamäärä, peli on voitettu. Metodia kutsutaan aina ruudun
     * avaamisen jälkeen.
     */
    private void tilannekatsaus() {

        int aukiOn = 0;
        for (int i = 0; i < pelinKoko * pelinKoko; i++) {
            if (!napukat.get(i).isEnabled()) {
                aukiOn++;
            }
        }
        if (logiikka.getMiinamaara() == pelinKoko * pelinKoko - aukiOn) {
            voitto();
        }
    }

    /**
     * Kun pelaaja avaa tyhjän kenttäruudun, myös kaikki sitä ympäröivät tyhjät
     * painikkeet avautuvat. Tämä metodi avaa tyhjän alueen reunoilta numerot
     * piilottavat painikkeet ja asettaa sen jälkeen avattujen painikkeiden
     * disabled-ikoniksi oikean numeron.
     *
     * @param a Tyhjien ruutujen x- ja y-koordinaatit.
     */
    public void avaaTyhjienReunat(Ruutu[] a) {

        int[][] miinakentta = logiikka.getRuudukko();
        int[] hashMapIndeksit = new int[pelinKoko * pelinKoko * 8];
        for (int i = 0; i < pelinKoko * pelinKoko * 8; i++) {
            hashMapIndeksit[i] = -9;
        }
        int indeksi = 0;

        for (int i = 0; i < a.length; i++) {

            if (a[i].getX() != -9) {

                // Ylä
                if (logiikka.onkoRuudukossa(a[i].getX() - 1, a[i].getY() - 1)) {
                    if (miinakentta[a[i].getX() - 1][a[i].getY() - 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(a[i].getX() - 1, a[i].getY() - 1);
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(a[i].getX() - 1, a[i].getY())) {
                    if (miinakentta[a[i].getX() - 1][a[i].getY()] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(a[i].getX() - 1, a[i].getY());
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(a[i].getX() - 1, a[i].getY() + 1)) {
                    if (miinakentta[a[i].getX() - 1][a[i].getY() + 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(a[i].getX() - 1, a[i].getY() + 1);
                        indeksi++;
                    }
                }
                // Sivut
                if (logiikka.onkoRuudukossa(a[i].getX(), a[i].getY() - 1)) {
                    if (miinakentta[a[i].getX()][a[i].getY() - 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(a[i].getX(), a[i].getY() - 1);
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(a[i].getX(), a[i].getY() + 1)) {
                    if (miinakentta[a[i].getX()][a[i].getY() + 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(a[i].getX(), a[i].getY() + 1);
                        indeksi++;
                    }
                }
                // Ala
                if (logiikka.onkoRuudukossa(a[i].getX() + 1, a[i].getY() - 1)) {
                    if (miinakentta[a[i].getX() + 1][a[i].getY() - 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(a[i].getX() + 1, a[i].getY() - 1);
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(a[i].getX() + 1, a[i].getY())) {
                    if (miinakentta[a[i].getX() + 1][a[i].getY()] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(a[i].getX() + 1, a[i].getY());
                        indeksi++;
                    }
                }
                if (logiikka.onkoRuudukossa(a[i].getX() + 1, a[i].getY() + 1)) {
                    if (miinakentta[a[i].getX() + 1][a[i].getY() + 1] != 9) {
                        hashMapIndeksit[indeksi] = teeIndeksi(a[i].getX() + 1, a[i].getY() + 1);
                        indeksi++;
                    }
                }
            }
        }
        ikonitTyhjienReunoille(hashMapIndeksit, miinakentta, indeksi);
    }

    /**
     * Metodi asettaa avaaTyhjienReunat-metodin määrittelemille ruuduille
     * numerokuvakkeet.
     *
     * @param hashMapIndeksit
     * @param miinakentta
     * @param indeksi
     *
     * @see grafiikat.Kayttoliittyma#avaaTyhjienReunat()
     */
    public void ikonitTyhjienReunoille(int[] hashMapIndeksit, int[][] miinakentta, int indeksi) {

        for (int i = 0; i < indeksi; i++) {
            if (hashMapIndeksit[i] != -9) {
                JButton nappi = napukat.get(hashMapIndeksit[i]);
                Ruutu r = haeIndeksi(hashMapIndeksit[i]);
                if (nappi.getIcon() == null) {
                    nappi.setIcon(numeroruudunAvaus(miinakentta[r.getX()][r.getY()]));
                    nappi.setEnabled(false);
                    nappi.setDisabledIcon(numeroruudunAvaus(miinakentta[r.getX()][r.getY()]));
                }
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
        Ruutu r = haeIndeksi(indeksi);
        int mikaRuutu = miinakentta[r.getX()][r.getY()];

        // Miinan avaus
        if (mikaRuutu == -1) {
            return tappio(indeksi);

            // Tyhjän ruudun avaus    
        } else if (mikaRuutu == 0) {
            logiikka.etsiTyhjat(miinakentta, r);
            // Tyhjien koordinaatit
            auki = logiikka.getTyhjat();

            // Tyhjien avaaminen ja int-taulukoiden tyhjien kenttien (-9) poissulkeminen
            for (int i = 0; i < auki.length; i++) {
                if (auki[i].getX() != -9) {
                    int nappiIndeksi = teeIndeksi(auki[i].getX(), auki[i].getY());
                    if (napukat.get(nappiIndeksi).getIcon() == null) {
                        napukat.get(nappiIndeksi).setEnabled(false);
                        napukat.get(nappiIndeksi).setIcon(null);
                    }
                }
            }
            // Tyhjien ruutujen reunojen avaus
            avaaTyhjienReunat(auki);
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
     * Metodi muuttaa JButton-HashMapin avaimen Ruuduksi
     *
     * @param indeksi
     * @return
     */
    public Ruutu haeIndeksi(int indeksi) {

        int kentanRuutu = 0;

        for (int x = 0; x < pelinKoko; x++) {
            for (int y = 0; y < pelinKoko; y++) {
                if (kentanRuutu == indeksi) {
                    return new Ruutu(x, y);
                }
                kentanRuutu++;
            }
        }
        return null;
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

                    if (ajastinKayntiin) {
                        ajastinKayntiin = false;
                        aika.aloita();
                    }
                    nappula.setIcon(asetaKuvaAvattuun(indeksi));
                    nappula.setEnabled(false);
                    nappula.setDisabledIcon(asetaKuvaAvattuun(indeksi));
                    tilannekatsaus();

                } // Jos ruutu tyhjä ja HIIRI3, niin ruutuun lippu
                else if (e.getButton() == MouseEvent.BUTTON3 && nappula.getIcon() == null
                        && nappula.isEnabled()) {
                    nappula.setIcon(lippu);
                    lippumaara--;
                    miinamittari.setText(Integer.toString(lippumaara));

                } // Jos ruudussa lippu ja HIIRI3, ruutuun ? 
                else if (e.getButton() == MouseEvent.BUTTON3 && nappula.getIcon() == lippu
                        && nappula.isEnabled()) {
                    nappula.setIcon(kysymys);
                    lippumaara++;
                    miinamittari.setText(Integer.toString(lippumaara));
                } // Jos ruudussa ? ja HIIRI3, ruutu tyhjäksi
                else if (e.getButton() == MouseEvent.BUTTON3 && nappula.getIcon() == kysymys
                        && nappula.isEnabled()) {
                    nappula.setIcon(null);
                }

            }
        });
    }

    /**
     * Metodi luo valikon napeille tapahtumakuuntelijat.
     */
    private void tapahtumakuuntelijatValikolle() {

        // Reset-nappi
        reset.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        reset.setIcon(harakka);
                        aika.seis();
                        aika.resetoi();
                        aloitaPeli(false);
                    }
                });

        // Koonvalintanapit
        kuuntelijatKokoNappuloille(9, pieni);
        kuuntelijatKokoNappuloille(16, normi);
        kuuntelijatKokoNappuloille(22, iso);
    }

    /**
     * Metodi luo parametrina annettavalle koonvalintanapille
     * tapahtumakuuntelijan.
     *
     * @param pkoko
     * @param kokoSML
     */
    public void kuuntelijatKokoNappuloille(final int pkoko, JButton kokoSML) {

        kokoSML.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        aika.seis();
                        aika.resetoi();
                        pelinKoko = pkoko;
                        aloitaPeli(false);
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
        miinamittari = new JTextField();
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
