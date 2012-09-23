package graphics;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-23
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public class InformationContainer {
    int stringPaddingY = 20;
    int stringPaddingX = 20;
    int numberOfRows = 0;
    LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    public InformationContainer(){

    }

    public InformationContainer(int padX, int padY){
        stringPaddingX = padX;
        stringPaddingY = padY;
    }

    public void add(String s){
        numberOfRows++;
        this.queue.add(s);
    }

    public void paint(Graphics2D g2d){
        for(int i = 0; i < numberOfRows; i++){
            g2d.drawString(this.queue.remove(), stringPaddingX, (stringPaddingY*(i + 1)));
        }
    }
}
