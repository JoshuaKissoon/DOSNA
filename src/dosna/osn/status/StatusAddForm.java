package dosna.osn.status;

import dosna.osn.actor.Actor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
public final class StatusAddForm extends JPanel
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
    public void setActionListener(ActionListener li)
    {
        this.actionListener = li;
        submitBtn.addActionListener(this.actionListener);
    }
    
    public String getEnteredStatus()
    {
        return this.statusTA.getText();
    }

    /**
     * Clear the text in the status box.
     */
    public void clearStatusBox()
    {
        this.statusTA.setText("");
    }

    /**
     * Default ActionListener for the Status Add Form
     */
    public static final class SAFActionListener implements ActionListener
    {
        
        private final Actor actor;
        private final StatusAddForm saf;

        /**
         * @param actor Actor object of the currently logged in user
         * @param saf   The StatusAddForm for which this listener is for
         */
        public SAFActionListener(final Actor actor, StatusAddForm saf)
        {
            this.actor = actor;
            this.saf = saf;
        }

        /**
         * When the user clicks the submit btn on a status, handle it
         *
         * @param evt
         */
        @Override
        public void actionPerformed(final ActionEvent evt)
        {
            final String text = saf.getEnteredStatus();
            if (text.trim().isEmpty())
            {
                return;
            }
            
            Status s = Status.createNew(this.actor, text);
            
            try
            {
                final int numStoredOn = this.actor.getContentManager().store(s);
                
                if (numStoredOn > 0)
                {
                    this.saf.clearStatusBox();
                }
                else
                {
                    /* Data was not stored online, maybe because of an error! handle it! */
                    
                }
            }
            catch (IOException ex)
            {
                /* @todo Handle failed status posting */
            }
        }
    }
}
