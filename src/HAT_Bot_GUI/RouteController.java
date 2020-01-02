package HAT_Bot_GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteController {

    private HashMap<String, String> routes;

    public RouteController(){
        this.routes = RouteIO.read();
    }

    public void addRoute(String name, String route){
        for(String routeName : this.getRouteNames()){
            if(routeName.equals(name)){
                return;
            }
        }

        this.routes.put(name, route);
        RouteIO.write(this.routes);
    }

    public String getRoute(String name){
        return this.routes.get(name);
    }

    public String[] getRouteNames(){
        String[] result = new String[this.routes.keySet().size()];
        this.routes.keySet().toArray(result);
        return result;
    }

}
