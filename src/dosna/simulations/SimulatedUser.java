package dosna.simulations;

import dosna.DOSNA;
import dosna.content.ContentManager;
import dosna.content.DOSNAContent;
import dosna.osn.actor.Actor;
import dosna.osn.actor.ActorManager;
import dosna.osn.status.Status;
import java.io.IOException;
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
    private DOSNA dosna;

    private String actorId;
    private String name;
    private String password;

    /**
     * Setup the simulated user
     *
     * @param actor The DOSN actor object for this user
     */
    public SimulatedUser(final Actor actor)
    {
        this.actor = actor;
    }

    /**
     * Setup the simulated user
     *
     * @param actorId  The DOSN actor's id for this user
     * @param name
     * @param password
     */
    public SimulatedUser(final String actorId, final String password, final String name)
    {
        this.actor = new Actor(actorId);
        this.actor.setName(name);
        this.actor.setPassword(password);

        this.actorId = actorId;
        this.name = name;
        this.password = password;
    }

    public Actor getActor()
    {
        return this.actor;
    }

    public void initializeDOSNA()
    {
        this.dosna = new DOSNA();
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

        if (res.isLoginSuccessful)
        {
            this.actor = res.loggedInActor;
            this.actor.init(this.dosna.getDataManager());
            this.dosna.launchNotificationChecker(this.actor);
        }

        return res.isLoginSuccessful;
    }

    /**
     * Creates a new status for this user and places it on the DHT.
     *
     * @param statusText The status to put
     *
     * @throws java.io.IOException
     */
    public void setNewStatus(String statusText) throws IOException
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
        ContentManager cm = new ContentManager(this.dosna.getDataManager());
        content.addActor(this.getActor().getId(), "editor");
        content.setUpdated();
        
        cm.updateContent(content);
    }

}
