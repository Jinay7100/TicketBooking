package org.example.services;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


public class UserBookingService {
    private User user;
    private List<User> userList;
    public static final String Users_Path = "C:\\Workspace\\Java\\TicketBooking\\app\\src\\main\\java\\org\\example\\localDB\\Users.json";

     private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    public void loadUsers() throws IOException {
//        File users = new File(Users_Path);
        userList = objectMapper.readValue(new File(Users_Path), new TypeReference<List<User>>() {});
    }

    public boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashedPassword());
        }).findFirst();
        return  foundUser.isPresent();
    }

    public boolean signUp(User user1){
        try {
            System.out.println("User is: "+user1.getName());
            System.out.println(user1);
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException e){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File users = new File(Users_Path);
        objectMapper.writeValue(users, userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }

//    cancel booking
    public Boolean cancelBooking(String ticketId) throws IOException {
        Stream<Ticket> id_index = user.getTicketsBooked().stream().filter(ticket -> {
            return Objects.equals(ticket.getTicketId(), ticketId);
        });
        user.getTicketsBooked().remove(id_index);
        saveUserListToFile();
        return Boolean.FALSE;
    }

    public List<Train> getTrains(String source, String dest){
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, dest);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }


    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train , int row, int seat) {
    try {
        TrainService trainService = new TrainService();
        List<List<Integer>> seats = train.getSeats();
        if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
            if (seats.get(row).get(seat) == 0) {
                seats.get(row).set(seat, 1);
                train.setSeats(seats);
                trainService.addTrain(train);
                return true; // Booking successful
            } else {
                return false; // Seat is already booked
            }
        } else {
            return false; // Invalid row or seat index
        }
    } catch (IOException e) {
        return Boolean.FALSE;
    }
    }
}
