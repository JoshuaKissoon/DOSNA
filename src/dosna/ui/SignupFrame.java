package dosna.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Frame to get user credentials
 *
 * @author Joshua Kissoon
 * @date 20140326
 */
public class SignupFrame extends JFrame
{

    /* Main Components */
    private JPanel form;

    /* Form Items */
    private JTextField userNameTF, fullNameTF;
    private JPasswordField passwordTF;
    private JButton btn;
    private JLabel label;

    /* Layout Manager Components */
    private GridBagConstraints gbc;

    /* Listeners */
    private ActionListener listener;

    public SignupFrame()
    {

    }

    public void createGUI()
    {
        form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(20, 20, 20, 20));

        /* Username */
        label = new JLabel("Username: ");
        gbc = GBConstraints.getLabelConstraints(4, 4);
        form.add(label, gbc);

        userNameTF = new JTextField(20);
        gbc = GBConstraints.getItemConstraints(5, 4);
        form.add(userNameTF, gbc);

        /* Password */
        label = new JLabel("Password: ");
        gbc = GBConstraints.getLabelConstraints(4, 5);
        form.add(label, gbc);

        passwordTF = new JPasswordField(20);
        gbc = GBConstraints.getItemConstraints(5, 5);
        form.add(passwordTF, gbc);

        /* Full Name */
        label = new JLabel("Full Name: ");
        gbc = GBConstraints.getLabelConstraints(4, 6);
        form.add(label, gbc);

        fullNameTF = new JTextField(20);
        gbc = GBConstraints.getItemConstraints(5, 6);
        form.add(fullNameTF, gbc);

        /* Submit btn */
        btn = new JButton("Signup");
        btn.setActionCommand("signup");
        btn.addActionListener(this.listener);
        gbc = GBConstraints.getItemConstraints(4, 8);
        form.add(btn, gbc);

        /* Adding the form to the frame */
        this.getContentPane().add(form);
    }

    public void display()
    {
        this.setTitle("Sign Up.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    public void setActionListener(final ActionListener listener)
    {
        this.listener = listener;
    }

    public String getUsername()
    {
        return userNameTF.getText();
    }

    public String getPassword()
    {
        return new String(passwordTF.getPassword());
    }

    public String getFullName()
    {
        return fullNameTF.getText();
    }
}
