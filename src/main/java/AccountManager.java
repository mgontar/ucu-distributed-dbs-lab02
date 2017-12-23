import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class AccountManager extends DatabaseManagerImpl{
    PreparedStatement insertStmt = null;
    String insertString =
            "INSERT INTO \"Account\"" +
                    " (\"Client Name\", \"Ammount\")" +
                    " VALUES(?,?)";
    PreparedStatement updateStmt = null;
    String updateString =
            "UPDATE \"Account\"" +
                    " SET \"Ammount\" = \"Ammount\" - ?" +
                    " WHERE \"Client Name\" = ?";

    public AccountManager() {
        try {
            open_db("DB3");
            insertStmt = con.prepareStatement(insertString);
            updateStmt = con.prepareStatement(updateString);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void insert(String clientName, Integer amount) throws SQLException {
        insertStmt.setString(1, clientName);
        insertStmt.setInt(2, amount);
        insertStmt.executeUpdate();
        System.out.println("Account insert success");
    };

    public void update(String clientName, Integer minusAmount) throws SQLException {
        updateStmt.setInt(1, minusAmount);
        updateStmt.setString(2, clientName);
        updateStmt.executeUpdate();
        System.out.println("Account update success");
    };
}
