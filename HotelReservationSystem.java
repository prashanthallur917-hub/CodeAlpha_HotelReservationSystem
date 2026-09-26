import java.util.ArrayList;
import java.util.Scanner;

class Room {

    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Reservation {

    int reservationId;
    String guestName;
    int roomNumber;
    String category;
    double amount;
    boolean paid;

    Reservation(int reservationId, String guestName, int roomNumber,
                String category, double amount) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amount = amount;
        this.paid = false;
    }
}

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    static int nextReservationId = 1;

    public static void main(String[] args) {

        addRooms();

        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("       HOTEL RESERVATION SYSTEM");
            System.out.println("======================================");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Reservations");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Make Payment");
            System.out.println("6. Exit");
            System.out.println("======================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewAvailableRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    viewReservations();
                    break;

                case 4:
                    cancelReservation();
                    break;

                case 5:
                    makePayment();
                    break;

                case 6:
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);

        sc.close();
    }

    // Add rooms to the hotel
    static void addRooms() {

        rooms.add(new Room(101, "Single", 1500));
        rooms.add(new Room(102, "Single", 1500));

        rooms.add(new Room(201, "Double", 2500));
        rooms.add(new Room(202, "Double", 2500));

        rooms.add(new Room(301, "Deluxe", 4000));
        rooms.add(new Room(302, "Deluxe", 4000));
    }

    // Display available rooms
    static void viewAvailableRooms() {

        System.out.println("\n========== AVAILABLE ROOMS ==========");

        boolean found = false;

        for (Room room : rooms) {

            if (room.available) {

                System.out.println(
                    "Room: " + room.roomNumber +
                    " | Category: " + room.category +
                    " | Price: ₹" + room.price
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
    }

    // Book a room
    static void bookRoom() {

        viewAvailableRooms();

        System.out.print("\nEnter room number to book: ");
        int roomNumber = sc.nextInt();

        Room selectedRoom = null;

        for (Room room : rooms) {

            if (room.roomNumber == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        if (!selectedRoom.available) {
            System.out.println("Sorry, this room is already booked.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter guest name: ");
        String guestName = sc.nextLine();

        Reservation reservation = new Reservation(
            nextReservationId,
            guestName,
            selectedRoom.roomNumber,
            selectedRoom.category,
            selectedRoom.price
        );

        reservations.add(reservation);

        selectedRoom.available = false;

        System.out.println("\nRoom booked successfully!");
        System.out.println("Reservation ID: " + nextReservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Number: " + selectedRoom.roomNumber);
        System.out.println("Category: " + selectedRoom.category);
        System.out.println("Amount: ₹" + selectedRoom.price);

        nextReservationId++;
    }

    // Display reservations
    static void viewReservations() {

        System.out.println("\n========== RESERVATIONS ==========");

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for (Reservation reservation : reservations) {

            System.out.println(
                "Reservation ID: " + reservation.reservationId
            );

            System.out.println(
                "Guest Name: " + reservation.guestName
            );

            System.out.println(
                "Room Number: " + reservation.roomNumber
            );

            System.out.println(
                "Category: " + reservation.category
            );

            System.out.println(
                "Amount: ₹" + reservation.amount
            );

            System.out.println(
                "Payment Status: " +
                (reservation.paid ? "Paid" : "Pending")
            );

            System.out.println("----------------------------------");
        }
    }

    // Cancel reservation
    static void cancelReservation() {

        if (reservations.isEmpty()) {
            System.out.println("No reservations available to cancel.");
            return;
        }

        System.out.print("Enter reservation ID to cancel: ");
        int id = sc.nextInt();

        Reservation selectedReservation = null;

        for (Reservation reservation : reservations) {

            if (reservation.reservationId == id) {
                selectedReservation = reservation;
                break;
            }
        }

        if (selectedReservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        for (Room room : rooms) {

            if (room.roomNumber == selectedReservation.roomNumber) {
                room.available = true;
                break;
            }
        }

        reservations.remove(selectedReservation);

        System.out.println("Reservation cancelled successfully.");
    }

    // Payment simulation
    static void makePayment() {

        if (reservations.isEmpty()) {
            System.out.println("No reservations available.");
            return;
        }

        System.out.print("Enter reservation ID for payment: ");
        int id = sc.nextInt();

        Reservation selectedReservation = null;

        for (Reservation reservation : reservations) {

            if (reservation.reservationId == id) {
                selectedReservation = reservation;
                break;
            }
        }

        if (selectedReservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        if (selectedReservation.paid) {
            System.out.println("Payment has already been completed.");
            return;
        }

        System.out.println("\nAmount to pay: ₹" + selectedReservation.amount);

        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. UPI");

        System.out.print("Select payment method: ");
        int paymentMethod = sc.nextInt();

        if (paymentMethod >= 1 && paymentMethod <= 3) {

            selectedReservation.paid = true;

            System.out.println("Payment successful!");

        } else {

            System.out.println("Invalid payment method.");
        }
    }
}