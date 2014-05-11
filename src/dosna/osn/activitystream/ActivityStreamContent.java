package dosna.osn.activitystream;

import javax.swing.JPanel;

/**
 * An interface for any content that wants to be displayed on the Home Stream.
 *
 * @author Joshua Kissoon
 *
 * @since 20140406
 */
public interface ActivityStreamContent
{

    /**
     * Here we get the JPanel that contains the content and is displayed on the HomeStream.
     *
     * @return JPanel
     */
    public JPanel getContentDisplay();

    public long getLastUpdatedTimestamp();
}
