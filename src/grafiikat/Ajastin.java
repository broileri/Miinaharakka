package grafiikat;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Ajastin extends JFrame implements ActionListener {

    private JTextField aikakentta;
    private Timer ajastin = new Timer(1000, this);
    private int sekunnit = 1;
    private boolean lopetus = false;

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
    public void seis() {
       lopetus = true; 
    }
    
    public void resetoi() {
        sekunnit = 1;
    }
    
    public void aloita() {
        ajastin.start();
    }
    
    public Timer getTimer() {
        return ajastin;
    }
    
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