package dosna.osn.activitystream;

import dosna.content.DOSNAContent;
import dosna.core.ContentMetadata;
import dosna.dhtAbstraction.DataManager;
import dosna.osn.status.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import kademlia.dht.KademliaStorageEntry;
import kademlia.exceptions.ContentNotFoundException;

/**
 * Manager to manage loading and updating the home stream data.
 *
 * We abstract this code so we can do home stream loading by different timers.
 *
 * @author Joshua Kissoon
 * @since 20140505
 */
public class ActivityStreamDataManager
{

    private final DataManager dataManager;

    /* Total time it took to load the content */
    private transient long totalLoadingTimeTaken;

    
    {
        this.totalLoadingTimeTaken = 0;
    }

    public ActivityStreamDataManager(final DataManager dataManager)
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

        if (contentMD.isEmpty())
        {
            return content;
        }

        for (ContentMetadata cmd : contentMD)
        {
            try
            {
                long startTime = System.nanoTime();
                KademliaStorageEntry e = dataManager.get(cmd.getKey(), cmd.getType(), cmd.getOwnerId());

                /* @todo We need to figure out a way to make this more generic for different types of content */
                Status s = (Status) new Status().fromSerializedForm(e.getContent());
                content.add(s);
                long endTime = System.nanoTime();
                this.totalLoadingTimeTaken += (endTime - startTime);
            }
            catch (IOException | ContentNotFoundException ex)
            {

            }
        }

        return content;
    }

    /**
     * Get the total time it took to load the content in the activity stream
     *
     * @return The total time taken in nanoseconds
     */
    public long getLoadingTime()
    {
        return this.totalLoadingTimeTaken;
    }
}
