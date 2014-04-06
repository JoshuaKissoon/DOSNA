package dosna.osn.status;

import dosna.osn.homestream.HomeStreamContent;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Class that displays a status on the home stream
 *
 * @author Joshua
 * @since
 */
public class StatusHomeStreamDisplay extends JPanel implements HomeStreamContent
{

    /* Elements */
    private final JTextArea statusDisplayTA;

    /**
     * Setting up the status home stream display.
     *
     * @param status The status to be displayed
     */
    public StatusHomeStreamDisplay(Status status)
    {
        this.setLayout(new BorderLayout());
        statusDisplayTA = new JTextArea(status.getStatusText());
        statusDisplayTA.setEditable(false);
        statusDisplayTA.setWrapStyleWord(true);
        statusDisplayTA.setLineWrap(true);

        this.add(statusDisplayTA, BorderLayout.CENTER);
    }

    @Override
    public JPanel getContentDisplay()
    {
        return this;
    }

}
