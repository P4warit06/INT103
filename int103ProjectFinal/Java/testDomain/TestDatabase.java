package testDomain;

import repository.database.DatabasePersonRepository;

public class TestDatabase {
    public static void main(String[] args) {
        testDatabasePerson();
    }
    public static void testDatabasePerson() {
        DatabasePersonRepository databasePersonRepository = new DatabasePersonRepository();
        var obj1 = databasePersonRepository.createPerson("Guy", "Guy@gmail.com", "1150");
        System.out.println(databasePersonRepository.retrievePerson("1"));


    }
}
