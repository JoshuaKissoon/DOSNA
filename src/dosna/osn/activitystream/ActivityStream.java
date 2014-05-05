package dosna.osn.activitystream;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import javax.swing.JPanel;

/**
 * The HomeStream displayed to the user.
 *
 * @author Joshua Kissoon
 * @since 20140406
 */
public class ActivityStream extends JPanel
{

    /* Properties */
    private Iterable<ActivityStreamContent> content;

    public ActivityStream()
    {

    }

    /**
     * Setup the HomeStream
     *
     * @param content The content to be displayed on the Home Stream
     */
    public ActivityStream(final Collection<ActivityStreamContent> content)
    {
        this.content = content;
    }

    public void setContent(final Collection<ActivityStreamContent> content)
    {
        this.content = content;
    }

    /**
     * Create the HomeStream
     */
    public void create()
    {
        this.setLayout(new GridBagLayout());

        this.addContent();
    }

    /**
     * Add the content onto the Home Stream
     */
    private void addContent()
    {
        int counter = 0;
        for (ActivityStreamContent hsc : this.content)
        {
            this.add(hsc.getContentDisplay(), getConstraints(0, counter++));
        }
    }
    
    public static GridBagConstraints getConstraints(int x, int y)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = GridBagConstraints.RELATIVE;
        c.weightx = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        return c;
    }
}
