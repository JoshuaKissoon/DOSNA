package dosna.osn.homestream;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * The HomeStream displayed to the user.
 *
 * @author Joshua Kissoon
 * @since 20140406
 */
public class HomeStream extends JPanel
{

    /* Properties */
    private final Iterable<HomeStreamContent> content;

    /**
     * Setup the HomeStream
     *
     * @param content The content to be displayed on the Home Stream
     */
    public HomeStream(final Iterable<HomeStreamContent> content)
    {
        this.content = content;
    }

    /**
     * Create the HomeStream
     */
    public void create()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.addContent();
    }

    /**
     * Add the content onto the Home Stream
     */
    private void addContent()
    {
        for (HomeStreamContent hsc : this.content)
        {
            this.add(hsc.getContentDisplay());
        }
    }
}
