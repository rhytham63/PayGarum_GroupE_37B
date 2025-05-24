package Database;
import java.sql.*;
/**

 * @author r4hul
 */
public interface database {
    Connection openConnection();
    void closeConnection(Connection conn);
    ResultSet runQuery(Connection conn, String query);
    int executeUpdate(Connection conn, String query);
    
}