package dosna.dhtAbstraction;

import dosna.content.DOSNAContent;
import dosna.DOSNAConfig;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import kademlia.dht.GetParameter;
import kademlia.KademliaNode;
import kademlia.dht.StorageEntry;
import kademlia.exceptions.ContentNotFoundException;
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
    private final KademliaNode kad;

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
        kad = new KademliaNode(ownerId, nodeId, port);
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
    public synchronized int put(final DOSNAContent content) throws IOException
    {
        return kad.put(content);
    }

    /**
     * Stores data locally
     */
    @Override
    public synchronized void putLocally(final DOSNAContent content) throws IOException
    {
        kad.putLocally(content);
    }

    /**
     * Put the content on the local node and on the network.
     *
     * @param content The content to put
     *
     * @return Integer The number of nodes this content was put on, excluding the local node.
     *
     * @throws java.io.IOException
     */
    @Override
    public synchronized int putLocallyAndUniversally(final DOSNAContent content) throws IOException
    {
        final int num = this.put(content);

        /* Only if the content was saved online do we save it locally, otherwise there is no use. */
        if (num > 0)
        {
            this.putLocally(content);
        }

        return num;
    }

    @Override
    public StorageEntry get(final GetParameter gp) throws IOException, ContentNotFoundException
    {
        return kad.get(gp);
    }

    @Override
    public StorageEntry get(NodeId key, String type) throws IOException, NoSuchElementException, ContentNotFoundException
    {
        return this.get(new GetParameter(key, type));
    }

    @Override
    public StorageEntry get(NodeId key, String type, String ownerId) throws IOException, NoSuchElementException, ContentNotFoundException
    {
        return this.get(new GetParameter(key, type, ownerId));
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

    @Override
    public KademliaNode getKademliaNode()
    {
        return this.kad;
    }
}
