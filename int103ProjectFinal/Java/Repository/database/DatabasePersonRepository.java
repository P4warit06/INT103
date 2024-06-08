package repository.database;

import connection.TestPersonConnection;
import domain.Person;
import exception.InvalidPersonFormatException;
import repository.PersonRepository;

import java.sql.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DatabasePersonRepository implements PersonRepository {
    private long nextPersonId = 1;
    private Map<String, Person> repo;

    public static Connection DatabasePersonConnection() {
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

    @Override
    public Person createPerson(String name, String email, String password) {
        if (name == null || name.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) return null;
        String insertQuery = "INSERT INTO person (name, email, password, balance) VALUES (?, ?, ?, 20000.00)";
        String personId = "PersonId" + nextPersonId++;
        if (repo.containsKey(personId)) {return null;};
        Person person = new Person(personId, name, email, password, 20000.00);
        try(Connection connection = DatabasePersonConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, person.getPersonId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setString(4, person.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return person;
    }

    @Override
    public Person retrievePerson(String id) {
        if (id == null || id.isBlank()) return null;
        String selectQuery = "SELECT * FROM person WHERE personId = ?";
        try(Connection connection = DatabasePersonConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repo.get(id);
    }

    @Override
    public Person loginPerson(String email, String password) {
        return null;
    }

    @Override
    public boolean updatePerson(Person person) {
        if (person == null) return false;
        String deleteQuery = "DELETE FROM person WHERE personId = ?";
        try(Connection connection = DatabasePersonConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, person.getPersonId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean deletePerson(Person person) {
        if (person == null) return false;
        String deleteQuery = "DELETE FROM person WHERE personId = ?";
        try(Connection connection = DatabasePersonConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, person.getPersonId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestPersonConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public Stream<Person> stream() {
        return Stream.empty();
    }
}
