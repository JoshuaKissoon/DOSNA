package dosna.osn.actor;

import dosna.core.ContentMetadata;
import dosna.content.DOSNAContent;
import dosna.dhtAbstraction.DataManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Each Actor will put many content onto his/her profile; This class:
 * - Puts content from this actor onto the DHT.
 * - Keeps track of all of the Actor's content.
 *
 * @author Joshua Kissoon
 * @since 20140401
 */
public class ActorContentManager implements Serializable
{

    /*  Set of content for a specific actor <String - The type of element stored, TreeSet<ContentMetadata>>
     */
    private HashMap<String, TreeSet<ContentMetadata>> actorContent;
    private transient DataManager dataManager;
    private transient Actor actor;

    /**
     * Blank constructor mainly used by serializers
     */
    public ActorContentManager()
    {

    }

    private ActorContentManager(final Actor actor)
    {
        actorContent = new HashMap<>();
        this.actor = actor;
    }

    public static ActorContentManager createNew(Actor actor)
    {
        return new ActorContentManager(actor);
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
    public synchronized int store(final DOSNAContent content) throws IOException
    {
        try
        {
            /* Lets store this content on the DHT now */
            final int numStored = this.dataManager.put(content);

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
            this.dataManager.put(this.actor);

            return numStored;
        }
        catch (IOException e)
        {
            /* Just re-throw this exception and return */
            throw new IOException(e);
        }
    }

    /**
     * Get the metadata for all content from this actor.
     *
     * @return A collection of the required content
     */
    public Collection<ContentMetadata> getAllContent()
    {
        Collection ret = new ArrayList();

        for (String type : this.actorContent.keySet())
        {
            ret.addAll(this.getAllContent(type));
        }

        return ret;
    }

    /**
     * Get the metadata for all content of a given type
     *
     * @param type The type of the content to get
     *
     * @return A collection of the required content
     */
    public Collection<ContentMetadata> getAllContent(String type)
    {
        if (!this.actorContent.containsKey(type))
        {
            return new ArrayList<>();
        }
        else
        {
            return this.actorContent.get(type);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("\nContentManager: ");
        sb.append("[# Content Types: ");
        sb.append(this.actorContent.size());
        sb.append("]");

        for (Map.Entry<String, TreeSet<ContentMetadata>> e : this.actorContent.entrySet())
        {
            sb.append("[Content Type: ");
            sb.append(e.getKey());
            sb.append(", # items: ");
            sb.append(e.getValue().size());

            sb.append(", Items: ");

            e.getValue().stream().forEach((md) ->
            {
                sb.append(md);
            });

            sb.append("]\n");
        }

        return sb.toString();
    }
}
