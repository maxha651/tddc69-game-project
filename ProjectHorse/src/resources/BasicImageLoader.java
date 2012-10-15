package resources;

import java.awt.*;
import java.awt.image.BufferedImage;

import model.background.Projectile;
import model.character.Player;

public class BasicImageLoader extends AbstractImageLoader implements ImageLoader {
	
		BufferedImage asteroid1;
	    BufferedImage spaceship1;
        BufferedImage redplayer;
        BufferedImage blueplayer;
	    BufferedImage laserblue1;
	    BufferedImage engineparticlered;
	    BufferedImage asteroidparticle1;
	    BufferedImage reddeath;
        BufferedImage defaultBackground;
	    
	    public BufferedImage getAsteroidImage(){
	        return asteroid1 = getImage(asteroid1, "asteroid1.png");	        
	    }

	    public BufferedImage getPlayerImage(Player p){
            if(p.getColor() == Color.RED){
                redplayer = getImage(redplayer, "redplayer.png");
                return redplayer;
            } else {
                blueplayer = getImage(blueplayer, "blueplayer.png");
                return blueplayer;
            }
	    }

	    public BufferedImage getProjectileImage(Projectile p){
	        laserblue1 = getImage(laserblue1, "laserblue1.png");
	        return laserblue1;
	    }

	    public BufferedImage getEngineParticleImage(){
	        engineparticlered = getImage(engineparticlered, "engineparticlered.png");
	        return engineparticlered;
	    }

	    public BufferedImage getAsteroidParticleImage(){
	        asteroidparticle1 = getImage(asteroidparticle1, "asteroidparticle.png");
	        return asteroidparticle1;
	    }

	    public BufferedImage getRedAsteroidParticleImage(){
	        engineparticlered = getImage(engineparticlered, "engineparticlered.png");
	        return engineparticlered;
	    }

	    public BufferedImage getRedDeathParticleImage(){
	        reddeath = getImage(reddeath, "reddeathparticle.png");
	        return reddeath;
	    }


}
