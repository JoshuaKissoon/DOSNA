package dosna.osn;

import dosna.dhtAbstraction.DOSNAContent;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Each Actor will put many content onto his/her profile; This class:
 * - Puts content from this actor onto the DHT.
 * - Keeps track of all of the Actor's content.
 *
 * @author Joshua Kissoon
 * @since 20140401
 */
public class ActorContentManager
{

    /**
     * Set of content for a specific actor
     * Type - String - The type of element stored
     */
    private final HashMap<String, TreeSet<ContentReference>> content;

    public ActorContentManager()
    {
        content = new HashMap<>();
        content.put("Key1", new TreeSet<ContentReference>());
        content.put("Key2", new TreeSet<ContentReference>());
        content.put("Key3", new TreeSet<ContentReference>());
        
        content.get("Key1").add(new ContentReference(null));
        content.get("Key2").add(new ContentReference(null));
        content.get("Key3").add(new ContentReference(null));
    }

    /**
     * Store a new Content on the DHT
     *
     * @param content The content to store
     */
    public void store(final DOSNAContent content)
    {

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
    /**
     * Class used by the content manager to reference a content.
     * It will be used to keep track of content in Sorted order by timestamp.
     */
    public final class ContentReference implements Comparable<ContentReference>
    {

        public ContentReference(final DOSNAContent content)
        {

        }

        @Override
        public int compareTo(final ContentReference o)
        {
            return 1;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("ContentManager: ");
        sb.append("[# Content: ");
        sb.append(this.content.size());
        sb.append("]");

        return sb.toString();
    }
}
