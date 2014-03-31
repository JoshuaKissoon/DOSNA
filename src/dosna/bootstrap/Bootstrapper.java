package dosna.bootstrap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import kademlia.Kademlia;
import kademlia.node.NodeId;

/**
 * Here we load the bootstrap node and keep it running
 *
 * @author Joshua Kissoon
 * @since 20140326
 *
 * @todo Handle saving state when the window closes
 */
public class Bootstrapper extends JFrame
{

    /* Properties */
    private final static int FRAME_WIDTH = 1200;
    private final static int FRAME_HEIGHT = 800;

    /* Display the contacts of the bootstrap node */
    private JTextArea contacts;
    private JScrollPane contactsScrollPane;

    /* Displays the content of the bootstrap node */
    private JTextArea content;
    private JScrollPane contentScrollPane;

    /* Split Pane */
    private JSplitPane splitPane;

    /* Other components */
    private JLabel lbl;

    /* Kademlia instance */
    Kademlia bootstrapInstance;

    public Bootstrapper()
    {
        /* Setup a timer to update the frame often */
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        Bootstrapper.this.updateData();
                    }
                },
                // Delay: 10 secs  // Interval
                10 * 1000, 10 * 1000
        );
    }

    /**
     * Creates the basic GUI
     */
    public void createGUI()
    {
        /* Setup the Contacts Panel */
        contacts = new JTextArea(10, 20);

        /* Setup the Content Panel */
        content = new JTextArea(10, 20);

        /* Populate the data */
        //this.populateData();

        this.contactsScrollPane = new JScrollPane(this.contacts);
        contactsScrollPane.setMinimumSize(new Dimension(400, 800));
        this.contentScrollPane = new JScrollPane(this.content);
        contentScrollPane.setMinimumSize(new Dimension(400, 800));

        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.contactsScrollPane, this.contentScrollPane);
        splitPane.setDividerLocation(FRAME_WIDTH / 2);

        splitPane.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        this.getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Show the bootstrap node data
     * Display the contacts data
     * Display the content data
     */
    private void populateData()
    {
        contacts.removeAll();
        contacts.setText(bootstrapInstance.getNode().getRoutingTable().toString());
        contacts.setWrapStyleWord(true);
        contacts.setLineWrap(true);

        content.removeAll();
        content.setText(bootstrapInstance.getDHT().toString());
        content.setWrapStyleWord(true);
        content.setLineWrap(true);
    }

    /**
     * Update the display by refreshing the JFrame
     */
    private void updateData()
    {
        System.out.println("Updating data");
        /* Re-Populate the new data */
        this.populateData();

        /* Refresh the frame */
        this.invalidate();
        this.validate();
        this.repaint();
    }

    /**
     * Displays the UI
     */
    public void display()
    {
        this.setTitle("Bootstrap Node UI.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Initialize the bootstrap node to be running so that every other node can connect through this node
     */
    public void launchBootstrapNode()
    {
        try
        {
            this.bootstrapInstance = new Kademlia("DOSNA", new NodeId("BOOTSTRAPBOOTSTRAPBO"), 15049);
        }
        catch (IOException e)
        {
            /**
             * @todo Handle this exception
             */
        }
    }

    public static void main(String[] args)
    {
        Bootstrapper bs = new Bootstrapper();
        bs.launchBootstrapNode();
        bs.createGUI();
        bs.display();
    }

    private final class BootstrapperActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent event)
        {

        }
    }
}
