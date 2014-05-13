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

    private final SimulationConfiguration config;
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
        System.out.println("Nodes & User Initialization Finished.\n\n\n");

        /* INITIALIZE THE USER'S CONTENT */
        this.createInitialUserContent();
        System.out.println("Initial content creation finished.\n\n\n");

        /* INITIALIZE THE USER'S CONNECTIONS */
        this.createInitialUsersConnections();
        System.out.println("Initial connections creation finished.\n\n\n");

        /* LETS PUT USERS OFFLINE */
        this.putUsersOffline();
        System.out.println("We've put users offline!!!\n\n\n");

        //this.printUsers();

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
            final int startUser = x * config.numUsersPerSet();  // Which user should we start at for this set of initialization
            threadsWaiter = new CountDownLatch(this.config.numUsersPerSet());       // Setup the CountDownLatch for this set

            /* Lets initialize this set of suers */
            for (int i = 0; i < config.numUsersPerSet(); i++)
            {
                final int userIndex = startUser + i;    // The index of the user we should operate on
                String actorId = config.randomStringShort() + userIndex;    // Get a random Actor Id

                /* Initialize the user's object */
                this.users[userIndex] = new SimulatedUser(actorId, actorId + "pass", "Actor " + userIndex + " Name", userIndex, this.users);

                /* Start a new thread to do the user's initialization */
                new Thread(new SimulatedUserInitialization(this.users[userIndex], config, threadsWaiter)).start();

                try
                {
                    /* Take a little nap before creating the other user */
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
                Thread.sleep(config.interSetWait()); // pause so that a KadRefresh can be ran
            }
            catch (InterruptedException ex)
            {
            }

            System.out.println("Finished creating user set " + x);
            System.out.println();
        }
    }

    /**
     * Create initial content for users
     */
    private void createInitialUserContent()
    {
        final int numSets = config.numUsers() / config.numUsersPerSet();

        for (int x = 0; x < numSets; x++)
        {
            final int startUser = x * config.numUsersPerSet();  // Which user should we start at for this set of initialization
            threadsWaiter = new CountDownLatch(this.config.numUsersPerSet());       // Setup the CountDownLatch for this set

            /* Lets work on this set of suers */
            for (int i = 0; i < config.numUsersPerSet(); i++)
            {
                final int userIndex = startUser + i;    // The index of the user we should operate on
                /* Start a new thread for this user  */
                new Thread(new SimulatedUserCreateInitialContent(this.users[userIndex], config, threadsWaiter)).start();
            }

            /* Wait for all threads to finish */
            try
            {
                threadsWaiter.await();
                Thread.sleep(config.interSetWait()); // pause so that a KadRefresh can be ran
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }

            System.out.println("Finished creating content set " + x);
            System.out.println();
        }
    }

    /**
     * Create the initial connections for the users.
     */
    private void createInitialUsersConnections()
    {
        final int numSets = config.numUsers() / config.numUsersPerSet();

        for (int x = 0; x < numSets; x++)
        {
            final int startUser = x * config.numUsersPerSet();  // Which user should we start at for this set of initialization
            threadsWaiter = new CountDownLatch(this.config.numUsersPerSet());       // Setup the CountDownLatch for this set

            /* Lets work on this set of suers */
            for (int i = 0; i < config.numUsersPerSet(); i++)
            {
                final int userIndex = startUser + i;    // The index of the user we should operate on
                /* Start a new thread for this user  */
                new Thread(new SimulatedUserCreateInitialConnections(this.users[userIndex], config, threadsWaiter)).start();
            }

            /* Wait for all threads to finish */
            try
            {
                threadsWaiter.await();
                Thread.sleep(config.interSetWait()); // pause so that a KadRefresh can be ran
            }
            catch (InterruptedException ex)
            {
            }

            System.out.println("Finished creating connections set " + x);
            System.out.println();
        }
    }

    /**
     * For the simulation, a number of users need to be placed offline,
     * we take care of that in this method.
     *
     * We randomly select users to be put offline and put them offline in sets.
     */
    public void putUsersOffline()
    {
        final int numSets = config.numOfflineUsers() / config.numUsersPerOfflineSet();

        for (int x = 0; x < numSets; x++)
        {
            threadsWaiter = new CountDownLatch(this.config.numUsersPerOfflineSet());       // Setup the CountDownLatch for this set
            /* Lets work on this set of users */
            for (int i = 0; i < config.numUsersPerOfflineSet(); i++)
            {
                /* Select a random user to put offline */
                int userIndex = (int) (Math.random() * config.numUsers());

                while (!this.users[userIndex].isOnline())
                {
                    userIndex = (int) (Math.random() * config.numUsers());
                }

                /* Now we have a user that is online, lets put it offline */
                this.users[userIndex].logout(true);

                /* Sleep a little before putting the other user offline */
                try
                {
                    Thread.sleep(config.interActivityUserWait());
                }
                catch (InterruptedException ex)
                {
                }

                threadsWaiter.countDown();
            }

            /* Wait for all threads to finish */
            try
            {
                threadsWaiter.await();
                Thread.sleep(config.interOfflineSetWait()); // pause so that a KadRefresh can be ran
            }
            catch (InterruptedException ex)
            {
            }

            System.out.println("Finished running putting users offline; set " + x);
            System.out.println();
        }
    }

    /**
     * Do the real time simulation operations.
     *
     * This is an expensive operation for each user, so we pause a lot during these operations.
     */
    private void startSimulation()
    {
        final int numSets = config.numUsers() / config.numUsersPerActivitySet();

        for (int x = 0; x < numSets; x++)
        {
            final int startUser = x * config.numUsersPerActivitySet();  // Which user should we start at for this set of initialization
            threadsWaiter = new CountDownLatch(this.config.numUsersPerActivitySet());       // Setup the CountDownLatch for this set

            /* Lets work on this set of suers */
            for (int i = 0; i < config.numUsersPerActivitySet(); i++)
            {
                final int userIndex = startUser + i;    // The index of the user we should operate on

                if (this.users[userIndex].isOnline())
                {
                    /* The user is online, lets do some actions */
                    new Thread(new SimulatedUserActions(this.users[userIndex], config, threadsWaiter)).start();
                    try
                    {
                        Thread.sleep(config.interActivityUserWait());
                    }
                    catch (InterruptedException ex)
                    {
                    }
                }
                else
                {
                    /* User is offline, lets decrement the count down latch */
                    threadsWaiter.countDown();
                }
            }

            /* Wait for all threads to finish */
            try
            {
                threadsWaiter.await();
                Thread.sleep(config.interSetWait()); // pause so that a KadRefresh can be ran
            }
            catch (InterruptedException ex)
            {
            }

            System.out.println("Finished Running Simulation set " + x);
            System.out.println();
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

            if (simUser.isOnline())
            {
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
        }

        /* Print the Statistics */
        DecimalFormat df = new DecimalFormat("#.00");
        int numUsers = this.config.numUsers();
        String stats = "\nAverage Statistics for " + numUsers + " users; " + this.config.numOfflineUsers() + " offline (offline user stats not incl.); \n";
        stats += "Avg Data Sent: " + df.format(dataSent / numUsers) + " KBs; \n";
        stats += "Avg Data Received: " + df.format(dataReceived / numUsers) + " KBs; \n";
        stats += "Avg Bootstrap Time: " + df.format(bootstrapTime / numUsers) + " ms; \n";
        stats += "Avg # Content Lookups: " + (numContentLookups / (double) numUsers) + "; \n";
        stats += "Avg Content Lookup Time: " + df.format(avgContentLookupTime / numUsers) + " ms; \n";
        stats += "Avg Content Lookup Route Length: " + df.format(avgContentLookupRouteLth / numUsers) + "; \n";
        stats += "Avg Activity Stream load time: " + df.format(avgActivityStreamLoadTime / numUsers) + " ms; \n";
        stats += "\n";
        System.out.println(stats);
    }

    public void printUsers()
    {
        System.out.println("\n\n\nPrinting Simulated Users: \n");
        for (SimulatedUser u : this.users)
        {
            System.out.println(u);
        }

        System.out.println("\nPrinting users ended. \n\n\n");
    }

    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        sim.start();
    }
}
