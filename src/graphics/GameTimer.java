package graphics;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * A class for timing the game.
 * @author Broileri
 * @see graphics.GUI
 */
public class GameTimer extends JFrame implements ActionListener {

    private final JTextField timeField;
    private Timer timer = new Timer(1000, this);
    private int seconds;
    private boolean timerStop;

    /**
     * The constructor adds a timer to the given JPanel.
     * @param panel The given JPanel.
     */
    public GameTimer(JPanel panel) {
        
        // Initializing values
        this.timer = new Timer(1000, this);
        this.seconds = 1;
        this.timerStop = false;

        // Building the field
        Font f = new Font("Helvetica", Font.BOLD, 14);
        timeField = new JTextField("0");
        timeField.setFont(f);
        timeField.setEditable(false);
        panel.add(timeField);
        timeField.setColumns(4);
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        timerStop = true;
    }

    /**
     * Resets the timer.
     */
    public void reset() {
        seconds = 1;
        timeField.setText("0");        
    }

    /**
     * Starts the timer.
     */
    public void start() {        
            timer.start();  
            timerStop = false;
    }

    /**
     * Returns the current value of the variable "seconds".
     */
    public int getSeconds() {
        return seconds;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!timerStop) {
            timeField.setText(Integer.toString(seconds));
            seconds = seconds + 1;
        } else {
            timer.stop();
            timerStop = false;
        }
    }
}