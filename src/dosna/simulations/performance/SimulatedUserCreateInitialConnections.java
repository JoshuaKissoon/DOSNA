package dosna.simulations.performance;

import dosna.osn.actor.Actor;
import java.util.concurrent.CountDownLatch;

/**
 * This part of the simulation initializes the user objects.
 *
 * @author Joshua Kissoon
 * @since 20140502
 */
public class SimulatedUserCreateInitialConnections implements Runnable
{

    private final SimulatedUser simulatedUser;
    private final SimConfig config;
    private final CountDownLatch threadsWaiter;
    private int numInitialConnections = 0;

    /**
     * Setup the simulated user
     *
     * @param simulatedUser The Simulated user object
     * @param config
     * @param threadsWaiter
     */
    public SimulatedUserCreateInitialConnections(final SimulatedUser simulatedUser, SimConfig config, CountDownLatch threadsWaiter)
    {
        this.simulatedUser = simulatedUser;
        this.config = config;
        this.threadsWaiter = threadsWaiter;
    }

    @Override
    public void run()
    {
        try
        {
            while (numInitialConnections < config.numInitialConnections())
            {
                /* Create and post a content */
                this.createConnection();
                Thread.sleep(config.randomWaitPeriod());
            }

            threadsWaiter.countDown();
        }
        catch (InterruptedException ex)
        {

        }
    }

    /**
     * Handles creating a new connection.
     */
    public synchronized void createConnection()
    {
        int connectionUid = (this.simulatedUser.userNumber + numInitialConnections + 1) % this.config.numUsers();
        this.simulatedUser.createConnection(new Actor("actor" + connectionUid));
        numInitialConnections++;
    }

}
