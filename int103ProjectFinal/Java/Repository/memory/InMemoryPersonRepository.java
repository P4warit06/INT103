package Repository.memory;
import Repository.PersonRepository;
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
        Person person = new Person(id, name, email, password) ;
        repo.put(id, person) ;
        ++NextPersonId;
        return person;
    }

    @Override
    public Person retrievePerson(String id) {
        if (id == null || id.isBlank()) return null;
        return repo.get(id);
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
