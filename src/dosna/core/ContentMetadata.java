package dosna.core;

import dosna.content.DOSNAContent;
import kademlia.node.NodeId;

/**
 * Class used by the content manager to store metadata of a content.
 * It will be used to keep track of content in Sorted order by timestamp.
 *
 * This class should be kept as small as possible and should never contain the actual content.
 *
 *
 * @author Joshua Kissoon
 * @since 20140406
 */
public final class ContentMetadata implements Comparable<ContentMetadata>
{

    private NodeId key;
    private long updateTs;
    private String ownerId;
    private String type;

    /**
     * Blank constructor used mainly by serializers.
     */
    public ContentMetadata()
    {

    }

    /**
     * Create a new ContentMetadata object for the given content
     *
     * @param content
     */
    public ContentMetadata(final DOSNAContent content)
    {
        this.key = content.getKey();
        this.updateTs = content.getLastUpdatedTimestamp();
        this.ownerId = content.getOwnerId();
        this.type = content.getType();
    }

    public NodeId getKey()
    {
        return this.key;
    }

    public long getLastUpdatedTimestamp()
    {
        return this.updateTs;
    }

    public String getOwnerId()
    {
        return this.ownerId;
    }

    public String getType()
    {
        return this.type;
    }

    /**
     * We compare content Metadata based on timestamp
     */
    @Override
    public int compareTo(final ContentMetadata o)
    {
        if (this.getKey().equals(o.getKey()))
        {
            return 0;
        }

        return this.getLastUpdatedTimestamp() > o.getLastUpdatedTimestamp() ? 1 : -1;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("ContentMetadata: ");

        sb.append("[Type: ");
        sb.append(this.getType());
        sb.append("]");

        sb.append("[Owner: ");
        sb.append(this.getOwnerId());
        sb.append("]");

        sb.append("");

        return sb.toString();
    }
}
