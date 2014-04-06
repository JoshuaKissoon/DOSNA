package dosna.gui;

import dosna.osn.status.StatusAddForm;
import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;
import dosna.osn.homestream.HomeStreamManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * The Main User Interface class of DOSNA.
 *
 * @author Joshua Kissoon
 * @since 20140401
 */
public final class AnanciUI extends JFrame
{

    /* Properties */
    private final static int FRAME_WIDTH = 1200;
    private final static int FRAME_HEIGHT = 800;

    private final DataManager dataManager;
    private final Actor actor;

    /* Components */
    private JSplitPane splitPane;
    private JPanel leftSection, rightSection;
    private JScrollPane leftSectionSP, rightSectionSP;

    /* Listeners */
    private final ActionListener actionListener;

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
        this.actionListener = new AnanciUIActionListener();
    }

    /**
     * Create the GUI and populate it with the necessary elements
     */
    public void create()
    {
        this.createMainMenu();
        this.addWindowListener(new AnanciUIWindowListener());

        /**
         * @section Left Side
         */
        leftSection = new JPanel();
        leftSectionSP = new JScrollPane(leftSection);
        //leftSectionSP.setMinimumSize(new Dimension(FRAME_WIDTH / 2, FRAME_HEIGHT / 2));

        /* Status Add Form */
        final StatusAddForm saf = new StatusAddForm();
        saf.setActionListener(new StatusAddForm.SAFActionListener(actor, saf));
        leftSection.add(saf, BorderLayout.NORTH);

        /**
         * @section Right Side
         */
        rightSection = new JPanel();
        rightSectionSP = new JScrollPane(rightSection);

        /**
         * @section Populating all content to the Main UI
         */
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.leftSectionSP, this.rightSectionSP);
        splitPane.setDividerLocation(FRAME_WIDTH / 2);
        this.getContentPane().add(splitPane, BorderLayout.CENTER);

        /**
         * Lets launch another thread to manage displaying home stream
         */
        HomeStreamManager hsm = new HomeStreamManager(actor, dataManager);
        Thread hsmt = new Thread(hsm);
        hsmt.start();
        
    }

    /**
     * Create the main menu and add it to the JFrame
     */
    private void createMainMenu()
    {
        final JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menu;
        JMenuItem menuItem;

        /* Setting up the Home menu */
        menu = new JMenu("Home");
        menuBar.add(menu);

        /* Setting up the Actor menu */
        menu = new JMenu("You");
        menuBar.add(menu);

        menuItem = new JMenuItem("Manage Connections");
        menuItem.addActionListener(this.actionListener);
        menuItem.setActionCommand(AnanciUIActionListener.AC_MANAGE_CONNECTIONS);
        menu.add(menuItem);

        menuItem = new JMenuItem("Add Connection");
        menuItem.addActionListener(this.actionListener);
        menuItem.setActionCommand(AnanciUIActionListener.AC_ADD_CONNECTION);
        menu.add(menuItem);

        /* Setting up the Help menu */
        menu = new JMenu("Help");

        menuItem = new JMenuItem("Print Storage");
        menuItem.addActionListener(this.actionListener);
        menuItem.setActionCommand(AnanciUIActionListener.AC_HELP_PRINT_STORAGE);
        menu.add(menuItem);

        menuItem = new JMenuItem("Print Actor Object");
        menuItem.addActionListener(this.actionListener);
        menuItem.setActionCommand(AnanciUIActionListener.AC_HELP_PRINT_ACTOR);
        menu.add(menuItem);

        menuItem = new JMenuItem("Print Routing Table");
        menuItem.addActionListener(this.actionListener);
        menuItem.setActionCommand(AnanciUIActionListener.AC_HELP_PRINT_ROUTING_TABLE);
        menu.add(menuItem);

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

    /**
     * Subclass that handles Action Events for this UI
     */
    private final class AnanciUIActionListener implements ActionListener
    {

        /* Action Commands used */
        public static final String AC_MANAGE_CONNECTIONS = "AC_MANAGE_CONNECTIONS";
        public static final String AC_ADD_CONNECTION = "AC_ADD_CONNECTIONS";

        public static final String AC_HELP_PRINT_STORAGE = "AC_HELP_PRINT_STORAGE";
        public static final String AC_HELP_PRINT_ACTOR = "AC_HELP_PRINT_ACTOR";
        public static final String AC_HELP_PRINT_ROUTING_TABLE = "AC_HELP_PRINT_ROUTING_TABLE";

        @Override
        public void actionPerformed(final ActionEvent evt)
        {
            switch (evt.getActionCommand())
            {
                case AC_MANAGE_CONNECTIONS:
                    /* Load the Connections Manager UI */
                    ConnectionsManagerUI cmui = new ConnectionsManagerUI(actor, dataManager);
                    cmui.create();
                    cmui.display();
                    break;
                case AC_ADD_CONNECTION:
                    /* Load the Connections Add Form */
                    ConnectionAddForm caf = new ConnectionAddForm(actor, dataManager);
                    caf.create();
                    caf.display();
                    break;

                /* Help Menu cases */
                case AC_HELP_PRINT_STORAGE:

                    break;
                case AC_HELP_PRINT_ACTOR:
                    System.out.println();
                    System.out.println(AnanciUI.this.actor);
                    System.out.println();
                    break;
                case AC_HELP_PRINT_ROUTING_TABLE:
                    break;
            }
        }
    }

    /**
     * Subclass that handles Window Events for this UI
     */
    private final class AnanciUIWindowListener extends WindowAdapter
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
