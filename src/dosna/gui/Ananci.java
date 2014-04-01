package dosna.gui;

import dosna.dhtAbstraction.DataManager;
import dosna.osn.Actor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * The Main User Interface class of DOSNA.
 *
 * @author Joshua Kissoon
 * @since 20140401
 */
public class Ananci extends JFrame
{

    private final DataManager dataManager;
    private final Actor actor;

    /**
     * Initialize the UI Class
     *
     * @param mngr  The DataManager used to read and store data
     * @param actor Actor currently logged in
     */
    public Ananci(final DataManager mngr, final Actor actor)
    {
        this.dataManager = mngr;
        this.actor = actor;
    }

    private class ElegantWindowListener extends WindowAdapter
    {

        @Override
        public void windowClosing(final WindowEvent e)
        {
            /* Save the state before closing */
            try
            {
                Ananci.this.dataManager.shutdown(true);
            }
            catch (IOException ex)
            {
                System.err.println("Shutdown save state operation failed; message: " + ex.getMessage());
            }
        }
    }
}
