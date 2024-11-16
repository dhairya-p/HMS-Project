package entity;

import java.time.LocalDateTime;

public class Availability {
    private String doctorID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Availability(String doctorID, LocalDateTime startTime, LocalDateTime endTime) {
        this.doctorID = doctorID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }


    @Override
    public String toString() {
        return String.format("Availability[DoctorID=%s, Start=%s, End=%s]",
                doctorID, startTime, endTime);
    }

}
