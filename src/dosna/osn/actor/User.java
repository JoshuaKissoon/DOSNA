package dosna.osn.actor;

import dosna.util.HashCalculator;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kademlia.node.NodeId;

/**
 * A OSN User
 *
 * @author Joshua Kissoon
 * @since 20140328
 *
 * @todo Limit UserId length to 8 or 10 characters
 */
public class User extends Actor
{
    
    public static final String TYPE = "User";
    
    private String userId;
    private String fullName;
    private NodeId key;
    private String hashedPassword;
    
    public User()
    {
        
    }

    /**
     * Create a new user object
     *
     * @param userId
     */
    public User(final String userId)
    {
        this.userId = userId.toLowerCase();
        this.generateKey();
    }

    /**
     * Generate a Node ID for this User object is the username/ownerId repeated until it have 20 characters
     *
     * @note The NodeId for this User object is the same as for the node this user uses,
     * A node will always store it's local user's uid
     */
    private void generateKey()
    {
        int numRepeats = ((NodeId.ID_LENGTH / 8) / this.userId.length()) + 1;
        StringBuilder nodeId = new StringBuilder();
        for (int i = 0; i < numRepeats; i++)
        {
            nodeId.append(this.userId);
        }
        this.key = new NodeId(nodeId.substring(0, 20));
    }
    
    @Override
    public String getUserId()
    {
        return this.userId;
    }
    
    public void setName(final String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * @return String This user's full name
     */
    @Override
    public String getName()
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
            /* Return a string version of the hash */
            return new BigInteger(1, HashCalculator.md5Hash(password, salt)).toString(16);
        }
        catch (NoSuchAlgorithmException e)
        {
            return new BigInteger(1, password.getBytes()).multiply(new BigInteger(1, salt.getBytes())).toString(16);
        }
    }
    
    @Override
    public String getOwnerId()
    {
        return this.userId;
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
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("User: ");
        
        sb.append("[username: ");
        sb.append(this.userId);
        
        sb.append("] ");
        sb.append("[name: ");
        sb.append(this.fullName);
        sb.append("] ");
        
        sb.append("[ContentManager: ");
        sb.append(this.getContentManager());
        sb.append("] ");
        
        sb.append("[ConnectionsManager: ");
        sb.append(this.getConnectionManager());
        sb.append("] ");
        
        return sb.toString();
    }
}
