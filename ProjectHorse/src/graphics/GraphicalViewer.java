package graphics;

import model.GameModel;
import model.background.Asteroid;
import model.background.Projectile;
import model.character.Player;
import model.interfaces.Boundable;
import model.spacecraft.Cargo;
import model.spacecraft.Engine;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObject;
import model.world.WorldObjectContainer;
import resources.ImageLoader;

import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-21
 * Time: 01:12
 * To change this template use File | Settings | File Templates.
 */
public class GraphicalViewer extends Viewer {
    GameModel gameModel;
    ImageLoader imageLoader;

    final static int DEFAULT_SCREEN_WIDTH_PX = 1366, DEFAULT_SCREEN_HEIGHT_PX = 768;
    final static int DEFAULT_STRING_SIZE = 14;
    final static Color DEFAULT_PAINT_COLOR = Color.WHITE;
    final static Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    final static Color DEFAULT_FONT_COLOR = Color.WHITE;

    Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
    Color informationFontColor = DEFAULT_FONT_COLOR;
    Color paintColor = DEFAULT_PAINT_COLOR;

    int width, height;
    boolean lockOnPlayer = true;
    boolean drawCross = false;
    boolean paintExtraInformation = true;
    long paintTime = 0;
    boolean paintKeyBindings = true;
    boolean paintWorldObjectBounds = false;

    int cameraX = - DEFAULT_SCREEN_WIDTH_PX/2, cameraY = - DEFAULT_SCREEN_HEIGHT_PX/2;

    public GraphicalViewer(GameModel gameModel){
         this.gameModel = gameModel;
         this.imageLoader = new ImageLoader();
         this.width = DEFAULT_SCREEN_WIDTH_PX;
         this.height = DEFAULT_SCREEN_HEIGHT_PX;
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g){
        long begin = System.currentTimeMillis();

        BufferedImage bufferedImage = new BufferedImage(DEFAULT_SCREEN_WIDTH_PX, DEFAULT_SCREEN_HEIGHT_PX, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bufferedImage.createGraphics();


        paintBackground(g2d);
        paintBackgroundObjects(g2d);
        if(drawCross){
            drawCross(g2d);
        }
        if(lockOnPlayer){
            lockCameraOnPlayer();
        }
        paintWorldObjects(g2d);
        if(paintExtraInformation){
            paintExtraInformation(g2d);
        }


        Graphics2D g2dComponent = (Graphics2D) g;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);

        paintTime = System.currentTimeMillis() - begin;
    }

