package dosna.osn.homestream;

import dosna.content.DOSNAContent;
import dosna.core.ContentMetadata;
import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;
import dosna.osn.status.Status;
import dosna.osn.status.StatusHomeStreamDisplay;
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
     * @return HomeStream The homestream object
     */
    public HomeStream getHomeStream()
    {
        return this.homeStream;
    }

    public HomeStream createHomeStream()
    {
        /* Get the home stream content */
        Collection<DOSNAContent> homeStreamContent = getHomeStreamContent();
        Collection<HomeStreamContent> toAdd = new ArrayList<>();
        for (DOSNAContent c : homeStreamContent)
        {
            if (c.getType().equals(Status.TYPE))
            {
                toAdd.add(new StatusHomeStreamDisplay((Status) c));
            }
        }


        this.homeStream.setContent(toAdd);
        this.homeStream.create();

        return this.homeStream;
    }

    /**
     * Loads the Home stream content from the DHT.
     *
     * @return The content to be displayed on the Home stream
     */
    public Collection<DOSNAContent> getHomeStreamContent()
    {
        /* Get the MetaData of the home stream content */
        Collection homeStreamContent = getHomeStreamContentMD();

        /* Use the home stream data manager to load the required content */
        HomeStreamDataManager hsdm = new HomeStreamDataManager(dataManager);
        return hsdm.loadContent(homeStreamContent);
    }

    /**
     * @return A set of content to be displayed on the home stream
     */
    public Collection<ContentMetadata> getHomeStreamContentMD()
    {
        /* Get a list of all content for all of this actor's connections */
        List<ContentMetadata> content = getConnectionsContentList();

        /* Use the selection algorithm to select which on the list is to be shown on the home stream */
        HomeStreamContentSelector hscs = new HomeStreamContentSelector(content);
        return hscs.getHomeStreamContent();
    }

    /**
     * Get a list of all content for all of this actor's connections
     *
     * @return
     */
    public List<ContentMetadata> getConnectionsContentList()
    {
        /* Load the actor objects of this actor's connections */
        Collection<Actor> connections = this.getConnections();

        /* Get a list of all content for all of this actor's connections */
        List<ContentMetadata> content = new ArrayList<>();
        for (Actor a : connections)
        {
            content.addAll(a.getContentManager().getAllContent());
        }

        return content;
    }

    /**
     * Gets the Actor object of all connections
     *
     * @return The set of connections
     */
    public Collection<Actor> getConnections()
    {
        Collection<Actor> connections = currentActor.getConnectionManager().loadConnections(this.dataManager);
        connections.add(currentActor);
        return connections;
    }
}
