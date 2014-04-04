package dosna.osn.actor;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Each actor will have many connections, here we keep track of these connections.
 *
 * @author Joshua Kissoon
 * @since 20140403
 */
public class ConnectionsManager
{

    /**
     * A set of relationships of this actor;
     * we keep this set of relationships in sorted order.
     */
    private TreeSet<Relationship> connections;
    private transient Actor actor;

    /**
     * Blank constructor to be used by Serializer
     */
    public ConnectionsManager()
    {

    }

    /**
     * This constructor is called when creating a new ConnectionManager object
     */
    private ConnectionsManager(final Actor actor)
    {
        this.actor = actor;
        this.connections = new TreeSet<>();
    }

    /**
     * We wrap the ConnectionManager constructor that creates a new ConnectionManager object to make it explicit.
     *
     * @param actor The actor who owns this ConnectionManager object
     *
     * @return A new ConnectionManager object
     */
    public static ConnectionsManager createNew(final Actor actor)
    {
        return new ConnectionsManager(actor);
    }

    /**
     * Adds a new relationship
     *
     * @param rel The relationship to add
     *
     * @return Whether the new relationship was added successfully or not
     */
    public boolean addConnection(final Relationship rel)
    {
        if (this.connections.contains(rel))
        {
            return false;
        }
        else
        {
            return this.connections.add(rel);
        }
    }

    /**
     * @return The set of all relationships that this actor manages
     */
    public Collection<Relationship> getConnection()
    {
        return this.connections;
    }
}
