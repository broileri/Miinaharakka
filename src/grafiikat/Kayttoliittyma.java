package grafiikat;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Kayttoliittyma extends JFrame {

    // Napit
    private JButton lippu, kysymys;
    private JButton reset;
    private HashMap<String, JButton> napukat;

    // Alkutila
    public Kayttoliittyma() {
        
        lippu = new JButton("|>");
        kysymys = new JButton("?");
        napukat = new HashMap<String, JButton>();
        
        // Napujen lisäys
        JPanel paneeli = new JPanel(new GridLayout(10, 10));
        teeNappeja(100);
        
        for (int i = 0; i < napukat.size(); i++) {
            String avain = Integer.toString(i);
            paneeli.add(napukat.get(avain));
        }

        this.setLayout(new BorderLayout());
        this.add("Center", paneeli);
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

    //Startti
    public static void main(String[] args) {

        Kayttoliittyma harava = new Kayttoliittyma();
        harava.setTitle("Miinaharava");
        harava.pack();
        harava.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // osaa loppua
        harava.setVisible(true); // olio näkyviin
    }
}