import java.util.ArrayList;

public class ProfileList {


    private ArrayList<Profile> profileList;

    public ProfileList() {
        this.profileList = new ArrayList<Profile>();
    }

    public ArrayList<Profile> getProfileList() {
        return this.profileList;
    }

    public void addProfile(Profile profile) {
        this.profileList.add(profile);
    }

    public Profile getProfile(int index) {
       return this.profileList.get(index);
    }

}
