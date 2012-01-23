package toimintalogiikka;

import grafiikat.Kayttoliittyma;
import java.util.HashMap;
import javax.swing.JButton;

public class LogiikkaGUIhin extends Kayttoliittyma {
    
    private HashMap<String, JButton> ruutuNapit, menuNapit;

    public LogiikkaGUIhin() {
        Kayttoliittyma gui = new Kayttoliittyma();
        Haravalogiikka logiikka = new Haravalogiikka(9);
        ruutuNapit = gui.getNapit();
        
    }
    
    public static void main(String[] args) {
        Kayttoliittyma testi = new Kayttoliittyma();
        Haravalogiikka testi2 = new Haravalogiikka(9);
        testi2.testaaRuudukkoa();
        testi2.etsiTyhjat(new Ruutu(1, 1));
        testi2.testaaRuudukkoa();        
    }
}
