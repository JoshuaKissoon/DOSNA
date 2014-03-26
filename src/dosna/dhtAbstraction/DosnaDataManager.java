package dosna.dhtAbstraction;

import java.io.IOException;
import kademlia.core.Kademlia;
import kademlia.node.NodeId;

/**
 * An implementation of the DataManager for this Application.
 * We use the Kademlia routing protocol here.
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class DosnaDataManager implements DataManager
{

    private final Kademlia kad;
    private static int randomPort = 4858;

    /**
     * Initialize Kademlia
     *
     * @param ownerId
     * @param port
     *
     * @throws java.io.IOException
     */
    public DosnaDataManager(final String ownerId, final int port) throws IOException
    {
        /* Node ID for an owner is the ownerId repeated until it have 20 characters */
        int numRepeats = ownerId.length() / (NodeId.ID_LENGTH / 8) + 1;

        StringBuilder nodeId = new StringBuilder();

        for (int i = 0; i < numRepeats; i++)
        {
            nodeId.append(ownerId);
        }

        kad = new Kademlia(ownerId, new NodeId(), port);
    }

    /**
     * Initialize Kademlia
     *
     * @param ownerId
     *
     * @throws java.io.IOException
     */
    public DosnaDataManager(final String ownerId) throws IOException
    {
        this(ownerId, randomPort);
    }

    /**
     * Put data onto the network.
     * This will put the data onto the network based on the routing protocol.
     * It may or may not store data locally.
     */
    @Override
    public void put()
    {

    }

    /**
     * Stores data locally
     */
    @Override
    public void putLocally()
    {

    }

    /**
     * Get data from the network.
     * Will get data either from local or remote network nodes.
     */
    @Override
    public void get()
    {

    }

    /**
     * Run an update call to update the data stored locally on this computer.
     * This may involve deleting some data and adding some other data.
     */
    @Override
    public void updateStorage()
    {

    }
    
    @Override
    public void shutdown(final boolean saveState) throws IOException
    {
        this.kad.shutdown(saveState);
    }
}
