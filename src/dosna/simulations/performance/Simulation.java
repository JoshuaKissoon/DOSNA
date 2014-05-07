package dosna.simulations.performance;

import dosna.core.DOSNAStatistician;
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
    private final SimulatedUser[] users;

    /* A count down latch to wait for all threads to execute */
    private CountDownLatch threadsWaiter;

    public Simulation()
    {
        this.config = new SimConfig();
        this.users = new SimulatedUser[config.numUsers()];
    }

    /**
     * Start the simulation
     */
    public void start()
    {
        /* INITIALIZE SIMULATED USERS & CONNECT THEM TO THE NETWORK */
        threadsWaiter = new CountDownLatch(this.config.numUsers());
        for (int i = 0; i < config.numUsers(); i++)
        {
            String actorId = "actor" + i;
            this.users[i] = new SimulatedUser(actorId, actorId + "pass", actorId + " Name", i);
            new Thread(new SimulatedUserInitialization(this.users[i], config, threadsWaiter)).start();
            try
            {
                Thread.sleep(config.userCreationDelay());
            }
            catch (InterruptedException ex)
            {

            }
        }

        /* Wait for all threads to finish */
        try
        {
            threadsWaiter.await();
        }
        catch (InterruptedException ex)
        {

        }

        System.out.println("\nNodes Startup Finished. \n");

        /* INITIALIZE THE USER'S CONTENT */
        threadsWaiter = new CountDownLatch(this.config.numUsers());
        for (int i = 0; i < config.numUsers(); i++)
        {
            /* Start a new thread for this user */
            new Thread(new SimulatedUserCreateInitialContent(this.users[i], config, threadsWaiter)).start();
        }

        /* Wait for threads to finish */
        try
        {
            threadsWaiter.await();
        }
        catch (InterruptedException ex)
        {

        }

        System.out.println("\nInitial content creation finished. \n");

        /* INITIALIZE THE USER'S CONNECTIONS */
        threadsWaiter = new CountDownLatch(this.config.numUsers());
        for (int i = 0; i < config.numUsers(); i++)
        {
            new Thread(new SimulatedUserCreateInitialConnections(this.users[i], config, threadsWaiter)).start();
        }

        /* Wait for threads to finish */
        try
        {
            threadsWaiter.await();
        }
        catch (InterruptedException ex)
        {

        }

        /* @todo Get the updated actor objects for the different simualated users */
        System.out.println("\nInitial connections creation finished. \n");

        try
        {
            Thread.sleep(30000);
        }
        catch (InterruptedException ex)
        {

        }

        System.out.println("\nStarting the real time operations now. \n");

        /* NOW LETS RUN THE PROCESSES */
        threadsWaiter = new CountDownLatch(this.config.numUsers());
        for (int i = 0; i < config.numUsers(); i++)
        {
            new Thread(new SimulatedUserActions(this.users[i], config, threadsWaiter)).start();
        }

        /* Wait for threads to finish */
        try
        {
            threadsWaiter.await();
        }
        catch (InterruptedException ex)
        {

        }

        /* PRINT USER'S DATA USAGE */
        for (int i = 0; i < config.numUsers(); i++)
        {
            SimulatedUser simUser = this.users[i];
            Statistician statsMan = simUser.getKademliaNode().getStatistician();
            DOSNAStatistician dosnaStatsMan = simUser.getStatistician();

            String stats = "User " + i + "; ";
            stats += "Data Sent: " + statsMan.getTotalDataSent() + "; ";
            stats += "Data Received: " + statsMan.getTotalDataReceived() + "; \n";
            stats += "Bootstrap Time: " + statsMan.getBootstrapTime() + "; ";
            stats += "# Content Lookups: " + statsMan.numContentLookups() + "; ";
            stats += "Avg Content Lookup Time: " + statsMan.averageContentLookupTime() + "; \n";
            stats += "Avg Content Lookup Route Length: " + statsMan.averageContentLookupRouteLength() + "; ";
            stats += "Avg Activity Stream load time: " + dosnaStatsMan.avgActivityStreamLoadTime() + "; ";
            stats += "\n";
            System.out.println(stats);
        }
    }

    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        sim.start();
    }
}
