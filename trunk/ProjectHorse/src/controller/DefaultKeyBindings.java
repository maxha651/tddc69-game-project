package controller;

import game.Game;
import game.GameFrame;
import graphics.GraphicalViewer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 12/19/12
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultKeyBindings extends AbstractController {


    //other key coding
    private final static int SHOW_INFORMATION = KeyEvent.VK_I;
    private final static int DRAW_BOUNDS = KeyEvent.VK_B;
    private final static int SHOW_KEY_CODING = KeyEvent.VK_K;
    private final static int DRAW_CROSS = KeyEvent.VK_C;
    private final static int PAUSE = KeyEvent.VK_P;
    private final static int QUIT = KeyEvent.VK_Q;
    private final static int RESTART = KeyEvent.VK_R;
    private final static int PLAYER_1_FOCUS_CAMERA = KeyEvent.VK_F;

    GraphicalViewer viewer1;
    Game game;
    GameFrame gameFrame;

    public DefaultKeyBindings(GraphicalViewer viewer1, Game game, GameFrame gameFrame) {
        this.viewer1 = viewer1;
        this.game = game;
        this.gameFrame = gameFrame;
    }

    /**
     * Decides what key is pressed and acts accordingly
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode){
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
            case PAUSE :
                game.setPause(!game.isPause());
                break;
            case RESTART :
                gameFrame.reset();
                break;
            case QUIT :
                System.exit(0);
                break;
        }
    }


    /**
     * Decides what key is released and acts accordingly.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Decides what mouse button is pressed and acts accordingly.
     */
    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    /**
     * Decides what mouse button is released and acts accordingly.
     */
    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

}
