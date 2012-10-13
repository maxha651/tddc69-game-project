package game;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class StartMenu extends JFrame{

    static final String FRAME_TITLE = "Welcome to Space Explorer";

    JButton startButton;
    JButton exitButton;

    public StartMenu(){
        super(FRAME_TITLE);
        this.setLayout(new BorderLayout());

        startButton = new JButton("Start");
        exitButton  = new JButton("Exit");
        //this.add();
    }
}
