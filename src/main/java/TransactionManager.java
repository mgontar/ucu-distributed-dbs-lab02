import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionManager {
    FlyManager flyManager;
    HotelManager hotelManager;
    AccountManager accountManager;

    List<DatabaseTransaction> transactions = new ArrayList<DatabaseTransaction>();

    private List<DatabaseTransaction> getPrepared() {
        List<DatabaseTransaction> result = transactions.stream()
                .filter(t -> t.getState() == TransactionState.Prepared)
                .collect(Collectors.toList());
        return result;
    }

    private DatabaseTransaction getTransactionByManager(DatabaseManager dbManager) {
        List<DatabaseTransaction> result = transactions.stream()
                .filter(t -> t.getManager() == dbManager)
                .collect(Collectors.toList());
        return result.get(0);
    }

    public TransactionManager() {
        flyManager = new FlyManager();
        hotelManager = new HotelManager();
        accountManager = new AccountManager();
    }

    public void prepare(String clientName) throws SQLException {
        accountManager.insert(clientName, 300);
    }

    public void proceed(String clientName) throws SQLException {
        transactions.add(new DatabaseTransactionImpl("01", flyManager));
        transactions.add(new DatabaseTransactionImpl("02", hotelManager));
        transactions.add(new DatabaseTransactionImpl("03", accountManager));
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse("02/02/2017");
            flyManager.insert(clientName, "GTF 123", "Kyjiv", "Lviv", date);
            getTransactionByManager(flyManager).prepare();

            Date arrivalDate = sdf.parse("02/02/2017");
            Date departureDate = sdf.parse("04/02/2017");
            hotelManager.insert(clientName, "Hetman", arrivalDate, departureDate);
            getTransactionByManager(hotelManager).prepare();

            accountManager.update(clientName, 100);
            getTransactionByManager(accountManager).prepare();

            getTransactionByManager(flyManager).commit();
            //getTransactionByManager(hotelManager).commit();
            getTransactionByManager(accountManager).commit();
        } catch (SQLException e) {
            e.printStackTrace();
            List<DatabaseTransaction> prepared = getPrepared();
            for (DatabaseTransaction t:prepared) {
                t.rollback();
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void close()
    {
        try {
            flyManager.close_db();
            hotelManager.close_db();
            accountManager.close_db();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
