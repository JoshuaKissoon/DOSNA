package dosna.dhtAbstraction;

import kademlia.dht.KadContent;

/**
 * An abstract class that represents any content within the system that needs to be stored on the distributed storage.
 *
 * It implements the KadContent interface from the Kademlia implementation we're using
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public abstract class DOSNAContent implements KadContent
{

    public final long createTs, updateTs;

    
    {
        this.createTs = this.updateTs = System.currentTimeMillis() / 1000L;
    }

    @Override
    public long getCreatedTimestamp()
    {
        return this.createTs;
    }

    @Override
    public long getLastUpdatedTimestamp()
    {
        return this.updateTs;
    }
}
