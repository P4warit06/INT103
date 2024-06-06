package Repository.memory;
import Repository.PersonRepository;
import domain.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class InMemoryPersonRepository  implements PersonRepository {

    private static long NextPersonId = 1L ;
    private final Map<String,Person> repository = new HashMap<>();

    public InMemoryPersonRepository() {
    }

    public Person Create(String id , String name , String email , String password ) {
        id = String.format("C%010d", NextPersonId);
        if (this.repository.containsKey(id) ){
            return null;
        } else {
            Person person = new Person(id, name,email,password);
            this.repository.put(id, person);
            ++NextPersonId;
            return person;
        }
    }

    public boolean Update(Person customer) {
        if (customer == null) {
            return false;
        } else {
            this.repository.replace(customer.getPersonId(), customer);
            return true;
        }
    }
    public Person Retrieve(String id) {
        return id == null ? null : (Person) this.repository.get(id);
    }
    public Stream<Person> stream() {
        return this.repository.values().stream();
    }


}