    public void paintBackground(Graphics2D g2d){
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);
    }

    public void paintBackgroundObjects(Graphics2D g2d){

    }




    public void paintWorldObjects(Graphics2D g2d){
        Player p = gameModel.getPlayer();
        ZoneCoordinate zs = p.getZoneCoordinate();

        Coordinate start = new Coordinate(p.getCoordinate().getX() - width/2, p.getCoordinate().getY() - width/2);
        Coordinate stop = new Coordinate(p.getCoordinate().getX() + width/2, p.getCoordinate().getY() + width/2);
        WorldObjectContainer woc = gameModel.getAllObjectsInArea(zs, start, stop);
        WorldObject wo;

        for(int i = 0; i < woc.size(); i++){
            wo = woc.get(i);

            //paint boundables
            //paintWorldObjectBounds(g2d, wo, Color.RED);
            if(paintWorldObjectBounds){
                paintWorldObjectBounds(g2d, wo, Color.RED);
            }
            paintWorldObject(g2d, wo);
        }
    }

    public void paintWorldObjectBounds(Graphics2D g2d, WorldObject wo, Color c){
        if(wo.getClass() == Player.class){
            g2d.setColor(Color.WHITE);
        }
        else{
            g2d.setColor(c);
        }

        Coordinate positionInZone = wo.getCoordinate();
        ZoneCoordinate zoneCoordinate = wo.getZoneCoordinate();
        double zoneSize = gameModel.getZoneSize();

        int positionX =(int) (positionInZone.getX() + zoneSize*zoneCoordinate.getX());
        int positionY =(int) (positionInZone.getY() + zoneSize*zoneCoordinate.getY());

        int bWidth = (int) wo.getBounds().getWidth();
        int bHeight = (int) wo.getBounds().getHeight();

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


        g2d.draw(new Rectangle(paintX, paintY, bWidth, bHeight));

        g2d.setTransform(saved);
        //random stuff for asteroid that can be removed but its fun

        /*
        if(wo.getClass() == Asteroid.class){
            g2d.setColor(Color.WHITE);
            g2d.translate(paintX, paintY);
            Font f = getFont().deriveFont(Font.TRUETYPE_FONT, 12);

            GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), "I am an asteroid");
            g2d.draw(v.getOutline());


        } g2d.setTransform(saved);
        */



    }

    public void paintWorldObject(Graphics2D g2d, WorldObject wo){


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
            g2d.drawImage(imageLoader.getAsteroidImage(), paintX, paintY, bWidth, bHeight, this);
        } else if(wo.getClass() == Player.class) {
            g2d.drawImage(imageLoader.getPlayerImage(gameModel.getPlayer()), paintX, paintY, bWidth, bHeight, this);
        } else if(wo.getClass() == Projectile.class) {
            g2d.drawImage(imageLoader.getProjectileImage((Projectile) wo), paintX, paintY, bWidth, bHeight, this);
        }
        g2d.setTransform(saved);

    }



    public void drawCross(Graphics2D g2d){
        g2d.setColor(Color.GREEN);
        //draw vertical lines
        g2d.drawLine(width/2, 0, width/2, height);
        g2d.drawLine(0, height/2, width, height/2);
    }

    public void paintExtraInformation(Graphics2D g2d){

        setFontToMonospace(g2d);
        Player p = gameModel.getPlayer();
        Engine e = p.getSpacecraft().getEngine();
        InformationContainer ic = new InformationContainer(20, 20);

        //the strings to draw
        String playerCoordinateString = toTruncatedStr(p.getCoordinate().getX(), 1) + ", " + toTruncatedStr(p.getCoordinate().getY(), 1);
        ic.add("Camera position  : " + cameraX + ", " + cameraY);
        ic.add("Player position  : " + playerCoordinateString);
        ic.add("Game version     : " + gameModel.VERSION);
        String velX = toTruncatedStr(p.getVelocityX(), 1);
        String velY = toTruncatedStr(p.getVelocityY(), 1);

        ic.add("Player velocity  : " + velX + ", " + velY);
        ic.add("Player absveloc  : " + toTruncatedStr(p.getAbsoluteVelocity(), 1));
        ic.add("Player angle     : " + toTruncatedStr(Math.toDegrees(p.getRotationAngle()), 1) + " (degrees), " + toTruncatedStr(p.getRotationAngle(), 1) + " (radians)");
        playerCoordinateString = (p.getZoneCoordinate().getX() + ", " + p.getZoneCoordinate().getY());
        ic.add("Player zone coord: " + playerCoordinateString);
        ic.add("World objects #  : " + gameModel.numberOfWorldObjects);
        ic.add("Update Time (ms) : " + gameModel.updateTime);
        ic.add("Paint Time  (ms) : " + this.paintTime);
        ic.add("Tick#(upd. done) : " + gameModel.tick);

        if(paintKeyBindings){
            ic.add("         ");
            ic.add("SPACECRAFT CONTROLS");
            ic.add("W     : Thrust");
            ic.add("A     : Rotate left");
            ic.add("D     : Rotate right");
            ic.add("SPACE : FIRE");
            ic.add("         ");
            ic.add("OTHER CONTROLS");
            ic.add("F     : Toggle camera lock");
            ic.add("I     : Toggle dev information");
            ic.add("K     : Toggle show keybinding");
            ic.add("C     : Toggle draw cross");
            ic.add("B     : Toggle bounds");

        }
        //draw the strings
        g2d.setColor(informationFontColor);
        ic.paint(g2d);
    }

    public void lockCameraOnPlayer(){
        Coordinate positionInZone = gameModel.getPlayer().getCoordinate();
        ZoneCoordinate zoneCoordinate = gameModel.getPlayer().getZoneCoordinate();
        double zoneSize = gameModel.getZoneSize();

        int positionX =(int) (positionInZone.getX() + zoneSize*zoneCoordinate.getX());
        int positionY =(int) (positionInZone.getY() + zoneSize*zoneCoordinate.getY());

        cameraX = (int) (positionX - width / 2);
        cameraY = (int) (positionY - height / 2);
    }

    static public String toTruncatedStr(double d, int decimals){
        return model.utility.strings.StringManipulator.toString(d, decimals);
    }

    static public void setFontToMonospace(Graphics2D g2d){
        Font f = new Font("MONOSPACED", Font.BOLD, DEFAULT_STRING_SIZE);
        g2d.setFont(f);

    }


    public boolean isLockOnPlayer() {
        return lockOnPlayer;
    }

    public void setLockOnPlayer(boolean lockOnPlayer) {
        this.lockOnPlayer = lockOnPlayer;
    }

    public boolean isPaintExtraInformation() {
        return paintExtraInformation;
    }

    public void setPaintExtraInformation(boolean paintExtraInformation) {
        this.paintExtraInformation = paintExtraInformation;
    }

    public boolean isDrawCross() {
        return drawCross;
    }

    public void setDrawCross(boolean drawCross) {
        this.drawCross = drawCross;
    }

    public boolean isPaintKeyBindings() {
        return paintKeyBindings;
    }

    public void setPaintKeyBindings(boolean paintKeyBindings) {
        this.paintKeyBindings = paintKeyBindings;
    }

    public boolean isPaintWorldObjectBounds() {
        return paintWorldObjectBounds;
    }

    public void setPaintWorldObjectBounds(boolean paintWorldObjectBounds) {
        this.paintWorldObjectBounds = paintWorldObjectBounds;
    }
}

