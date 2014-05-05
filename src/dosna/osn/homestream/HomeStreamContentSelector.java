package dosna.osn.homestream;

import dosna.core.ContentMetadata;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 * An algorithm that selects which content to display on the user's home stream
 *
 * @author Joshua Kissoon
 * @since 20140505
 */
public class HomeStreamContentSelector
{

    private final static int NUM_CONTENT_ON_HOME_STREAM = 100;

    public TreeSet<ContentMetadata> contentMD;

    /**
     * Setup the content selector.
     *
     * This content selector returns the 100 most recent content.
     *
     * @param iContentMD The MetaData of all possible content
     */
    public HomeStreamContentSelector(Collection<ContentMetadata> iContentMD)
    {
        this.contentMD = new TreeSet<>();

        for (ContentMetadata cmd : iContentMD)
        {
            this.contentMD.add(cmd);
        }
    }

    /**
     * Select the content to be displayed on the home stream and return it.
     *
     * @return Set of content to be put on the home stream
     */
    public Collection<ContentMetadata> getHomeStreamContent()
    {
        Collection<ContentMetadata> ret = new ArrayList<>();

        int i = 0;
        for (ContentMetadata cmd : this.contentMD)
        {
            ret.add(cmd);
            
            if (++i == NUM_CONTENT_ON_HOME_STREAM)
            {
                break;
            }
        }

        return ret;
    }
}
