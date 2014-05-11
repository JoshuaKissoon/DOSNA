package dosna.osn.actor;

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

    public User()
    {
        
    }

    @Override
    public String getType()
    {
        return TYPE;
    }
}
