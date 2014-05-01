package dosna.osn.actor;

import dosna.dhtAbstraction.DataManager;
import dosna.notification.NotificationBox;
import java.io.IOException;

/**
 * A class that handles creating a new actor on the DOSN.
 *
 * @author Joshua Kissoon
 * @since 20140501
 */
public class ActorCreator
{

    private final DataManager dataManager;
    private final Actor actor;

    /**
     * Initialize the actor creator class
     *
     * @param dataManager The Network Data Manager
     * @param actor       a pre-populated actor object with basic data
     */
    public ActorCreator(DataManager dataManager, Actor actor)
    {
        this.dataManager = dataManager;
        this.actor = actor;
    }

    /**
     * Method that creates the new actor
     *
     * @return Boolean whether the actor creation was successful or not
     *
     * @throws java.io.IOException
     */
    public Actor createActor() throws IOException
    {
        /* Lets create a new notification box for this actor */
        NotificationBox nb = new NotificationBox(actor);
        dataManager.putLocallyAndUniversally(nb);
        actor.setNotificationBoxNid(nb.getKey());
        
        System.out.println("Notification box put; NID: " + nb.getKey());

        dataManager.putLocally(actor);
        dataManager.put(actor);

        return this.actor;
    }
}
