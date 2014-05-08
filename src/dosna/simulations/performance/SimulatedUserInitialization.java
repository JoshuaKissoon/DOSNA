package dosna.simulations.performance;

import java.util.concurrent.CountDownLatch;

/**
 * This part of the simulation initializes the user objects.
 *
 * @author Joshua Kissoon
 * @since 20140502
 */
public class SimulatedUserInitialization implements Runnable
{

    private final SimulatedUser simulatedUser;
    private final SimConfig config;
    private final CountDownLatch threadsWaiter;

    /**
     * Setup the simulated user
     *
     * @param simulatedUser The Simulated user object
     * @param config
     * @param threadsWaiter
     */
    public SimulatedUserInitialization(final SimulatedUser simulatedUser, SimConfig config, CountDownLatch threadsWaiter)
    {
        this.simulatedUser = simulatedUser;
        this.config = config;
        this.threadsWaiter = threadsWaiter;
    }

    @Override
    public void run()
    {
        this.simulatedUser.signup();
        threadsWaiter.countDown();
    }

}
