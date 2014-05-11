package dosna.notification;

import kademlia.node.KademliaId;

/**
 * A notification to be added to the notification box
 *
 * @author Joshua
 * @since
 */
public class Notification
{

    private final KademliaId key;
    private final String notification;

    /**
     * Create a new notification
     *
     * @param key          The key of the content this notification is for
     * @param notification The Notification itself.
     */
    public Notification(KademliaId key, String notification)
    {
        this.key = key;
        this.notification = notification;
    }

    public KademliaId getContentKey()
    {
        return this.key;
    }

    public String getNotification()
    {
        return this.notification;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Notification: ");

        sb.append("[Text: ");
        sb.append(notification);
        sb.append("]");

        return sb.toString();
    }
}
