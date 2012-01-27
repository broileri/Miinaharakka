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
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import toimintalogiikka.Haravalogiikka;
import toimintalogiikka.Ruutu;

public class Kayttoliittyma extends JFrame {

    // Napit ja muut
    private JButton iso, pieni, normi, reset;
    private HashMap<String, JButton> napukat;
    private int pelinKoko, ax, yy;
    private int[] xAuki, yAuki;
    private Ajastin aika;
    private ImageIcon harakka, hkummastus, hvoitto, hkuolema, miina, lippu, kysymys,
            yksi, kaksi, kolme, nelja, viisi, kuusi, seitseman, kahdeksan;
    private Haravalogiikka logiikka;

    // Alkutila
    public Kayttoliittyma() {

        // Nappi-ikonit
        harakka = new ImageIcon(getClass().getResource("Kuvat/miinaharakka.png"));
        hkummastus = new ImageIcon(getClass().getResource("Kuvat/miinaharakkaiik.png"));
        hvoitto = new ImageIcon(getClass().getResource("Kuvat/miinaharakkajee.png"));
        hkuolema = new ImageIcon(getClass().getResource("Kuvat/miinaharakkakuol.png"));
        miina = new ImageIcon(getClass().getResource("Kuvat/miina.png"));
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
        napukat = new HashMap<String, JButton>();
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

    private void aloitaPeli() {

        teePeliIkkuna();
        this.pack();

        for (int i = 0; i < pelinKoko * pelinKoko; i++) {
            hiirikuuntelijatRuudukolle(i);
        }
        logiikka = new Haravalogiikka(pelinKoko);
    }

    // Kun peli läpi, kutsutaan tätä
    public void voittoKotiin() {
        reset.setIcon(hvoitto);
        aika.seis();
        // Lisää kentän paljastus tähän
    }

    // Kun peli menee mönkään, kutsutaan tätä
    public void kirvelevaTappio() {
        reset.setIcon(hkuolema);
        aika.seis();
        // Lisää kentän paljastus tähän
    }

    public void avaaTyhjienReunat(int[] x, int[] y) {

        int[][] miinakentta = logiikka.getRuudukko();
        int[] hashMapIndeksit = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            hashMapIndeksit[i] = -9;
        }

        int indeksi = 0;

        for (int i = 0; i < x.length; i++) {

            if (x[i] != -9) {

                // Yla
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
            JButton nappi = napukat.get(Integer.toString(hashMapIndeksit[i]));
            haeIndeksi(hashMapIndeksit[i]);
            nappi.setIcon(numeroruudunAvaus(miinakentta[ax][yy]));
            nappi.setEnabled(false);
            nappi.setDisabledIcon(numeroruudunAvaus(miinakentta[ax][yy]));
            }
        }

    }

    // Avaa ruutuja
    private ImageIcon asetaKuvaAvattuun(int indeksi, JButton nappi) {
        int[][] miinakentta = logiikka.getRuudukko();
        haeIndeksi(indeksi);
        int mikaRuutu = miinakentta[ax][yy];

        // Miinan avaus
        if (mikaRuutu == -1) {
            kirvelevaTappio();
            return miina;

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
                    //xAuki[i] + yAuki[i] + (xAuki[i] * (pelinKoko - 1));
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
        } else {
            return kahdeksan;
        }
    }

    // JButton-HashMapin avaimen muuttaminen int[]-indekseiksi
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

    // int[]-indeksien muuttaminen JButton-HashMapin avaimeksi
    public int teeIndeksi(int x, int y) {

        return (x + y + x * (pelinKoko - 1));
    }

    // Ruudukolle kuuntelijat
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

    // Menu-napukoille kuuntelijat
    private void tapahtumakuuntelijatValikolle() {

        reset.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        reset.setIcon(harakka);
                        aika.seis();
                        aika.resetoi();
                        // Lisää tähän vielä uuden pelin luonti

                    }
                });

        pieni.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        aika.seis();
                        aika.resetoi();
                        pelinKoko = 9;
                        // Lisää tähän vielä uuden pelin luonti

                    }
                });

        normi.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        aika.seis();
                        aika.resetoi();
                        pelinKoko = 16;
                        // Lisää tähän vielä uuden pelin luonti

                    }
                });

        iso.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent tapahtuma) {
                        aika.seis();
                        aika.resetoi();
                        pelinKoko = 22;
                        // Lisää tähän vielä uuden pelin luonti

                    }
                });
    }

    // Metodi tekee peli-ikkunan
    private void teePeliIkkuna() {

        // Yläpaneelin lisäys
        JPanel ylapaneeli = new JPanel(new FlowLayout());
        ylapaneeli.add(pieni);
        ylapaneeli.add(normi);
        ylapaneeli.add(iso);
        aika = new Ajastin(ylapaneeli);
        ylapaneeli.add(reset);

        // Nappularuudukon lisäys
        JPanel ruudukkopaneeli = new JPanel(new GridLayout(pelinKoko, pelinKoko));
        teeNappeja(pelinKoko * pelinKoko);
        for (int i = 0; i < napukat.size(); i++) {
            String avain = Integer.toString(i);
            ruudukkopaneeli.add(napukat.get(avain));
        }

        // Paneelien lisääminen pelikenttään        
        this.setLayout(new BorderLayout(10, 10));
        this.add("Center", ruudukkopaneeli);
        this.add("North", ylapaneeli);
        this.setBounds(300, 300, 200, 200);
        this.setResizable(false);
        this.setVisible(true);
    }

    // Tekee annetun määrän ruutuja HashMapiin
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

    // Antaa pelinKoon
    public int getPelinKoko() {
        return pelinKoko;
    }

    // Antaa napit
    public HashMap getNapit() {
        return napukat;
    }
}
