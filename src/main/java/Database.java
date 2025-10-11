import java.sql.*;

public class Database {
    private final String url;
    private final String user;
    private final String pass;

    public Database(String url, String user, String pass) {
            this.url = url;
            this.user = user;
            this.pass = pass;
        }

    public String getDrinkName(int id) {
        String query = "SELECT name FROM drink WHERE drink_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString("name");
            } else {
                return null;
            }

        } catch(SQLException e) {
            System.out.println("Database Error");
            return null;
        }
    }

    public double getDrinkPrice(int id) {
        String query = "SELECT price FROM drink WHERE drink_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                return resultSet.getDouble("price");
            } else {
                return 0.0;
            }

        } catch(SQLException e) {
            System.out.println("Database Error");
            return 0.0;
        }
    }
    

}   