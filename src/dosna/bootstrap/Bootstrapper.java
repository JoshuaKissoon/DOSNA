package dosna.bootstrap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private final static String BOOTSTRAP_OWNER_ID = "DOSNA";   // Owner id of the bootstrap kademlia instance
    private final static int BOOTSTRAP_NODE_PORT = 15049;

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
        
        this.addWindowListener(new BootstrapperWindowListener());
        
        /* Setup the Contacts Panel */
        contacts = new JTextArea(10, 20);

        /* Setup the Content Panel */
        content = new JTextArea(10, 20);

        /* Populate the data */
        this.populateData();

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
            /* Try to load the state from file */
            this.bootstrapInstance = Kademlia.loadFromFile(BOOTSTRAP_OWNER_ID);

        }
        catch (IOException | ClassNotFoundException ex)
        {
            /* Loading state from file failed: create a new instance */
            System.err.println("Loading state from file failed; message: " + ex.getMessage());
            try
            {
                /* Create a new instance */
                this.bootstrapInstance = new Kademlia(BOOTSTRAP_OWNER_ID, new NodeId("BOOTSTRAPBOOTSTRAPBO"), BOOTSTRAP_NODE_PORT);
            }
            catch (IOException exx)
            {
                exx.printStackTrace();
            }
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

    private final class BootstrapperWindowListener extends WindowAdapter
    {

        @Override
        public void windowClosing(WindowEvent e)
        {
            /* Save the state before closing */
            try
            {
                Bootstrapper.this.bootstrapInstance.shutdown(true);
            }
            catch (IOException ex)
            {
                System.err.println("Shutdown Save State failed; message: " + ex.getMessage());
            }
        }
    }
}
