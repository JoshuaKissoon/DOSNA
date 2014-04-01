package dosna.gui;

import dosna.dhtAbstraction.DataManager;
import dosna.osn.Actor;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * The Main User Interface class of DOSNA.
 *
 * @author Joshua Kissoon
 * @since 20140401
 */
public class AnanciUI extends JFrame
{

    /* Properties */
    private final static int FRAME_WIDTH = 1200;
    private final static int FRAME_HEIGHT = 800;

    private final DataManager dataManager;
    private final Actor actor;

    /**
     * Initialize the UI Class
     *
     * @param mngr  The DataManager used to read and store data
     * @param actor Actor currently logged in
     */
    public AnanciUI(final DataManager mngr, final Actor actor)
    {
        this.dataManager = mngr;
        this.actor = actor;
    }

    /**
     * Create the GUI and populate it with the necessary elements
     */
    public void create()
    {
        this.createMainMenu();
        this.addWindowListener(new AnanciUIWindowListener());
    }

    /**
     * Create the main menu and add it to the JFrame
     */
    private void createMainMenu()
    {
        final JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menu;

        /* Setting up the Home menu */
        menu = new JMenu("Home");
        menuBar.add(menu);

        /* Setting up the Profile menu */
        menu = new JMenu("Profile");
        menuBar.add(menu);

        /* Setting up the Connections menu */
        menu = new JMenu("Connections");
        menuBar.add(menu);

        /* Setting up the Help menu */
        menu = new JMenu("Help");
        menuBar.add(menu);
    }

    /**
     * Display the GUI to the screen.
     */
    public void display()
    {
        this.setTitle("Ananci - " + this.actor.getName());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Refresh the content on the GUI
     */
    public void refresh()
    {
        this.invalidate();
        this.validate();
        this.repaint();
    }

    private class AnanciUIWindowListener extends WindowAdapter
    {

        @Override
        public void windowClosing(final WindowEvent e)
        {
            /* Save the state before closing */
            try
            {
                AnanciUI.this.dataManager.shutdown(true);
            }
            catch (IOException ex)
            {
                System.err.println("Shutdown save state operation failed; message: " + ex.getMessage());
            }
        }
    }
}
