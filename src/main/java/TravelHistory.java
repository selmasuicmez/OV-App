import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TravelHistory {
    private String locationA;
    private String locationB;
    private LocalDate travelDate;
    private LocalTime startTravelTime;

    public TravelHistory(String locationA, String locationB) {
        this.locationA = locationA;
        this.locationB = locationB;
        this.travelDate = LocalDate.now();
        this.startTravelTime = LocalTime.now();
    }

    public void setLocationA(String locationA) {
        this.locationA = locationA;
    }

    public String getLocationA() {
        return this.locationA;
    }

    public void setLocationB(String locationB) {
        this.locationB = locationB;
    }

    public String getLocationB() {
        return this.locationB;
    }

    public void setTravelDate() {
        this.travelDate = LocalDate.now();
    }

    public String getTravelDateToString() {
        return this.travelDate.toString();
    }

    public void setStartTravelTime (LocalTime startTravelTime){
        this.startTravelTime = startTravelTime;
    }

    public LocalTime getStartTravelTime() {
        return this.startTravelTime;
    }

    public String getTravelHistoryToString() {



        StringBuilder travelHistoryString = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startTravelTime = getStartTravelTime().format(formatter);

        travelHistoryString.append(getLocationA());
        travelHistoryString.append(" ---> ");
        travelHistoryString.append(getLocationB());
        travelHistoryString.append(" ||| ");
        travelHistoryString.append(startTravelTime);
        travelHistoryString.append(" --- ");
        travelHistoryString.append(getTravelDateToString());
        travelHistoryString.append(" ||| ");

        return travelHistoryString.toString();
    }

}

