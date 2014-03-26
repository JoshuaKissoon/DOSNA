package dosna;

import dosna.ui.LoginFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main DOSNA launch file
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class DOSNA
{

    public DOSNA()
    {

    }

    public void init()
    {

    }

    /* Starts the DOSNA */
    public void start()
    {
        /* Ask the user to login */
        LoginFrame login = new LoginFrame(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if (event.getActionCommand().equals("login"))
                {
                    /* @todo Login the user */
//                    Session.userId = userNameTF.getText();
//                    Session.password = new String(passwordTF.getPassword());
//                    Socialize soc = new Socialize(Session.password);
//                    frame.dispose();
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new DOSNA().start();
    }

}
