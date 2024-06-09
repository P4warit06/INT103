package repository.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    public static Connection connect(){
        String URL = "jdbc:mysql://localhost:3306/mydb3";
        String Username = "root";
        String Password = "mysql@sit";
        String url = "jdbc:mysql://127.0.0.1:3306/login_schema";
        String username = "root";
        String password = "mysql";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connection;
    }
}

