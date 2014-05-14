package dosna.simulations.performance.large;

/**
 * Configuration information for this simulation.
 *
 * @note For simplification, the entire simulation assumes that the number of users is a multiple of the set size.
 *
 * @todo Create an interface and create different configurations for the different numbers of users online
 *
 * @author Joshua Kissoon
 * @since 20140508
 */
public class SimConfig50Online implements SimulationConfiguration
{

    /* Number of users in the simulation */
    public final static int N = 500;

    /* Number of users that should be offline */
    public final static int NUMBER_OFFLINE_USERS = 250;

    /* How much users to put offline at a time */
    public final static int NUM_USERS_PER_OFFLINE_SET = 10;

    /* Number of users to process per set */
    public final static int SET_SIZE = 50;

    /* Number of users to process per activity set */
    public final static int ACTIVITIES_SET_SIZE = 25;

    /* How much time (in milliseconds) to wait between processing sets */
    public final static long INTER_SET_WAIT = 30 * 1000;

    /* How much time (in milliseconds) to wait between processing "putting users offline" sets */
    public final static long INTER_USERS_OFFLINE_SET_WAIT = 100 * 1000; // Long wait so refresh activities can be ran


    /* How much time (in milliseconds) to wait between each user running their activities */
    public final static long INTER_ACTIVITY_USER_WAIT = 10 * 1000;

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
    private final static int MAX_LONG_WAIT_PERIOD = 7500;

    @Override
    public int numUsers()
    {
        return N;
    }

    @Override
    public int numUsersPerSet()
    {
        return SET_SIZE;
    }

    @Override
    public int numOfflineUsers()
    {
        return NUMBER_OFFLINE_USERS;
    }

    @Override
    public int numUsersPerOfflineSet()
    {
        return NUM_USERS_PER_OFFLINE_SET;
    }

    @Override
    public int numUsersPerActivitySet()
    {
        return ACTIVITIES_SET_SIZE;
    }

    @Override
    public long interSetWait()
    {
        return INTER_SET_WAIT;
    }

    @Override
    public long interOfflineSetWait()
    {
        return INTER_USERS_OFFLINE_SET_WAIT;
    }

    @Override
    public long interActivityUserWait()
    {
        return INTER_ACTIVITY_USER_WAIT;
    }

    @Override
    public int userCreationDelay()
    {
        return USER_CREATION_DELAY;
    }

    @Override
    public int numInitialContent()
    {
        return NUM_INITIAL_CONTENT;
    }

    @Override
    public int numContent()
    {
        return NUM_CONTENT;
    }

    @Override
    public int numInitialConnections()
    {
        return NUM_INITIAL_CONNECTIONS;
    }

    @Override
    public int numConnections()
    {
        return NUM_CONNECTIONS;
    }

    @Override
    public int numActivityStreamRefreshes()
    {
        return NUM_ACTIVITY_STREAM_REFRESHES;
    }

    @Override
    public int numContentModifications()
    {
        return NUM_CONTENT_MODIFICATIONS;
    }

    @Override
    public int minWaitPeriod()
    {
        return MIN_WAIT_PERIOD;
    }

    @Override
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
