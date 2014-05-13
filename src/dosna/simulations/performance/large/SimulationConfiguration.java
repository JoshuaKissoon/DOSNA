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
public interface SimulationConfiguration
{

    /**
     * @return The number of users in the simulation
     */
    public int numUsers();

    /**
     * @return How many users should be processed per set
     */
    public int numUsersPerSet();

    /**
     * @return How many users should be offline during the simulation
     */
    public int numOfflineUsers();

    public int numUsersPerOfflineSet();

    /**
     * @return How many users should be processed per set
     */
    public int numUsersPerActivitySet();

    public long interSetWait();

    public long interOfflineSetWait();

    public long interActivityUserWait();

    /**
     * @return How long to delay between creating sets of users on the network
     */
    public int userCreationDelay();

    /**
     * @return The number of initial content each user has to have
     */
    public int numInitialContent();

    /**
     * @return The number of content each user has to have
     */
    public int numContent();

    /**
     * @return The number of connections each user has to have
     */
    public int numInitialConnections();

    /**
     * @return The number of connections each user has to have
     */
    public int numConnections();

    /**
     * @return The number of times a user's activity stream has to be refreshed in the simulation.
     */
    public int numActivityStreamRefreshes();

    /**
     * @return The number of connections content a user have to modify.
     */
    public int numContentModifications();

    /**
     * @return The minimum time a user has to wait before performing another operation
     */
    public int minWaitPeriod();

    /**
     * @return The maximum time a user has to wait before performing another operation
     */
    public int maxWaitPeriod();

    /**
     * @return The minimum time a user has to wait before performing another operation
     */
    public int minLongWaitPeriod();

    /**
     * @return The maximum time a user has to wait before performing another operation
     */
    public int maxLongWaitPeriod();

    /**
     * Computes a random wait period between the min and max wait period.
     *
     * @return The computed value
     */
    public default long randomWaitPeriod()
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
    public default long randomLongWaitPeriod()
    {
        /* Compute a random value */
        long val = (long) (Math.random() * maxLongWaitPeriod());

        /* If the value is less than the minimum wait period, we add the minimum wait to the value */
        return (val < minLongWaitPeriod()) ? val + minLongWaitPeriod() : val;
    }

    /**
     * Generate a random string
     *
     * @return the random string
     */
    public default String randomString()
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
    public default String randomStringShort()
    {
        return UUID.randomUUID().toString();
    }
}
