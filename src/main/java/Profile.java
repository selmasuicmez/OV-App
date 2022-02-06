

public class Profile {
    private String name;
    private String password;
    private TravelHistoryList travelHistoryList;
    private FavoriteTravelList favoriteTravelList;


    public Profile(String name, String password) {
        this.name = name;
        this.password = password;
        this.travelHistoryList = new TravelHistoryList();
        this.favoriteTravelList = new FavoriteTravelList();
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public TravelHistoryList getTravelHistorylist() {
        return this.travelHistoryList;
    }

    public FavoriteTravelList getFavoriteTravelList() {
        return favoriteTravelList;
    }

}

