package graphics;

import model.GameModel;
import model.background.Asteroid;
import model.background.AsteroidParticle;
import model.background.EngineParticle;
import model.background.Projectile;
import model.background.RedAsteroidParticle;
import model.background.RedProjectileDeathParticle;
import model.background.SpacecraftDeathParticle;
import model.character.Player;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObject;
import resources.ImageLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.Observer;

/**
 * Basically maps WorldObject objects to some paint method
 */
public class WorldObjectPainter {

    ImageLoader imageLoader;
    GameModel gm;

    /**
     * Basic constructor. Uses ImageLoader interface for obvious reasons. gm is only used to get its zone size at the moment.
     */
    public WorldObjectPainter(ImageLoader img, GameModel gm){
    	this.gm = gm;
    	this.imageLoader = img;
    }

    public void paintWorldObjectBounds(WorldObject wo, Graphics2D g2d, int cameraX, int cameraY, Color c){
        g2d.setColor(c);

        Coordinate positionInZone = wo.getCoordinate();
        ZoneCoordinate zoneCoordinate = wo.getZoneCoordinate();
        double zoneSize = gm.getZoneSize();

        int positionX =(int) (positionInZone.getX() + zoneSize*zoneCoordinate.getX());
        int positionY =(int) (positionInZone.getY() + zoneSize*zoneCoordinate.getY());

        int bWidth = (int) wo.getBoundingWidth();
        int bHeight = (int) wo.getBoundingHeight();

        int paintX;
        int paintY;

        paintX = (int) ((-cameraX + positionX) - bWidth / 2);
        paintY = (int) ((-cameraY + positionY) - bHeight / 2);

        g2d.drawRect(paintX, paintY, bWidth, bHeight);

    }

    public void paintWorldObject(WorldObject wo, Graphics2D g2d, int cameraX, int cameraY, GraphicalViewer gv){

        Coordinate positionInZone = wo.getCoordinate();
        ZoneCoordinate zoneCoordinate = wo.getZoneCoordinate();
        double zoneSize = gm.getZoneSize();

        int positionX =(int) (positionInZone.getX() + zoneSize*zoneCoordinate.getX());
        int positionY =(int) (positionInZone.getY() + zoneSize*zoneCoordinate.getY());

        int bWidth = (int) wo.getWidth();
        int bHeight = (int) wo.getHeight();

        int paintX;
        int paintY;

        int rotateX;
        int rotateY;

        rotateX = (int) ((-cameraX + positionX));
        rotateY = (int) ((-cameraY + positionY));

        paintX = (int) ((-cameraX + positionX) - bWidth / 2);
        paintY = (int) ((-cameraY + positionY) - bHeight / 2);

        //rotation
        double angle = wo.getRotationAngle();

        final AffineTransform rotate = AffineTransform.getRotateInstance(angle, rotateX, rotateY);
        g2d.transform(rotate);

        /**
         * Draw the image needed.
         */
        if(wo.getClass() == Asteroid.class){
            g2d.drawImage(imageLoader.getAsteroidImage(), paintX, paintY, bWidth, bHeight, gv);
        } else if(wo.getClass() == Player.class) {
            g2d.drawImage(imageLoader.getPlayerImage((Player) wo), paintX, paintY, bWidth, bHeight, gv);
            if(outOfCamera((Player) wo, gv)){
                drawPlayerIndicator(g2d, wo, cameraX, cameraY);
            }
        } else if(wo.getClass() == EngineParticle.class) {
            g2d.drawImage(imageLoader.getEngineParticleImage(), paintX, paintY, bWidth, bHeight, gv);
        } else if(wo.getClass() == Projectile.class) {
            g2d.drawImage(imageLoader.getProjectileImage((Projectile) wo), paintX, paintY, bWidth, bHeight, gv);
        } else if(wo.getClass() == AsteroidParticle.class) {
            g2d.drawImage(imageLoader.getAsteroidParticleImage(), paintX, paintY, bWidth, bHeight, gv);
        } else if(wo.getClass() == RedAsteroidParticle.class) {
            g2d.drawImage(imageLoader.getRedAsteroidParticleImage(), paintX, paintY, bWidth, bHeight, gv);
        } else if(wo.getClass() == RedProjectileDeathParticle.class) {
        	g2d.drawImage(imageLoader.getRedDeathParticleImage(), paintX, paintY, bWidth, bHeight, gv);
        } else if(wo.getClass() == SpacecraftDeathParticle.class){
        	g2d.drawImage(imageLoader.getRedDeathParticleImage(), paintX, paintY, bWidth, bHeight, gv);
        }
    }

    public boolean outOfCamera(Player p, GraphicalViewer gv){
        ZoneCoordinate zc = p.getZoneCoordinate();
        Coordinate c = p.getCoordinate();
        return true;
    }

    public void drawPlayerIndicator(Graphics2D g2d, WorldObject wo, int cameraX, int cameraY){

    }


}
