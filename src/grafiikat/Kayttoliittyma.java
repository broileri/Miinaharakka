package grafiikat;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Kayttoliittyma extends JFrame {

    // Napit ja muut
    private JButton iso, pieni, normi, reset;
    private HashMap<String, JButton> napukat;
    private int pelinKoko;
    private Ajastin aika;

    // Alkutila
    public Kayttoliittyma() {

        reset = new JButton("reset");
        pieni = new JButton("S");
        normi = new JButton("M");
        iso = new JButton("L");
        napukat = new HashMap<String, JButton>();
        pelinKoko = 9;
        teePeliIkkuna();
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
}