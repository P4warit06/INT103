package Repository;

import domain.Person;
import domain.Room;

import java.util.stream.Stream;

public interface PersonRepository {

    Person createPerson(String name, String email, String password);
    Person retrievePerson(String name);
    boolean updatePerson(Person person);
    boolean deletePerson (Person person) ;
    Stream<Person> stream();

}

