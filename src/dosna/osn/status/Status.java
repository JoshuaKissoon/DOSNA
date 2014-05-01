package dosna.osn.status;

import dosna.content.DOSNAContent;
import dosna.osn.actor.Actor;
import dosna.util.HashCalculator;
import java.security.NoSuchAlgorithmException;
import kademlia.node.NodeId;

/**
 * A status update from an Actor.
 *
 * @author Joshua Kissoon
 * @since 20140405
 */
public class Status extends DOSNAContent
{

    public static final String TYPE = "Status";

    private String text;
    private String userId;
    private transient Actor actor;

    private NodeId key;

    /**
     * Blank public constructor mainly used by serializer.
     */
    public Status()
    {

    }

    /**
     * Private constructor used to create a new status
     *
     * @param actor The currently logged in actor
     * @param text  The text of the status
     */
    private Status(final Actor actor, final String text)
    {
        this.text = text;
        this.actor = actor;
        this.userId = actor.getId();
        this.generateKey();
    }

    /**
     * Generate a new key for this status given the userId.
     *
     * The key contains 10 characters of the userId first followed by the current timestamp (10 characters).
     */
    private void generateKey()
    {
        final long currentTs = System.currentTimeMillis() / 1000L;

        try
        {
            this.key = new NodeId(HashCalculator.sha1Hash(userId + currentTs));
        }
        catch (NoSuchAlgorithmException ex)
        {
            /* @todo Handle this error */
        }
    }

    /**
     * We wrap the Status constructor that creates a new Status object to make it explicit.
     *
     * @param actor The actor who owns object
     * @param text  The text of the status
     *
     * @return A new Status object
     */
    public static Status createNew(final Actor actor, final String text)
    {
        return new Status(actor, text);
    }

    /**
     * @return String The text of the status
     */
    public String getStatusText()
    {
        return this.text;
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

    @Override
    public String getOwnerId()
    {
        return this.userId;
    }

}
