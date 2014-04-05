package dosna.osn.actor;

import dosna.core.ContentMetadata;
import dosna.dhtAbstraction.DOSNAContent;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Each Actor will put many content onto his/her profile; This class:
 * - Puts content from this actor onto the DHT.
 * - Keeps track of all of the Actor's content.
 *
 * @author Joshua Kissoon
 * @since 20140401
 */
public class ContentManager
{

    /**
     * Set of content for a specific actor
     * Type - String - The type of element stored
     */
    private final HashMap<String, TreeSet<ContentMetadata>> actorContent;

    public ContentManager()
    {
        actorContent = new HashMap<>();
        actorContent.put("Key1", new TreeSet<>());
        actorContent.put("Key2", new TreeSet<>());
        actorContent.put("Key3", new TreeSet<>());

        actorContent.get("Key1").add(new ContentMetadata(null));
        actorContent.get("Key2").add(new ContentMetadata(null));
        actorContent.get("Key3").add(new ContentMetadata(null));
    }

    /**
     * Store a new Content on the DHT
     *
     * @param content The content to store
     */
    public void store(final DOSNAContent content)
    {
        if (!this.actorContent.containsKey(content.getType()))
        {
            this.actorContent.put(content.getType(), new TreeSet<>());
        }

        this.actorContent.get(content.getType()).add(new ContentMetadata(content));
    }

    /**
     * Gets all content from this actor posted after the given timestamp
     *
     * @param ts
     *
     * @return
     */
//    public List<ContentReference> getContentPostedAfter(final long ts)
//    {
//
//    }
    /**
     * Gets all content of a specific type from this actor posted after the given timestamp
     *
     * @param ts
     * @param type The type of content
     *
     * @return
     */
//    public List<ContentReference> getContentPostedAfter(final long ts, final String type)
//    {
//
//    }
    /**
     * Gets all content from this actor posted before the given timestamp
     *
     * @param ts
     *
     * @return
     */
//    public List<ContentReference> getContentPostedBefore(final long ts)
//    {
//
//    }
    /**
     * Gets all content of a specific type from this actor posted before the given timestamp
     *
     * @param ts
     * @param type The type of content
     *
     * @return
     */
//    public List<ContentReference> getContentPostedBefore(final long ts, final String type)
//    {
//
//    }
    /**
     * Gets all content from this actor posted between the given timestamps
     *
     * @param startTs
     * @param endTs
     *
     * @return
     */
//    public List<ContentReference> getContentPostedBetween(final long startTs, final long endTs)
//    {
//
//    }
    /**
     * Gets all content of a specific type from this actor posted between the given timestamps
     *
     * @param startTs
     * @param endTs
     * @param type    The type of content
     *
     * @return
     */
//    public List<ContentReference> getContentPostedBetween(final long startTs, final long endTs, final String type)
//    {
//
//    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("ContentManager: ");
        sb.append("[# Content: ");
        sb.append(this.actorContent.size());
        sb.append("]");

        return sb.toString();
    }
}
