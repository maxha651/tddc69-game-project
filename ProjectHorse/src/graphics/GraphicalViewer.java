package graphics;

import model.GameModel;
import model.character.Player;
import model.properties.Collideable;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObject;
import resources.ImageLoader;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 * The graphical viewer is a standard 2D game viewer that paints a GameModel object.
 * Uses double buffering.
 * @author      Fredrik Max
 * @version     1.0
 * @since       2012-10-13
 */

public class GraphicalViewer extends Viewer {

    //model used in painting
    public GameModel gameModel;
    public Player p;
    private BufferedImage bufferedImage;
    private AffineTransform saved;

    //reference/pointer to the image loader used
    private ImageLoader imageLoader;
    private WorldObjectPainter painter;

    //default values / constants
    final static int PANEL_PADDING_VERT = 80; //pads the width  of the graphical viewer
    final static int PANEL_PADDING_HORI = 20; //pads the height of the graphical viewer
    final static int DEFAULT_FONT_SIZE = 14;
    final static int PLAYER_INFORMATION_FONT_PAD = 25;
    final static int POINTER_DISTANCE_FROM_EDGE = 50;
    final static int POINTER_SIZE = 10;
    final static Color POINTER_COLOR = Color.RED;
    final static Color DEFAULT_PAINT_COLOR = Color.WHITE;
    final static Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    final static Color DEFAULT_FONT_COLOR = Color.WHITE;
    final static Color DEFAULT_BOUNDS_COLOR = Color.GREEN;
    final static Color DEFAULT_CROSS_COLOR = Color.blue;
    private final static int SCREEN_PADDING = 100; //how many pixels outside of screen width and height we should paint

    //colors used for painting
    public Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
    public Color informationFontColor = DEFAULT_FONT_COLOR;
    private Color paintColor = DEFAULT_PAINT_COLOR; //used for painting anything else

    //controllers for painting
    public boolean lockOnPlayer = true;             //make camera move with player
    public boolean drawCross = false;               //draw a cross in middle of screen
    public boolean paintExtraInformation = false;   //paint extra information suchs as paint time, update time etc
    public boolean paintKeyBindings = false;        //make key bindings visible for the player
    public boolean paintWorldObjectBounds = false;  //paint bounds of type WorldObjects on the screen
    public boolean drawScore = true;               //draw player score
    public boolean drawPlayerInformation = true;   //draw player information such as health etc

    //other controllers
    private long paintTime = 0;                    //for measuring elapsed time for 1 paint
    public int width;
    public int height;                      //graphical viewer width and height

    //camera position in the current zone
    private int cameraX, cameraY;
    
    /**
     * Standard constructor that takes a gameModel and initializes the image loader
     */
    public GraphicalViewer(GameModel gameModel, Player p, ImageLoader imageLoader){
         this.p = p;
         this.gameModel = gameModel;
         this.imageLoader = imageLoader;
         this.painter = new WorldObjectPainter(imageLoader, gameModel);
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         this.width = (screenSize.width - PANEL_PADDING_HORI)/2;
         this.height = (screenSize.height - PANEL_PADDING_VERT);
         this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
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
        drawPointerToOtherPlayer(g2d);

        //Draws a pointer to the other player (broken)
        //drawPointerToOtherPlayer(g2d);
        if(paintExtraInformation){
            paintExtraInformation(g2d);
        }
        if(drawCross){
            drawCross(g2d);
        }
        if(drawScore){
            drawScore(g2d);
        }
        if(drawPlayerInformation){
            drawPlayerInformation(g2d);
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

        ZoneCoordinate zs = p.getZoneCoordinate();

        // calculate start painting coordinate and end painting coordinate
        Coordinate start = new Coordinate(p.getCoordinate().getX() - width/2 - SCREEN_PADDING, p.getCoordinate().getY() - height/2 - SCREEN_PADDING);
        Coordinate stop = new Coordinate(p.getCoordinate().getX() + width/2 + SCREEN_PADDING, p.getCoordinate().getY() + height/2 + SCREEN_PADDING);

        // get all world objects close to the start and stop positions.
        Collection<WorldObject> worldObjects = gameModel.getAllObjectsInArea(zs, start, stop);

        for(WorldObject wo : worldObjects){
            painter.paintWorldObject(wo, g2d, cameraX, cameraY, this);
            g2d.setTransform(saved);
            if(paintWorldObjectBounds){
                if(Collideable.class.isAssignableFrom(wo.getClass())){
                    painter.paintWorldObjectBounds(wo, g2d, cameraX, cameraY, DEFAULT_BOUNDS_COLOR);
                }
            }
        }
    }

    /**
     * Draws a cross in middle of screen.
     */
    public void drawCross(Graphics2D g2d){
        g2d.setColor(DEFAULT_CROSS_COLOR);
        //draw vertical lines
        g2d.drawLine(width/2, 0, width/2, height);
        g2d.drawLine(0, height/2, width, height/2);
    }

    /**
     * Draws the score in middle of the screen
     */
    public void drawScore(Graphics2D g2d){
        
    	setFontToMonospace(g2d, 16);
        int shadowPad = 2; 
        
        g2d.setColor(p.getColor());
        g2d.drawString("score : " + p.getScore(), PLAYER_INFORMATION_FONT_PAD/2, PLAYER_INFORMATION_FONT_PAD *2);
        g2d.setColor(Color.white);
        g2d.drawString("score : " + p.getScore(), (PLAYER_INFORMATION_FONT_PAD) / 2 - shadowPad, (PLAYER_INFORMATION_FONT_PAD) * 2 - shadowPad);
    }

    /**
     * Draw player information on the screen.
     * @param g2d
     */
    public void drawPlayerInformation(Graphics2D g2d){

        int health = p.getHealth();
        String healthString;
    	setFontToMonospace(g2d, 16);
        int shadowPad = 2;
        if(health > 0){
            healthString = Integer.toString(health);
        } else {
            healthString = "DEAD";
        }

        g2d.setColor(p.getColor());
        g2d.drawString("health : " + healthString, PLAYER_INFORMATION_FONT_PAD/2 , PLAYER_INFORMATION_FONT_PAD );
        g2d.setColor(Color.white);
        g2d.drawString("health : " + healthString, (PLAYER_INFORMATION_FONT_PAD)/2 - shadowPad, PLAYER_INFORMATION_FONT_PAD - shadowPad);
    }

    /**
     * Paints extra information about the GameModel and the GraphicalViewer on the screen
     */
    public void paintExtraInformation(Graphics2D g2d){

        //initialize font and information container
        setFontToMonospace(g2d, DEFAULT_FONT_SIZE);
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
        ic.add("Angle between pl : " + gameModel.getPlayer(1).getAngleTo(gameModel.getPlayer(2), gameModel.getZoneSize()));
        ic.add("World objects #  : " + gameModel.numberOfWorldObjects);
        ic.add("Zones #          : " + gameModel.numberOfZones());
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
            ic.add("P     : Pause game");
            ic.add("ESC   : End game");
            ic.add("R     : Restart game");
        }

        g2d.setColor(informationFontColor);
        ic.paint(g2d);
    }

