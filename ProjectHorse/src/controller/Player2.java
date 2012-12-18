package controller;

import model.GameModel;
import model.character.Player;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 12/19/12
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player2 extends AbstractController{

    //player 2 key coding
    private final static int PLAYER_2_TURN_LEFT = KeyEvent.VK_LEFT;
    private final static int PLAYER_2_FIRE = KeyEvent.VK_ENTER;
    private final static int PLAYER_2_TURN_RIGHT = KeyEvent.VK_RIGHT;
    private final static int PLAYER_2_THROTTLE = KeyEvent.VK_UP;
    private final static int PLAYER_2_THROTTLE_MOUSE = MouseEvent.BUTTON3;
    private final static int PLAYER_2_FIRE_MOUSE = MouseEvent.BUTTON1;

    Player player;

    public Player2(Player player) {
        this.player = player;
    }

    /**
     * Decides what key is pressed and acts accordingly
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode){
            case PLAYER_2_TURN_LEFT:
                player.setTurnLeftRequest(true);
                break;
            case PLAYER_2_TURN_RIGHT:
                player.setTurnRightRequest(true);
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
            case PLAYER_2_TURN_LEFT:
                player.setTurnLeftRequest(false);
                break;
            case PLAYER_2_TURN_RIGHT:
                player.setTurnRightRequest(false);
                break;
        }
    }

    /**
     * Decides what mouse button is pressed and acts accordingly.
     */
    @Override
    public void mousePressed(MouseEvent arg0) {
        int mouseCode = arg0.getButton();
        switch(mouseCode){
            case PLAYER_2_FIRE_MOUSE:
                player.setFireRequest(true);
                break;
            case PLAYER_2_THROTTLE_MOUSE:
                player.setAccelerationRequest(true);
                break;
        }
    }

    /**
     * Decides what mouse button is released and acts accordingly.
     */
    @Override
    public void mouseReleased(MouseEvent arg0) {
        int mouseCode = arg0.getButton();
        switch(mouseCode){
            case PLAYER_2_FIRE_MOUSE:
                player.setFireRequest(false);
                break;
            case PLAYER_2_THROTTLE_MOUSE:
                player.setAccelerationRequest(false);
                break;
        }
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
