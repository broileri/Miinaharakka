
package grafiikat;

import javax.swing.JFrame;


public class Main extends Kayttoliittyma {
    
        //Startti
    public static void main(String[] args) {

        Kayttoliittyma harava = new Kayttoliittyma();
        harava.setTitle("Miinaharava");
        harava.pack();
        harava.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // osaa loppua
        harava.setVisible(true); // olio näkyviin
    }
    
}
