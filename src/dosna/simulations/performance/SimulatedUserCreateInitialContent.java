package dosna.simulations.performance;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * This part of the simulation initializes the user objects.
 *
 * @author Joshua Kissoon
 * @since 20140502
 */
public class SimulatedUserCreateInitialContent implements Runnable
{

    private final SimulatedUser simulatedUser;
    private final SimConfig config;
    private final CountDownLatch threadsWaiter;
    private long numInitialContent = 0;

    /**
     * Setup the simulated user
     *
     * @param simulatedUser The Simulated user object
     * @param config
     * @param threadsWaiter
     */
    public SimulatedUserCreateInitialContent(final SimulatedUser simulatedUser, SimConfig config, CountDownLatch threadsWaiter)
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
            while (numInitialContent < config.numInitialContent())
            {
                /* Create and post a content */
                this.createAndPostContent();
                Thread.sleep(config.initialOperationsDelay());
            }

            threadsWaiter.countDown();
        }
        catch (InterruptedException ex)
        {

        }
    }

    /**
     * Handles creating and posting a new content on the DHT.
     */
    public synchronized void createAndPostContent()
    {
        try
        {
            String status = this.simulatedUser.getActor().getName() + " - Status " + numInitialContent;

            for (int i = 0; i < (int) (Math.random() * 10) + 2; i++)
            {
                status += status;
            }

            this.simulatedUser.setNewStatus(status);
            numInitialContent++;
        }
        catch (IOException ex)
        {
            System.err.println("User " + this.simulatedUser.getActor().getId() + " error whiles posting status. Msg: " + ex.getMessage());
        }
    }

}
