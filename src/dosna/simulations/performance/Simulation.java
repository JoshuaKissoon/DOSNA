package dosna.simulations.performance;

import kademlia.Statistics;

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

    public Simulation()
    {
        config = new SimConfig();
        users = new UserSimulation[config.numUsers()];
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
            users[i] = new UserSimulation(config, i);
            Thread t = new Thread(users[i]);
            t.start();
        }
        
        System.out.println(Statistics.dataReceived);
        System.out.println(Statistics.dataSent);
    }
    
    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        sim.start();
    }
}
