package dosna;

import dosna.ui.LoginFrame;
import dosna.ui.SignupFrame;
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

    public void init(String userName)
    {

    }

    /* Starts the DOSNA */
    public void start()
    {
        this.userLogin();
    }

    /**
     * Launch the user login form and handle logging in the user
     */
    public void userLogin()
    {
        /* Ask the user to login */
        final LoginFrame login = new LoginFrame();
        login.setActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                switch (event.getActionCommand())
                {
                    case "login":
                        /* @todo Login the user */
                        String username = login.getUsername();
                        String password = login.getPassword();

                        /* Initialize DOSNA, so we create a node and join the network */
                        DOSNA.this.init(username);

                        /* @todo Check if a user profile exists for this username */
                        /* @todo If a profile exists, check if the password is valid by decrypting the encrypted username */
                        /* @todo Everything's good, launch the application and populate this user's account */
                        //                    Socialize soc = new Socialize(Session.password);
                        //                    frame.dispose();
                        break;
                    case "signup":
                        /* The user wants to signup, get them the signup form */
                        login.dispose();
                        DOSNA.this.userSignup();
                        break;
                }
            }
        });
        login.createGUI();
        login.display();
    }

    /**
     * Launch the user signup form and handle signing in the user
     */
    public void userSignup()
    {
        final SignupFrame signup = new SignupFrame();

        signup.setActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("SIgnup Action Perfoemed called.");
            }
        });

        signup.createGUI();
        signup.display();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new DOSNA().start();
    }

}
