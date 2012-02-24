package toimintalogiikka;

/**
 * Apuluokka, joka luo "ruudun", jolla on x- ja y-koordinaatti.
 * @author Broileri
 */
public class Ruutu {

    private int x, y;

    /**
     * Konstruktori
     * @param ax Annettu x-koordinaatti.
     * @param yy Annettu y-koordinaatti.
     */
    public Ruutu(int ax, int yy) {
        x = ax;
        y = yy;
    }    
 
    public int getX() {
        return x;
    }    
   
    public int getY() {
        return y;
    }
    
    public void setX(int ax) {
        x = ax;
    }
    
    public void setY(int yy) {
        y = yy;
    }
}
