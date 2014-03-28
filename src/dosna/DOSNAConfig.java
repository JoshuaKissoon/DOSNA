package dosna;

import java.net.InetAddress;
import java.net.UnknownHostException;
import kademlia.node.Node;
import kademlia.node.NodeId;

/**
 * Configuration for DOSNA
 *
 * @author Joshua Kissoon
 * @since 20140328
 */
public class DOSNAConfig
{

    /**
     * @return The Kademlia node that serves as the bootstrap node for the network
     *
     * @throws java.net.UnknownHostException
     */
    public Node getBootstrapNode() throws UnknownHostException
    {
        return new Node(new NodeId("BOOTSTRAPBOOTSTRAPBO"), InetAddress.getLocalHost(), 9999);
    }
}
