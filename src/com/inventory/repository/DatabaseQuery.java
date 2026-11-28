
import com.inventory.service.DatabaseConnection;
import java.io.IOException;
import java.sql.*;

public class DatabaseQuery {

    public static void GetItem(String[] args) {

        String sql = "";

        // Try-with-resources automatically closes the connection and statements
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
