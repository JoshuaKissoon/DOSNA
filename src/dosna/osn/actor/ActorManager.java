package dosna.osn.actor;

import dosna.dhtAbstraction.DataManager;
import dosna.notification.NotificationBox;
import java.io.IOException;
import kademlia.dht.GetParameter;
import kademlia.dht.StorageEntry;
import kademlia.exceptions.ContentNotFoundException;

/**
 * A class that handles actors on the DOSN.
 *
 * @author Joshua Kissoon
 * @since 20140501
 */
public class ActorManager
{

    private final DataManager dataManager;

    /**
     * Initialize the actor creator class
     *
     * @param dataManager The Network Data Manager
     */
    public ActorManager(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    /**
     * Method that creates the new actor
     *
     * @param actor The actor to place on the DHT
     *
     * @return Boolean whether the actor creation was successful or not
     *
     * @throws java.io.IOException
     */
    public Actor createActor(Actor actor) throws IOException
    {
        /* Lets create a new notification box for this actor */
        NotificationBox nb = new NotificationBox(actor);
        dataManager.putLocallyAndUniversally(nb);
        actor.setNotificationBoxNid(nb.getKey());

        dataManager.putLocally(actor);
        dataManager.put(actor);

        return actor;
    }

    /**
     * Load the actor object from the DHT
     *
     * @param id The actor's user ID on the network
     *
     * @return The actor
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public Actor loadActor(String id) throws IOException, ContentNotFoundException
    {
        Actor a = new Actor(id);
        GetParameter gp = new GetParameter(a.getKey(), a.getType(), a.getId());
        StorageEntry se = dataManager.get(gp);
        return (Actor) new Actor().fromBytes(se.getContent().getBytes());
    }
}
