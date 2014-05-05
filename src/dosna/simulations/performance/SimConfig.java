package dosna.simulations.performance;

/**
 * Configuration information for this simulation.
 *
 * @author Joshua Kissoon
 * @since 20140505
 */
public class SimConfig
{

    /* Number of users in the simulation */
    public final static int N = 10;

    /* How many users should be created at once on the network */
    private final static int NUM_USERS_PER_SET = 2;

    /* How long should we delay between creating users on the network */
    private final static int USER_CREATION_DELAY = 100;

    /* The number of times a user can perform the activities */
    private final static int NUM_CONTENT = 10;
    private final static int NUM_CONNECTIONS = 5;
    private final static int NUM_ACTIVITY_STREAM_REFRESHES = 15;
    private final static int NUM_CONTENT_MODIFICATIONS = 10;

    /**
     * Maximum and minimum wait period (in ms) before a user does another activity,
     * the user simulation class will wait a random time between this period.
     */
    private final static int MIN_WAIT_PERIOD = 10;
    private final static int MAX_WAIT_PERIOD = 100;

    /**
     * @return The number of users in the simulation
     */
    public int numUsers()
    {
        return N;
    }

    /**
     * @return How many users should be created per set
     */
    public int numUsersPerSet()
    {
        return NUM_USERS_PER_SET;
    }

    /**
     * @return How long to delay between creating sets of users on the network
     */
    public int userCreationDelay()
    {
        return USER_CREATION_DELAY;
    }

    /**
     * @return The number of content each user has to have
     */
    public int numContent()
    {
        return NUM_CONTENT;
    }

    /**
     * @return The number of connections each user has to have
     */
    public int numConnections()
    {
        return NUM_CONNECTIONS;
    }

    /**
     * @return The number of times a user's activity stream has to be refreshed in the simulation.
     */
    public int numActivityStreamRefreshes()
    {
        return NUM_ACTIVITY_STREAM_REFRESHES;
    }

    /**
     * @return The number of connections content a user have to modify.
     */
    public int numContentModifications()
    {
        return NUM_CONTENT_MODIFICATIONS;
    }

    /**
     * @return The minimum time a user has to wait before performing another operation
     */
    public int minWaitPeriod()
    {
        return MIN_WAIT_PERIOD;
    }

    /**
     * @return The maximum time a user has to wait before performing another operation
     */
    public int maxWaitPeriod()
    {
        return MAX_WAIT_PERIOD;
    }
}
