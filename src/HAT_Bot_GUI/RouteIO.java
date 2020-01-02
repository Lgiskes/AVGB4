package HAT_Bot_GUI;

import java.io.*;
import java.util.HashMap;

public class RouteIO {

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

