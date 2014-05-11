package dosna.notification;

import dosna.content.DOSNAContent;
import dosna.osn.actor.Actor;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import kademlia.node.KademliaId;
import kademlia.util.HashCalculator;

/**
 * A box containing all notifications meant for a user
 *
 * @author Joshua Kissoon
 * @since 20140501
 */
public class NotificationBox extends DOSNAContent
{

    public final static String TYPE = "NotificationBox";

    private List<Notification> notifications;
    private String ownerId;

    private KademliaId key;

    
    {
        notifications = new ArrayList<>();
    }

    public NotificationBox()
    {

    }

    /**
     * Setup the Notification Box
     *
     * @param owner The owner of this notification box
     */
    public NotificationBox(final Actor owner)
    {
        this(owner.getId());
    }

    /**
     * Setup the Notification Box
     *
     * @param ownerId The ID of the owner of this notification box
     */
    public NotificationBox(final String ownerId)
    {
        this.ownerId = ownerId;
        this.generateNodeId();
    }

    private void generateNodeId()
    {
        byte[] keyData = null;
        try
        {
            keyData = HashCalculator.sha1Hash(this.ownerId + "NotificationBox");
        }
        catch (NoSuchAlgorithmException ex)
        {
            /*@todo try some other hash here */
            System.err.println("SHA-1 Hash algorithm isn't existent.");
        }
        this.key = new KademliaId(keyData);
    }

    /**
     * Adds a new notification to the notification box
     *
     * @param n the notification to add
     */
    public void addNotification(Notification n)
    {
        this.notifications.add(n);
        this.setUpdated();
    }

    /**
     * @return All notifications in this notifications box
     */
    public List<Notification> getNotifications()
    {
        return this.notifications;
    }

    /**
     * Deletes all notifications from the notification box.
     */
    public void emptyBox()
    {
        this.notifications = new ArrayList<>();
        this.setUpdated();
    }

    /**
     * Check whether this box has any notifications.
     *
     * @return
     */
    public boolean hasNotifications()
    {
        return !this.notifications.isEmpty();
    }

    @Override
    public KademliaId getKey()
    {
        return this.key;
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    @Override
    public String getOwnerId()
    {
        return this.ownerId;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("NotificationBox: ");

        sb.append("[Owner: ");
        sb.append(ownerId);
        sb.append("");

        for (Notification n : this.notifications)
        {
            sb.append(n);
            sb.append(" ; ");
        }

        sb.append("]");

        return sb.toString();
    }

}
