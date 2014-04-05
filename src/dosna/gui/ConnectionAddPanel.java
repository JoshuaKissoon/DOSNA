package dosna.gui;

import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;
import dosna.osn.actor.Relationship;
import dosna.osn.actor.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import kademlia.core.GetParameter;
import kademlia.dht.StorageEntry;

/**
 * The main component of the add connection form is this panel.
 * We make this panel separate however, so that other parts of the system can use it.
 *
 * @author Joshua Kissoon
 * @since 20140404
 */
public class ConnectionAddPanel extends JPanel
{

    /* Properties */
    private final static int FRAME_WIDTH = 450;
    private final static int FRAME_HEIGHT = 250;

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
    public ConnectionAddPanel(final Actor actor, final DataManager dataManager)
    {
        /* Setup the Panel */
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        this.actor = actor;
        this.dataManager = dataManager;

        Border b = new EmptyBorder(10, 10, 10, 10);

        /* Setup the search box */
        searchBox = new JTextField();
        searchBox.setBorder(b);
        searchBox.addActionListener(new SearchActionListener());
        this.add(searchBox, BorderLayout.NORTH);

        /* Search Results panel */
        resultsPanel = new JPanel();
        resultsPanel.setBorder(b);
        this.add(resultsPanel, BorderLayout.CENTER);

    }

    /**
     * Update the JPanel and it's data.
     */
    public void refresh()
    {
        this.invalidate();
        this.validate();
        this.repaint();
    }

    /**
     * Updates the result panel with the given result.
     *
     * @param conn The actor that was found
     */
    private void setResult(final Actor conn)
    {
        /* Empty the results */
        resultsPanel.removeAll();
        resultsPanel.setLayout(new BorderLayout());

        /* Place the new actor there */
        final JPanel actorData = new JPanel();
        actorData.setLayout(new BoxLayout(actorData, BoxLayout.Y_AXIS));

        actorData.add(new JLabel("User ID: " + conn.getUserId()));
        actorData.add(new JLabel("Name: " + conn.getName()));

        resultsPanel.add(actorData, BorderLayout.CENTER);

        /* Add the Add button */
        final JButton btn = new JButton("Add Connection");
        btn.setBorder(new EmptyBorder(10, 10, 10, 10));
        btn.putClientProperty("actor", conn);
        btn.addActionListener(new ConnectionAddActionListener());
        resultsPanel.add(btn, BorderLayout.EAST);

        /* Refresh to show data to user */
        this.refresh();
    }

    private class SearchActionListener implements ActionListener
    {

        /**
         * Handle searching when the user presses enter in the searchbox
         */
        @Override
        public void actionPerformed(final ActionEvent evt)
        {
            final String txt = searchBox.getText();

            /* Only do something if they user has entered some text */
            if (!txt.trim().isEmpty())
            {
                User u = new User(txt);

                /* Create our GetParameter to do the search */
                GetParameter gp = new GetParameter(u.getKey(), u.getType());

                /* Now lets search for content */
                StorageEntry val = null;
                try
                {
                    val = ConnectionAddPanel.this.dataManager.get(gp);
                    u = (User) new User().fromBytes(val.getContent());
                    ConnectionAddPanel.this.setResult(u);
                }
                catch (NoSuchElementException | IOException ex)
                {
                    System.err.println("Ran into a prob when searching for person. Message: " + ex.getMessage());
                }

                if (val != null)
                {

                }
            }
        }
    }

    /**
     * The default class to handle the event where a user wants to connect to another user.
     *
     * @author Joshua Kissoon
     * @since 20140404
     */
    private class ConnectionAddActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(final ActionEvent evt)
        {
            final JButton btn = (JButton) evt.getSource();
            final Actor connection = (Actor) btn.getClientProperty("actor");

            Relationship r = new Relationship(actor, connection);
            actor.getConnectionManager().addConnection(r);

            try
            {
                /* Now let's put this data back on the DHT */
                if (dataManager.putLocallyAndUniversally(actor) > 0)
                {
                    JOptionPane.showMessageDialog(null, "You have successfully added this connection!! Congrats!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "An error occured whiles adding this connection! Please try again later.");
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(ConnectionAddPanel.class.getName()).log(Level.SEVERE, "Unable to upload User object after add connection.", ex);
            }
        }
    }
}
