package dosna.simulations.performance.large;

import java.util.concurrent.CountDownLatch;

/**
 * This part of the simulation initializes the user objects.
 *
 * @author Joshua Kissoon
 * @since 20140508
 */
public class SimulatedUserInitialization implements Runnable
{

    private final SimulatedUser simulatedUser;
    private final SimulationConfiguration config;
    private final CountDownLatch threadsWaiter;

    /**
     * Setup the simulated user
     *
     * @param simulatedUser The Simulated user object
     * @param config
     * @param threadsWaiter
     */
    public SimulatedUserInitialization(final SimulatedUser simulatedUser, SimulationConfiguration config, CountDownLatch threadsWaiter)
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
