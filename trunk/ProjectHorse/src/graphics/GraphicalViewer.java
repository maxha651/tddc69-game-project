package graphics;

import model.GameModel;
import model.character.Player;

import javax.swing.*;
import java.awt.*;

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
    Color DEFAULT_PAINT_COLOR = Color.WHITE;
    Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
    Color paintColor = DEFAULT_PAINT_COLOR;
    int width, height;

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
        Graphics2D g2d = (Graphics2D) g;

        paintBackground(g2d);
        paintPlayer(g2d);

    }

    public void paintBackground(Graphics g2d){
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);
    }

    public void paintPlayer(Graphics g2d){
        g2d.setColor(paintColor);
        g2d.fillRect((int)gameModel.getPlayer().getPosition().getX(),
                     (int)gameModel.getPlayer().getPosition().getY(),
                     (int)gameModel.getPlayer().getSpacecraft().getBoundingRectangle().getWidth(),
                     (int)gameModel.getPlayer().getSpacecraft().getBoundingRectangle().getHeight());
    }

}
