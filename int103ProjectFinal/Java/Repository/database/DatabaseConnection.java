package repository.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    public static Connection connect(){
//        SIT MySQL
        String URL = "jdbc:mysql://localhost:3306/mydb3";
        String Username = "root";
        String Password = "mysql@sit";
//        Wee's MySQL
        String url = "jdbc:mysql://127.0.0.1:3306/login_schema";
        String username = "root";
        String password = "root";
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

//import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//public class MyJDBC {
//
//     public static void main(String[] args) throws ClassNotFoundException {
//         String url = "jdbc:mysql://127.0.0.1:3306/login_schema";
//         String username = "root";
//         String password = "root";
//         try {
//             Class.forName("com.mysql.cj.jdbc.Driver");
//             System.out.println("Driver Loaded");
//             Connection connection = DriverManager.getConnection(url,username,password);
//             System.out.println("Database Connected");
//             Statement statement = connection.createStatement();
//
//             String sql = "SELECT * FROM student";
//             ResultSet results = statement.executeQuery(sql);
//             while(results.next()){
//                 System.out.println(results.getString(1)
//                 + " " + results.getString(2)
//                 + " " + results.getString(3)
//                 + " " + results.getString(4)
//                 + " " + results.getString(5)
//                 );
//             }
////             String sql2 = "INSERT INTO student(studentID,firstname,lastname,email,deptID) " +
////                     "VALUES('111111','AAAA','BBBB','aaaa@gmail.com','IT')";
////             statement.executeUpdate(sql2);
//
//             String sql3 = "UPDATE student SET email='aaaa@kmutt.ac.th' WHERE studentID = '111111'";
//             statement.executeUpdate(sql3);
//
//         } catch (ClassNotFoundException | SQLException e){
//             Logger.getLogger(MyJDBC.class.getName()).log(Level.SEVERE,null,e);
//         }
//
//     }
//
//
//
//}