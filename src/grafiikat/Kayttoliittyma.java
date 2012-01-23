package grafiikat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Kayttoliittyma extends JFrame {

    // Napit ja muut
    private JButton iso, pieni, normi, reset;
    private HashMap<String, JButton> napukat;
    private int pelinKoko;
    private Ajastin aika;
    private ImageIcon harakka, hkummastus, hvoitto, hkuolema;

    // Alkutila
    public Kayttoliittyma() {

        // Lintuikonit
        harakka = new ImageIcon(getClass().getResource("Kuvat/miinaharakka.png"));
        hkummastus = new ImageIcon(getClass().getResource("Kuvat/miinaharakkaiik.png"));
        hvoitto = new ImageIcon(getClass().getResource("Kuvat/miinaharakkajee.png"));
        hkuolema = new ImageIcon(getClass().getResource("Kuvat/miinaharakkakuol.png"));

        // Nappulat
        reset = new JButton(harakka);
        reset.setPressedIcon(hkummastus);
        pieni = new JButton("S");
        normi = new JButton("M");
        iso = new JButton("L");
        napukat = new HashMap<String, JButton>();
        pelinKoko = 9;

        // Tapahtumakuuntelijoiden asettaminen
        tapahtumakuuntelijatValikolle();

        // Peli-ikkunan luonti
        teePeliIkkuna();

        // Tapahtumakuuntelijat ruudukon napeille 
        tapahtumakuuntelijatRuudukolle();

        // Asetukset
        this.setTitle("Miinaharakka"); // Otsikko \o/        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Osaa loppua
        this.setVisible(true); // Olio näkyviin
        this.pack(); // Tekee ikkunasta sopivan kokoisen

    }
    // Kun peli läpi, kutsutaan tätä

    public void voittoKotiin() {
        reset.setIcon(hvoitto);
        aika.seis();
        // Lisää kentän paljastus tähän
    }
    // Kun peli meni mönkään, kutsutaan tätä

    public void kirvelevaTappio() {
        reset.setIcon(hkuolema);
        aika.seis();
        // Lisää kentän paljastus tähän
    }

    // Ruudukolle kuuntelijat
    private void tapahtumakuuntelijatRuudukolle() {

        for (int i = 0; i < pelinKoko * pelinKoko; i++) {
            napukat.get(Integer.toString(i)).addActionListener(
                    new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent tapahtuma) {
                            // Jotain fiksua tänne :P
                        }
                    });
        }

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
            nappula = new JButton(" ");
            napukat.put(avain, nappula);
        }
    }

    // Antaa napit
    public HashMap getNapit() {
        return napukat;
    }
}