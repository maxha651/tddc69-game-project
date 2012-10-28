package graphics;

import java.awt.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class is used for any kind of information container you want painted on the screen.
 * Uses a LinkedBlockingQueue to store each row of information and then paints the row one by one
 * correctly on a Graphics2D object given. Not thread safe.
 */
public class InformationContainer {
	private static final int START_POSITION_PAD = 4;
    private final int stringPaddingY;
    private final int stringPaddingX;
    private static final int DEFAULT_PADDING_X = 20;
    private static final int DEFAULT_PADDING_Y = 20;
    private int numberOfRows = 0;
    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    /**
     * Standard constructor for default padding
     */
    public InformationContainer(){
    	stringPaddingX = DEFAULT_PADDING_X;
    	stringPaddingY = DEFAULT_PADDING_Y;
    }
    
    /**
     * Constructor for player padding
     * @param padX
     * @param padY
     */
    public InformationContainer(int padX, int padY){
        stringPaddingX = padX;
        stringPaddingY = padY;
    }

    /**
     * Adds a string to the paint queue.
     * @param s
     */
    public void add(String s){
        numberOfRows++;
        this.queue.add(s);
    }

    /**
     * Paints all strings contained in the queue.
     * @param g2d
     */
    public void paint(Graphics2D g2d){
        for(int i = 0; i < numberOfRows; i++){
            g2d.drawString(this.queue.remove(), stringPaddingX, (stringPaddingY*(i + START_POSITION_PAD)));
        }
    }
}
