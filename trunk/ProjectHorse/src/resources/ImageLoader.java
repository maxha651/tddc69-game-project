package resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-30
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class ImageLoader {

    String asteroid1ImagePath = "asteroid1.png";
    BufferedImage asteroid1;

    public ImageLoader(){
        String currentPath = "[error - no path was loaded]";
        try {
            //load all images here
            currentPath = asteroid1ImagePath;
            asteroid1 = ImageIO.read(this.getClass().getResource(currentPath));
        } catch (IOException e) {
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.err.print("[ImageLoader] Could not load image from path: " + currentPath);
            System.exit(0);
        }
    }

    public BufferedImage getAsteroidImage(){
        return asteroid1;
    }
}
