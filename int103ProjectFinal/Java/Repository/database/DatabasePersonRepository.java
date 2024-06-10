package repository.database;

import domain.Person;
import repository.PersonRepository;
import java.sql.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DatabasePersonRepository implements PersonRepository {
    private long nextPersonId = 1;
    private final Map<String, Person> repo;
    public DatabasePersonRepository() {
        repo = new TreeMap<>();
    }
    @Override
    public Person createPerson(String name, String email, String password)  {
        if (name == null || email == null || password == null||name.isBlank() || email.isBlank() || password.isBlank())
            return null;
        Connection con = DatabaseConnection.connect();
        String sql = "insert into person(name,email,password,balance) values(?,?,?,?)";
        String personalId = null;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            preparedStatement.setInt(4,20000);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                int newId = rs.getInt(1);
                personalId = String.valueOf(newId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        Person person = new Person(personalId, name, email, password, 20000.00);
        repo.put(personalId, person);
        nextPersonId++;
        return person;
    }

    @Override
    public Person retrievePerson(String id) {
        if (id == null || id.isBlank()) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM person WHERE personID = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                System.out.println(results.getString(1)
                        + " " + results.getString(2)
                        + " " + results.getString(3)
                        + " " + results.getString(4)
                        + " " + results.getString(5)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repo.get(id);
    }

    @Override
    public Person loginPerson(String email, String password) {
        if (email == null || password == null || email.isBlank() || password.isBlank()) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM person WHERE email = ? AND password = ?";
        Person person = null;
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                System.out.println(results.getString(1)
                        + " " + results.getString(2)
                        + " " + results.getString(3)
                        + " " + results.getString(4)
                        + " " + results.getString(5)
                );
                person = new Person(results.getString(1), results.getString(2),
                        results.getString(3), results.getString(4),
                        results.getDouble(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return person;
    }
    @Override
    public boolean updatePerson(Person person) {
        if (person == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "UPDATE person SET name = ?, email = ?, password = ?, balance = ? where personID = ? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,person.getName());
            preparedStatement.setString(2,person.getEmail());
            preparedStatement.setString(3,person.getPassword());
            preparedStatement.setDouble(4,person.getBalance());
            preparedStatement.setInt(5, Integer.parseInt(person.getPersonId()));
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        if (person  == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "DELETE FROM person WHERE personID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,person.getPersonId());
            preparedStatement.executeUpdate();
            return repo.remove(person.getPersonId() , person) ;
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Stream<Person> stream() {
        Connection con = DatabaseConnection.connect();
        String sql = "SELECT * FROM person";
        try {
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repo.values().stream();


    }
}