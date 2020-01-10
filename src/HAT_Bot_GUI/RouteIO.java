package HAT_Bot_GUI;

import java.io.*;
import java.util.HashMap;

/**
 * Writes and reads the file in which the routes are saved.
 */

public class RouteIO {

    /**
     * Writes a route to the datafile
     * @param map Hashmap in which the route is saved.
     */

    public static void write(HashMap<String,String> map){
       try{
           FileOutputStream routeFileOutput = new FileOutputStream(new File("BoebotRoutes.txt"));
           ObjectOutputStream fileOutput = new ObjectOutputStream(routeFileOutput);
           fileOutput.writeObject(map);
           fileOutput.close();
       }
       catch (FileNotFoundException e) {
           System.out.println("File not found");
        }
       catch (IOException e) {
        System.out.println("Error initializing stream");
        }
    }

    /**
     * Reads the Hashmap containing all the routes
     * @return the Hashmap with the routes
     */

    public static HashMap<String,String> read(){
        try {
            FileInputStream routeFileInput = new FileInputStream(new File("BoebotRoutes.txt"));
            ObjectInputStream fileInput = new ObjectInputStream(routeFileInput);
            HashMap<String, String> readMap = (HashMap)fileInput.readObject();
            fileInput.close();
            return readMap;
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}

