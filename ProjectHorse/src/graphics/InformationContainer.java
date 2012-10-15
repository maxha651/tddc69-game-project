package graphics;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class is used for any kind of information container you want painted on the screen.
 * Uses a LinkedBlockingQueue to store each row of information and then paints the row one by one
 * correctly on a Graphics2D object given. Not thread safe.
 */
public class InformationContainer {
	private int startPositionPad = 4;
    private int stringPaddingY;
    private int stringPaddingX;
    private int originX = 0;
    private int originY = 0;
    private int numberOfRows = 0;
    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    public InformationContainer(){
    	stringPaddingX = 20;
    	stringPaddingY = 20;
    }
    
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
            g2d.drawString(this.queue.remove(), stringPaddingX, (stringPaddingY*(i + startPositionPad)));
        }
    }
}
