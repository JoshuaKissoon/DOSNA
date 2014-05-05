package dosna.osn.homestream;

import dosna.content.DOSNAContent;
import dosna.core.ContentMetadata;
import dosna.dhtAbstraction.DataManager;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Manager to manage loading and updating the home stream data.
 *
 * We abstract this code so we can do home stream loading by different timers.
 *
 * @author Joshua Kissoon
 * @since 20140505
 */
public class HomeStreamDataManager
{

    private final DataManager dataManager;

    public HomeStreamDataManager(final DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    /**
     * Loads the given set of content from the DHT.
     *
     * @param contentMD The metadata of the content to be loaded
     *
     * @return A collection of the content loaded from the DHT
     */
    public Collection<DOSNAContent> loadContent(Collection<ContentMetadata> contentMD)
    {
        Collection<DOSNAContent> content = new ArrayList<>();

    }
}
