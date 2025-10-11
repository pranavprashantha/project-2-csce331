package database;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Time;

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
    
    public void completeOrder(LocalDate date, int week, LocalTime time, double price, String drinks) {
        String query = "INSERT INTO orders (date, week, time, price, drinks) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setInt(2, week);
            stmt.setTime(3, java.sql.Time.valueOf(time.truncatedTo(ChronoUnit.SECONDS)));
            stmt.setDouble(4, price);
            stmt.setString(5, drinks);

            int n = stmt.executeUpdate();

        } catch(SQLException e) {
            System.out.println("Table has not been updated");
            System.out.println("Table has not been updated: " + e.getMessage());

        }
    }
}   