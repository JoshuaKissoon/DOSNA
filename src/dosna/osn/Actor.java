package dosna.osn;

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
    ActorContentManager contentManager;

    
    {
        this.contentManager = new ActorContentManager();
    }

    public abstract String getUsername();

    public abstract String getName();

    /**
     * @return The ContentManager that manages this actors contents
     */
    public ActorContentManager getContentManager()
    {
        return this.contentManager;
    }

    /**
     * Set a new ContentManager for this Actor
     *
     * @param cm The new content manager
     */
    public void setContentManager(ActorContentManager cm)
    {
        this.contentManager = cm;
    }
}
