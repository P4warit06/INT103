package Repository.file;

import Repository.*;

import domain.Person;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FilePersonRepository implements PersonRepository {

    @Override
    public Person createPerson(String name, String email, String password) {
        return null;
    }

    @Override
    public Person retrievePerson(String id) {
        return null;
    }

    @Override
    public boolean updatePerson(Person person) {
        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        return false;
    }

    @Override
    public Stream<Person> stream() {
        return Stream.empty();
    }
}
