package repository.file;

import repository.ReservationRepository;
import domain.Payment;
import domain.Person;
import domain.Reservation;
import domain.Room;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReservationRepository implements ReservationRepository {
    private String filename = "reservation.dat";
    private long nextReservationId = 0;
    private Map<String, Reservation> repo;

    public FileReservationRepository() {
        File fileReservation = new File(filename);
        if (fileReservation.exists()) {
            try(FileInputStream fi = new FileInputStream(fileReservation);
                BufferedInputStream bi = new BufferedInputStream(fi);
                ObjectInputStream oi = new ObjectInputStream(bi)) {
                nextReservationId = oi.readLong();
                repo = (Map<String, Reservation>) oi.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                nextReservationId = 1;
                repo = new TreeMap<>();
            }
        } else {
            nextReservationId = 1;
            repo = new TreeMap<>();
            writeFile();
        }
    }

    private void writeFile() {
        try(FileOutputStream fo = new FileOutputStream(filename);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeLong(nextReservationId);
            oo.writeObject(repo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation createReservation(Person person, Room room, Room status, LocalDate checkInDate, LocalDate checkOutDate) {
        if (person == null || room == null || status == null || checkInDate == null || checkOutDate == null) return null;
        String number = String.format("A%011d", nextReservationId);
        if (repo.containsKey(number)) return null;
        Reservation reservation = new Reservation(number,person,room,status,checkInDate,checkOutDate);
        repo.put(number,reservation);
        ++nextReservationId;
        return reservation;
    }

    @Override
    public Reservation createReservation(Person person, Room room) {
        return null;
    }

    @Override
    public Reservation retrieveReservation(String number) {
        if (number == null || number.isBlank()) return null;
        return repo.get(number);
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        if (reservation == null) return false;
        repo.replace(reservation.getReservationID(),reservation); // status , id
        return true;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        if (reservation== null) return false;
        repo.remove(reservation.getReservationID(),reservation);
        return true;
    }

    @Override
    public Stream<Reservation> stream() { return repo.values().stream(); }

    @Override
    public List<Reservation> getReservation(String personalId) {
        Collection<Reservation> values = repo.values();
        return values.stream()
                .filter(r -> r.getPerson().getPersonId().equals(personalId))
                .collect(Collectors.toList());
    }
}