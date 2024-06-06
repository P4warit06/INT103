package Repository.memory;
import Repository.PersonRepository;
import domain.Person;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPersonRepository  implements PersonRepository {

    private long NextPersonId = 1L ;
    private final Map<String,Person> repository = new HashMap<>();

    public InMemoryPersonRepository() {
    }

    public Person Create(String name) {
        String id = String.format("C%010d", this.NextPersonId);
        if (this.repository.containsKey(id)) {
            return null;
        } else {
            Person person = new Person(id, name);
            this.repository.put(id, Person);
            ++this.NextPersonId;
            return person;
        }
    }

    public boolean update(Person customer) {
        if (customer == null) {
            return false;
        } else {
            this.repo.replace(customer.getId(), customer);
            return true;
        }
    }

    public Customer retrieve(String id) {
        return id == null ? null : (Customer)this.repo.get(id);
    }

}
