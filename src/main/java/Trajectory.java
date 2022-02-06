import java.util.ArrayList;

public class Trajectory {
    private ArrayList<Station> stationList;
    private int increments; // is how often the first train goes from the first station for example: 30


    //each half hour or 15 minutes the train departures so we need the time when it first departs so the time can be calculated further
    int firstDepartureTime;

    String vehicleIdentifier;

    public Trajectory(ArrayList<Station> stationList, int increments, int firstDepartureTime, String vehicleIdentifier) {
        this.stationList = stationList;
        this.increments = increments;
        this.firstDepartureTime = firstDepartureTime;
        this.vehicleIdentifier = vehicleIdentifier;
    }

    public ArrayList<Station> getStationList() {
        return stationList;
    }

    public void setStationList(ArrayList<Station> newStationList) {
        this.stationList = newStationList;
    }

    public int indexOf(String station) {

        for (int i = 0; i < stationList.size(); i++) {
            Station selStation = stationList.get(i);
            if (selStation.getName().equals(station)) {
                return i;
            }
        }
        return -1;

    }

    public double getDistanceToNextStation(int index) {
        Station selStation = stationList.get(index);
        return selStation.getDistanceToNextStation();


    }

    public String getStationName(int index) {
        Station selStation = stationList.get(index);
        return selStation.getName();

    }

    public double getDistanceToPreviousStation(int index) {
        try {
            Station selStation = stationList.get(index - 1);
            return selStation.getDistanceToNextStation();
        } catch (Exception e) {
            return 0;
        }


    }

    public int getTimeToNextStation(int index) {
        Station selStation = stationList.get(index);
        return selStation.getTimeToNextStation();


    }

    public int getTimePreviousStation(int index) {
        try {
            Station selStation = stationList.get(index - 1);
            return selStation.getTimeToNextStation();

        } catch (Exception e) {
            return 0;
        }


    }

    public int getIncrements() {
        return increments;
    }

    public int getFirstDepartureTime() {
        return firstDepartureTime;
    }

    public String getVehicleIdentifier() {
        return vehicleIdentifier;
    }

    public boolean isStationInTrajectory(String station) {
        for (Station selectedStation : stationList) {
            if (station.equals(selectedStation.getName())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getStops() {
        ArrayList<String> stops = new ArrayList<>();

        for (Station station : stationList) {
            stops.add(station.getName());
        }
        return stops;
    }
}