    /**
     * Calculates the camera position from player coordinates.
     */
    public void lockCameraOnPlayer(){
        Coordinate positionInZone = p.getCoordinate();
        ZoneCoordinate zoneCoordinate = p.getZoneCoordinate();
        double zoneSize = gameModel.getZoneSize();

        int positionX =(int) (positionInZone.getX() + zoneSize*zoneCoordinate.getX());
        int positionY =(int) (positionInZone.getY() + zoneSize*zoneCoordinate.getY());

        cameraX = (positionX - width / 2);
        cameraY = (positionY - height / 2);
    }

    /**
     * Draws a box in the direction of the other player if the other
     * player is outside the camera
     */
    public void drawPointerToOtherPlayer(Graphics2D g2d){
        Player otherPlayer = gameModel.getOtherPlayer(p);
        double zoneSize = gameModel.getZoneSize();

        if(outOfCamera(otherPlayer)){
            double angle = p.getAngleTo(otherPlayer, zoneSize);

            int positionX = (int) (p.getZoneCoordinate().getX()*zoneSize + p.getCoordinate().getX());
            int positionY = (int) (p.getZoneCoordinate().getY()*zoneSize + p.getCoordinate().getY());

            int paintX = (int) (-cameraX + positionX + Math.cos(angle)*(width/2 -POINTER_DISTANCE_FROM_EDGE));
            int paintY = (int) (-cameraY + positionY + Math.sin(angle)*(height/2 -POINTER_DISTANCE_FROM_EDGE));

            g2d.setColor(POINTER_COLOR);
            g2d.fillOval(paintX, paintY, POINTER_SIZE, POINTER_SIZE);

            g2d.setColor(Color.white);
            g2d.drawString("ENEMY", paintX - POINTER_SIZE*5, paintY);
        }
    }

    /**
     * Checking if a player p is outside of camera.
     * @param p
     * @return
     */
    public boolean outOfCamera(Player p){
        double zoneSize = (int) gameModel.getZoneSize();

        ZoneCoordinate zc = p.getZoneCoordinate();
        Coordinate c = p.getCoordinate();
        int cameraX = getCameraX();
        int cameraY = getCameraY();

        double posX = p.getCoordinate().getX() + zc.getX()*zoneSize;
        double posY = p.getCoordinate().getY() + zc.getY()*zoneSize;

        return !(posX < cameraX + width && posX > cameraX &&
                posY < cameraY + height && posY > cameraY);
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
    static public void setFontToMonospace(Graphics2D g2d, int size){
        Font f = new Font("MONOSPACED", Font.BOLD, size);
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

    public int getCameraX() {
        return cameraX;
    }

    public void setCameraX(int cameraX) {
        this.cameraX = cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }

    public void setCameraY(int cameraY) {
        this.cameraY = cameraY;
    }
}

