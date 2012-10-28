package graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.ImageLoader;
import model.GameModel;
import model.world.WorldObject;

/**
 * Abstract class for the OO Strategy design pattern.
 * @author Askh
 */
public abstract class AbstractWorldObjectPainter {
	protected ImageLoader imageLoader;
	protected GameModel gm;
	
	public abstract void paintWorldObjectBounds(WorldObject wo, Graphics2D g2d, int cameraX, int cameraY, Color c);
	public abstract void paintWorldObject(WorldObject wo, Graphics2D g2d, int cameraX, int cameraY, GraphicalViewer gv);
}
