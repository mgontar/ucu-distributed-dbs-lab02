import java.sql.SQLException;

public class DatabaseTransactionImpl implements DatabaseTransaction {
    String id;
    DatabaseManager dbManager;
    TransactionState state;

    public DatabaseTransactionImpl(String id, DatabaseManager dbManager) throws SQLException {
        this.id = id;
        this.dbManager = dbManager;
        state = TransactionState.None;
        this.dbManager.tpc_begin();
    }

    public String getID() {
        return id;
    }

    public TransactionState getState() {
        return state;
    }

    public DatabaseManager getManager()
    {
        return dbManager;
    }

    public void prepare() throws SQLException {
        dbManager.tpc_prepare(getID());
        state = TransactionState.Prepared;
    }

    public void commit() throws SQLException {
        dbManager.tpc_commit(getID());
        state = TransactionState.Commited;
    }

    public void rollback() throws SQLException {
        dbManager.tpc_rollback(getID());
        state = TransactionState.Rollbacked;
    }
}
