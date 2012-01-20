package grafiikat;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Kayttoliittyma extends JFrame {

    // Napit
    private JButton ruutu, lippu, kysymys;
    private JButton reset;

    // Alkutila
    public Kayttoliittyma() {
        
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