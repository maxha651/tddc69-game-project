package resources;

import java.awt.image.BufferedImage;

import model.background.Projectile;
import model.character.Player;

public interface ImageLoader {
	abstract public BufferedImage getAsteroidImage();
	abstract public BufferedImage getPlayerImage(Player p);
	abstract public BufferedImage getProjectileImage(Projectile p);
	abstract public BufferedImage getEngineParticleImage();
	abstract public BufferedImage getAsteroidParticleImage();
	abstract public BufferedImage getRedAsteroidParticleImage();
	abstract public BufferedImage getRedDeathParticleImage();
}
