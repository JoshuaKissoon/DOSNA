package dosna.dhtAbstraction;

import dosna.DOSNAConfig;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.NoSuchElementException;
import kademlia.core.GetParameter;
import kademlia.Kademlia;
import kademlia.dht.KadContent;
import kademlia.dht.StorageEntry;
import kademlia.node.NodeId;

/**
 * An implementation of the DataManager for this Application.
 * We use the Kademlia routing protocol here.
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public final class DosnaDataManager implements DataManager
{

    /**
     * The Kademlia instance to be used.
     * We use composition rather than inheritance.
     */
    private final Kademlia kad;

    /**
     * Initialize Kademlia
     *
     * @param ownerId
     * @param nodeId  The NodeId to be used for this Kademlia instance
     * @param port
     *
     * @throws java.io.IOException
     * @throws java.net.UnknownHostException
     * @todo Try loading data from file, if no file exist, then create a new Kademlia instance
     */
    public DosnaDataManager(final String ownerId, final NodeId nodeId, final int port) throws IOException, UnknownHostException
    {
        kad = new Kademlia(ownerId, nodeId, port);
        kad.bootstrap(new DOSNAConfig().getBootstrapNode());
    }

    /**
     * Initialize Kademlia with a random port number
     *
     * @param ownerId
     * @param nodeId
     *
     * @throws java.io.IOException
     */
    public DosnaDataManager(final String ownerId, final NodeId nodeId) throws IOException
    {
        this(ownerId, nodeId, (int) ((Math.random() * 10000) + 5000));
    }

    /**
     * Put data onto the network.
     * This will put the data onto the network based on the routing protocol.
     * It may or may not store data locally.
     */
    @Override
    public void put(KadContent content) throws IOException
    {
        kad.put(content);
    }

    /**
     * Stores data locally
     */
    @Override
    public void putLocally(KadContent content) throws IOException
    {
        kad.putLocally(content);
    }

    @Override
    public List<StorageEntry> get(GetParameter gp, int numReaultsReq) throws IOException
    {
        return kad.get(gp, numReaultsReq);
    }

    @Override
    public StorageEntry get(GetParameter gp) throws IOException
    {
        List<StorageEntry> results = kad.get(gp, 1);

        if (!results.isEmpty())
        {
            return results.get(0);
        }
        else
        {
            throw new NoSuchElementException("No result exists for the given parameters.");
        }
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
