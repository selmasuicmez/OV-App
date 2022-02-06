public class Station {
    private String name;
    private int timeToNextStation;
    private double distanceToNextStation;

    public Station (String name, int timeToNextStation, double distanceToNextStation) {
        this.name = name;
        this.timeToNextStation = timeToNextStation;
        this.distanceToNextStation = distanceToNextStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    public int getTimeToNextStation() {
        return timeToNextStation;
    }
    public void setTimeToNextStation(int newTime) {
        timeToNextStation = newTime;
    }

    public double getDistanceToNextStation() {
        return distanceToNextStation;
    }

    public void setDistanceToNextStation(double newDistance){
        distanceToNextStation = newDistance;
    }

}
