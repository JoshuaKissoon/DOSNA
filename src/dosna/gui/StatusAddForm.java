package dosna.gui;

import dosna.dhtAbstraction.DataManager;
import dosna.osn.Actor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * Form used to post a new status
 *
 * @author Joshua Kissoon
 * @since 20140401
 */
public class StatusAddForm extends JPanel
{

    /* Form Elements */
    private final JTextArea statusTA;
    private final JButton submitBtn;
    private final JScrollPane scrollPane;

    /* Listeners */
    private ActionListener actionListener;

    /**
     * Create the form
     */
    public StatusAddForm()
    {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        /* Status Text */
        statusTA = new JTextArea(5, 40);
        statusTA.setWrapStyleWord(true);
        statusTA.setLineWrap(true);
        scrollPane = new JScrollPane(statusTA);
        this.add(scrollPane, BorderLayout.CENTER);

        /* Submit button */
        submitBtn = new JButton("Post");
        submitBtn.setActionCommand("postStatus");
        this.add(submitBtn, BorderLayout.EAST);

        /* Set some form properties */
        this.setPreferredSize(new Dimension(540, 100));
    }

    /**
     * Set the ActionListener to listen for actions from this form
     *
     * @param li
     */
    private void setActionListener(ActionListener li)
    {
        this.actionListener = li;

        submitBtn.addActionListener(this.actionListener);
    }

    /**
     * Default ActionListener for the Status Add Form
     */
    public class SAFActionListener implements ActionListener
    {

        private final DataManager dataManager;

        /**
         * @param dataManager DataManager will be used to post a new status on the network
         * @param actor       Actor object of the currently logged in user
         */
        public SAFActionListener(DataManager dataManager, Actor actor)
        {
            this.dataManager = dataManager;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {

        }
    }
}
