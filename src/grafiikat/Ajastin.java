package grafiikat;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * Luokka pelin ajanottoa varten.
 * 
 * @author Broileri
 * 
 * @see grafiikat.Kayttoliittyma
 */
public class Ajastin extends JFrame implements ActionListener {

    private JTextField aikakentta;
    private Timer ajastin = new Timer(1000, this);
    private int sekunnit = 1;
    private boolean lopetus = false;

    /**
     * Konstruktori lisää annettuun JPaneliin ajastimen, joka laskee
     * sekunteja ja näyttää ne vaihtuvina numeroina ajastinkentässä.
     * 
     * @param paneeli Annettu JPanel.
     */
    public Ajastin(JPanel paneeli) {

        // Kentän rakentaminen
        Font f = new Font("Helvetica", Font.BOLD, 14);
        aikakentta = new JTextField("0");
        aikakentta.setFont(f);
        aikakentta.setEditable(false);
        paneeli.add(aikakentta);        
        aikakentta.setColumns(4);       

        ajastin.start();
    }
    /**
     *  Pysäyttää ajastimen.
     */
    public void seis() {
       lopetus = true; 
    }
    
    /**
     * Saattaa ajastimen alkutilaansa.
     */
    public void resetoi() {
        sekunnit = 1;
        aikakentta.setText("0");
    }
    /**
     * Käynnistää ajastimen.
     */
    public void aloita() {
        ajastin.start();
    }

    /**
     * Palauttaa sekunnit.
     * @return 
     */
    public int getSekunnit() {
        return sekunnit;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!lopetus) {            
            aikakentta.setText(Integer.toString(sekunnit));            
            sekunnit = sekunnit + 1;
        } else {
            ajastin.stop();
            lopetus = false;
        }
    }
}