package graphics;

import model.GameModel;
import model.character.Player;
import model.spacecraft.Engine;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;

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
        g2d.setColor(paintColor);

        Coordinate position = gameModel.getPlayer().getCoordinate();
        Spacecraft spacecraft = gameModel.getPlayer().getSpacecraft();
        Player player = gameModel.getPlayer();

        double positionX = position.getX();
        double positionY = position.getY();

        int paintX;
        int paintY;

        if(lockOnPlayer){
            paintX = (int) Math.ceil((-cameraX + positionX) - spacecraft.getWidth() / 2);
            paintY = (int) Math.ceil((-cameraY + positionY) - spacecraft.getWidth() / 2);
            cameraX = (int) Math.ceil(positionX - width / 2);
            cameraY = (int) Math.ceil(positionY - height / 2);
        } else {
            paintX = (int) Math.ceil((-cameraX + positionX) - spacecraft.getWidth() / 2);
            paintY = (int) Math.ceil((-cameraY + positionY) - spacecraft.getWidth() / 2);
        }

        int paintWidth = (int) spacecraft.getWidth();
        int paintHeight = (int) spacecraft.getHeight();

        g2d.fillRect(paintX, paintY, paintWidth, paintHeight);
    }

    public void paintWorldObjects(Graphics2D g2d){

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
        String velX = toTruncatedStr(e.getVelocityX(), 1);
        String velY = toTruncatedStr(e.getVelocityY(), 1);

        ic.add("Player velocity  : " + velX + ", " + velY);
        ic.add("Player absveloc  : " + toTruncatedStr(e.getAbsoluteVelocity(), 1));
        ic.add("Player angle     : " + toTruncatedStr(Math.toDegrees(p.getRotationAngle()), 1) + " (degrees), " + toTruncatedStr(p.getRotationAngle(), 1) + " (radians)");
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

