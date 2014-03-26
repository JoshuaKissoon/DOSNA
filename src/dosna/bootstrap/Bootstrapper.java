package dosna.bootstrap;

import java.io.IOException;
import kademlia.core.Kademlia;
import kademlia.node.NodeId;

/**
 * Here we load the bootstrap node and keep it running
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class Bootstrapper
{
    
    public Bootstrapper()
    {
        
    }

    /**
     * Initialize the bootstrap node to be running so that every other node can connect through this node
     */
    public void launchBootstrapNode()
    {
        try
        {
            Kademlia bootstrapInstance = new Kademlia("DOSNA", new NodeId("BOOTSTRAPBOOTSTRAPBO"), 9999);
        }
        catch (IOException e)
        {
            /**
             * @todo Handle this exception
             */
        }
    }

    public static void main(String[] args)
    {
        Bootstrapper bs = new Bootstrapper();
        bs.launchBootstrapNode();
    }
}
