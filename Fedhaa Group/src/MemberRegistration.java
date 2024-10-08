import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberRegistration extends JFrame {
    private JTextField nameField, ageField, feeField, sharesField;
    private JButton registerButton;

    public MemberRegistration() {
        // Set up the form layout
        setTitle("Member Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Name field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 25);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(150, 50, 150, 25);
        add(nameField);

        // Age field
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 90, 100, 25);
        add(ageLabel);
        ageField = new JTextField();
        ageField.setBounds(150, 90, 150, 25);
        add(ageField);

        // Registration Fee field
        JLabel feeLabel = new JLabel("Registration Fee:");
        feeLabel.setBounds(50, 130, 100, 25);
        add(feeLabel);
        feeField = new JTextField();
        feeField.setBounds(150, 130, 150, 25);
        add(feeField);

        // Shares field
        JLabel sharesLabel = new JLabel("Shares:");
        sharesLabel.setBounds(50, 170, 100, 25);
        add(sharesLabel);
        sharesField = new JTextField();
        sharesField.setBounds(150, 170, 150, 25);
        add(sharesField);

        // Register button
        registerButton = new JButton("Register");
        registerButton.setBounds(150, 210, 100, 25);
        add(registerButton);

        // Action listener for registration button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerMember();
            }
        });
    }

    private void registerMember() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        double registrationFee = Double.parseDouble(feeField.getText());
        double shares = Double.parseDouble(sharesField.getText());

        Connection conn = DatabaseConnection.connect();
        if (conn != null) {
            try {
                String query = "INSERT INTO Members (name, age, registration_fee, shares_contributed) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, name);
                statement.setInt(2, age);
                statement.setDouble(3, registrationFee);
                statement.setDouble(4, shares);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Member Registered Successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error registering member.");
            }
        }
    }

    public static void main(String[] args) {
        new MemberRegistration().setVisible(true);
    }
}
