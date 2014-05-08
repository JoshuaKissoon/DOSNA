package dosna.simulations.performance.large;

import java.util.UUID;

/**
 * Configuration information for this simulation.
 *
 * @note For simplification, the entire simulation assumes that the number of users is a multiple of the set size.
 *
 * @author Joshua Kissoon
 * @since 20140508
 */
public class SimConfig
{

    /* Number of users in the simulation */
    public final static int N = 500;

    /* Number of users that should be offline */
    public final static int NUMBER_OFFLINE_USERS = 200;

    /* Number of users to process per set */
    public final static int SET_SIZE = 250;

    /* How long should we delay between creating users on the network */
    private final static int USER_CREATION_DELAY = 1000;

    /* Initial details of a user */
    private final static int NUM_INITIAL_CONTENT = 20;
    private final static int NUM_INITIAL_CONNECTIONS = 20;

    /* The number of times a user can perform the activities */
    private final static int NUM_CONTENT = 2;
    private final static int NUM_CONNECTIONS = 2;
    private final static int NUM_ACTIVITY_STREAM_REFRESHES = 3;
    private final static int NUM_CONTENT_MODIFICATIONS = 1;

    /**
     * Maximum and minimum wait period (in ms) before a user does another activity,
     * the user simulation class will wait a random time between this period.
     */
    private final static int MIN_WAIT_PERIOD = 1500;
    private final static int MAX_WAIT_PERIOD = 5000;

    private final static int MIN_LONG_WAIT_PERIOD = 1500;
    private final static int MAX_LONG_WAIT_PERIOD = 5000;

    /**
     * @return The number of users in the simulation
     */
    public int numUsers()
    {
        return N;
    }

    /**
     * @return How many users should be offline during the simulation
     */
    public int numOfflineUsers()
    {
        return NUMBER_OFFLINE_USERS;
    }

    /**
     * @return How many users should be processed per set
     */
    public int numUsersPerSet()
    {
        return SET_SIZE;
    }

    /**
     * @return How long to delay between creating sets of users on the network
     */
    public int userCreationDelay()
    {
        return USER_CREATION_DELAY;
    }

    /**
     * @return The number of initial content each user has to have
     */
    public int numInitialContent()
    {
        return NUM_INITIAL_CONTENT;
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
    public int numInitialConnections()
    {
        return NUM_INITIAL_CONNECTIONS;
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

    /**
     * Computes a random wait period between the min and max wait period.
     *
     * @return The computed value
     */
    public synchronized long randomWaitPeriod()
    {
        /* Compute a random value */
        long val = (long) (Math.random() * maxWaitPeriod());

        /* If the value is less than the minimum wait period, we add the minimum wait to the value */
        return (val < minWaitPeriod()) ? val + minWaitPeriod() : val;
    }

    /**
     * Computes a random wait period between the min and max wait period.
     *
     * @return The computed value
     */
    public synchronized long randomLongWaitPeriod()
    {
        /* Compute a random value */
        long val = (long) (Math.random() * MAX_LONG_WAIT_PERIOD);

        /* If the value is less than the minimum wait period, we add the minimum wait to the value */
        return (val < MIN_LONG_WAIT_PERIOD) ? val + MIN_LONG_WAIT_PERIOD : val;
    }

    /**
     * Generate a random string
     *
     * @return the random string
     */
    public String randomString()
    {
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < (int) (Math.random() * 10) + 2; i++)
        {
            ret.append(UUID.randomUUID().toString());
            ret.append(" ");
        }

        return ret.toString();
    }

    /**
     * Generate a random string
     *
     * @return the random string
     */
    public String randomStringShort()
    {
        return UUID.randomUUID().toString();
    }
}
