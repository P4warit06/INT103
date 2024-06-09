package repository.file;

import repository.*;

import domain.Person;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FilePersonRepository implements PersonRepository {
        private String filename = "person.dat";
        private long nextPersonId = 0;
        private Map<String, Person> repo;

        public FilePersonRepository() {
            File filePerson = new File(filename);
            if (filePerson.exists()) {
                try (FileInputStream fi = new FileInputStream(filePerson);
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
            if (name == null || name.isBlank() || email == null || email.isBlank() || password == null || password.isBlank())
                return null;
            String personId = "PersonId: " + nextPersonId++;
            if (repo.containsKey(personId)) return null;
            Person person = new Person(personId, name, email, password, 20000);
            repo.put(personId, person);
            return person;
        }

        @Override
        public Person retrievePerson(String id) {
            if (id == null || id.isBlank()) return null;
            return repo.get(id);
        }

    @Override
    public Person loginPerson(String email, String password) {
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
            if (person == null) return false;
            repo.remove(person.getPersonId(), person);
            return true;
        }

        @Override
        public Stream<Person> stream() {
            return repo.values()
                    .stream()
                    .filter(Objects::nonNull);
        }
    }
