package graphics;

import model.GameModel;
import model.background.Asteroid;
import model.background.AsteroidParticle;
import model.background.EngineParticle;
import model.background.Projectile;
import model.character.Player;
import model.properties.Boundable;
import model.properties.Collideable;
import model.spacecraft.parts.Engine;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObject;
import model.world.WorldObjectContainer;
import resources.ImageLoader;

import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The graphical viewer is a standard 2D game viewer that paints a GameModel object.
 * Uses double buffering.
 * @author      Fredrik Max
 * @version     1.0
 * @since       2012-10-13
 */

public class GraphicalViewer extends Viewer {

    //model used in painting
    GameModel gameModel;
    BufferedImage bufferedImage;
    AffineTransform saved;

    //reference/pointer to the image loader used
    ImageLoader imageLoader;

    //default values / constants
    final static int DEFAULT_SCREEN_WIDTH_PX = 1366, DEFAULT_SCREEN_HEIGHT_PX = 768;
    final static int DEFAULT_STRING_SIZE = 14;
    final static Color DEFAULT_PAINT_COLOR = Color.WHITE;
    final static Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    final static Color DEFAULT_FONT_COLOR = Color.WHITE;

    //colors used for painting
    public Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
    public Color informationFontColor = DEFAULT_FONT_COLOR;
    private Color paintColor = DEFAULT_PAINT_COLOR; //used for painting anything else

    //controllers for painting
    public boolean lockOnPlayer = true;            //make camera move with player
    public boolean drawCross = false;              //draw a cross in middle of screen
    public boolean paintExtraInformation = true;   //paint extra information suchs as paint time, update time etc
    public boolean paintKeyBindings = true;        //make key bindings visible for the player
    public boolean paintWorldObjectBounds = false; //paint bounds on the screen

    //other controllers
    private long paintTime = 0;                    //for measuring elapsed time for 1 paint
    public int width;
    public int height;                      //graphical viewer width and height

    //camera position in the current zone
    private int cameraX = - DEFAULT_SCREEN_WIDTH_PX/2, cameraY = - DEFAULT_SCREEN_HEIGHT_PX/2;

    //how many pixels outside of screen width and height we should paint
    private final static int SCREEN_PADDING = 100;

    /**
     * Standard constructor that takes a gameModel and initializes the image loading
     */
    public GraphicalViewer(GameModel gameModel){
         this.gameModel = gameModel;
         this.imageLoader = new ImageLoader();
         this.width = DEFAULT_SCREEN_WIDTH_PX;
         this.height = DEFAULT_SCREEN_HEIGHT_PX;
         bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
    }

    /**
     *  Main paint method that paints the whole game.
     */
    @Override
    public void paintComponent(Graphics g){
        long begin = System.currentTimeMillis(); //time stamp for start of painting
        if(lockOnPlayer){
            lockCameraOnPlayer();
        }

        //initialize the double buffer
        Graphics2D g2d = bufferedImage.createGraphics();

        //start painting in correct order
        paintBackground(g2d);
        paintWorldObjects(g2d);
        if(paintExtraInformation){
            paintExtraInformation(g2d);
        }
        if(drawCross){
            drawCross(g2d);
        }

        //paint on the actual graphics object -- cast to Graphics2D so we can use built in drawImage method
        Graphics2D g2dComponent = (Graphics2D) g;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);

