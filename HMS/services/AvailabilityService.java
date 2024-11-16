package services;

import entity.Availability;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityService {
    private List<Availability> availabilityList;

    public AvailabilityService() {
        this.availabilityList = new ArrayList<>();
    }

    public void addAvailability(String doctorID, LocalDateTime startTime, LocalDateTime endTime) {
        availabilityList.add(new Availability(doctorID, startTime, endTime));
        System.out.println("Availability added for DoctorID: " + doctorID);
    }

    public List<Availability> getAvailableSlots(String doctorID) {
        return availabilityList.stream()
                .filter(slot -> slot.getDoctorID().equals(doctorID))
                .collect(Collectors.toList());
    }

    public List<Availability> getAllAvailableSlots() {
        return availabilityList.stream()
                .filter(slot -> slot.getStartTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
    

    public void displayAvailability(String doctorID) {
        List<Availability> slots = getAvailableSlots(doctorID);
        if (slots.isEmpty()) {
            System.out.println("No availability found for DoctorID: " + doctorID);
            return;
        }
        System.out.println("Available Slots for DoctorID: " + doctorID);
        slots.forEach(System.out::println);
    }
    public List<Availability> getAvailableSlotsForDate(LocalDate date) {
    return availabilityList.stream()
            .filter(slot -> slot.getStartTime().toLocalDate().equals(date))
            .collect(Collectors.toList());
    }

}
