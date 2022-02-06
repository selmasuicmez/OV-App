public class FavoriteTravel {
    private String favoriteLocationA;
    private String favoriteLocationB;

    FavoriteTravel(String locationA, String locationB) {
        this.favoriteLocationA = locationA;
        this.favoriteLocationB = locationB;
    }

    public void setFavoriteLocationA(String locationA) {
        this.favoriteLocationA = locationA;
    }

    public String getFavoriteLocationA() {
        return this.favoriteLocationA;
    }


    public void setFavoriteLocationB(String locationB) {
        this.favoriteLocationB = locationB;
    }

    public String getFavoriteLocationB() {
        return this.favoriteLocationB;
    }

    public String getFavoriteTravelToString() {
        StringBuilder favoriteTravelString = new StringBuilder();

        favoriteTravelString.append(getFavoriteLocationA());
        favoriteTravelString.append(" --- ");
        favoriteTravelString.append(getFavoriteLocationB());

        return favoriteTravelString.toString();
    }

}
