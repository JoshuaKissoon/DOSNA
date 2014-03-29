package dosna.bootstrap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import kademlia.core.Kademlia;
import kademlia.node.NodeId;

/**
 * Here we load the bootstrap node and keep it running
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class Bootstrapper extends JFrame
{

    /* Display the contacts of the bootstrap node */
    private JPanel contactsPanel;
    private JScrollPane contactsScrollPane;

    /* Displays the content of the bootstrap node */
    private JPanel contentPanel;
    private JScrollPane contentScrollPane;

    /* Split Pane */
    private JSplitPane splitPane;

    /* Other components */
    private JLabel lbl;

    /* Kademlia instance */
    Kademlia bootstrapInstance;

    public Bootstrapper()
    {

    }

    /**
     * Creates the basic GUI
     */
    public void createGUI()
    {
        this.populateData();

        this.contentScrollPane = new JScrollPane(this.contentPanel);
        this.contactsScrollPane = new JScrollPane(this.contactsPanel);
        this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.contentScrollPane, this.contactsScrollPane);

        this.add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Show the bootstrap node data
     * Display the contacts data
     * Display the content data
     */
    private void populateData()
    {
        contactsPanel = new JPanel();
        contactsPanel.add(new JLabel(this.bootstrapInstance.getNode().getRoutingTable().toString()));

        contentPanel = new JPanel();
        contactsPanel.add(new JLabel(this.bootstrapInstance.getNode().getRoutingTable().toString()));
    }

    /**
     * Update the display by refreshing the JFrame
     */
    private void updateDisplay()
    {

    }

    /**
     * Displays the UI
     */
    public void display()
    {
        this.setTitle("Bootstrap Node UI.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
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
            this.bootstrapInstance = new Kademlia("DOSNA", new NodeId("BOOTSTRAPBOOTSTRAPBO"), 9999);
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
