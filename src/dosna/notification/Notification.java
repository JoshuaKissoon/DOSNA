package dosna.notification;

import kademlia.node.NodeId;

/**
 * A notification to be added to the notification box
 *
 * @author Joshua
 * @since
 */
public class Notification
{
    
    private final NodeId key;
    private final String notification;

    /**
     * Create a new notification
     *
     * @param key          The key of the content this notification is for
     * @param notification The Notification itself.
     */
    public Notification(NodeId key, String notification)
    {
        this.key = key;
        this.notification = notification;
    }
    
    public NodeId getContentKey()
    {
        return this.key;
    }
    
    public String getNotification()
    {
        return this.notification;
    }
}
