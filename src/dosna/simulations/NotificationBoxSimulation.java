package dosna.simulations;

import dosna.notification.Notification;
import dosna.notification.NotificationBox;
import java.io.IOException;
import kademlia.KademliaNode;
import kademlia.dht.GetParameter;
import kademlia.dht.StorageEntry;
import kademlia.exceptions.ContentNotFoundException;
import kademlia.node.KademliaId;

/**
 * Just testing notification box handling.
 *
 * @author Joshua Kissoon
 * @since 20140504
 */
public class NotificationBoxSimulation
{

    public NotificationBoxSimulation()
    {
        try
        {
            KademliaNode kad = new KademliaNode("Joshua", new KademliaId("JOSHUAJOSHUAJOSHUAJO"), 3445);
            KademliaNode kad2 = new KademliaNode("Joshua2", new KademliaId("JOSHUAJOSHUAJOSHUAJK"), 3446);

            kad.bootstrap(kad2.getNode());

            NotificationBox nb = new NotificationBox("Joshua");
            nb.addNotification(new Notification(new KademliaId(), "Some Notification 1"));

            /* Put the notification box */
            kad.put(nb);

            /* Re-retrieve the notification box */
            StorageEntry se = kad.get(new GetParameter(nb));
            NotificationBox nb2 = (NotificationBox) new NotificationBox().fromBytes(se.getContent());
            System.out.println(nb2);

            Thread.sleep(1000);

            System.out.println("\n\n\n\n************* Adding new Notification ********************* ");

            /* Add a notification and put the box back on the DHT */
            nb2.addNotification(new Notification(new KademliaId(), "Some Notification 2"));
            kad.put(nb2);
            System.out.println(nb2);

            Thread.sleep(1000);
            System.out.println("\n\n\n\n************* Retrieving again ********************* ");

            /* Retrieve it again */
            StorageEntry se2 = kad.get(new GetParameter(nb));
            NotificationBox nb3 = (NotificationBox) new NotificationBox().fromBytes(se2.getContent());
            System.out.println(nb3);
        }
        catch (IOException | ContentNotFoundException | InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new NotificationBoxSimulation();
    }
}
