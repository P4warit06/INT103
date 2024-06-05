package Domain;

import java.io.Serializable;

public class Room implements Comparable<Room>  , Serializable {
    private final String roomNumber;
    private final String type;
    private final int capacity;
    private final double Price;
    private final String amenities;
    private final String availability;

    public Room(String roomNumber, String type, double price, String amenities, String availability , User userid ) {
        if (roomNumber != null & roomNumber.isBlank() && userid != null ) {
           this.roomNumber = roomNumber;
           this.availability = availability ;
           this.capacity = 0 ;
       }
        else{
            throw new InvalidRoomException();
        }
        this.type = type;
        Price = price;
        this.amenities = amenities;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public String getType() {
        return type;
    }
    public int getCapacity() {
        return capacity;
    }
    public double getPrice() {
        return Price;
    }
    public String getAmenities() {
        return amenities;
    }
    public String getAvailability() {
        return availability;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", Price=" + Price +
                ", amenities='" + amenities + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }

    public void  Reservation(int RoomId , int Userid ){
        if (){
            this.roomNumber
        }   else {
            throw  new InvalidRoomException();
        }

    }
    @Override
    public int compareTo(Room RoomId) {
        return this.roomNumber.compareTo(RoomId.roomNumber);
    }
}



