import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanApplication extends JFrame {
    private JComboBox<String> memberComboBox, loanTypeComboBox, guarantorComboBox, loanComboBox;
    private JTextField loanAmountField, repaymentPeriodField, guaranteedAmountField;
    private JButton applyLoanButton, addGuarantorButton;

    public LoanApplication() {
        setTitle("Loan and Guarantor Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Loan Application", createLoanPanel());
        tabbedPane.addTab("Guarantors", createGuarantorPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createLoanPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Member selection
        JLabel memberLabel = new JLabel("Select Member:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(memberLabel, gbc);

        memberComboBox = new JComboBox<>();
        populateMembers();
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(memberComboBox, gbc);

        // Loan Type selection
        JLabel loanTypeLabel = new JLabel("Loan Type:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(loanTypeLabel, gbc);

        loanTypeComboBox = new JComboBox<>();
        populateLoanTypes();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(loanTypeComboBox, gbc);

        // Loan amount
        JLabel amountLabel = new JLabel("Loan Amount:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(amountLabel, gbc);

        loanAmountField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loanAmountField, gbc);

        // Repayment period
        JLabel repaymentLabel = new JLabel("Repayment Period (months):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(repaymentLabel, gbc);

        repaymentPeriodField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(repaymentPeriodField, gbc);

        // Apply loan button
        applyLoanButton = new JButton("Apply Loan");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(applyLoanButton, gbc);

        applyLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyForLoan();
            }
        });

        return panel;
    }

    private JPanel createGuarantorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Loan selection
        JLabel loanLabel = new JLabel("Select Loan:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(loanLabel, gbc);

        loanComboBox = new JComboBox<>();
        populateLoans();
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(loanComboBox, gbc);

        // Guarantor selection
        JLabel guarantorLabel = new JLabel("Guarantor:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(guarantorLabel, gbc);

        guarantorComboBox = new JComboBox<>();
        populateGuarantors();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(guarantorComboBox, gbc);

        // Guaranteed amount
        JLabel guaranteedAmountLabel = new JLabel("Guaranteed Amount:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(guaranteedAmountLabel, gbc);

        guaranteedAmountField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(guaranteedAmountField, gbc);

        // Add guarantor button
        addGuarantorButton = new JButton("Add Guarantor");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addGuarantorButton, gbc);

        addGuarantorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGuarantor();
            }
        });

        return panel;
    }

    private void populateMembers() {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT member_id, name FROM Members";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                memberComboBox.addItem(resultSet.getInt("member_id") + " - " + resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateLoanTypes() {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT loan_type_id, loan_type_name FROM LoanTypes";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                loanTypeComboBox.addItem(resultSet.getInt("loan_type_id") + " - " + resultSet.getString("loan_type_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateLoans() {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT loan_id, loan_amount FROM Loans";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                loanComboBox.addItem(resultSet.getInt("loan_id") + " - Loan Amount: " + resultSet.getDouble("loan_amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateGuarantors() {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT member_id, name FROM Members";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                guarantorComboBox.addItem(resultSet.getInt("member_id") + " - " + resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void applyForLoan() {
        String selectedMember = (String) memberComboBox.getSelectedItem();
        int memberId = Integer.parseInt(selectedMember.split(" - ")[0]);
        String selectedLoanType = (String) loanTypeComboBox.getSelectedItem();
        int loanTypeId = Integer.parseInt(selectedLoanType.split(" - ")[0]);
        double loanAmount = Double.parseDouble(loanAmountField.getText());
        int repaymentPeriod = Integer.parseInt(repaymentPeriodField.getText());

        try (Connection conn = DatabaseConnection.connect()) {
            String query = "INSERT INTO Loans (member_id, loan_type_id, loan_amount, repayment_period, interest_rate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, memberId);
            statement.setInt(2, loanTypeId);
            statement.setDouble(3, loanAmount);
            statement.setInt(4, repaymentPeriod);
            statement.setDouble(5, getInterestRate(loanTypeId, conn));
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Loan Applied Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error applying for loan.");
        }
    }

    private double getInterestRate(int loanTypeId, Connection conn) throws SQLException {
        String query = "SELECT interest_rate FROM LoanTypes WHERE loan_type_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, loanTypeId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("interest_rate");
        }
        return 0.0;  // Default interest rate if not found
    }

    private void addGuarantor() {
        String selectedLoan = (String) loanComboBox.getSelectedItem();
        int loanId = Integer.parseInt(selectedLoan.split(" - ")[0]);
        String selectedGuarantor = (String) guarantorComboBox.getSelectedItem();
        int guarantorId = Integer.parseInt(selectedGuarantor.split(" - ")[0]);
        double guaranteedAmount = Double.parseDouble(guaranteedAmountField.getText());

        try (Connection conn = DatabaseConnection.connect()) {
            String query = "INSERT INTO Guarantors (loan_id, guarantor_member_id, guaranteed_amount) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, loanId);
            statement.setInt(2, guarantorId);
            statement.setDouble(3, guaranteedAmount);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Guarantor Added Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding guarantor.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoanApplication().setVisible(true));
    }
}


