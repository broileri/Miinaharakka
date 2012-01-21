package grafiikat;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Kayttoliittyma extends JFrame {

    // Napit ja muut
    private JButton lippu, kysymys, iso, pieni, normi;
    private JButton reset;
    private HashMap<String, JButton> napukat;
    private int pelinKoko;

    // Alkutila
    public Kayttoliittyma() {
        
        reset = new JButton("reset");
        pieni = new JButton("S");
        normi = new JButton("M");
        iso = new JButton("L");        
        napukat = new HashMap<String, JButton>();
        pelinKoko = 9;
        
        teePeliruutu();
    }
    
        // Pelialueen piirto
        private void teePeliruutu() {
            
        // Yläpaneelin lisäys
        JPanel ylapaneeli = new JPanel(new FlowLayout());
        ylapaneeli.add(pieni); ylapaneeli.add(normi); ylapaneeli.add(iso);
        ylapaneeli.add(reset);
        
        
        
        // Ruudukon lisäys
        JPanel ruudukkopaneeli = new JPanel(new GridLayout(pelinKoko, pelinKoko));
        teeNappeja(pelinKoko*pelinKoko);
        
        for (int i = 0; i < napukat.size(); i++) {
            String avain = Integer.toString(i);
            ruudukkopaneeli.add(napukat.get(avain));
        }

        this.setLayout(new BorderLayout());
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