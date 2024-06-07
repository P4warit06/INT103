package Repository.file;

import Repository.*;

import domain.Person;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FilePersonRepository implements PersonRepository {
    private String filename = "person.dat";
    private long nextPersonId;
    private Map<String, Person> repo;

    public FilePersonRepository() {
        File filePerson = new File(filename);
        if (filePerson.exists()) {
            try(FileInputStream fi = new FileInputStream(filePerson);
                BufferedInputStream bi = new BufferedInputStream(fi);
                ObjectInputStream oi = new ObjectInputStream(bi)) {
                nextPersonId = oi.readLong();
                repo = (Map<String, Person>) oi.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                nextPersonId = 1;
                repo = new TreeMap<>();
            }
        } else {
            nextPersonId = 1;
            repo = new TreeMap<>();
        }
    }

    @Override
    public Person createPerson(String name, String email, String password) {
        if (name == null || name.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) return null;
        String personId = "PersonId: " + ++nextPersonId;
        if (repo.containsKey(personId)) return null;
        Person person = new Person(personId, name, email, password);
        repo.put(personId, person);
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
        if (person == null) return false;
        repo.remove(person.getPersonId(), person);
        return true;
    }

    @Override
    public Stream<Person> stream() {
        return Stream.empty();
    }
}
