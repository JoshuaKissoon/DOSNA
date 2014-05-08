package dosna.simulations.performance;

import dosna.core.DOSNAStatistician;
import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;
import kademlia.KadStatistician;

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
            String actorId = config.randomStringShort() + i;
            this.users[i] = new SimulatedUser(actorId, actorId + "pass", "Actor " + i + " Name", i, this.users);
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

        /* USER'S DATA USAGE */
        double dataSent = 0, dataReceived = 0, bootstrapTime = 0, avgContentLookupTime = 0;
        double avgContentLookupRouteLth = 0, avgActivityStreamLoadTime = 0;
        int numContentLookups = 0;

        /* Aggregate the total user's data */
        for (int i = 0; i < config.numUsers(); i++)
        {
            SimulatedUser simUser = this.users[i];
            KadStatistician statsMan = simUser.getKademliaNode().getStatistician();
            DOSNAStatistician dosnaStatsMan = simUser.getStatistician();

            dataSent += statsMan.getTotalDataSent();
            dataReceived += statsMan.getTotalDataReceived();
            bootstrapTime += statsMan.getBootstrapTime();
            numContentLookups += statsMan.numContentLookups();
            avgContentLookupTime += statsMan.averageContentLookupTime();
            avgContentLookupRouteLth += statsMan.averageContentLookupRouteLength();
            avgActivityStreamLoadTime += dosnaStatsMan.avgActivityStreamLoadTime();
        }

        /* Print the Statistics */
        DecimalFormat df = new DecimalFormat("#.00");
        int numUsers = this.config.numUsers();
        String stats = "\nAverage Statistics for " + numUsers + " users; \n";
        stats += "Avg Data Sent: " + (dataSent / numUsers) + " bytes; \n";
        stats += "Avg Data Received: " + (dataReceived / numUsers) + " bytes; \n";
        stats += "Avg Bootstrap Time: " + df.format(bootstrapTime / numUsers) + " ms; \n";
        stats += "Avg # Content Lookups: " + (numContentLookups / (double) numUsers) + "; \n";
        stats += "Avg Content Lookup Time: " + df.format(avgContentLookupTime / numUsers) + " ms; \n";
        stats += "Avg Content Lookup Route Length: " + df.format(avgContentLookupRouteLth / numUsers) + "; \n";
        stats += "Avg Activity Stream load time: " + df.format(avgActivityStreamLoadTime / numUsers) + " ms; \n";
        stats += "\n";
        System.out.println(stats);
    }

    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        sim.start();
    }
}
