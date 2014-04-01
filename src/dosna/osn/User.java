package dosna.osn;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kademlia.node.NodeId;

/**
 * A OSN User
 *
 * @author Joshua Kissoon
 * @since 20140328
 */
public class User extends Actor
{

    private final String username;
    private String fullName;
    private final NodeId key;
    private String hashedPassword;

    public static final String TYPE = "User";

    public User(final String username)
    {
        this.username = username;
        /**
         * Node ID for a User object is the username/ownerId repeated until it have 20 characters
         *
         * @note The NodeId for this User object is the same as for the node this user uses,
         * A node will always store it's local user's uid
         */
        int numRepeats = ((NodeId.ID_LENGTH / 8) / username.length()) + 1;
        StringBuilder nodeId = new StringBuilder();
        for (int i = 0; i < numRepeats; i++)
        {
            nodeId.append(username);
        }
        this.key = new NodeId(nodeId.substring(0, 20));
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    public void setFullName(final String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * @return String This user's full name
     */
    public String getFullName()
    {
        return this.fullName;
    }

    /**
     * Sets a new password for this user
     *
     * @param password
     */
    public void setPassword(final String password)
    {
        if (password.trim().equals(""))
        {
            throw new NullPointerException();
        }

        this.hashedPassword = this.hashPassword(password);
    }

    public boolean isPassword(final String password)
    {
        return this.hashedPassword.equals(this.hashPassword(password));
    }

    /**
     * Hashes the password
     *
     * @param password The password to be hashed
     *
     * @return String The hashed password
     */
    private String hashPassword(final String password)
    {
        final String salt = "iu4rkjd&^876%ewfuhi4Y&*&*^*03487658347*&^&^";

        try
        {
            /* Create a MessageDigest */
            MessageDigest md = MessageDigest.getInstance("MD5");

            /* Add password bytes to digest */
            md.update(password.getBytes());

            /* Get the hashed bytes */
            byte[] bytes = md.digest(salt.getBytes());

            /* Get the string version */
            return new BigInteger(1, bytes).toString(16);
        }
        catch (NoSuchAlgorithmException e)
        {
            return new BigInteger(1, password.getBytes()).multiply(new BigInteger(1, salt.getBytes())).toString(16);
        }
    }

    @Override
    public String getOwnerId()
    {
        return this.username;
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    @Override
    public NodeId getKey()
    {
        return this.key;
    }
}
