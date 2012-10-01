package resources;

import model.background.Projectile;
import model.character.Player;

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

    BufferedImage asteroid1;
    BufferedImage spaceship1;
    BufferedImage laserblue1;
    BufferedImage engineparticlered;

    public BufferedImage getAsteroidImage(){
        asteroid1 = getImage(asteroid1, "asteroid2.png");
        return asteroid1;
    }

    public BufferedImage getPlayerImage(Player p){
        spaceship1 = getImage(spaceship1, "spaceship1.png");
        return spaceship1;
    }

    public BufferedImage getProjectileImage(Projectile p){
        //check what type and return for that
        laserblue1 = getImage(laserblue1, "laserblue1.png");
        return laserblue1;
    }

    public BufferedImage getEngineParticleImage(){
        //check what type and return for that
        engineparticlered = getImage(engineparticlered, "engineparticlered.png");
        return engineparticlered;
    }

    private BufferedImage getImage(BufferedImage bf, String path){
        if(bf == null){
            bf = load(path, bf);
            return bf;
        }
        return bf;
    }



    public BufferedImage load(String path, BufferedImage bf){
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
