package dosna.gui;

import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * A form used to add a new connection
 *
 * @author Joshua Kissoon
 * @since 20140404
 */
public class ConnectionAddForm extends JFrame
{

    /* Properties */
    private final static int FRAME_WIDTH = 450;
    private final static int FRAME_HEIGHT = 250;

    private final Actor actor;
    private final DataManager dataManager;

    /**
     * Setup the add connection form
     *
     * @param actor       Currently logged in Actor
     * @param dataManager Used to put and get data
     */
    public ConnectionAddForm(final Actor actor, final DataManager dataManager)
    {
        this.actor = actor;
        this.dataManager = dataManager;
    }

    /**
     * Create the frame and populate it's contents
     */
    public void create()
    {
        this.getContentPane().add(new ConnectionAddPanel(this.actor, this.dataManager));
    }

    /**
     * Display the frame onto the screen
     */
    public void display()
    {
        this.setTitle("Ananci: Add Connection - " + this.actor.getName());

        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
}
