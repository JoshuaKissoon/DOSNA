package dosna.dhtAbstraction;

import dosna.DOSNAConfig;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import kademlia.core.GetParameter;
import kademlia.Kademlia;
import kademlia.dht.KadContent;
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

    private final Kademlia kad;
    private static int randomPort = 4858;

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
     * Initialize Kademlia
     *
     * @param ownerId
     * @param nodeId
     *
     * @throws java.io.IOException
     */
    public DosnaDataManager(final String ownerId, final NodeId nodeId) throws IOException
    {
        this(ownerId, nodeId, randomPort);
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

    /**
     * Get data from the network.
     * Will get data either from local or remote network nodes.
     *
     * @param gp
     * @param numReaultsReq
     *
     * @return
     */
    @Override
    public List<KadContent> get(GetParameter gp, int numReaultsReq) throws IOException
    {
        return kad.get(gp, numReaultsReq);
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
