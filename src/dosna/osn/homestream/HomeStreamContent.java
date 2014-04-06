package dosna.osn.homestream;

import javax.swing.JPanel;

/**
 * An interface for any content that wants to be displayed on the Home Stream.
 *
 * @author Joshua Kissoon
 *
 * @since 20140406
 */
public interface HomeStreamContent
{

    /**
     * Here we get the JPanel that contains the content and is displayed on the HomeStream.
     *
     * @return JPanel
     */
    public JPanel getContentDisplay();

    public long getLastUpdatedTimestamp();
}
