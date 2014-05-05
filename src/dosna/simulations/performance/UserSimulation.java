package dosna.simulations.performance;

/**
 * This is a class that simulates the user activities for a single user;
 * this is part of the overall simulation of users.
 *
 * @author Joshua Kissoon
 * @since 20140505
 */
public class UserSimulation implements Runnable
{

    private final SimConfig config;
    private long numContent, numConnections, numActivityStreamRefreshes, numContentModified;

    
    {
        this.numContent = 0;
        this.numConnections = 0;
        this.numActivityStreamRefreshes = 0;
        this.numContentModified = 0;
    }

    /**
     * Setup the simulation for a single user
     *
     * @param config The simulation configuration
     */
    public UserSimulation(SimConfig config)
    {
        this.config = config;
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
                }

                Thread.sleep(this.randomWaitPeriod());

                if (numConnections < config.numConnections())
                {
                    /* Create a new connection */
                    this.createConnection();
                }

                Thread.sleep(this.randomWaitPeriod());

                if (numActivityStreamRefreshes < config.numActivityStreamRefreshes())
                {
                    /* Refresh our activity stream here */
                    this.refreshActivityStream();
                }

                Thread.sleep(this.randomWaitPeriod());

                if (numContentModified < config.numContentModifications())
                {
                    /* Modify some content here */
                    this.modifyContent();
                }

                Thread.sleep(this.randomWaitPeriod());
            }
            catch (InterruptedException ex)
            {
                System.err.println("User Simulation interrupted. ");
            }
        }
        
        System.out.println("Finished Executing everything....");
    }

    /**
     * Handles creating and posting a new content on the DHT.
     */
    public void createAndPostContent()
    {
        numContent++;
    }

    /**
     * Handles creating a new connection.
     */
    public void createConnection()
    {
        numConnections++;
    }

    /**
     * Refreshes the activity stream
     */
    public void refreshActivityStream()
    {
        numActivityStreamRefreshes++;
    }

    /**
     * Modify some content from a connection
     */
    public void modifyContent()
    {
        numContentModified++;
    }

    /**
     * Computes a random wait period between the min and max wait period.
     *
     * @return The computed value
     */
    public long randomWaitPeriod()
    {
        /* Compute a random value */
        long val = (long) (Math.random() * config.maxWaitPeriod());

        /* If the value is less than the minimum wait period, we add the minimum wait to the value */
        return (val < config.minWaitPeriod()) ? val + config.minWaitPeriod() : val;
    }
}
