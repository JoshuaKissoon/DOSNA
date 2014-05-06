package dosna.simulations.performance;

import java.util.concurrent.CountDownLatch;
import kademlia.Statistician;

/**
 * The class that launches the simulation and aggregates the statistics after the completion of the simulation.
 *
 * @author Joshua Kissoon
 * @since 20140505
 */
public class Simulation
{

    private final SimConfig config;
    private final UserSimulation[] users;

    /* A count down latch to wait for all threads to execute */
    private final CountDownLatch threadsWaiter;

    public Simulation()
    {
        this.config = new SimConfig();
        this.users = new UserSimulation[config.numUsers()];

        this.threadsWaiter = new CountDownLatch(this.config.numUsers());
    }

    /**
     * Start the simulation
     */
    public void start()
    {
        for (int i = 0; i < config.numUsers(); i++)
        {
            /**
             * If we've created a set of users, lets wait a while before creating the next set
             */
            if (i % config.numUsersPerSet() == 0)
            {
                try
                {
                    Thread.sleep(config.userCreationDelay());
                }
                catch (InterruptedException ex)
                {

                }
            }

            /* Start a new thread for this user */
            users[i] = new UserSimulation(config, i, this.threadsWaiter);
            Thread t = new Thread(users[i]);
            t.start();
        }

        /* Lets wait for all users to finish */
        try
        {
            this.threadsWaiter.await();

            /* Lets print their data usage */
            for (int i = 0; i < config.numUsers(); i++)
            {
                UserSimulation us = this.users[i];
                SimulatedUser simUser = us.getSimulatedUser();

                Statistician statsMan = simUser.getKademliaNode().getStatistician();

                System.out.println("\n\nUser " + i);
                System.out.println("Total Data Sent: " + statsMan.getTotalDataSent());
                System.out.println("Total Data Received: " + statsMan.getTotalDataReceived());
            }
        }
        catch (InterruptedException ex)
        {
            System.err.println("Count down latch await() interrupted. Msg: " + ex.getMessage());
        }

//        System.out.println(Statistics.dataReceived);
//        System.out.println(Statistics.dataSent);
    }

    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        sim.start();
    }
}
