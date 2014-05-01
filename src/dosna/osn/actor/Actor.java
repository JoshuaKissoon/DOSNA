package dosna.osn.actor;

import dosna.content.DOSNAContent;
import dosna.dhtAbstraction.DataManager;
import dosna.util.HashCalculator;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import kademlia.node.NodeId;

/**
 * Any type of user/group within the system of the system
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class Actor extends DOSNAContent
{

    public static final String TYPE = "Actor";

    /* Serialization keys */
    final static String SERIALK_CONTENT_MANAGER = "CManager";

    /* Manage the content posted by this actor */
    protected ContentManager contentManager;

    /* Manage relationships this actor have to other actors */
    protected ConnectionsManager connectionManager;

    /* Attributes */
    private String id;
    private String fullName;
    private NodeId key;
    private String hashedPassword;

    /* References to other objects */
    private NodeId notificationBoxNid;

    
    {
        this.contentManager = ContentManager.createNew(this);
        this.connectionManager = ConnectionsManager.createNew(this);
    }

    /**
     * Empty constructor mainly used by serializers
     */
    public Actor()
    {

    }

    /**
     * Create a new Actor object
     *
     * @param id
     */
    public Actor(final String id)
    {
        this.id = id.toLowerCase();
        this.generateKey();
    }

    /**
     * Generate a Node ID for this User object is the username/ownerId repeated until it have 20 characters
     *
     * @note The NodeId for this User object is the same as for the node this user uses,
     * A node will always store it's local user's uid
     */
    private void generateKey()
    {
        int numRepeats = ((NodeId.ID_LENGTH / 8) / this.id.length()) + 1;
        StringBuilder nodeId = new StringBuilder();
        for (int i = 0; i < numRepeats; i++)
        {
            nodeId.append(this.id);
        }
        this.key = new NodeId(nodeId.substring(0, 20));
    }

    /**
     * Method that initializes the actor object and it's components.
     *
     * @param mngr The DataManager to manage content on the DHT
     */
    public final void init(final DataManager mngr)
    {
        /* After the ContentManager have been loaded from file, we need to set a few things */
        this.contentManager.setDataManager(mngr);
        this.contentManager.setActor(this);

        /* After the ConnectionsManager have been loaded from file, we need to set a few things */
        this.connectionManager.setActor(this);
    }

    public String getId()
    {
        return this.id;
    }

    public void setName(final String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * @return String This user's full name
     */
    public String getName()
    {
        return this.fullName;
    }

    /**
     * Sets a new password for this user
     *
     * @param password
     */
    public void setPassword(final String password)
    {
        if (password.trim().equals(""))
        {
            throw new NullPointerException();
        }

        this.hashedPassword = this.hashPassword(password);
    }

    public boolean isPassword(final String password)
    {
        return this.hashedPassword.equals(this.hashPassword(password));
    }

    /**
     * Hashes the password
     *
     * @param password The password to be hashed
     *
     * @return String The hashed password
     */
    private String hashPassword(final String password)
    {
        final String salt = "iu4rkjd&^876%ewfuhi4Y&*&*^*03487658347*&^&^";

        try
        {
            /* Return a string version of the hash */
            return new BigInteger(1, HashCalculator.md5Hash(password, salt)).toString(16);
        }
        catch (NoSuchAlgorithmException e)
        {
            return new BigInteger(1, password.getBytes()).multiply(new BigInteger(1, salt.getBytes())).toString(16);
        }
    }

    @Override
    public String getOwnerId()
    {
        return this.id;
    }

    @Override
    public NodeId getKey()
    {
        return this.key;
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    /**
     * @return The ContentManager that manages this actors contents
     */
    public ContentManager getContentManager()
    {
        return this.contentManager;
    }

    /**
     * Set a new ContentManager for this Actor
     *
     * @param cm The new content manager
     */
    public void setContentManager(final ContentManager cm)
    {
        this.contentManager = cm;
    }

    /**
     * @return The ConnectionManager for this actor
     */
    public ConnectionsManager getConnectionManager()
    {
        return this.connectionManager;
    }

    /**
     * Sets the reference to the Notification Box Node ID on the network for this actor.
     *
     * @param notificationBoxNid
     */
    public void setNotificationBoxNid(NodeId notificationBoxNid)
    {
        this.notificationBoxNid = notificationBoxNid;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("User: ");

        sb.append("[username: ");
        sb.append(this.getOwnerId());

        sb.append("] ");
        sb.append("[name: ");
        sb.append(this.fullName);
        sb.append("] ");

        sb.append("[ContentManager: ");
        sb.append(this.getContentManager());
        sb.append("] ");

        sb.append("[ConnectionsManager: ");
        sb.append(this.getConnectionManager());
        sb.append("] ");

        return sb.toString();
    }
}
