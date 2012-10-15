package resources;

import model.background.Projectile;
import model.character.Player;
import model.utility.math.Randomizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Basic template for an image loader. New image loaders can be made by extending this one and then making sure each 
 * abstract method returns a correct resource (a buffered image in most cases).
 */
public abstract class AbstractImageLoader {
	
	/**
	 * Loads an image from this class package if the image has not yet been loaded.
	 * Loads from filename/path "path" to BufferedImage bf.
	 * If the image has already been loaded then return reference to the image.
	 * @param bf
	 * @param path
	 * @return
	 */
    protected BufferedImage getImage(BufferedImage bf, String path){
        if(bf == null){
            bf = load(path, bf);
            return bf;
        }
        return bf;
    }

    /**
     * Tries to load an image from path into BufferedImage bf
     * System exits if it can't find the image
     * @param path
     * @param bf
     * @return
     */
    private BufferedImage load(String path, BufferedImage bf){
        try {
            bf = ImageIO.read(this.getClass().getResource(path));
        } catch (IOException e) {
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.err.print("[ImageLoader] Could not load image from path: " + path);
            System.exit(0);
        }
        return bf;
    }
}
