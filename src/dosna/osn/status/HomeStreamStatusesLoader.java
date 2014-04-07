package dosna.osn.status;

import dosna.core.ContentMetadata;
import dosna.dhtAbstraction.DataManager;
import dosna.osn.homestream.HomeStreamContent;
import dosna.osn.homestream.HomeStreamLoadingEventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import kademlia.dht.GetParameter;
import kademlia.dht.StorageEntry;

/**
 * Class that handles loading the set of statuses when the home stream is being loaded.
 *
 * @author Joshua Kissoon
 * @since 20140406
 */
public class HomeStreamStatusesLoader implements HomeStreamLoadingEventHandler<Status>
{

    private final Collection<ContentMetadata> contentMD;
    private final ArrayList<HomeStreamContent> statuses;

    
    {
        contentMD = new ArrayList<>();
        statuses = new ArrayList<>();
    }

    @Override
    public void addContent(final Collection<ContentMetadata> content)
    {
        this.contentMD.addAll(content);
    }

    @Override
    public Collection<HomeStreamContent> loadContent(DataManager dataManager)
    {
        /* We have no statuses to load, just return an empty set */
        if (contentMD.isEmpty())
        {
            return statuses;
        }

        /* Lets load some statuses */
        for (ContentMetadata md : this.contentMD)
        {
            /* Lets start a new thread for each content to be gotten */
//            new Thread()
//            {
//                @Override
//                public void run()
//                {
                    GetParameter gp = new GetParameter(md.getKey(), md.getType(), md.getOwnerId());
                    try
                    {
                        StorageEntry e = dataManager.get(gp);
                        Status s = (Status) new Status().fromBytes(e.getContent().getBytes());
                        statuses.add(new StatusHomeStreamDisplay(s));
                    }
                    catch (IOException e)
                    {
                        /* Means we're unable to load this status. @todo Handle this error */
                    }
//                }
//            }.start();

        }

        return statuses;
    }

    /**
     * Comparator class used to sort statuses
     */
    private final class StatusSorter implements Comparator<Status>
    {

        @Override
        public int compare(Status a, Status b)
        {
            if (a == b)
            {
                return 0;
            }

            if (a.getLastUpdatedTimestamp() > b.getLastUpdatedTimestamp())
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

}
