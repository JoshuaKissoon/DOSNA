package dosna;

import dosna.dhtAbstraction.DataManager;
import dosna.dhtAbstraction.DosnaDataManager;
import dosna.gui.AnanciUI;
import dosna.gui.LoginFrame;
import dosna.gui.SignupFrame;
import dosna.osn.actor.Actor;
import dosna.osn.actor.ActorCreator;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import kademlia.dht.GetParameter;
import kademlia.dht.StorageEntry;
import kademlia.exceptions.ContentNotFoundException;
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

                    final LoginResult res = this.loginUser(username, password);
                    if (res.isLoginSuccessful)
                    {
                        /* Everything's great! Launch the app */
                        login.dispose();
                        DOSNA.this.launchMainGUI(res.loggedInActor);
                    }
                    else
                    {
                        if (res.isUserExistent)
                        {
                            JOptionPane.showMessageDialog(null, "Invalid password! please try again.");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Sorry, no account exists for the given user.");
                        }
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
     * Try to login a user into the system
     *
     * @param username
     * @param password
     *
     * @return Whether the user login was successful or not
     */
    public LoginResult loginUser(String username, String password)
    {
        final Actor u = new Actor(username);

        if (DOSNA.this.initRouting(username, u.getKey()))
        {
            try
            {
                /* Checking if a user exists */
                final GetParameter gp = new GetParameter(u.getKey(), u.getType(), username);
                StorageEntry items = this.dataManager.get(gp);

                /* User exists! Now check if password matches */
                Actor actor = (Actor) new Actor().fromBytes(items.getContent().getBytes());
                if (actor.isPassword(password))
                {
                    return new LoginResult(actor, true);
                }
            }
            catch (ContentNotFoundException cnfex)
            {
                /* The user does not exist */
                return new LoginResult();
            }
            catch (IOException ex)
            {

            }
        }

        /* Login was unsuccessful */
        return new LoginResult(false);
    }

    /**
     * A class used to return the result of a login
     */
    public class LoginResult
    {

        public Actor loggedInActor = null;
        public boolean isLoginSuccessful = false;
        public boolean isUserExistent = false;

        public LoginResult(final Actor loggedInActor, final boolean isLoginSuccessful, final boolean isUserExistent)
        {
            this.loggedInActor = loggedInActor;
            this.isLoginSuccessful = isLoginSuccessful;
            this.isUserExistent = isUserExistent;
        }

        public LoginResult(final Actor loggedInActor, final boolean isLoginSuccessful)
        {
            this(loggedInActor, isLoginSuccessful, true);
        }

        public LoginResult(final Boolean isLoginSuccessful)
        {
            this.isLoginSuccessful = isLoginSuccessful;
            this.isUserExistent = true;
        }

        public LoginResult()
        {

        }
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

                final Actor u = new Actor(username);
                u.setPassword(password);
                u.setName(fullName);

                boolean usernameTaken = false;

                try
                {
                    /* Initialize our routing */
                    DOSNA.this.initRouting(username, u.getKey());

                    /* See if this user object already exists on the network */
                    GetParameter gp = new GetParameter(u.getKey(), username, u.getType());
                    StorageEntry item = dataManager.get(gp);

                    /* Username is already taken, block this user and show a warning */
                    JOptionPane.showMessageDialog(null, "This username is already taken! Please try another username.");
                }
                catch (IOException | ContentNotFoundException ex)
                {
                    try
                    {
                        /* Lets add this user to the system */
                        ActorCreator ac = new ActorCreator(dataManager, u);

                        Actor createdActor = ac.createActor();

                        /* User added, now launch DOSNA */
                        signup.dispose();
                        JOptionPane.showMessageDialog(null, "You have successfully joined! welcome!");
                        DOSNA.this.launchMainGUI(createdActor);
                    }
                    catch (IOException exc)
                    {

                    }
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
    public void launchMainGUI(final Actor actor)
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
