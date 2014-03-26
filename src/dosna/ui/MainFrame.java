package dosna.ui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * The Main UI frame launched with the application
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class MainFrame extends JFrame
{

    public MainFrame()
    {
        /* Set the frame's listeners */
        this.addWindowListener(new FrameWindowListener());
    }

    /**
     * Display the frame
     */
    public void display()
    {
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private class FrameWindowListener implements WindowListener
    {

        @Override
        public void windowActivated(WindowEvent e)
        {

        }

        @Override
        public void windowDeactivated(WindowEvent e)
        {
            
        }

        @Override
        public void windowIconified(WindowEvent e)
        {
            /**
             * @todo Minimize the application and show an icon in the taskbar
             */
        }

        @Override
        public void windowDeiconified(WindowEvent e)
        {
            /**
             * @todo Remove the taskbar icon
             */
        }

        @Override
        public void windowOpened(WindowEvent e)
        {

        }

        @Override
        public void windowClosing(WindowEvent e)
        {
            /**
             * @todo Save the Kademlia state and DOSNA state and shutdown
             */
        }

        @Override
        public void windowClosed(WindowEvent e)
        {

        }
    }
}
