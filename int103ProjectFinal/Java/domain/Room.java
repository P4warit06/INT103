package domain;

import exception.InvalidRoomFormatException;

public class Room {
    private final String roomNumber;
    private String type;
    private String capacity;
    private String amenities;
    private boolean available;
    private double price;
    public Room(String roomNumber, String type, String capacity, String amenities, double price) {
        if (roomNumber == null || roomNumber.isBlank() || type == null || type.isBlank() || capacity == null
                || capacity.isBlank() || amenities == null || amenities.isBlank() || price < 0.0)  throw new InvalidRoomFormatException();
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.amenities = amenities;
        this.price = price;
        this.available = true;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || type.isBlank()) throw new InvalidRoomFormatException();
        this.type = type;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        if (capacity == null || capacity.isBlank()) throw new InvalidRoomFormatException();
        this.capacity = capacity;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        if (amenities == null || amenities.isBlank()) throw new InvalidRoomFormatException();
        this.amenities = amenities;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0.0) throw new InvalidRoomFormatException();
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Room: (RoomNumber:%s, Type:%s, Capacity:%s, Amenities:%s, Price:%.2f)", roomNumber, type, capacity, amenities, price);
    }
}
