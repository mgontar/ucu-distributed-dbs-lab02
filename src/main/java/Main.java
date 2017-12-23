import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String clientName = "John Dopkins";
        TransactionManager tm = new TransactionManager();
        try {
            tm.prepare(clientName);

            for(int i = 0; i < 3; i++)
            {
                tm.proceed(clientName);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            tm.close();
        }
    }
}
