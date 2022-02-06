import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;


public class DataHandler {

    public static ProfileList loadProfileList(String fName) {
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(fName);

            ProfileList profileList = gson.fromJson(reader, ProfileList.class);

            reader.close();

            System.out.println("Profile list has loaded");

            return profileList;
        } catch (Exception e) {

        }
        return null;
    }

    public static void saveProfileList(ProfileList profileList, String fName) {
        try {
            Writer writer = new FileWriter(fName);
            Gson gson = new Gson();
            // 1. Java object to JSON file
            gson.toJson(profileList, writer);
            writer.flush(); //flush data to file

            System.out.println("profile list has exported to json");//   <---
        } catch (Exception e) {


        }
    }



//        //ProfileList profileList = DataHandler.loadProfileList();
//        ProfileList profileList = new ProfileList();
//        Profile profile1 = new Profile("Hichem", "123");
//        Profile profile2 = new Profile("Hichem", "123");
//        Profile profile3 = new Profile("Hichem", "123");
//
//        profileList.addProfile(profile1);
//        profileList.addProfile(profile2);
//        profileList.addProfile(profile3);
//
//
//        DataHandler.saveTestProfileList(profileList, "/home/yawgmoth/Dropbox/HU-ADSD/S1/Periode1/OV-app/src/test.json");
//    }

    ///////////////////
    //  routedata   //
    //////////////////
    public static ArrayList<Trajectory> loadTrajectoryData(String fName) {
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(fName);

            TrajectoryList trajectoryData = gson.fromJson(reader, TrajectoryList.class);

            reader.close();
            return trajectoryData.getTrajectoryList();
        } catch (Exception e) {
        }
        return null;
    }

    public static void saveTrajectoryData(TrajectoryList trajectoryData, String fName) {
        try {
            Writer writer = new FileWriter(fName);
            Gson gson = new Gson();
            // 1. Java object to JSON file
            gson.toJson(trajectoryData, writer);
            writer.flush(); //flush data to file   <---
        } catch (Exception e) {
        }
    }

    public static void createTrajectoryDataJSONFile(String fName) {
/*

         ArrayList<Trajectory> trajectoryList = new ArrayList();

        ArrayList<Station> stationUtrechtToAmsterdamTrain = new ArrayList();

        stationUtrechtToAmsterdamTrain.add(new Station("Utrecht CS",3 ,2.04));
        stationUtrechtToAmsterdamTrain.add(new Station("Utrecht Zuilen",5 ,5.29));
        stationUtrechtToAmsterdamTrain.add(new Station("Maarssen",4 ,4.83));
        stationUtrechtToAmsterdamTrain.add(new Station("Breukelen",10 ,12.1));
        stationUtrechtToAmsterdamTrain.add(new Station("Abcoude",0 ,0));
        Trajectory trajectoryUtrechtToAmsterdam = new Trajectory(stationUtrechtToAmsterdamTrain,30,20,"train");

        ArrayList<Station> stationUtrechtToZwolleTrain = new ArrayList();
        stationUtrechtToZwolleTrain.add(new Station("Utrecht CS",4,4.2));
        stationUtrechtToZwolleTrain.add(new Station("Utrecht Overvecht",5 ,11.1));
        stationUtrechtToZwolleTrain.add(new Station("Bilthoven",3 ,2.9));
        stationUtrechtToZwolleTrain.add(new Station("Den Dolder",9 ,13.5));
        stationUtrechtToZwolleTrain.add(new Station("Amesfoort Centraal",4,11.8));
        stationUtrechtToZwolleTrain.add(new Station("Amesfoort Schothorst",3 ,5.0));
        stationUtrechtToZwolleTrain.add(new Station("Amesfoort Vathorst",5,9.5));
        stationUtrechtToZwolleTrain.add(new Station("Nijkerk",5 ,11.0));
        stationUtrechtToZwolleTrain.add(new Station("Putten",5 ,7.2));
        stationUtrechtToZwolleTrain.add(new Station("Ermelo",4 ,6.6));
        stationUtrechtToZwolleTrain.add(new Station("Hardewijk",8 ,11.7));
        stationUtrechtToZwolleTrain.add(new Station("Nunspeet",6 ,12.0));
        stationUtrechtToZwolleTrain.add(new Station("'t Harde",7 ,11.6));
        stationUtrechtToZwolleTrain.add(new Station("Wezep",7 ,10.5));
        stationUtrechtToZwolleTrain.add(new Station("Zwolle Centraal",0 ,0));
        Trajectory trajectoryUtrechtToZwolle = new Trajectory(stationUtrechtToZwolleTrain,30,21,"train");



        //Bus trajectories
        //===========================================================================================================
        ArrayList<Station> utrechtToAmesfoortBus = new ArrayList();
        utrechtToAmesfoortBus.add(new Station("Utrecht CS",4 ,1.5));
        utrechtToAmesfoortBus.add(new Station("Vredenburg Utrecht",1 ,2.4));
        utrechtToAmesfoortBus.add(new Station("Neude Utrecht",1 ,0.3));
        utrechtToAmesfoortBus.add(new Station("Janskerkhof Utrecht",1 ,1.0));
        utrechtToAmesfoortBus.add(new Station("Stadsschouwburg Utrecht",2 ,1.5));
        utrechtToAmesfoortBus.add(new Station("Wittevrouwen Utrecht",1 ,2.4));
        utrechtToAmesfoortBus.add(new Station("Oorsprongpark Utrecht",2 ,1.7));
        utrechtToAmesfoortBus.add(new Station("Archimedeslaan Utrecht",1 ,2.6));
        utrechtToAmesfoortBus.add(new Station("Steinenburglaan De Bilt",1 ,0.8));
        utrechtToAmesfoortBus.add(new Station("Kerklaan De Bilt",3 ,3.4));
        utrechtToAmesfoortBus.add(new Station("KNMI De Bilt",3 ,5.3));
        utrechtToAmesfoortBus.add(new Station("Amesfoortseweg De Bilt",0,0)); // end station
        Trajectory trajectoryUtrechtToAmesfoort = new Trajectory(utrechtToAmesfoortBus,30,20,"bus");


        ArrayList<Station> utrechtToZeistBus = new ArrayList();
        utrechtToZeistBus.add(new Station("Utrecht CS",4 ,1.5));
        utrechtToZeistBus.add(new Station("Neude Utrecht",1 ,0.3));
        utrechtToZeistBus.add(new Station("Janskerkhof Utrecht",1 ,1.0));
        utrechtToZeistBus.add(new Station("Stadsschouwburg Utrecht",2 ,1.5));
        utrechtToZeistBus.add(new Station("Wittevrouwen Utrecht",1 ,2.4));
        utrechtToZeistBus.add(new Station("Oorsprongpark Utrecht",2 ,1.7));
        utrechtToZeistBus.add(new Station("KNMI De Bilt",4 ,2.4));// end station
        utrechtToZeistBus.add(new Station("Jordanlaan Utrecht",5 ,1.5));// end station
        utrechtToZeistBus.add(new Station("Winkelcentrum Vollenhove Zeist",1 ,0.3));// end station
        utrechtToZeistBus.add(new Station("Gunningenlaan Vollenhove Zeist",1 ,0.5));// end station
        utrechtToZeistBus.add(new Station("L-Flat Zeist",1 ,0.7));// end station
        utrechtToZeistBus.add(new Station("De Dreef/Panweg Zeist",3 ,0.6));// end station
        utrechtToZeistBus.add(new Station("Nepveulaan Zeist",4 ,1.5));// end station
        utrechtToZeistBus.add(new Station("Schaerweijdelaan Zeist",3 ,1.5));// end station
        utrechtToZeistBus.add(new Station("Steylaan Zeist",3 ,0.7));// end station
        utrechtToZeistBus.add(new Station("Bussation zeist",0 ,0));// end station
        Trajectory trajectoryUtrechtToZeist = new Trajectory(utrechtToZeistBus,30,20, "bus");

        //Bus trajectories
        trajectoryList.add(trajectoryUtrechtToZeist);
        trajectoryList.add(trajectoryUtrechtToAmesfoort);

        //Train trajectories
        trajectoryList.add(trajectoryUtrechtToAmsterdam);
        trajectoryList.add(trajectoryUtrechtToZwolle);

        TrajectoryList trajectorys = new TrajectoryList();
        trajectorys.setTrajectoryList(trajectoryList);
       DataHandler.saveTrajectoryData(trajectorys,fName);




 */
    }
}
