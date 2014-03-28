package dosna;

import dosna.dhtAbstraction.DataManager;
import dosna.dhtAbstraction.DosnaDataManager;
import dosna.osn.User;
import dosna.ui.LoginFrame;
import dosna.ui.SignupFrame;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import kademlia.core.GetParameter;
import kademlia.dht.KadContent;
import kademlia.node.NodeId;

/**
 * The main DOSNA launch file
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class DOSNA
{

    private DataManager dataManager;

    public DOSNA()
    {

    }

    /**
     * Launch the main node for this instance
     * Connect to the network
     * Launch the main Application UI
     *
     * @param username
     * @param nid
     *
     * @throws java.io.IOException
     */
    public void initRouting(String username, NodeId nid) throws IOException
    {
        dataManager = new DosnaDataManager(username, nid);
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

                        User u = new User(username);

                        try
                        {
                            /* Initialize DOSNA, so we create a node and join the network */
                            DOSNA.this.initRouting(username, u.getKey());
                        }
                        catch (IOException ex)
                        {

                        }

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
                /* Handle signing up the user */
                String username = signup.getUsername().trim();
                String password = signup.getPassword().trim();
                String fullName = signup.getFullName().trim();

                if (username.isEmpty() || password.isEmpty() || fullName.isEmpty())
                {
                    
                }
                else
                {

                    User u = new User(username);
                    u.setPassword(password);
                    u.setFullName(fullName);

                    try
                    {
                        /* Initialize our routing */
                        DOSNA.this.initRouting(username, u.getKey());

                        /* See if this user object already exists on the network */
                        GetParameter gp = new GetParameter(u.getKey(), username, User.TYPE);
                        List<KadContent> items = dataManager.get(gp, 3);

                        if (items.size() > 0)
                        {
                            /**
                             * Username is already taken, block this user and show a warning
                             */
                            System.out.println("Account exists");
                            JOptionPane.showMessageDialog(null, "This username is already taken! Please try another username.");
                        }
                        else
                        {
                            /* Lets add this user to the system */
                            dataManager.putLocally(u);
                            dataManager.put(u);

                            /* User added, now launch DOSNA */
                            signup.dispose();
                            JOptionPane.showMessageDialog(null, "You have successfully joined! welcome!");

                        }
                    }
                    catch (IOException ex)
                    {

                    }
                }
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
