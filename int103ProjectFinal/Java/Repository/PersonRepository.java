package Repository;

import domain.Person;

import java.util.stream.Stream;

public interface PersonRepository {
    Person Retrieve(String var2);
    Person Create(String var1, String var2 ,String var3 , String var4 );
    boolean Update(Person var2);
    Stream<Person> stream();
}

