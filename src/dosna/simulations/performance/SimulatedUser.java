package dosna.simulations.performance;

import dosna.DOSNA;
import dosna.content.ContentManager;
import dosna.content.DOSNAContent;
import dosna.core.ContentMetadata;
import dosna.core.DOSNAStatistician;
import dosna.osn.activitystream.ActivityStreamManager;
import dosna.osn.actor.Actor;
import dosna.osn.actor.ActorManager;
import dosna.osn.status.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kademlia.KademliaNode;
import kademlia.dht.StorageEntry;
import kademlia.exceptions.ContentNotFoundException;
import kademlia.node.NodeId;

/**
 * A user used in simulation; this user performs the actions of a user of the system.
 *
 * @author Joshua Kissoon
 * @since 20140502
 */
public class SimulatedUser
{

    private Actor actor;
    private final DOSNA dosna;

    private String actorId;
    private String name;
    private String password;

    public int userNumber;

    /* Content Manager to manage the content on the network */
    private ContentManager contentManager;

    /* DOSNA Statistician */
    private final DOSNAStatistician statistician;

    /* Set of users in the simulation */
    private final SimulatedUser[] users;

    
    {
        this.dosna = new DOSNA();
        statistician = new DOSNAStatistician();
    }

    /**
     * Setup the simulated user
     *
     * @param actor      The DOSN actor object for this user
     * @param userNumber The user number in the simulation
     * @param users      Set of users in the simulation
     */
    public SimulatedUser(final Actor actor, final int userNumber, final SimulatedUser[] users)
    {
        this.actor = actor;
        this.userNumber = userNumber;
        this.users = users;
    }

    /**
     * Setup the simulated user
     *
     * @param actorId    The DOSN actor's id for this user
     * @param name
     * @param password
     * @param userNumber The user number in the simulation
     * @param users      The set of users in the simulation
     */
    public SimulatedUser(final String actorId, final String password, final String name, final int userNumber, final SimulatedUser[] users)
    {
        this.actor = new Actor(actorId);
        this.actor.setName(name);
        this.actor.setPassword(password);

        this.actorId = actorId;
        this.name = name;
        this.password = password;

        this.userNumber = userNumber;

        this.users = users;
    }

    public Actor getActor()
    {
        return this.actor;
    }

    public boolean signup()
    {
        DOSNA.SignupResult res = this.dosna.signupUser(this.actorId, this.password, this.name);

        if (res.isSignupSuccessful)
        {
            this.actor = res.actor;
        }

        return res.isSignupSuccessful;
    }

    public boolean login()
    {
        DOSNA.LoginResult res = this.dosna.loginUser(this.actorId, this.password);

        this.contentManager = new ContentManager(this.dosna.getDataManager());

        if (res.isLoginSuccessful)
        {
            this.actor = res.loggedInActor;
            this.actor.init(this.dosna.getDataManager());
            this.dosna.launchNotificationChecker(this.actor);
        }

        return res.isLoginSuccessful;
    }

    /**
     * Log the user out and shutdown the system
     *
     * @return Whether logout and shutdown was successful or not
     */
    public boolean logout()
    {
        try
        {
            this.dosna.getDataManager().shutdown(true);
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    /**
     * Creates a new status for this user and places it on the DHT.
     *
     * @param statusText The status to put
     *
     * @throws java.io.IOException
     */
    public synchronized void setNewStatus(String statusText) throws IOException
    {
        Status status = Status.createNew(this.getActor(), statusText);
        this.getActor().getContentManager().store(status);
    }

    public Actor loadActor(String uid) throws IOException, ContentNotFoundException
    {
        return new ActorManager(this.dosna.getDataManager()).loadActor(uid);
    }

    public Status loadStatus(NodeId statusId) throws IOException, ContentNotFoundException
    {
        StorageEntry e = this.dosna.getDataManager().get(statusId, Status.TYPE);
        return (Status) new Status().fromBytes(e.getContent().getBytes());
    }

    public void updateContent(DOSNAContent content)
    {
        content.addActor(this.getActor().getId(), "editor");
        content.setUpdated();

        this.contentManager.updateContent(content);
    }

    /**
     * Randomly Select a connection,
     * Randomly select a content from this connection
     * Load the content
     * Update it and put it on the DHT.
     */
    public void updateRandomContent()
    {
        /* Load all connections and select one randomly */
        List<Actor> connections = new ArrayList<>(this.actor.getConnectionManager().loadConnections(this.dosna.getDataManager()));

        if (connections.isEmpty())
        {
            return;
        }
        int randActor = (int) (Math.random() * connections.size());
        Actor selected = connections.get(randActor);

        /* Randomly select a content from this actor */
        List<ContentMetadata> content = new ArrayList<>(selected.getContentManager().getAllContent());

        if (content.isEmpty())
        {
            return;
        }

        int randContent = (int) (Math.random() * content.size());
        ContentMetadata selectedContent = content.get(randContent);

        /* Load this content, and update it */
        try
        {
            StorageEntry e = this.dosna.getDataManager().get(selectedContent.getKey(), selectedContent.getType(), selectedContent.getOwnerId());
            Status cc = (Status) new Status().fromBytes(e.getContent().getBytes());
            this.updateContent(cc);
        }
        catch (IOException | ContentNotFoundException ex)
        {
            System.err.println(this.actor.getName() + " SimulatedUser.updateRandomContent() exception whiles updating content. Msg: " + ex.getMessage());
        }

    }

    /**
     * Creates a new connection with the given actor
     *
     * @param connActor The actor to create the connection with
     */
    public void createConnection(final Actor connActor)
    {
        this.actor.addConnection(connActor.getId());

        /* Update the actor object online */
        this.contentManager.updateContent(this.actor);
    }

    /**
     * Create a connection with a random actor
     */
    public void createRandomConnection()
    {
        int randActor = (int) (Math.random() * users.length);
        Actor toConnect = this.users[randActor].getActor();

        while (this.actor.hasConnection(toConnect))
        {
            randActor = (int) (Math.random() * users.length);
            toConnect = this.users[randActor].getActor();
        }

        /* Have a new actor to connect to */
        this.createConnection(toConnect);
    }

    /**
     * Refreshes the activity stream of the user.
     *
     * Here we only go as far as loading the content since we're not displaying it.
     *
     * @return Number of items on activity streams
     */
    public int refreshActivityStream()
    {
        ActivityStreamManager acm = new ActivityStreamManager(actor, this.dosna.getDataManager(), this.statistician);
        Collection<DOSNAContent> cont = acm.getHomeStreamContent();
        return cont.size();
    }

    public void stopKadRefreshOperation()
    {
        this.dosna.getDataManager().getKademliaNode().stopRefreshOperation();
    }

    public KademliaNode getKademliaNode()
    {
        return this.dosna.getDataManager().getKademliaNode();
    }

    /**
     * We used this statistician throughout the system to check statistics.
     *
     * @return The statistician and his statistics.
     */
    public DOSNAStatistician getStatistician()
    {
        return this.statistician;
    }

}
