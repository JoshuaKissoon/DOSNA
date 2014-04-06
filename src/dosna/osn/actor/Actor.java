package dosna.osn.actor;

import dosna.dhtAbstraction.DOSNAContent;
import dosna.dhtAbstraction.DataManager;

/**
 * Any type of user/group within the system of the system
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public abstract class Actor extends DOSNAContent
{

    /* Serialization keys */
    final static String SERIALK_CONTENT_MANAGER = "CManager";

    /* Manage the content posted by this actor */
    protected ContentManager contentManager;

    /* Manage relationships this actor have to other actors */
    protected ConnectionsManager connectionManager;

    
    {
        this.contentManager = ContentManager.createNew(this);
        this.connectionManager = ConnectionsManager.createNew(this);
    }

    /**
     * Method that initializes the actor object and it's components.
     *
     * @param mngr The DataManager to manage content on the DHT
     */
    public final void init(final DataManager mngr)
    {
        /* After the ContentManager have been loaded from file, we need to set a few things */
        this.contentManager.setDataManager(mngr);
        this.contentManager.setActor(this);
    }

    public abstract String getUserId();

    public abstract String getName();

    /**
     * @return The ContentManager that manages this actors contents
     */
    public ContentManager getContentManager()
    {
        return this.contentManager;
    }

    /**
     * Set a new ContentManager for this Actor
     *
     * @param cm The new content manager
     */
    public void setContentManager(final ContentManager cm)
    {
        this.contentManager = cm;
    }

    /**
     * @return The ConnectionManager for this actor
     */
    public ConnectionsManager getConnectionManager()
    {
        return this.connectionManager;
    }
}
