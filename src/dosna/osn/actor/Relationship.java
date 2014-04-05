package dosna.osn.actor;

/**
 * An object that is used to represent relationships between users.
 *
 * @author Joshua Kissoon
 * @since 20140403
 */
public class Relationship implements Comparable<Relationship>
{

    /**
     * The owner is the person who creates the relationship with the other user.
     */
    private transient Actor owner;
    private String ownerUid;

    /* Connection is the other user */
    private String connectionUid;

    private long createTs;

    public Relationship()
    {

    }

    public Relationship(final Actor owner, final String connectionUid)
    {
        this.owner = owner;
        this.ownerUid = owner.getUserId();
        this.connectionUid = connectionUid;
        this.createTs = System.currentTimeMillis() / 1000L;
    }

    public Relationship(final Actor owner, final Actor connection)
    {
        this(owner, connection.getUserId());
    }

    public String getOwnerUid()
    {
        return this.ownerUid;
    }

    public String getConnectionUid()
    {
        return this.connectionUid;
    }

    public long getCreatedTimestamp()
    {
        return this.createTs;
    }

    /**
     * Compare this relationship to another relationship by timestamp
     */
    @Override
    public int compareTo(Relationship o)
    {
        /* Check if they are the same object */
        if (this == o)
        {
            return 0;
        }
        if (this.getConnectionUid().equals(o.getConnectionUid()) && this.getOwnerUid().equals(o.getOwnerUid()))
        {
            return 0;
        }

        /* Compare based on timestamp now */
        return (this.getCreatedTimestamp() > o.getCreatedTimestamp()) ? 1 : -1;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Relationship: ");
        
        sb.append("[");
        sb.append("Owner: ");
        sb.append(this.getOwnerUid());
        sb.append("]");
        
        sb.append("[");
        sb.append("Connection: ");
        sb.append(this.getConnectionUid());
        sb.append("]");
        
        return sb.toString();
    }
}
