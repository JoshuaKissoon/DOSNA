package dosna.osn.status;

import dosna.content.DOSNAContent;
import dosna.osn.actor.Actor;
import dosna.util.HashCalculator;
import java.security.NoSuchAlgorithmException;
import kademlia.node.KademliaId;

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

    private KademliaId key;

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
        this.userId = actor.getId();
        this.generateKey();

        /* Add the owner as an actor related to this content */
        addActor(actor, "owner");
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
            this.key = new KademliaId(HashCalculator.sha1Hash(userId + currentTs));
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
    public KademliaId getKey()
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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(super.toString());

        sb.append("[");

        sb.append("owner: ");
        sb.append(this.userId);
        sb.append("; ");

        sb.append("text: ");
        sb.append(this.text);
        sb.append("; ");

        sb.append("]");

        return sb.toString();
    }
}
