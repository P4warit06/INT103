package Repository.file;

import Repository.PersonRepository;

import domain.Person;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FilePersonRepository implements PersonRepository {
    private String filename = "person.dat";
    private long nextPersonId;
    private Map<String, Person> repo;

    public FilePersonRepository() {
        try(FileInputStream fi = new FileInputStream(filename);
            BufferedInputStream bi = new BufferedInputStream(fi);
            ObjectInputStream oi = new ObjectInputStream(bi)) {
            int i;
            while ((i = oi.read()) != 1) {
                System.out.println("Output: " + i);
            }
        } catch (Exception e) {
            nextPersonId = 1;
            repo = new TreeMap<>();
            e.printStackTrace();
        }
    }

    @Override
    public Person createPerson(String name, String email, String password) {
        if (name == null || name.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) throw new UnsupportedOperationException();
        String personId = "PersonId: " + ++nextPersonId;
        Person person = new Person(personId, name, email, password);
        try(FileOutputStream fo = new FileOutputStream(filename);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeObject(person);
            repo.put(personId, person);
            return person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person retrievePerson(String id) {
        if (id == null || id.isBlank()) throw new UnsupportedOperationException("Error");
        try(FileOutputStream fo = new FileOutputStream(filename);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeObject(repo.get(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try(FileInputStream fi = new FileInputStream(filename);
            BufferedInputStream bi = new BufferedInputStream(fi);
            ObjectInputStream oi = new ObjectInputStream(bi)) {
            System.out.println("Output: " + oi.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repo.get(id);
    }

    @Override
    public boolean updatePerson(Person person) {
        if (person == null) throw new UnsupportedOperationException();
        try(FileOutputStream fo = new FileOutputStream(filename);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeObject(person);
            repo.replace(person.getPersonId(), person);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        if (person == null) throw new UnsupportedOperationException();
        try(FileOutputStream fo = new FileOutputStream(filename);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeObject(repo.remove(person.getPersonId(), person));
            repo.remove(person.getPersonId(), person);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Stream<Person> stream() {
        if (repo == null) throw new UnsupportedOperationException();
        try(FileOutputStream fo = new FileOutputStream(filename);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeObject(repo.values().stream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try(FileInputStream fi = new FileInputStream(filename);
            BufferedInputStream bi = new BufferedInputStream(fi);
            ObjectInputStream oo = new ObjectInputStream(bi)) {
            int i;
            while ((i = oo.read()) != 1) {
                System.out.println("Output: " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repo.values().stream();
    }
}
