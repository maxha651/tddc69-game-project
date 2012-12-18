package game;

import graphics.GraphicalViewer;
import model.GameModel;
import resources.BasicImageLoader;
import resources.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Main frame controlling a GameModel and a GraphicalViewer with KeyListeners and MouseListeners
 */
public class GameFrame extends JFrame implements KeyListener, MouseListener{

	//declarations and default initializations
    private static final String FRAME_TITLE = "Space Explorer";
    private GameModel gameModel;
    private Game game; //used for pausing/unpausing/destroying game
    private GraphicalViewer viewer1;
    private GraphicalViewer viewer2;

    //player 1 key coding
    private final static int PLAYER_1_FIRE = KeyEvent.VK_SPACE;
    private final static int PLAYER_1_TURN_LEFT = KeyEvent.VK_A;
    private final static int PLAYER_1_TURN_RIGHT = KeyEvent.VK_D;
    private final static int PLAYER_1_THROTTLE = KeyEvent.VK_W;
    private final static int PLAYER_1_FOCUS_CAMERA = KeyEvent.VK_F;

    //player 2 key coding
    private final static int PLAYER_2_TURN_LEFT = KeyEvent.VK_LEFT;
    private final static int PLAYER_2_FIRE = KeyEvent.VK_ENTER;
    private final static int PLAYER_2_TURN_RIGHT = KeyEvent.VK_RIGHT;
    private final static int PLAYER_2_THROTTLE = KeyEvent.VK_UP;
    private final static int PLAYER_2_THROTTLE_MOUSE = MouseEvent.BUTTON3;
    private final static int PLAYER_2_FIRE_MOUSE = MouseEvent.BUTTON1;

    //other key coding
    private final static int SHOW_INFORMATION = KeyEvent.VK_I;
    private final static int DRAW_BOUNDS = KeyEvent.VK_B;
    private final static int SHOW_KEY_CODING = KeyEvent.VK_K;
    private final static int DRAW_CROSS = KeyEvent.VK_C;
    private final static int PAUSE = KeyEvent.VK_P;
    private final static int QUIT = KeyEvent.VK_Q;
    private final static int RESTART = KeyEvent.VK_R;
    
    /**
     * The default constructor that creates 2 graphical viewers (one for each player).
     */
    public GameFrame(GameModel gameModel, Game game) {
        super(FRAME_TITLE);
        this.gameModel = gameModel;
        this.game = game;
        
        //key and mouse listeners
        this.addKeyListener(this);
        this.addMouseListener(this);

        //graphical initialization
        addAllComponents();
        
        //---
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
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
                gameModel.getPlayer(1).setAccelerationRequest(true);
                break;
            case PLAYER_1_TURN_LEFT:
                gameModel.getPlayer(1).setTurnLeftRequest(true);
                break;
            case PLAYER_1_TURN_RIGHT:
                gameModel.getPlayer(1).setTurnRightRequest(true);
                break;
            case PLAYER_1_FIRE:
                gameModel.getPlayer(1).setFireRequest(true);
                break;
            /*case PLAYER_2_THROTTLE:
                gameModel.getPlayer(2).accelerationRequest = true;
                break;*/
            case PLAYER_2_TURN_LEFT:
                gameModel.getPlayer(2).setTurnLeftRequest(true);
                break;
            case PLAYER_2_TURN_RIGHT:
                gameModel.getPlayer(2).setTurnRightRequest(true);
                break;
            /*case PLAYER_2_FIRE:
                gameModel.getPlayer(2).fireRequest = true;
                break;*/
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
            	reset();
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
        int keyCode = e.getKeyCode();
        switch(keyCode){
            case PLAYER_1_THROTTLE:
                gameModel.getPlayer(1).setAccelerationRequest(false);
                break;
            case PLAYER_1_TURN_LEFT:
                gameModel.getPlayer(1).setTurnLeftRequest(false);
                break;
            case PLAYER_1_TURN_RIGHT:
                gameModel.getPlayer(1).setTurnRightRequest(false);
                break;
            case PLAYER_1_FIRE:
                gameModel.getPlayer(1).setFireRequest(false);
                break;
            /*case PLAYER_2_THROTTLE:
                gameModel.getPlayer(2).accelerationRequest = false;
                break;*/
            case PLAYER_2_TURN_LEFT:
                gameModel.getPlayer(2).setTurnLeftRequest(false);
                break;
            case PLAYER_2_TURN_RIGHT:
                gameModel.getPlayer(2).setTurnRightRequest(false);
                break;
            /*case PLAYER_2_FIRE:
                gameModel.getPlayer(2).fireRequest = false;
                break;*/
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
			gameModel.getPlayer(2).setFireRequest(true);
			break;
		case PLAYER_2_THROTTLE_MOUSE:
			gameModel.getPlayer(2).setAccelerationRequest(true);
			break;
		}
	}

	/**
     * Decides what mouse button is released and acts accordingly.
     */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int mouseCode = arg0.getButton();
		switch(mouseCode){
		case PLAYER_2_FIRE_MOUSE:
			gameModel.getPlayer(2).setFireRequest(false);
			break;
		case PLAYER_2_THROTTLE_MOUSE:
			gameModel.getPlayer(2).setAccelerationRequest(false);
			break;		
		}
	}

    /**
     * Resets the game model and the locks the camera on the player.
     */
    public void reset(){
        gameModel.reset();
    }

    /**
     * Add all the components to the frame.
     * Default is 2 player with 2 viewers.
     */
    private void addAllComponents(){
        BasicImageLoader img = new BasicImageLoader();
        viewer1 = new GraphicalViewer(gameModel, gameModel.getPlayer(1), img);
        viewer2 = new GraphicalViewer(gameModel, gameModel.getPlayer(2), img);

        GridLayout gl = new GridLayout(0, 2);
        gl.setHgap(5);
        this.setLayout(gl);

        //add components to the frame
        this.add(viewer1);
        this.add(viewer2);

        this.pack();
    }
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
