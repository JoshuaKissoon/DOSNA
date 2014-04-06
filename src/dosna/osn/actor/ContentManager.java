package dosna.osn.actor;

import dosna.core.ContentMetadata;
import dosna.dhtAbstraction.DOSNAContent;
import dosna.dhtAbstraction.DataManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Each Actor will put many content onto his/her profile; This class:
 * - Puts content from this actor onto the DHT.
 * - Keeps track of all of the Actor's content.
 *
 * @author Joshua Kissoon
 * @since 20140401
 */
public class ContentManager
{

    /*  Set of content for a specific actor <String - The type of element stored, TreeSet<ContentMetadata>>
     */
    private HashMap<String, TreeSet<ContentMetadata>> actorContent;
    private transient DataManager dataManager;
    private transient Actor actor;

    /**
     * Blank constructor mainly used by serializers
     */
    public ContentManager()
    {

    }

    private ContentManager(final Actor actor)
    {
        actorContent = new HashMap<>();
        this.actor = actor;
    }

    public static ContentManager createNew(Actor actor)
    {
        return new ContentManager(actor);
    }

    /**
     * Set the DataManager used by this class to put content on the DHT.
     * This needs to be set every time the Actor is loaded.
     *
     * @param mngr
     */
    public void setDataManager(final DataManager mngr)
    {
        this.dataManager = mngr;
    }

    /**
     * Set the Actor that owns this ContentManager.
     *
     * @param actor
     */
    public void setActor(final Actor actor)
    {
        this.actor = actor;
    }

    /**
     * Store a new Content on the DHT and add a reference to the content in this actor's object.
     * This content is stored both locally and universally.
     *
     * @return The number of nodes this data was stored on.
     *
     * @throws java.io.IOException
     * @note Only content owned by this Actor should be managed by this content manager
     *
     * @param content The content to store
     */
    public int store(final DOSNAContent content) throws IOException
    {
        try
        {
            /* Lets store this content on the DHT now */
            final int numStored = this.dataManager.putLocallyAndUniversally(content);
            
            if (numStored > 0)
            {
                /* The data was stored online!, lets store it here now*/
                if (!this.actorContent.containsKey(content.getType()))
                {
                    this.actorContent.put(content.getType(), new TreeSet<>());
                }

                this.actorContent.get(content.getType()).add(new ContentMetadata(content));
            }            
            
            /* Lets also update the actor object on the DHT */
            this.actor.setUpdated();
            this.dataManager.putLocallyAndUniversally(this.actor);

            return numStored;
        }
        catch (IOException e)
        {
            /* Just re-throw this exception and return */
            throw new IOException(e);
        }
    }

    /**
     * Gets all content from this actor posted after the given timestamp
     *
     * @param ts
     *
     * @return
     */
//    public List<ContentReference> getContentPostedAfter(final long ts)
//    {
//
//    }
    /**
     * Gets all content of a specific type from this actor posted after the given timestamp
     *
     * @param ts
     * @param type The type of content
     *
     * @return
     */
//    public List<ContentReference> getContentPostedAfter(final long ts, final String type)
//    {
//
//    }
    /**
     * Gets all content from this actor posted before the given timestamp
     *
     * @param ts
     *
     * @return
     */
//    public List<ContentReference> getContentPostedBefore(final long ts)
//    {
//
//    }
    /**
     * Gets all content of a specific type from this actor posted before the given timestamp
     *
     * @param ts
     * @param type The type of content
     *
     * @return
     */
//    public List<ContentReference> getContentPostedBefore(final long ts, final String type)
//    {
//
//    }
    /**
     * Gets all content from this actor posted between the given timestamps
     *
     * @param startTs
     * @param endTs
     *
     * @return
     */
//    public List<ContentReference> getContentPostedBetween(final long startTs, final long endTs)
//    {
//
//    }
    /**
     * Gets all content of a specific type from this actor posted between the given timestamps
     *
     * @param startTs
     * @param endTs
     * @param type    The type of content
     *
     * @return
     */
//    public List<ContentReference> getContentPostedBetween(final long startTs, final long endTs, final String type)
//    {
//
//    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("ContentManager: ");
        sb.append("[# Content: ");
        sb.append(this.actorContent.size());
        sb.append("]");

        return sb.toString();
    }
}
