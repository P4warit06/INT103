package repository.memory;
import repository.PersonRepository;
import domain.Person;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class InMemoryPersonRepository  implements PersonRepository {
    private long NextPersonId = 1;
    private final Map<String, Person> repo;

    public InMemoryPersonRepository() {
        repo = new TreeMap<>();
    }
@Override
    public Person createPerson(String name, String email, String password) {
        if (name == null || email == null || password == null||name.isBlank() || email.isBlank() || password.isBlank()) return null;
        String id = String.format("C%010d", NextPersonId);
        if (repo.containsKey(id)) return null;
        Person person = new Person(id, name, email, password, 20000) ;
        repo.put(id, person) ;
        ++NextPersonId;
        return person;
    }

    @Override
    public Person retrievePerson(String name ) {
        if (name == null || name.isBlank()) return null;
        return repo.get(name);
    }

    @Override
    public Person loginPerson(String email, String password){
        if (email == null || password == null || email.isBlank() || password.isBlank()) return null;
        for (Person person : repo.values()) {
            if (person.getEmail().equals(email) && person.getPassword().equals(password)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public boolean updatePerson(Person person) {
        if (person == null) return false;
        repo.replace(person.getPersonId(), person);
        return true;
    }

    @Override
    public boolean deletePerson(Person person) {
        if (person  == null) return false;
        return repo.remove(person.getPersonId() , person) ;
    }

    @Override
    public Stream<Person> stream() {
        return repo.values().stream();
    }
}
