package dosna.content;

import com.google.gson.Gson;
import dosna.osn.actor.Actor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kademlia.dht.KadContent;

/**
 * An abstract class that represents any content within the system that needs to be stored on the distributed storage.
 *
 * It implements the KadContent interface from the Kademlia implementation we're using
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public abstract class DOSNAContent implements KadContent, ActorRelatedContent, Serializable
{

    public static final String TYPE = "DOSNAContent";

    public final long createTs;
    public long updateTs;

    /* Set of actors related to this content */
    Map<String, String> relatedActors;

    
    {
        this.createTs = this.updateTs = System.currentTimeMillis() / 1000L;
        relatedActors = new HashMap<>();
    }

    public DOSNAContent()
    {

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

    @Override
    public byte[] toBytes()
    {
        Gson gson = new Gson();
//        try (
//                ByteArrayOutputStream bout = new ByteArrayOutputStream();
//                ObjectOutputStream out = new ObjectOutputStream(bout))
//        {
//            out.writeObject(this);
//            out.close();
//            return bout.toByteArray();
//        }
//        catch (IOException ex)
//        {
//            ex.printStackTrace();
//        }

        return gson.toJson(this).getBytes();
    }

    @Override
    public DOSNAContent fromBytes(byte[] data)
    {
//        try (ByteArrayInputStream bin = new ByteArrayInputStream(data);
//                ObjectInputStream oin = new ObjectInputStream(bin))
//        {
//            DOSNAContent val = (DOSNAContent) oin.readObject();
//            oin.close();
//            System.out.println("Deserialized: " + val);
//            return val;
//        }
//        catch (IOException | ClassNotFoundException ex)
//        {
//            ex.printStackTrace();
//        }
        Gson gson = new Gson();
        DOSNAContent val = gson.fromJson(new String(data), this.getClass());
        return val;
    }

    /**
     * When some updates have been made to a content,
     * this method can be called to update the lastUpdated timestamp.
     */
    public void setUpdated()
    {
        this.updateTs = System.currentTimeMillis() / 1000L;
    }

    /**
     * When adding an actor we store the actor's ID and the actor's relationship to this content
     */
    @Override
    public void addActor(String userId, String relationship)
    {
        this.relatedActors.put(userId, relationship);
    }

    @Override
    public void addActor(Actor a, String relationship)
    {
        addActor(a.getId(), relationship);
    }

    @Override
    public List<String> getRelatedActors()
    {
        return new ArrayList<>(this.relatedActors.keySet());
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getType());

        sb.append(": ");

        sb.append("[Actors: ");

        for (String a : this.relatedActors.keySet())
        {
            sb.append(a);
            sb.append("; ");
        }

        sb.append("]");

        return sb.toString();
    }
}
