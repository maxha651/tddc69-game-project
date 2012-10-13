package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class StartMenu extends JFrame{

    static final String FRAME_TITLE = "Welcome to Space Explorer!";

    JButton startButton;
    JButton exitButton;

    public StartMenu(){
        super(FRAME_TITLE);
        this.setLayout(new GridLayout(2,1));

        startButton = new JButton("Start");
        exitButton  = new JButton("Exit");
        startButton.setFont(new Font("MONOSPACED", Font.BOLD, 20));
        exitButton.setFont(new Font("MONOSPACED", Font.BOLD, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                Game game = new Game();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        this.add(startButton);
        this.add(exitButton);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        this.setSize((int) screenSize.getWidth()/5, (int) screenSize.getHeight()/5);
        this.setLocation((int) screenSize.getWidth()*2/5, (int) screenSize.getHeight()*2/5);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
