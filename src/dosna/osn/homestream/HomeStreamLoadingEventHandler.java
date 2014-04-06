package dosna.osn.homestream;

import dosna.core.ContentMetadata;
import dosna.dhtAbstraction.DataManager;
import java.util.Collection;

/**
 * An API event called when the HomeStream is being loaded.
 *
 * @author Joshua Kissoon
 * @param <T> The type of content returned.
 *
 * @since 20140406
 *
 * @note This is just a simple version to try to comply with event rules not implemented as yet
 */
public interface HomeStreamLoadingEventHandler<T>
{

    /**
     * Used to add content to this event handler
     *
     * @param content A collection of content metadata
     */
    public void addContent(Collection<ContentMetadata> content);

    /**
     * Load the content from metadata given and return a collection of this content.
     *
     * @param dataManager The DataManager used to handle storing/retrieving content
     *
     * @return A set of content in sorted order.
     */
    public Collection<HomeStreamContent> loadContent(DataManager dataManager);
}
