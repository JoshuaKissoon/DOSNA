package dosna.content;

import dosna.dhtAbstraction.DataManager;
import dosna.notification.Notification;
import dosna.notification.NotificationBox;
import java.io.IOException;
import java.util.List;
import kademlia.dht.StorageEntry;
import kademlia.exceptions.ContentNotFoundException;

/**
 * A class that does various content management operations.
 *
 * @author Joshua Kissoon
 * @since 20140501
 */
public class ContentManager
{

    private final DataManager dataManager;

    /**
     * Construct a new content manager
     *
     * @param dataManager
     */
    public ContentManager(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    /**
     * Places a content object update on the DHT and handles all of the extra operations.
     *
     * When a content is to be updated:
     * - The updated content is placed on the DHT.
     * - A notification is added to the Notification box of all of it's associated actors
     * -- All of these notification boxes are updated onto the DHT.
     *
     * @param content The updated content to be placed.
     */
    public void updateContent(DOSNAContent content)
    {
        try
        {
            /* Firstly, lets put the updated content onto the network */
            this.dataManager.put(content);

            /* Now lets notify associated users */
            List<String> associatedUsers = content.getRelatedActors();
            for (String actorId : associatedUsers)
            {
                /* Lets construct a temporary Notification Box for this actor since the key will be generated */
                NotificationBox temp = new NotificationBox(actorId);

                try
                {
                    /* Retrieve this users notification box from the network */
                    StorageEntry e = this.dataManager.get(temp.getKey(), temp.getType());
                    NotificationBox original = (NotificationBox) new NotificationBox().fromBytes(e.getContent().getBytes());

                    /* Add the updated notification box */
                    original.addNotification(new Notification(content.getKey(), "Content has been modified"));

                    /* Push the notification box back onto the network */
                    dataManager.put(original);
                }
                catch (IOException | ContentNotFoundException ex)
                {

                }
            }
        }
        catch (IOException ex)
        {

        }

    }
}
