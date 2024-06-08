package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonConnection {
    public static void main(String[] args) {
        personConnection();
    }
    public static Connection personConnection() {
        String URL = "jdbc:mysql://localhost:3306/dormitorydatabase";
        String username = "root";
        String password = "mysql";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3)
                        + " " + resultSet.getString(4));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}
