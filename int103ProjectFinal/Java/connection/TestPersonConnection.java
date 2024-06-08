package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestPersonConnection {
    public static void main(String[] args) {
//        personConnection1();
//        personConnectionInsert2();
//        personConnectionUpdate2();
//        personConnectionDelete2();
        testMetaData();
    }
    public static Connection personConnection1() {
        String URL = "jdbc:mysql://localhost:3306/dormitorydatabase";
        String username = "root";
        String password = "mysql";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM person";
//            String insert = "INSERT INTO person(personId, name, email, password) VALUES(1160, 'Hello C++', 'HelloC++@gmail.com', 'C++12345');";
//            String update = "UPDATE person SET name = 'C' WHERE personId = 1160;";
//            String delete = "DELETE FROM person WHERE personId = 1160;";
//            statement.executeUpdate(delete);
//            statement.executeUpdate(update);
//            statement.executeUpdate(insert);
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3)
                        + " " + resultSet.getString(4));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    public static Connection personConnectionInsert2() {
        String URL = "jdbc:mysql://localhost:3306/dormitorydatabase";
        String username = "root";
        String password = "mysql";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person(personId, name, email, password) VALUES(?, ?, ?, ?);");
            preparedStatement.setString(1, "11111");
            preparedStatement.setString(2, "TTT");
            preparedStatement.setString(3, "TTT@gmail.com");
            preparedStatement.setString(4, "1150");
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    public static Connection personConnectionUpdate2() {
        String URL = "jdbc:mysql://localhost:3306/dormitorydatabase";
        String username = "root";
        String password = "mysql";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
            String update = "UPDATE person SET name = ?, email = ?, password = ? WHERE personId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, "Kai");
            preparedStatement.setString(2, "Kai@gmail.com");
            preparedStatement.setString(3, "EiEi");
            preparedStatement.setString(4, "11111");
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    public static Connection personConnectionDelete2() {
        String URL = "jdbc:mysql://localhost:3306/dormitorydatabase";
        String username = "root";
        String password = "mysql";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    public static void testMetaData() {
        String URL = "jdbc:mysql://localhost:3306/dormitorydatabase";
        String username = "root";
        String password = "mysql";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println("URL " + databaseMetaData.getURL());
            System.out.println("DatabaseProductName " + databaseMetaData.getDatabaseProductName());
            System.out.println("DatabaseProductVersion " + databaseMetaData.getDatabaseProductVersion());
            System.out.println("DriverName " + databaseMetaData.getDriverName());
            System.out.println("DriverVersion " + databaseMetaData.getDriverVersion());
            System.out.println("MajorVersion " + databaseMetaData.getDatabaseMajorVersion());

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
