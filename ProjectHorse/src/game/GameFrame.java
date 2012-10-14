package game;

import graphics.GraphicalViewer;
import model.GameModel;
import resources.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-20
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class GameFrame extends JFrame implements KeyListener{

    JMenuBar menuBar;
    JMenu fileMenu, settingsMenu, viewMenu;
    static final String FRAME_TITLE = "Space Explorer";
    GameModel gameModel;
    GraphicalViewer viewer1;
    GraphicalViewer viewer2;

    //key coding

    //player 1
    final static int PLAYER_1_FIRE = KeyEvent.VK_SPACE;
    final static int PLAYER_1_TURN_LEFT = KeyEvent.VK_A;
    final static int PLAYER_1_TURN_RIGHT = KeyEvent.VK_D;
    final static int PLAYER_1_THROTTLE = KeyEvent.VK_W;
    final static int PLAYER_1_FOCUS_CAMERA = KeyEvent.VK_F;

    //player 2
    final static int PLAYER_2_TURN_LEFT = KeyEvent.VK_LEFT;
    final static int PLAYER_2_FIRE = KeyEvent.VK_ENTER;
    final static int PLAYER_2_TURN_RIGHT = KeyEvent.VK_RIGHT;
    final static int PLAYER_2_THROTTLE = KeyEvent.VK_UP;

    //other
    final static int SHOW_INFORMATION = KeyEvent.VK_I;
    final static int DRAW_BOUNDS = KeyEvent.VK_B;
    final static int SHOW_KEY_CODING = KeyEvent.VK_K;
    final static int DRAW_CROSS = KeyEvent.VK_C;



    public GameFrame(GameModel gameModel) {

        super(FRAME_TITLE);

        this.gameModel = gameModel;
        this.addKeyListener(this);

        ImageLoader img = new ImageLoader();
        viewer1 = new GraphicalViewer(gameModel, gameModel.getPlayer(1), img);
        viewer2 = new GraphicalViewer(gameModel, gameModel.getPlayer(2), img);

        this.setLayout(new FlowLayout());

        this.add(viewer1);
        this.add(viewer2);

        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }



    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Player p = gameModel.getPlayer();
        switch(keyCode){
            case PLAYER_1_THROTTLE:
                gameModel.getPlayer(1).accelerationRequest = true;
                break;
            case PLAYER_1_TURN_LEFT:
                gameModel.getPlayer(1).turnLeftRequest = true;
                break;
            case PLAYER_1_TURN_RIGHT:
                gameModel.getPlayer(1).turnRightRequest = true;
                break;
            case PLAYER_1_FIRE:
                gameModel.getPlayer(1).fireRequest = true;
                break;
            case PLAYER_2_THROTTLE:
                gameModel.getPlayer(2).accelerationRequest = true;
                break;
            case PLAYER_2_TURN_LEFT:
                gameModel.getPlayer(2).turnLeftRequest = true;
                break;
            case PLAYER_2_TURN_RIGHT:
                gameModel.getPlayer(2).turnRightRequest = true;
                break;
            case PLAYER_2_FIRE:
                gameModel.getPlayer(2).fireRequest = true;
                break;
            case PLAYER_1_FOCUS_CAMERA:
                viewer1.setLockOnPlayer(!viewer1.isLockOnPlayer());
                break;
            case SHOW_INFORMATION:
                viewer1.setPaintExtraInformation(!viewer1.isPaintExtraInformation());
                break;
            case DRAW_CROSS:
                viewer1.setDrawCross(!viewer1.isDrawCross());
                break;
            case SHOW_KEY_CODING:
                viewer1.setPaintKeyBindings(!viewer1.isPaintKeyBindings());
                break;
            case DRAW_BOUNDS :
                viewer1.setPaintWorldObjectBounds(!viewer1.isPaintWorldObjectBounds());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode){
            case PLAYER_1_THROTTLE:
                gameModel.getPlayer(1).accelerationRequest = false;
                break;
            case PLAYER_1_TURN_LEFT:
                gameModel.getPlayer(1).turnLeftRequest = false;
                break;
            case PLAYER_1_TURN_RIGHT:
                gameModel.getPlayer(1).turnRightRequest = false;
                break;
            case PLAYER_1_FIRE:
                gameModel.getPlayer(1).fireRequest = false;
                break;
            case PLAYER_2_THROTTLE:
                gameModel.getPlayer(2).accelerationRequest = false;
                break;
            case PLAYER_2_TURN_LEFT:
                gameModel.getPlayer(2).turnLeftRequest = false;
                break;
            case PLAYER_2_TURN_RIGHT:
                gameModel.getPlayer(2).turnRightRequest = false;
                break;
            case PLAYER_2_FIRE:
                gameModel.getPlayer(2).fireRequest = false;
                break;
        }

    }
}
