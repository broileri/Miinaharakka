package grafiikat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Ajastin extends JFrame implements ActionListener {

    private JTextField ajastinkentta;
    private Timer ajastin = new Timer(1000, this);
    int sekunnit = 1;
    boolean lopetus = false;

    public Ajastin() {

        ajastinkentta = new JTextField();
        ajastinkentta.setEditable(false);
        this.add(ajastinkentta);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        this.setVisible(true);

        ajastin.start();
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!lopetus) {            
            ajastinkentta.setText(Integer.toString(sekunnit));            
            sekunnit = sekunnit + 1;
        } else {
            ajastin.stop();
        }
    }

    public static void main(String[] args) {
        Ajastin display = new Ajastin();
    }
}