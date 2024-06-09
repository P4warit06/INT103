package testDomain;

import domain.Person;
import repository.PersonRepository;
import repository.database.DatabasePersonRepository;

public class TestDatabase {
    public static void main(String[] args) {
        PersonRepository repository = new DatabasePersonRepository();
        System.out.println("Create Person ------------------------------------------");
        Person newPerson = repository.createPerson("John Doe", "john.doe@example.com", "password123");
        System.out.println("Created person: " + newPerson);
        Person newPerson2 = repository.createPerson("Johns Doe", "johns.doe@example.com", "password1234");
        System.out.println("Created person: " + newPerson2);
        Person newPerson3 = repository.createPerson("Dohn Doe", "dohn.doe@example.com", "password12345");
        System.out.println("Created person: " + newPerson);
        Person newPerson4 = repository.createPerson("Johns Joe", "johns.joe@example.com", "password123456");
        System.out.println("Created person: " + newPerson2);
        Person newPerson5= repository.createPerson("Dohns Doe", "dohns.doe@example.com", "password123567");
        System.out.println("Created person: " + newPerson);
        Person newPerson6 = repository.createPerson("Johns DoeS", "johns.does@example.com", "password12345678");
        System.out.println("Created person: " + newPerson2);
        System.out.println("");

        System.out.println("Retrieve Person --------------------------------------------------------");
        Person retrievedPerson = repository.retrievePerson("1");
        Person retrievedPerson2 = repository.retrievePerson("3");
        System.out.println();

        System.out.println("Log In -------------------------------------------------------------------");
        Person loggedInPerson = repository.loginPerson("john.doe@example.com", "password123");
        System.out.println();

        System.out.println("Update ------------------------------------------------------------------------");
        System.out.println("Present Name : " + newPerson.getName());
        System.out.println("Present Email : " + newPerson.getEmail());
            newPerson.reName("HOME HALAND");
            newPerson.reEmail("home.haland@example.com");
        System.out.println("New name: " + newPerson.getName());
        System.out.println("New Email: " + newPerson.getEmail());
          repository.updatePerson(newPerson);
            Person updatedPerson = repository.retrievePerson("1");
            System.out.println("Updated retrieved person: " + updatedPerson);

        System.out.println();
        System.out.println("Stream ---------------------------------");
        System.out.println("ID PERSON1: " + newPerson.getPersonId());
        boolean deleted = repository.deletePerson(newPerson);
        if (deleted) {
            System.out.println("Person deleted successfully.");
        } else {
            System.out.println("Failed to delete person.");
        }

        System.out.println("Persons in repository:");
        repository.stream().forEach(System.out::println);
    }
}