        paintTime = System.currentTimeMillis() - begin; //calculate time taken after painting finished
    }

    /**
     * Fills a rectangle over the whole canvas (color specified by backgroundColor variable)
     */
    public void paintBackground(Graphics2D g2d){
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);
    }

    /**
     * Paints all the world objects in proximity to the camera position. The world objects are
     * retrieved from the game model.
     */
    public void paintWorldObjects(Graphics2D g2d){
        final AffineTransform saved = g2d.getTransform();
        Player p = gameModel.getPlayer();
        ZoneCoordinate zs = p.getZoneCoordinate();

        // calculate start painting coordinate and end painting coordinate
        Coordinate start = new Coordinate(p.getCoordinate().getX() - width/2 - SCREEN_PADDING, p.getCoordinate().getY() - height/2 - SCREEN_PADDING);
        Coordinate stop = new Coordinate(p.getCoordinate().getX() + width/2 + SCREEN_PADDING, p.getCoordinate().getY() + height/2 + SCREEN_PADDING);

        // get all world objects close to the start and stop positions.
        WorldObjectContainer woc = gameModel.getAllObjectsInArea(zs, start, stop);

        for(WorldObject wo : woc){

            paintWorldObject(g2d, wo);
            g2d.setTransform(saved);
            if(paintWorldObjectBounds){
                if(Collideable.class.isAssignableFrom(wo.getClass())){
                    paintWorldObjectBounds(g2d, wo, Color.RED);
                }
            }
        }
    }

    public void paintWorldObjectBounds(Graphics2D g2d, WorldObject wo, Color c){
        g2d.setColor(c);

        Coordinate positionInZone = wo.getCoordinate();
        ZoneCoordinate zoneCoordinate = wo.getZoneCoordinate();
        double zoneSize = gameModel.getZoneSize();

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

    /**
     * Paints a single world object upon the g2d object
     */
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


        final AffineTransform rotate = AffineTransform.getRotateInstance(angle, rotateX, rotateY);
        g2d.transform(rotate);

        //random stuff for asteroid that can be removed but its fun
        if(wo.getClass() == Asteroid.class){
            g2d.drawImage(imageLoader.getAsteroidImage(), paintX, paintY, bWidth, bHeight, this);
        } else if(wo.getClass() == Player.class) {
            g2d.drawImage(imageLoader.getPlayerImage(gameModel.getPlayer()), paintX, paintY, bWidth, bHeight, this);
        } else if(wo.getClass() == EngineParticle.class) {
            g2d.drawImage(imageLoader.getEngineParticleImage(), paintX, paintY, bWidth, bHeight, this);
        } else if(wo.getClass() == Projectile.class) {
            g2d.drawImage(imageLoader.getProjectileImage((Projectile) wo), paintX, paintY, bWidth, bHeight, this);
        } else if(wo.getClass() == AsteroidParticle.class) {
            g2d.drawImage(imageLoader.getAsteroidParticleImage(), paintX, paintY, bWidth, bHeight, this);
        }


    }

    /**
     * Draws a cross in middle of screen.
     */
    public void drawCross(Graphics2D g2d){
        g2d.setColor(Color.GREEN);
        //draw vertical lines
        g2d.drawLine(width/2, 0, width/2, height);
        g2d.drawLine(0, height/2, width, height/2);
    }

    /**
     * Paints extra information about the GameModel and the GraphicalViewer on the screen
     */
    public void paintExtraInformation(Graphics2D g2d){

        Player p = gameModel.getPlayer();
        Engine e = p.getSpacecraft().getEngine();

        //initialize font and information container
        setFontToMonospace(g2d);
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

        //draw key bindings
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

        g2d.setColor(informationFontColor);
        ic.paint(g2d);
    }

    /**
     * Calculates the camera position from player coordinates.
     */
    public void lockCameraOnPlayer(){
        Coordinate positionInZone = gameModel.getPlayer().getCoordinate();
        ZoneCoordinate zoneCoordinate = gameModel.getPlayer().getZoneCoordinate();
        double zoneSize = gameModel.getZoneSize();

        int positionX =(int) (positionInZone.getX() + zoneSize*zoneCoordinate.getX());
        int positionY =(int) (positionInZone.getY() + zoneSize*zoneCoordinate.getY());

        cameraX = (int) (positionX - width / 2);
        cameraY = (int) (positionY - height / 2);
    }

    /**
     * Truncates a double to a number of decimals.
     * @param d
     * @param decimals
     * @return a truncated String with [decimals] decimals out of the double d.
     */
    static public String toTruncatedStr(double d, int decimals){
        return model.utility.strings.StringManipulator.toString(d, decimals);
    }

    //getters and setters
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

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

