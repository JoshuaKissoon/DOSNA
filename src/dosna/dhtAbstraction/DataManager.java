package dosna.dhtAbstraction;

import java.io.IOException;

/**
 * An abstraction that handles routing data on the network and storing data.
 * This class is an abstraction of the DHT and routing protocol for our system.
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public interface DataManager
{

    /**
     * Put data onto the network.
     * This will put the data onto the network based on the routing protocol.
     * It may or may not store data locally.
     */
    public void put();

    /**
     * Stores data locally
     */
    public void putLocally();

    /**
     * Get data from the network.
     * Will get data either from local or remote network nodes.
     */
    public void get();

    /**
     * Run an update call to update the data stored locally on this computer.
     * This may involve deleting some data and adding some other data.
     */
    public void updateStorage();

    /**
     * Save the state of the DataManager and shutdown
     *
     * @param saveState Whether to save the state of the application or not
     *
     * @throws java.io.IOException
     */
    public void shutdown(boolean saveState) throws IOException;
}
