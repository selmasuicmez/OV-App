import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FunctionsToUiProvider {
    public RouteData routeData;
    public JXBrowser jxBrowser;
    public ResourceBundle messages;
    public ProfileList profileList;

    private Profile selectedProfile;
    private int profileIndex;

    private String fNameTrajectoryData = "src/TrajectoryData.json";
    private String fNameProfileData = "src/ProfileData.json";


    public FunctionsToUiProvider() throws IOException {
        routeData = new RouteData();
        //DataHandler.createTrajectoryDataJSONFile(fNameTrajectoryData);

        this.routeData.setTrajectoryList(DataHandler.loadTrajectoryData(fNameTrajectoryData));
        this.profileList = DataHandler.loadProfileList(fNameProfileData);
        this.messages = ResourceBundle.getBundle("MessagesBundle");
        this.jxBrowser = new JXBrowser();
        this.profileIndex = 0;

    }

    public boolean ifProfileJsonFileExists() {
        File profileFile = new File(fNameProfileData);
        return profileFile.exists();
    }

    public boolean ifTrajectoryDataJsonFileExists() {
        File trajectoryFile = new File(fNameTrajectoryData);
        return trajectoryFile.exists();
    }

    public boolean checkIfProfileExists(String name, String password) {
        for (Profile profile : profileList.getProfileList()) {
            if (profile.getName().equals(name) && profile.getPassword().equals(password)) {
                return true;
            }
        }
        return false;

//        profileList = DataHandler.loadProfileList();
    }

    //Saves selected profile
    public void setSelectedProfile(Profile selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

    // fetches selected profile
    public Profile getSelectedProfile() {
        return this.selectedProfile;
    }

    public void saveLoggedInProfile(String name, String password) {

        for (Profile profile : profileList.getProfileList()) {

            if (profile.getName().equals(name) && profile.getPassword().equals(password)) {
                profileIndex = profileList.getProfileList().indexOf(profile);
                setSelectedProfile(profile);
            }
        }
    }

    public boolean isLoggedIn(){
        if (selectedProfile != null){
            return true;
        } else{
            return false;
        }
    }
    public ArrayList getTravelHistoryListSelectedProfile() {
        if (selectedProfile == null) {
            ArrayList<String> noTravelHistoryList = new ArrayList<String>();
            noTravelHistoryList.add("No travel History, please login with your account to view your travel history.");

            return noTravelHistoryList;

        } else if (selectedProfile.getTravelHistorylist().getTravelHistoryListing().isEmpty()) {

            ArrayList<String> noTravelHistory = new ArrayList<String>();

            noTravelHistory.add("No travel History, please navigate with the application.");

            return (noTravelHistory);
        }

        return selectedProfile.getTravelHistorylist().getTravelHistoryListToString();
    }

    public void addTravelHistoryListSelectedProfile() {
        TravelHistory travelHistory = new TravelHistory(routeData.getLocationA(), routeData.getLocationB());


        profileList.getProfileList().get(profileIndex).getTravelHistorylist().addTravelHistoryString(travelHistory.getTravelHistoryToString());

        DataHandler.saveProfileList(profileList, fNameProfileData);
        System.out.println("Added new Travel History to profile");
    }

    public ArrayList getFavoriteTravelsListSelectedProfile() {

        if (selectedProfile != null && selectedProfile.getFavoriteTravelList().getFavoriteTravelListing().isEmpty()) {
            ArrayList<String> noFavoriteTravels = new ArrayList<String>();

            noFavoriteTravels.add("No favorite travels to view, please add a favorite to your travels.");
            return noFavoriteTravels;

        } else if (selectedProfile != null) {
            ArrayList<String> favoriteTravelsToStringList = new ArrayList<String>();

            for (FavoriteTravel favoriteTravel : selectedProfile.getFavoriteTravelList().getFavoriteTravelListing()) {
                favoriteTravelsToStringList.add(favoriteTravel.getFavoriteTravelToString());
            }

            return favoriteTravelsToStringList;
        }


        ArrayList<String> noFavoriteTravelsList = new ArrayList<String>();
        noFavoriteTravelsList.add("No travel favorite travels, please login and add a travel to your favorite travels.");

        return noFavoriteTravelsList;
    }

    public void addFavoriteTravelSelectedProfile(String locationA, String locationB) {
        if (selectedProfile != null) {


            boolean alreadyExists = false;


            for (FavoriteTravel favoriteTravelindex : selectedProfile.getFavoriteTravelList().getFavoriteTravelListing()) {
                if (favoriteTravelindex.getFavoriteLocationA().equals(locationA) && favoriteTravelindex.getFavoriteLocationB().equals(locationB)) {
                    alreadyExists = true;
                }
            }

            if (alreadyExists) {
                System.out.println("This travel is already added to favorites");
            } else {
                FavoriteTravel favoriteTravel = new FavoriteTravel(locationA, locationB);

                selectedProfile.getFavoriteTravelList().getFavoriteTravelListing().add(favoriteTravel);

                DataHandler.saveProfileList(profileList, fNameProfileData);
                System.out.println("Added new favorite travel to profile list");
            }


        } else {
            System.out.println("Please login to add a travel to favorites");
        }
    }

    public String fetchFavoriteLocationA(int selectedIndex) {
        String favoriteLocationA = getSelectedProfile().getFavoriteTravelList().getFavoriteTravelListing().get(selectedIndex).getFavoriteLocationA();

        return favoriteLocationA;
    }

    public String fetchFavoriteLocationB(int selectedIndex) {
        String favoriteLocationB = getSelectedProfile().getFavoriteTravelList().getFavoriteTravelListing().get(selectedIndex).getFavoriteLocationB();

        return favoriteLocationB;
    }

    public ArrayList<String> generateRoute(Trajectory selectedTrajectory) {
        ArrayList<String> generatedRoute = new ArrayList<String>();

        int indexA = selectedTrajectory.indexOf(routeData.getLocationA());
        int indexB = selectedTrajectory.indexOf(routeData.getLocationB());


        for (int i = indexA; i <= indexB; i++) {
            generatedRoute.add(selectedTrajectory.getStationName(i));
        }
        return generatedRoute;
    }

    public int calcMinutesToStation() {
        Trajectory selectedTrajectory = routeData.getSelectedTrajectory(); // there is only one trajectory atm so it starts at Utrecht.

        //check if station is in list
        // if it return -1 the station name doesn't exist

        int indexA = selectedTrajectory.indexOf(routeData.getLocationA());
        int indexB = selectedTrajectory.indexOf(routeData.getLocationB());

        //we start at 0 so the program can just go through the list and add the distances
        int totalTime = 0;
        for (int i = indexA; i < indexB; i++) {
            totalTime = totalTime + selectedTrajectory.getTimeToNextStation(i);
        }

        return totalTime;

    }

    public double calcDistanceToStation() {
        Trajectory selectedTrajectory = routeData.getSelectedTrajectory(); // there is only one trajectory atm so it starts at Utrecht.

        //check if station is in list
        // if it return -1 the station name doesn't exist

        int indexA = selectedTrajectory.indexOf(routeData.getLocationA());
        int indexB = selectedTrajectory.indexOf(routeData.getLocationB());


        //we start at 0 so the program can just go through the list and add the distances
        double totalDistance = 0.0;
        for (int i = indexA; i < indexB; i++) {
            totalDistance = totalDistance + selectedTrajectory.getDistanceToNextStation(i);
        }

        return totalDistance;
    }

    public ArrayList<String> getArrivalAndDepartureTimes(int listLenght, Trajectory trajectory, String locationA, String locationB) {
        ArrayList<LocalTime> departureTimes = generateListArrivalTimesAtLocation(listLenght, trajectory, locationA);
        ArrayList<LocalTime> arrivalTimes = generateListArrivalTimesAtLocation(listLenght, trajectory, locationB);

        ArrayList<String> listArrivalTimesDepartureTimes = new ArrayList<String>();

        for (int i = 0; i < listLenght; i++) {
            String stringDepartureTime = departureTimes.get(i).toString();
            String stringArrivalTime = arrivalTimes.get(i).toString();

            listArrivalTimesDepartureTimes.add(stringDepartureTime + "-" + stringArrivalTime);
        }
        return listArrivalTimesDepartureTimes;
    }

    private ArrayList<LocalTime> generateListArrivalTimesAtLocation(int listLength, Trajectory trajectory, String location) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        int possibleIncrementsBetweenDepartureTimes = trajectory.getIncrements();
        int firstDepartureTime = trajectory.getFirstDepartureTime();

        ArrayList<Station> stationListTrajectory = trajectory.getStationList();
        for (int i = 0; i < trajectory.indexOf(location); i++) {
            firstDepartureTime = firstDepartureTime + stationListTrajectory.get(i).getTimeToNextStation();
            // refactor this code

        }
        // bug: fix that the firstDepartureTime can over 60

        //get local time
        LocalTime currentTime = LocalTime.parse("00:00", formatter).now();


        String[] currentTimeSplit = currentTime.toString().split(":");
        int currentMinutes = Integer.parseInt(currentTimeSplit[1]);
        int currentHours = Integer.parseInt(currentTimeSplit[0]);

        int departureHour = currentHours;

        // calculate closest departure time
        int departureMinute = firstDepartureTime;
        while (!(currentMinutes <= departureMinute)) {
            departureMinute = departureMinute + possibleIncrementsBetweenDepartureTimes;
        }


        // if departure time is over 60 minutes it converts it to a new hour with the current departure time
        while (departureMinute > 59) {
            departureMinute = departureMinute - 60;
            departureHour = departureHour + 1;

        }

        System.out.println(departureHour + " " + departureMinute);
        //if departure time is 24 hour it converts it to 00 hours midnight in the string
        String departureTimeString = generateTimeNumber(departureHour, true) + ":" + generateTimeNumber(departureMinute, false);
        System.out.println(departureTimeString);


        // generates list of possible departure times or arrival times, it depends on the locationA-B
        LocalTime departureTime = LocalTime.parse(departureTimeString, formatter);
        ArrayList<LocalTime> listDepartureTimes = new ArrayList<LocalTime>();
        for (int i = 0; i < listLength; i++) {
            listDepartureTimes.add(departureTime);
            departureTime = departureTime.plusMinutes(possibleIncrementsBetweenDepartureTimes);
        }

        return listDepartureTimes;
    }

    private String generateTimeNumber(int number, boolean hour) {
        String returnNumber;
        if (number < 10) {
            return ("0" + number);
        }

        if (hour == true && number == 24) {
            return ("00");
        }
        return String.valueOf(number);
    }

    // filters the right trajectory out that the user needs to navigate and sends it to the gui
    public Trajectory fetchRightTrajectory() {
        String locationA = routeData.getLocationA();
        String locationB = routeData.getLocationB();
        String vehicleID = routeData.getVehicleIdentifier();

        ArrayList<Trajectory> trajectories = routeData.getTrajectoryList();

        for (Trajectory trajectory : trajectories) {
            if (trajectory.getVehicleIdentifier().equals(vehicleID)) {

                if (trajectory.getStops().contains(locationA) && trajectory.getStops().contains(locationB)) {
                    return trajectory;
                }
            }
        }
        return null;
    }

    // validates if fetched trajectory exists or is possible
    public boolean validateTrajectory(Trajectory trajectory) {
        String locationA = routeData.getLocationA();
        String locationB = routeData.getLocationB();

        if (trajectory.indexOf(locationA) < trajectory.indexOf(locationB)) {
            return true;
        } else {
            return false;
        }
    }


}
