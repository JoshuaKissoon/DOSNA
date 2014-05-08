package dosna.simulations.performance.large;

import dosna.core.DOSNAStatistician;
import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;
import kademlia.KadStatistician;

/**
 * The class that launches the simulation and aggregates the statistics after the completion of the simulation.
 *
 * We process this simulation with sets of users at a time.
 *
 * @author Joshua Kissoon
 * @since 20140508
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
        this.initializeUsers();
        System.out.println("Nodes & User Initialization Finished.");

        /* INITIALIZE THE USER'S CONTENT */
        this.createInitialUserContent();
        System.out.println("Initial content creation finished.");

        /* INITIALIZE THE USER'S CONNECTIONS */
        this.createInitialUsersConnections();
        System.out.println("Initial connections creation finished.");

        /* Pause a little before real time operations */
        try
        {
            Thread.sleep(30000);
        }
        catch (InterruptedException ex)
        {

        }

        System.out.println("Starting the real time operations now. \n");

        /* NOW LETS RUN THE PROCESSES */
        this.startSimulation();

        /* USER'S DATA USAGE */
        this.computeAggregatedStatistics();
    }

    /**
     * Initialize simulation users and connect them to the network.
     *
     * We initialize users in sets.
     */
    private void initializeUsers()
    {
        final int numSets = config.numUsers() / config.numUsersPerSet();

        for (int x = 0; x < numSets; x++)
        {
            /* Which user should we start at for this set of initialization */
            final int startUser = x * config.numUsersPerSet();

            /* Setup the CountDownLatch for this set */
            threadsWaiter = new CountDownLatch(this.config.numUsersPerSet());

            /* Lets initialize this set of suers */
            for (int i = 0; i < config.numUsersPerSet(); i++)
            {
                /* The index of the user we should operate on */
                final int userIndex = startUser + i;

                /* Random Actor Id */
                String actorId = config.randomStringShort() + userIndex;

                /* Initialize the user's object */
                this.users[userIndex] = new SimulatedUser(actorId, actorId + "pass", "Actor " + userIndex + " Name", userIndex, this.users);

                /* Start a new thread to do the user's initialization */
                new Thread(new SimulatedUserInitialization(this.users[userIndex], config, threadsWaiter)).start();

                /* Take a little nap before creating the other user */
                try
                {
                    Thread.sleep(config.userCreationDelay());
                }
                catch (InterruptedException ex)
                {

                }
            }

            /* Wait for all users in the set to finish initialization */
            try
            {
                threadsWaiter.await();
            }
            catch (InterruptedException ex)
            {

            }

            System.out.println("Finished creating user set " + x);
        }
    }

    /**
     * Create initial content for users
     */
    private void createInitialUserContent()
    {
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
    }

    /**
     * Create the initial connections for the users.
     */
    private void createInitialUsersConnections()
    {
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
    }

    /**
     * Do the real time simulation operations.
     */
    private void startSimulation()
    {
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
    }

    /**
     * Computed the aggregate statistics for user's data usage.
     *
     * @todo Get a class to manage this aggregation
     */
    private void computeAggregatedStatistics()
    {
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
        stats += "Avg Data Sent: " + (dataSent / numUsers) + " KBs; \n";
        stats += "Avg Data Received: " + (dataReceived / numUsers) + " KBs; \n";
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
