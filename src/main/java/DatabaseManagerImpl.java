import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagerImpl implements DatabaseManager {
    Connection con = null;
    Statement stmt = null;

    public void open_db(String db_name) throws SQLException {
        con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/"+db_name,
                        "postgres", "postgres");
        System.out.println(db_name + " database opened successfully");
        stmt = con.createStatement();
    }

    public void tpc_begin() throws SQLException {
        String sql = "BEGIN;";
        stmt.executeUpdate(sql);
        System.out.println("Transaction begin success");
    }

    public void tpc_prepare(String transact_id) throws SQLException {
        String sql = "PREPARE TRANSACTION '"+transact_id+"';";
        stmt.executeUpdate(sql);
        System.out.println("Transaction "+transact_id+" prepare success");
    }

    public void tpc_commit(String transact_id) throws SQLException {
        String sql = "COMMIT PREPARED  '"+transact_id+"';";
        stmt.executeUpdate(sql);
        System.out.println("Transaction "+transact_id+" commit success");
    }

    public void tpc_rollback(String transact_id) throws SQLException {
        String sql = "ROLLBACK PREPARED  '"+transact_id+"';";
        stmt.executeUpdate(sql);
        System.out.println("Transaction "+transact_id+" rollback success");
    }

    public void close_db() throws SQLException {
        stmt.close();
        con.close();
        System.out.println("DB close success");
    }
}
