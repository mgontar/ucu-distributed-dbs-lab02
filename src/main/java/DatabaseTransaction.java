import java.sql.SQLException;

public interface DatabaseTransaction {
    String getID();
    TransactionState getState();
    DatabaseManager getManager();
    void prepare() throws SQLException;
    void commit() throws SQLException;
    void rollback() throws SQLException;
}
