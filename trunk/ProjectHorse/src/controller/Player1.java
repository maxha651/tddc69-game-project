package controller;

import model.GameModel;
import model.character.Player;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 12/18/12
 * Time: 11:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player1 extends AbstractController {

    //player 1 key coding
    private final static int PLAYER_1_FIRE = KeyEvent.VK_SPACE;
    private final static int PLAYER_1_TURN_LEFT = KeyEvent.VK_A;
    private final static int PLAYER_1_TURN_RIGHT = KeyEvent.VK_D;
    private final static int PLAYER_1_THROTTLE = KeyEvent.VK_W;
    private final static int PLAYER_1_FOCUS_CAMERA = KeyEvent.VK_F;

    Player player;

    public Player1(Player player) {
        this.player = player;
    }

    /**
     * Decides what key is pressed and acts accordingly
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Player p = gameModel.getPlayer();
        switch(keyCode){
            case PLAYER_1_THROTTLE:
                player.setAccelerationRequest(true);
                break;
            case PLAYER_1_TURN_LEFT:
                player.setTurnLeftRequest(true);
                break;
            case PLAYER_1_TURN_RIGHT:
                player.setTurnRightRequest(true);
                break;
            case PLAYER_1_FIRE:
                player.setFireRequest(true);
                break;
        }
    }


    /**
     * Decides what key is released and acts accordingly.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode){
            case PLAYER_1_THROTTLE:
                player.setAccelerationRequest(false);
                break;
            case PLAYER_1_TURN_LEFT:
                player.setTurnLeftRequest(false);
                break;
            case PLAYER_1_TURN_RIGHT:
                player.setTurnRightRequest(false);
                break;
            case PLAYER_1_FIRE:
                player.setFireRequest(false);
                break;
        }
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
        //To change body of implemented methods use File | Settings | File Templates.
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
