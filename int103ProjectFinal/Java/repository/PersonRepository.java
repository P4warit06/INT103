package repository;

import domain.Person;

import java.sql.SQLException;
import java.util.stream.Stream;

public interface PersonRepository {
    Person createPerson(String name, String email, String password) ;
    Person retrievePerson(String id);
    Person loginPerson(String email, String password);
    boolean updatePerson(Person person,String id);
    boolean deletePerson (Person person,String id) ;
    Stream<Person> stream();

}

