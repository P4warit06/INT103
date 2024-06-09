package repository.database;

import domain.Person;
import org.w3c.dom.ls.LSOutput;
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
        if (name == null || email == null || password == null||name.isBlank() || email.isBlank() || password.isBlank()) return null;
        String personId = "PersonId: " + nextPersonId;
        if (repo.containsKey(personId)) return null;
        Connection con = DatabaseConnection.connect();
        String sql = "insert into person(personID,name,email,password,balance) values(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1,nextPersonId);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,password);
            preparedStatement.setInt(5,20000);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        Person person = new Person(personId, name, email, password, 20000.00);
        repo.put(personId, person);
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
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Person person : repo.values()) {
            if (person.getEmail().equals(email) && person.getPassword().equals(password)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public boolean updatePerson(Person person,String id) {
        if (person == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "UPDATE person SET name = ?, email = ?, password = ? WHERE personID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,person.getName());
            preparedStatement.setString(2,person.getEmail());
            preparedStatement.setString(3,person.getPassword());
            preparedStatement.setString(4,id);
            preparedStatement.executeUpdate();
            repo.replace(person.getPersonId(), person);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean deletePerson(Person person,String id) {
        if (person  == null) return false;
        Connection con = DatabaseConnection.connect();
        String sql = "DELETE FROM person WHERE personID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,id);
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
