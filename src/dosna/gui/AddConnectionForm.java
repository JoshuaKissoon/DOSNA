package dosna.gui;

import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;
import dosna.osn.actor.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import kademlia.core.GetParameter;
import kademlia.dht.StorageEntry;

/**
 * A form used to add a new connection
 *
 * @author Joshua Kissoon
 * @since 20140404
 */
public class AddConnectionForm extends JFrame
{

    /* Properties */
    private final static int FRAME_WIDTH = 800;
    private final static int FRAME_HEIGHT = 600;

    private final Actor actor;
    private final DataManager dataManager;

    /**
     * Setup the add connection form
     *
     * @param actor       Currently logged in Actor
     * @param dataManager Used to put and get data
     */
    public AddConnectionForm(final Actor actor, final DataManager dataManager)
    {
        this.actor = actor;
        this.dataManager = dataManager;
    }

    /**
     * Create the frame and populate it's contents
     */
    public void create()
    {
        this.getContentPane().add(new AddConnectionPanel(this.actor, this.dataManager));
    }

    /**
     * Display the frame onto the screen
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

    /**
     * The main component of the add connection form is this panel.
     * We make this panel separate however, so that other parts of the system can use it.
     *
     * @author Joshua Kissoon
     * @since 20140404
     */
    public class AddConnectionPanel extends JPanel
    {

        /* Main components */
        private final Actor actor;
        private final DataManager dataManager;

        /* Form components */
        private final JTextField searchBox;
        private final JPanel resultsPanel;

        /**
         * Setup the add connection panel
         *
         * @param actor       Currently logged in Actor
         * @param dataManager Used to put and get data
         */
        public AddConnectionPanel(final Actor actor, final DataManager dataManager)
        {
            this.actor = actor;
            this.dataManager = dataManager;

            /* Setup the search box */
            searchBox = new JTextField();
            searchBox.addActionListener(new SearchActionListener());
            this.add(searchBox, BorderLayout.NORTH);

            /* Search Results panel */
            resultsPanel = new JPanel();
            this.add(resultsPanel, BorderLayout.CENTER);
        }

        private class SearchActionListener implements ActionListener
        {

            /**
             * Handle searching when the user presses enter in the searchbox
             */
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                final String txt = searchBox.getText();

                /* Only do something if they user has entered some text */
                if (!txt.trim().isEmpty())
                {
                    User u = new User(txt);

                    /* Create our GetParameter to do the search */
                    GetParameter gp = new GetParameter(u.getKey(), u.getType());

                    /* Now lets search for content */
                    try
                    {
                        List<StorageEntry> ret = AddConnectionPanel.this.dataManager.get(gp, 1);
                    }
                    catch (IOException ex)
                    {
                        
                    }
                }
            }
        }
    }
}
