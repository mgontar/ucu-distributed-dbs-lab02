import java.sql.SQLException;

public interface DatabaseManager {
    void open_db(String db_name) throws SQLException;
    void tpc_begin() throws SQLException;
    void tpc_prepare(String transact_id) throws SQLException;
    void tpc_commit(String transact_id) throws SQLException;
    void tpc_rollback(String transact_id) throws SQLException;
    void close_db() throws SQLException;
}
