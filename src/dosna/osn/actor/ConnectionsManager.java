package dosna.osn.actor;

import dosna.dhtAbstraction.DataManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import kademlia.dht.GetParameter;
import kademlia.dht.StorageEntry;

/**
 * Each actor will have many connections, here we keep track of these connections.
 *
 * @author Joshua Kissoon
 * @since 20140403
 *
 * @todo Add an isConnection method
 * @todo When a person is a connection, show that status in the addConnection frame
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
     * Set the Actor that owns this Manager.
     *
     * @param actor
     */
    public void setActor(final Actor actor)
    {
        this.actor = actor;
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
     * @return The set of all relationships that this actor has
     */
    public Collection<Relationship> getRelationships()
    {
        return this.connections;
    }

    /**
     * Here we load all Actors that are connected with this actor.
     *
     * @param dataManager DataManager to be used to load the connections
     *
     * @return The set of Actors connected to this actor
     */
    public Collection<Actor> loadConnections(final DataManager dataManager)
    {
        if (this.connections.isEmpty())
        {
            return new ArrayList<>();
        }

        final Collection<Actor> conns = new ArrayList<>();

        for (Relationship r : this.connections)
        {
            /* Lets load the actor in this relationship */
            try
            {
                Actor a = new Actor(r.getConnectionUid());
                GetParameter gp = new GetParameter(a.getKey(), a.getType(), a.getId());
                StorageEntry se = dataManager.get(gp);
                conns.add((Actor) new Actor().fromBytes(se.getContent().getBytes()));
            }
            catch (IOException | NoSuchElementException ex)
            {
                /**
                 * @think We didn't find this profile, do something
                 * I think we can ignore it since one of the assumptions we make is that all content is available all the time
                 */
                ex.printStackTrace();
            }
        }

        return conns;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("ConnectionManager: ");

        sb.append("[");
        sb.append("# Connections: ");
        sb.append(this.connections.size());
        sb.append("]");

        sb.append("[");
        sb.append("Connections: ");
        this.connections.stream().forEach((s) ->
        {
            sb.append(s);
        });
        sb.append("]");

        return sb.toString();
    }
}
