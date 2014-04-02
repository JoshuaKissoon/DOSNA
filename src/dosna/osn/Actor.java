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

    /* Manage the content posted by this actor */
    final ActorContentManager contentManager;

    
    {
        this.contentManager = new ActorContentManager();
    }

    public abstract String getUsername();

    public abstract String getName();

    public ActorContentManager getContentManager()
    {
        return this.contentManager;
    }
}
