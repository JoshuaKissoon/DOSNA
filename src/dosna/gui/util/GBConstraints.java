package dosna.gui.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Some constraints used throughout the application's UI
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class GBConstraints
{

    public static GridBagConstraints getLabelConstraints(int x, int y)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.weightx = 0.0;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.BOTH;

        return c;
    }

    public static GridBagConstraints getItemConstraints(int x, int y)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.WEST;

        return c;
    }
}
