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
public class SimConfig implements SimulationConfiguration
{

    /* Number of users in the simulation */
    public final static int N = 300;

    /* Number of users that should be offline */
    public final static int NUMBER_OFFLINE_USERS = 350;

    /* How much users to put offline at a time */
    public final static int NUM_USERS_PER_OFFLINE_SET = 10;

    /* Number of users to process per set */
    public final static int SET_SIZE = 50;

    /* Number of users to process per activity set */
    public final static int ACTIVITIES_SET_SIZE = 50;

    /* How much time (in milliseconds) to wait between processing sets */
    public final static long INTER_SET_WAIT = 30 * 1000;

    /* How much time (in milliseconds) to wait between processing "putting users offline" sets */
    public final static long INTER_USERS_OFFLINE_SET_WAIT = 60 * 1000; // Long wait so refresh activities can be ran


    /* How much time (in milliseconds) to wait between each user running their activities */
    public final static long INTER_ACTIVITY_USER_WAIT = 8 * 1000;

    /* How long should we delay between creating users on the network */
    private final static int USER_CREATION_DELAY = 1000;

    /* Initial details of a user */
    private final static int NUM_INITIAL_CONTENT = 20;
    private final static int NUM_INITIAL_CONNECTIONS = 20;

    /* The number of times a user can perform the activities */
    private final static int NUM_CONTENT = 5;
    private final static int NUM_CONNECTIONS = 5;
    private final static int NUM_ACTIVITY_STREAM_REFRESHES = 6;
    private final static int NUM_CONTENT_MODIFICATIONS = 5;

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
     * @return How many users should be processed per set
     */
    public int numUsersPerSet()
    {
        return SET_SIZE;
    }

    /**
     * @return How many users should be offline during the simulation
     */
    public int numOfflineUsers()
    {
        return NUMBER_OFFLINE_USERS;
    }

    public int numUsersPerOfflineSet()
    {
        return NUM_USERS_PER_OFFLINE_SET;
    }

    /**
     * @return How many users should be processed per set
     */
    public int numUsersPerActivitySet()
    {
        return ACTIVITIES_SET_SIZE;
    }

    public long interSetWait()
    {
        return INTER_SET_WAIT;
    }

    public long interOfflineSetWait()
    {
        return INTER_USERS_OFFLINE_SET_WAIT;
    }

    public long interActivityUserWait()
    {
        return INTER_ACTIVITY_USER_WAIT;
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

    @Override
    public int minLongWaitPeriod()
    {
        return MIN_LONG_WAIT_PERIOD;
    }

    @Override
    public int maxLongWaitPeriod()
    {
        return MAX_LONG_WAIT_PERIOD;
    }
}
