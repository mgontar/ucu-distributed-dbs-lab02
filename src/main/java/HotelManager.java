import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class HotelManager extends DatabaseManagerImpl {
    PreparedStatement insertStmt = null;

    String insertString =
            "INSERT INTO \"Hotel Booking\"" +
                    " (\"Client Name\", \"Hotel Name\", \"Arrival\", \"Departure\")" +
                    " VALUES(?,?,?,?)";

    public HotelManager() {
        try {
            open_db("DB2");
            insertStmt = con.prepareStatement(insertString);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void insert(String clientName, String hotelName, Date arrivalDate, Date departureDate) throws SQLException{
        java.sql.Date sqlArrivalDate = new java.sql.Date(arrivalDate.getTime());
        java.sql.Date sqlDepartureDate = new java.sql.Date(departureDate.getTime());
        insertStmt.setString(1, clientName);
        insertStmt.setString(2, hotelName);
        insertStmt.setDate(3, sqlArrivalDate);
        insertStmt.setDate(4, sqlDepartureDate);
        insertStmt.executeUpdate();
        System.out.println("Hotel insert success");
    };
}
