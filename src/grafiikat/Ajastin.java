package grafiikat;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Ajastin extends JFrame implements ActionListener {

    private JTextField aikakentta;
    private Timer ajastin = new Timer(1000, this);
    int sekunnit = 1;
    boolean lopetus = false;

    public Ajastin(JPanel paneeli) {

        // Kentän rakentaminen
        Font f = new Font("Helvetica", Font.BOLD, 14);
        aikakentta = new JTextField();
        aikakentta.setFont(f);
        aikakentta.setEditable(false);
        paneeli.add(aikakentta);        
        aikakentta.setColumns(4);       

        ajastin.start();
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