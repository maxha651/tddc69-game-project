package graphics;

import model.GameModel;
import model.background.Asteroid;
import model.background.AsteroidParticle;
import model.background.EngineParticle;
import model.background.Projectile;
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

    GameModel gameModel;
    ImageLoader imageLoader;

    public WorldObjectPainter(GameModel gm){
        gameModel = gm;
        imageLoader = new ImageLoader();
    }

    public void paint(WorldObject wo, Graphics2D g2d, int cameraX, int cameraY, ImageObserver obs){

        Coordinate positionInZone = wo.getCoordinate();
        ZoneCoordinate zoneCoordinate = wo.getZoneCoordinate();
        double zoneSize = gameModel.getZoneSize();

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

        final AffineTransform saved = g2d.getTransform();
        final AffineTransform rotate = AffineTransform.getRotateInstance(angle, rotateX, rotateY);
        g2d.transform(rotate);

        //random stuff for asteroid that can be removed but its fun
        if(wo.getClass() == Asteroid.class){
            g2d.drawImage(imageLoader.getAsteroidImage(), paintX, paintY, bWidth, bHeight, obs);
        } else if(wo.getClass() == Player.class) {
            g2d.drawImage(imageLoader.getPlayerImage(gameModel.getPlayer()), paintX, paintY, bWidth, bHeight, obs);
        } else if(wo.getClass() == EngineParticle.class) {
            g2d.drawImage(imageLoader.getEngineParticleImage(), paintX, paintY, bWidth, bHeight, obs);
        } else if(wo.getClass() == Projectile.class) {
            g2d.drawImage(imageLoader.getProjectileImage((Projectile) wo), paintX, paintY, bWidth, bHeight, obs);
        } else if(wo.getClass() == AsteroidParticle.class) {
            g2d.drawImage(imageLoader.getAsteroidParticleImage(), paintX, paintY, bWidth, bHeight, obs);
        }
        g2d.setTransform(saved);


    }
}
