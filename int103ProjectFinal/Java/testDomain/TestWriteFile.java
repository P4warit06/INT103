package testDomain;

import Repository.file.FilePersonRepository;
import Repository.file.FileRoomRepository;
import domain.Room;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.TreeMap;

public class TestWriteFile {
    public static void main(String[] args) {
//        testFilePerson();
        testFileRoom();
    }
    public static void testFilePerson() {

    }

    public static void testFileRoom() {
        FileRoomRepository fileRoomRepository = new FileRoomRepository();
        //testFileObject
        var obj1 = fileRoomRepository.createRoom("Fan", "2 people", "don't have", 3000.00);
        var obj2 = fileRoomRepository.createRoom("Fan", "2 people", "don't have", 3000.00);
        System.out.println(fileRoomRepository.retrieveRoom(obj1.getRoomNumber()));
        System.out.println(fileRoomRepository.retrieveRoom(obj2.getRoomNumber()));
        //test delete
        System.out.println("###Test delete###");
        fileRoomRepository.deleteRoom(obj1);
        System.out.println(fileRoomRepository.retrieveRoom(obj1.getRoomNumber()));
        System.out.println(fileRoomRepository.retrieveRoom(obj2.getRoomNumber()));
        //test replace
        System.out.println("###Test replace###");
        var obj3 = fileRoomRepository.createRoom("FanAndAirConditional", "4 people", "have", 6000.00);
        System.out.println(fileRoomRepository.retrieveRoom(obj3.getRoomNumber()));
        obj3.setPrice(10000.00);
        fileRoomRepository.updateRoom(obj3);
        System.out.println(fileRoomRepository.retrieveRoom(obj3.getRoomNumber()));
        //test stream
        System.out.println("###Test stream###");
        System.out.println(fileRoomRepository.stream());
    }
}
