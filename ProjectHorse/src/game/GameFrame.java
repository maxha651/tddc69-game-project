package game;

import controller.AbstractController;
import controller.DefaultKeyBindings;
import controller.Player1;
import controller.Player2;
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
import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Main frame controlling a GameModel and a GraphicalViewer with KeyListeners and MouseListeners
 */
public class GameFrame extends JFrame{

	//declarations and default initializations
    private static final String FRAME_TITLE = "Space Explorer";
    private GameModel gameModel;
    private Game game; //used for pausing/unpausing/destroying game
    private GraphicalViewer viewer1;
    private GraphicalViewer viewer2;
    private Collection<AbstractController> abstractControllers;
    
    /**
     * The default constructor that creates 2 graphical viewers (one for each player).
     */
    public GameFrame(GameModel gameModel, Game game) {
        super(FRAME_TITLE);
        this.gameModel = gameModel;
        this.game = game;

        //key and mouse listeners
        abstractControllers = new LinkedList<AbstractController>();
        abstractControllers.add(new Player1(gameModel.getPlayer(1)));
        abstractControllers.add(new Player2(gameModel.getPlayer(2)));
        abstractControllers.add(new DefaultKeyBindings(viewer1, game, this));

        for(AbstractController controller : abstractControllers){
            this.addKeyListener(controller);
            this.addMouseListener(controller);
        }

        //graphical initialization
        addAllComponents();
        
        //---
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
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
}
