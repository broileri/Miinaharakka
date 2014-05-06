package game_logic;

/**
 * A class for creating a tile that has X and Y coordinates.
 * @author Broileri
 */
public class Tile {

    private int x, y;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }    
 
    public int getX() {
        return x;
    }    
   
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}
