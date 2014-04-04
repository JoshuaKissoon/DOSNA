package dosna.gui;

import dosna.osn.actor.Actor;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * The user interface used to manage connections
 *
 * @author Joshua Kissoon
 * @since 20140404
 */
public class ConnectionsManagerUI extends JFrame
{

    /* Properties */
    private final static int FRAME_WIDTH = 800;
    private final static int FRAME_HEIGHT = 600;

    /* Main components */
    private final Actor actor;

    /**
     * @param actor The actor currently logged in
     */
    public ConnectionsManagerUI(final Actor actor)
    {
        this.actor = actor;
    }

    /**
     * Create the Main GUI and populate it's contents
     */
    public void create()
    {

    }

    /**
     * Display the main GUI onto the screen
     */
    public void display()
    {
        this.setTitle("Ananci: Manage Connections - " + this.actor.getName());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
}
