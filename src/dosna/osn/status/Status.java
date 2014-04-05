package dosna.osn.status;

import dosna.dhtAbstraction.DOSNAContent;
import dosna.osn.actor.Actor;
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
     * Blank public constructor mainly used by serializer
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
        this.userId = actor.getUserId();
    }

    /**
     * Generate a new key for this status given the userId.
     *
     * The key contains 10 characters of the userId first followed by the current timestamp (10 characters).
     */
    private void generateKey()
    {
        final int numRepeats = (((NodeId.ID_LENGTH / 8) / 2) / this.userId.length()) + 1;
        final StringBuilder repeatedUserId = new StringBuilder();
        for (int i = 0; i < numRepeats; i++)
        {
            repeatedUserId.append(this.userId);
        }

        final String uidPart = repeatedUserId.substring(0, 10);
        final long currentTs = System.currentTimeMillis() / 1000L;

        this.key = new NodeId(uidPart + "" + currentTs);
    }

    /**
     * We wrap the Status constructor that creates a new Status object to make it explicit.
     *
     * @param actor The actor who owns object
     * @param text  The text of the status
     *
     * @return A new Status object
     */
    public Status createNew(final Actor actor, final String text)
    {
        return new Status(actor, text);
    }

    @Override
    public NodeId getKey()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
