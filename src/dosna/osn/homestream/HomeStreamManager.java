package dosna.osn.homestream;

import dosna.core.ContentMetadata;
import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;
import dosna.osn.status.HomeStreamStatusesLoader;
import dosna.osn.status.Status;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class manages creating the home stream, loading all content and displaying the home stream.
 * This is a runnable class since loading of the Home Stream can take a lot of time.
 *
 * @author Joshua Kissoon
 * @since 20140406
 */
public class HomeStreamManager
{

    private final Actor currentActor;
    private final DataManager dataManager;
    private final HomeStream homeStream;

    /**
     * Initialize the HomeStreamManager
     *
     * @param currentActor The actor currently logged in
     * @param mngr         Used to manage Data sending/receiving to/from the DHT
     */
    public HomeStreamManager(final Actor currentActor, final DataManager mngr)
    {
        this.currentActor = currentActor;
        this.dataManager = mngr;

        this.homeStream = new HomeStream();
    }

    /**
     * Gets the Actor object of all connections
     *
     * @return The set of connections
     */
    public Collection<Actor> getConnections()
    {
        return currentActor.getConnectionManager().loadConnections(this.dataManager);
    }

    /**
     * @return HomeStream The homestream object
     */
    public HomeStream getHomeStream()
    {
        return this.homeStream;
    }

    public HomeStream createHomeStream()
    {
        Collection<Actor> connections = this.getConnections();

        /**
         * Here we're supposed to let all class that implement HomeStreamLoad hook set the content to be displayed on the home stream
         *
         * @todo Do this when the APIs are setup
         *
         * For now, let's just show the statuses
         */
        List<ContentMetadata> statuses = new ArrayList<>();

        for (Actor a : connections)
        {
            statuses.addAll(a.getContentManager().getAllContent(Status.TYPE));
        }

        HomeStreamStatusesLoader hssl = new HomeStreamStatusesLoader();
        hssl.addContent(statuses);

        Collection<HomeStreamContent> toAdd = hssl.loadContent(dataManager);

        System.out.println("Statuses on home stream: " + toAdd.size());

        this.homeStream.setContent(toAdd);
        this.homeStream.create();

        return this.homeStream;
    }
}
