package dosna.simulations.performance.large;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * This is a class that simulates the user activities for a single user;
 * this is part of the overall simulation of users.
 *
 * @author Joshua Kissoon
 * @since 20140508
 */
public class SimulatedUserActions implements Runnable
{

    private final SimConfig config;
    private int numContent, numConnections, numActivityStreamRefreshes, numContentModified;
    private final SimulatedUser simulatedUser;
    private final CountDownLatch threadsWaiter;

    
    {
        this.numContent = 0;
        this.numConnections = 0;
        this.numActivityStreamRefreshes = 0;
        this.numContentModified = 0;
    }

    /**
     * Setup the simulation for a single user
     *
     * @param config        The simulation configuration
     * @param simulatedUser The user in the simulation
     * @param threadsWaiter A latch used to indicate to the main program when this thread is finished
     */
    public SimulatedUserActions(final SimulatedUser simulatedUser, SimConfig config, CountDownLatch threadsWaiter)
    {
        this.config = config;
        this.simulatedUser = simulatedUser;
        this.threadsWaiter = threadsWaiter;
    }

    @Override
    public void run()
    {
        while (numContent < config.numContent() || numConnections < config.numConnections()
                || numActivityStreamRefreshes < config.numActivityStreamRefreshes() || numContentModified < config.numContentModifications())
        {
            try
            {
                if (numContent < config.numContent())
                {
                    /* Create and post a content */
                    this.createAndPostContent();
                    Thread.sleep(this.config.randomLongWaitPeriod());
                }

                if (numConnections < config.numConnections())
                {
                    /* Create a new connection */
                    this.createConnection();
                    Thread.sleep(this.config.randomLongWaitPeriod());
                }

                if (numActivityStreamRefreshes < config.numActivityStreamRefreshes())
                {
                    /* Refresh our activity stream here */
                    this.refreshActivityStream();
                    Thread.sleep(this.config.randomLongWaitPeriod());
                }

                if (numContentModified < config.numContentModifications())
                {
                    /* Modify some content here */
                    this.modifyContent();
                    Thread.sleep(this.config.randomLongWaitPeriod());
                }

            }
            catch (InterruptedException ex)
            {
                System.err.println("User Simulation interrupted. ");
            }
        }

        try
        {
            /* Wait a few seconds before shutting down */
            Thread.sleep(this.config.randomLongWaitPeriod());

            /* Lets shutdown everything */
            this.simulatedUser.stopKadRefreshOperation();
            //System.out.println("Finished Executing everything....");

            /* Finished executing!, lets inform the main program */
            this.threadsWaiter.countDown();
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
            String status = this.simulatedUser.getActor().getName() + " - Status " + numContent + " " + config.randomString();
            this.simulatedUser.setNewStatus(status);
            numContent++;
        }
        catch (IOException ex)
        {
            System.err.println("User " + this.simulatedUser.getActor().getId() + " error whiles posting status. Msg: " + ex.getMessage());
        }
    }

    /**
     * Handles creating a new connection.
     */
    public synchronized void createConnection()
    {
        this.simulatedUser.createRandomConnection();
        numConnections++;
    }

    /**
     * Refreshes the activity stream
     */
    public synchronized void refreshActivityStream()
    {
            int numItems = this.simulatedUser.refreshActivityStream();
            numActivityStreamRefreshes++;
        //if (numActivityStreamRefreshes == this.config.numActivityStreamRefreshes())
            {
                System.out.println(this.simulatedUser.getActor().getId() + " - Activity Stream Refreshed: " + numItems + " Content in activity stream");
            }
        }

    /**
     * Modify some content from a connection
     */
    public synchronized void modifyContent()
    {
        this.simulatedUser.updateRandomContent();
        numContentModified++;
    }

    public SimulatedUser getSimulatedUser()
    {
        return this.simulatedUser;
    }
}
