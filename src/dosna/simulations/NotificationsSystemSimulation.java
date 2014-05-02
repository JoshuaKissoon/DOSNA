package dosna.simulations;

import dosna.content.ContentManager;
import dosna.core.ContentMetadata;
import dosna.osn.actor.Actor;
import dosna.osn.status.Status;
import java.io.IOException;
import kademlia.exceptions.ContentNotFoundException;
import kademlia.node.NodeId;

/**
 * Simulating basic notification system changes to test it.
 *
 * @author Joshua Kissoon
 * @since 20140502
 */
public class NotificationsSystemSimulation
{

    public NotificationsSystemSimulation()
    {
        /* Initialize DOSNA instances for each actor */
        SimulatedUser user1 = new SimulatedUser("JoshuaK1", "pass", "Joshua Kissoon 1");
        SimulatedUser user2 = new SimulatedUser("JoshuaK2", "pass", "Joshua Kissoon 2");

        user1.initializeDOSNA();
        user2.initializeDOSNA();

        /* Lets create 2 users and log them in */
        user1.signup();
        user2.signup();

        user1.login();
        user2.login();

        /* Lets create content and put it on the DHT */
        try
        {
            user1.setNewStatus("User 1 Status");
            user2.setNewStatus("User 2 Status");
        }
        catch (IOException ex)
        {

        }

        /* Let user 1 retrieve user 2 status and update it */
        try
        {
            Actor user2Actor = user1.loadActor(user2.getActor().getId());
            System.out.println(user2Actor);

            /* Retrieve u2's first status and update it */
            for (ContentMetadata cmd : user2Actor.getContentManager().getAllContent(Status.TYPE))
            {
                NodeId key = cmd.getKey();

                /* Lets retrieve the status */
                Status a = user1.loadStatus(key);
                
                /* Let user 1 update the content */
                user1.updateContent(a);

                System.out.println(a);
                break;
            }
        }
        catch (IOException | ContentNotFoundException ex)
        {

        }
    }

    public static void main(String[] args)
    {
        NotificationsSystemSimulation simulation = new NotificationsSystemSimulation();
    }
}
