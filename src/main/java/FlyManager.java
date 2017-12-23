import java.sql.*;
import java.util.Date;

public class FlyManager extends DatabaseManagerImpl {
    PreparedStatement insertStmt = null;

    String insertString =
            "INSERT INTO \"Fly Booking\"" +
                    " (\"Client Name\", \"Fly Number\", \"From\", \"To\", \"Date\")" +
                    " VALUES(?,?,?,?,?)";

    public FlyManager() {
        try {
            open_db("DB1");
            insertStmt = con.prepareStatement(insertString);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void insert(String clientName, String flyNumber, String from, String to, Date date) throws SQLException{
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        insertStmt.setString(1, clientName);
        insertStmt.setString(2, flyNumber);
        insertStmt.setString(3, from);
        insertStmt.setString(4, to);
        insertStmt.setDate(5, sqlDate);
        insertStmt.executeUpdate();
        System.out.println("Fly insert success");
    };
}
