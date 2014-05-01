package dosna.content;

import dosna.osn.actor.Actor;
import java.util.Collection;

/**
 * This interface defines any content that actors are related to.
 *
 * @author Joshua Kissoon
 * @since 20140501
 */
public interface ActorRelatedContent
{

    /**
     * Add a new actor relation to this content
     *
     * @param a            The actor related to this content
     * @param relationship What's the relationship between the actor and the content
     */
    public void addActor(Actor a, String relationship);

    /**
     * Add a new actor relation to this content
     *
     * @param userId       The ID of the actor related to this content
     * @param relationship What's the relationship between the actor and the content
     */
    public void addActor(String userId, String relationship);

    /**
     * Get all the actors related to this content and their relationships to the content.
     *
     * @return A Map with the details
     */
    public Collection getActors();
}
