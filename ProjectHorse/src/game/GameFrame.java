package game;

import graphics.GraphicalViewer;
import model.GameModel;
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
    JMenuBar menuBar;
    JMenu fileMenu, settingsMenu, viewMenu;
    static final String FRAME_TITLE = "Space Explorer";
    GameModel gameModel;
    Game game; //used for pausing/unpausing/destroying game
    GraphicalViewer viewer1;
    GraphicalViewer viewer2;

    //player 1 key coding
    final static int PLAYER_1_FIRE = KeyEvent.VK_SPACE;
    final static int PLAYER_1_TURN_LEFT = KeyEvent.VK_A;
    final static int PLAYER_1_TURN_RIGHT = KeyEvent.VK_D;
    final static int PLAYER_1_THROTTLE = KeyEvent.VK_W;
    final static int PLAYER_1_FOCUS_CAMERA = KeyEvent.VK_F;

    //player 2 key coding
    final static int PLAYER_2_TURN_LEFT = KeyEvent.VK_LEFT;
    final static int PLAYER_2_FIRE = KeyEvent.VK_ENTER;
    final static int PLAYER_2_TURN_RIGHT = KeyEvent.VK_RIGHT;
    final static int PLAYER_2_THROTTLE = KeyEvent.VK_UP;
    final static int PLAYER_2_THROTTLE_MOUSE = MouseEvent.BUTTON3;
    final static int PLAYER_2_FIRE_MOUSE = MouseEvent.BUTTON1;

    //other key coding
    final static int SHOW_INFORMATION = KeyEvent.VK_I;
    final static int DRAW_BOUNDS = KeyEvent.VK_B;
    final static int SHOW_KEY_CODING = KeyEvent.VK_K;
    final static int DRAW_CROSS = KeyEvent.VK_C;
    final static int PAUSE = KeyEvent.VK_P;
    final static int QUIT = KeyEvent.VK_Q;
    final static int RESTART = KeyEvent.VK_R;
    
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
            /*case PLAYER_2_THROTTLE:
                gameModel.getPlayer(2).accelerationRequest = true;
                break;*/
            case PLAYER_2_TURN_LEFT:
                gameModel.getPlayer(2).turnLeftRequest = true;
                break;
            case PLAYER_2_TURN_RIGHT:
                gameModel.getPlayer(2).turnRightRequest = true;
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
            /*case PLAYER_2_THROTTLE:
                gameModel.getPlayer(2).accelerationRequest = false;
                break;*/
            case PLAYER_2_TURN_LEFT:
                gameModel.getPlayer(2).turnLeftRequest = false;
                break;
            case PLAYER_2_TURN_RIGHT:
                gameModel.getPlayer(2).turnRightRequest = false;
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
			gameModel.getPlayer(2).fireRequest = true;
			break;
		case PLAYER_2_THROTTLE_MOUSE:
			gameModel.getPlayer(2).accelerationRequest = true;
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
			gameModel.getPlayer(2).fireRequest = false;
			break;
		case PLAYER_2_THROTTLE_MOUSE:
			gameModel.getPlayer(2).accelerationRequest = false;
			break;		
		}
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
	
	public void reset(){
		gameModel.reset();
		viewer1.lockCameraOnPlayer();
	}
	
	public void addAllComponents(){
		    ImageLoader img = new ImageLoader();
	        viewer1 = new GraphicalViewer(gameModel, gameModel.getPlayer(1), img);
	        viewer2 = new GraphicalViewer(gameModel, gameModel.getPlayer(2), img);
	        
	        viewer1.paintExtraInformation = true;
	        viewer1.paintKeyBindings = true;

	        GridLayout gl = new GridLayout(0, 2);
	        gl.setHgap(5);
	        this.setLayout(gl);

	        //add components to the frame
	        this.add(viewer1);
	        this.add(viewer2);
	        
	        this.pack();
	}
}
