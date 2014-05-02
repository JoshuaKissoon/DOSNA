package dosna.simulations;

import dosna.dhtAbstraction.DataManager;
import dosna.osn.actor.Actor;
import dosna.osn.actor.ActorCreator;
import java.io.IOException;

/**
 * Class that manages actors; this class is used in simulations.
 *
 * @author Joshua Kissoon
 * @since 20140502
 */
public class ActorManager
{

    private final DataManager dataManager;

    /**
     * Setup the actor manager
     *
     * @param dataManager
     */
    public ActorManager(final DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    /**
     * Create a new actor in the system.
     *
     *
     * @param username
     * @param password
     * @param fullName
     *
     * @return
     *
     * @throws java.io.IOException
     */
    public Actor createActor(final String username, final String password, final String fullName) throws IOException
    {
        Actor a = new Actor(username);
        a.setName(fullName);
        a.setPassword(password);

        a = new ActorCreator(dataManager, a).createActor();

        return a;
    }

}
