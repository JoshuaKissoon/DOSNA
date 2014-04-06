package dosna;

import dosna.dhtAbstraction.DataManager;
import dosna.dhtAbstraction.DosnaDataManager;
import dosna.gui.AnanciUI;
import dosna.osn.actor.User;
import dosna.gui.LoginFrame;
import dosna.gui.SignupFrame;
import dosna.osn.actor.Actor;
import java.util.List;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import kademlia.dht.GetParameter;
import kademlia.dht.StorageEntry;
import kademlia.node.NodeId;

/**
 * The main DOSNA launch file
 *
 * @author Joshua Kissoon
 * @since 20140326
 */
public class DOSNA
{

    private DataManager dataManager = null;

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
     * @return Boolean Whether the initialization was successful or not
     */
    public boolean initRouting(String username, NodeId nid)
    {
        if (dataManager == null)
        {
            try
            {
                dataManager = new DosnaDataManager(username, nid);
            }
            catch (IOException ex)
            {
                System.err.println("Routing initialization failed! Message: " + ex.getMessage());
                return false;
            }
        }
        return true;
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
        login.setActionListener((final ActionEvent event) ->
        {
            switch (event.getActionCommand())
            {
                case "login":
                    /* @todo Login the user */
                    final String username = login.getUsername();
                    final String password = login.getPassword();

                    final User u = new User(username);

                    if (!DOSNA.this.initRouting(username, u.getKey()))
                    {
                        return;
                    }

                    try
                    {
                        /* Checking if a user exists */
                        final GetParameter gp = new GetParameter(u.getKey(), User.TYPE, username);
                        final List<StorageEntry> items = this.dataManager.get(gp, 1);

                        if (items.size() > 0)
                        {
                            /* User exists! Now check if password matches */
                            final User user = (User) new User().fromBytes(items.get(0).getContent());
                            System.out.println("Loaded User: " + user);
                            if (user.isPassword(password))
                            {
                                /* Everything's great! Launch the app */
                                login.dispose();
                                DOSNA.this.launchMainGUI(user);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Invalid password! please try again.");
                            }
                        }
                        else
                        {
                            /* No user exists */
                            JOptionPane.showMessageDialog(null, "Sorry, no account exists for the given user.");
                        }
                    }
                    catch (IOException ex)
                    {
                        System.err.println("Problem encountered during login whiles checking for existing profile; message: " + ex.getMessage());
                    }
                    break;
                case "signup":
                    /* The user wants to signup, get them the signup form */
                    login.dispose();
                    DOSNA.this.userSignup();
                    break;
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

        signup.setActionListener((ActionEvent e) ->
        {
            final String username = signup.getUsername().trim();
            final String password = signup.getPassword().trim();
            final String fullName = signup.getFullName().trim();

            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "All fields are required.");
            }
            else
            {

                final User u = new User(username);
                u.setPassword(password);
                u.setName(fullName);

                try
                {
                    /* Initialize our routing */
                    DOSNA.this.initRouting(username, u.getKey());

                    /* See if this user object already exists on the network */
                    GetParameter gp = new GetParameter(u.getKey(), username, User.TYPE);
                    List<StorageEntry> items = dataManager.get(gp, 1);

                    if (items.size() > 0)
                    {
                        /**
                         * Username is already taken, block this user and show a warning
                         */
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
                        DOSNA.this.launchMainGUI(u);
                    }
                }
                catch (IOException ex)
                {

                }
            }
        });

        signup.createGUI();
        signup.display();
    }

    /**
     * Launch the main GUI
     *
     * @param actor Which actor are we launching it for
     */
    public void launchMainGUI(Actor actor)
    {
        /* Lets set the data manager for this actor's content manager */
        actor.init(this.dataManager);
        AnanciUI mainUi = new AnanciUI(dataManager, actor);
        mainUi.create();
        mainUi.display();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new DOSNA().start();
    }

}
