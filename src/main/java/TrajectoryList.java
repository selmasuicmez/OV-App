import java.util.ArrayList;

public class TrajectoryList {
    private ArrayList<Trajectory> trajectoryList;

    public TrajectoryList(){
        this.trajectoryList = new ArrayList<Trajectory>();
    }

    public ArrayList<Trajectory> getTrajectoryList() {
        return trajectoryList;
    }

    public void setTrajectoryList(ArrayList<Trajectory> trajectoryList) {
        this.trajectoryList = trajectoryList;
    }
}
