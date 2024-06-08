package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
            System.out.println("Data loaded");
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Database connected");
            Statement statement = connection.createStatement();

            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}
