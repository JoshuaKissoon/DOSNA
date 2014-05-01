package dosna.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * A box containing all notifications meant for a user
 *
 * @author Joshua Kissoon
 * @since 20140501
 */
public class NotificationBox
{

    private List<Notification> notifications;

    
    {
        notifications = new ArrayList<>();
    }

    /**
     * Adds a new notification to the notification box
     *
     * @param n the notification to add
     */
    public void addNotification(Notification n)
    {
        this.notifications.add(n);
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
    }

}
