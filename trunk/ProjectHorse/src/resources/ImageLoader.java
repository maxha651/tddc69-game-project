package resources;

import java.awt.image.BufferedImage;

import model.background.Projectile;
import model.character.Player;

/**
 * Implement this interface if you want to make sure all image getters are defined fully.
 * @author Askh
 *
 */
public interface ImageLoader {
	abstract public BufferedImage getAsteroidImage();
	abstract public BufferedImage getPlayerImage(Player p);
	abstract public BufferedImage getProjectileImage(Projectile p);
	abstract public BufferedImage getEngineParticleImage();
	abstract public BufferedImage getAsteroidParticleImage();
	abstract public BufferedImage getRedAsteroidParticleImage();
	abstract public BufferedImage getRedDeathParticleImage();
}
