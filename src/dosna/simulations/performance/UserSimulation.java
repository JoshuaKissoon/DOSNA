package dosna.simulations.performance;

import java.io.IOException;

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
    private int numContent, numConnections, numActivityStreamRefreshes, numContentModified;

    private final int userNumber;
    private final String actorId, password, name;

    private final SimulatedUser simulatedUser;

    
    {
        this.numContent = 0;
        this.numConnections = 0;
        this.numActivityStreamRefreshes = 0;
        this.numContentModified = 0;
    }

    /**
     * Setup the simulation for a single user
     *
     * @param config     The simulation configuration
     * @param userNumber The user number in the simulation
     */
    public UserSimulation(SimConfig config, int userNumber)
    {
        this.config = config;
        this.userNumber = userNumber;

        this.actorId = "actor" + userNumber;
        this.password = "password" + userNumber;
        this.name = "Actor Name " + userNumber;

        simulatedUser = new SimulatedUser(this.actorId, this.password, this.name);
    }

    @Override
    public void run()
    {
        /* Lets signup and login */
        this.signupAndLogin();

        while (numContent < config.numContent() || numConnections < config.numConnections()
                || numActivityStreamRefreshes < config.numActivityStreamRefreshes() || numContentModified < config.numContentModifications())
        {
            try
            {
                if (numContent < config.numContent())
                {
                    /* Create and post a content */
                    this.createAndPostContent();
                    Thread.sleep(this.randomWaitPeriod());
                }

                if (numConnections < config.numConnections())
                {
                    /* Create a new connection */
                    this.createConnection();
                    Thread.sleep(this.randomWaitPeriod());
                }

                if (numActivityStreamRefreshes < config.numActivityStreamRefreshes())
                {
                    /* Refresh our activity stream here */
                    this.refreshActivityStream();
                    Thread.sleep(this.randomWaitPeriod());
                }

                if (numContentModified < config.numContentModifications())
                {
                    /* Modify some content here */
                    this.modifyContent();
                    Thread.sleep(this.randomWaitPeriod());
                }

            }
            catch (InterruptedException ex)
            {
                System.err.println("User Simulation interrupted. ");
            }
        }

        /* Lets shutdown everything */
        this.simulatedUser.stopKadRefreshOperation();
        System.out.println("Finished Executing everything....");
    }

    /**
     * Let the user signup and log into the system
     */
    private synchronized void signupAndLogin()
    {
        try
        {
            this.simulatedUser.signup();
            Thread.sleep(1000);
            this.simulatedUser.login();
            Thread.sleep(1000);
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
            String status = this.name + " - Status " + numContent;
            this.simulatedUser.setNewStatus(status);
            numContent++;
        }
        catch (IOException ex)
        {
            System.err.println("User " + this.userNumber + " error whiles posting status. Msg: " + ex.getMessage());
        }
    }

    /**
     * Handles creating a new connection.
     */
    public synchronized void createConnection()
    {
        int connectionUid = (this.userNumber + numConnections + 1) % this.config.numUsers();
        this.simulatedUser.createConnection("actor" + connectionUid);
        numConnections++;
    }

    /**
     * Refreshes the activity stream
     */
    public synchronized void refreshActivityStream()
    {
        try
        {
            this.simulatedUser.refreshActivityStream();
            numActivityStreamRefreshes++;
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            System.err.println("Activity stream refresh thread interrupted exception. ");
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

    /**
     * Computes a random wait period between the min and max wait period.
     *
     * @return The computed value
     */
    public synchronized long randomWaitPeriod()
    {
        /* Compute a random value */
        long val = (long) (Math.random() * config.maxWaitPeriod());

        /* If the value is less than the minimum wait period, we add the minimum wait to the value */
        return (val < config.minWaitPeriod()) ? val + config.minWaitPeriod() : val;
    }
}
