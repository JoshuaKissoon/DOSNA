package dosna.osn.actor;

import dosna.dhtAbstraction.DOSNAContent;

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
        this.contentManager = new ContentManager();
        this.connectionManager = ConnectionsManager.createNew(this);
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
