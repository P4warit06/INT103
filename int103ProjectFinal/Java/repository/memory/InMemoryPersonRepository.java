package repository.memory;
import repository.PersonRepository;
import domain.Person;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class InMemoryPersonRepository  implements PersonRepository {
    private long NextPersonId = 1L;
    private final Map<String, Person> repository  ;

    public InMemoryPersonRepository() {
        repository = new TreeMap<>();
    }
@Override
    public Person createPerson( String name, String email, String password) {
        if (name == null || email == null || password == null||name.isBlank()||email.isBlank()||password.isBlank())
            return null;
        String id = String.format("C%010d", NextPersonId);
        if (repository.containsKey(id))
            return null;
        Person person = new Person(id, name, email, password) ;
        repository.put(id, person) ;
        ++NextPersonId;
        return person;
    }

    @Override
    public Person retrievePerson(String name ) {
        if (name == null || name.isBlank()) return null;
        return repository.get(name);
    }
    @Override
    public boolean updatePerson(Person person) {
        if (person == null) return false;
        repository.replace(person.getPersonId(), person);
        return true;
    }

    @Override
    public boolean deletePerson(Person person) {
        if (person  == null) return false;
        return repository.remove(person.getPersonId() , person) ;
    }

    @Override
    public Stream<Person> stream() {
        return repository.values().stream();
    }


}
