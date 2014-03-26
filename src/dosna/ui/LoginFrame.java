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
public class LoginFrame extends JFrame
{
    /* Main Components */
    private final JPanel form;

    /* Form Items */
    private final JTextField userNameTF;
    private final JPasswordField passwordTF;
    private final JButton btn;
    private JLabel label;

    /* Layout Manager Components */
    private GridBagConstraints gbc;

    public LoginFrame(final ActionListener loginListener)
    {
        form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(20, 20, 20, 20));

        label = new JLabel("Username: ");
        gbc = GBConstraints.getLabelConstraints(4, 4);
        form.add(label, gbc);

        userNameTF = new JTextField(20);
        gbc = GBConstraints.getItemConstraints(5, 4);
        form.add(userNameTF, gbc);

        label = new JLabel("Password: ");
        gbc = GBConstraints.getLabelConstraints(4, 5);
        form.add(label, gbc);

        passwordTF = new JPasswordField(20);
        gbc = GBConstraints.getItemConstraints(5, 5);
        form.add(passwordTF, gbc);

        btn = new JButton("Login");
        btn.setActionCommand("login");
        btn.addActionListener(loginListener);
        gbc = GBConstraints.getItemConstraints(4, 8);
        form.add(btn, gbc);

        /* Adding the form to the frame */
        this.getContentPane().add(form);
    }

    public void display()
    {
        this.setTitle("Socialize: Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
}
