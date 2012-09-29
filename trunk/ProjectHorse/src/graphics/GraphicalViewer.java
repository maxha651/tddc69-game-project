package graphics;

import model.GameModel;
import model.character.Player;
import model.interfaces.Boundable;
import model.spacecraft.Cargo;
import model.spacecraft.Engine;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObject;
import model.world.WorldObjectContainer;

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
    int cameraX = - DEFAULT_SCREEN_WIDTH_PX/2, cameraY = - DEFAULT_SCREEN_HEIGHT_PX/2;

    public GraphicalViewer(GameModel gameModel){
         this.gameModel = gameModel;
         this.width = DEFAULT_SCREEN_WIDTH_PX;
         this.height = DEFAULT_SCREEN_HEIGHT_PX;
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g){


        BufferedImage bufferedImage = new BufferedImage(DEFAULT_SCREEN_WIDTH_PX, DEFAULT_SCREEN_HEIGHT_PX, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bufferedImage.createGraphics();

        paintBackground(g2d);
        paintBackgroundObjects(g2d);
        paintPlayer(g2d);
        paintWorldObjects(g2d);
        paintExtraInformation(g2d);

        Graphics2D g2dComponent = (Graphics2D) g;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);
    }

    public void paintBackground(Graphics2D g2d){
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);
    }

    public void paintBackgroundObjects(Graphics2D g2d){

    }

    public void paintPlayer(Graphics2D g2d){

        Coordinate positionInZone = gameModel.getPlayer().getCoordinate();
        ZoneCoordinate zoneCoordinate = gameModel.getPlayer().getZoneCoordinate();
        double zoneSize = gameModel.getZoneSize();
        Player player = gameModel.getPlayer();

        int positionX =(int) (positionInZone.getX() + zoneSize*zoneCoordinate.getX());
        int positionY =(int) (positionInZone.getY() + zoneSize*zoneCoordinate.getY());

        if(lockOnPlayer){
            cameraX = (int) (positionX - width / 2);
            cameraY = (int) (positionY - height / 2);
        }

        paintWorldObjectBounds(g2d, player, paintColor);
    }

    public void paintWorldObjects(Graphics2D g2d){
        Player p = gameModel.getPlayer();
        ZoneCoordinate zs = p.getZoneCoordinate();

        Coordinate start = new Coordinate(p.getCoordinate().getX() - 500, p.getCoordinate().getY() - 400);
        Coordinate stop = new Coordinate(p.getCoordinate().getX() + 500, p.getCoordinate().getY() + 400);
        WorldObjectContainer woc = gameModel.getAllObjectsInArea(zs, start, stop);
        WorldObject wo;

        for(int i = 0; i < woc.size(); i++){
            wo = woc.get(i);

            //paint boundables
            paintWorldObjectBounds(g2d, wo, Color.RED);
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

        paintX = (int) ((-cameraX + positionX) - bWidth / 2);
        paintY = (int) ((-cameraY + positionY) - bHeight / 2);

        paintBounds(g2d, paintX, paintY, bWidth, bHeight);
    }

    public void paintBounds(Graphics2D g2d, int x, int y, int width, int height){
        g2d.fillRect(x, y, width, height);
    }

    public void paintExtraInformation(Graphics2D g2d){
        g2d.setColor(informationFontColor);
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
        playerCoordinateString = toTruncatedStr(p.getZoneCoordinate().getX(), 0) + ", " + toTruncatedStr(p.getZoneCoordinate().getY(), 0);
        ic.add("Player zone coord: " + playerCoordinateString);
        ic.add("World objects #  : " + gameModel.numberOfWorldObjects);
        ic.add("Tick Update Time : " + gameModel.updateTime);
        //draw the strings
        ic.paint(g2d);
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
}

