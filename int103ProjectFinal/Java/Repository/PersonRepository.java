package Repository;

import domain.Person;

import java.util.stream.Stream;

public interface PersonRepository {
    Person retrieve(String var2);
    Person Create(Person var2);
    boolean update(Person var2);
    Stream<Person> stream();
}

