package HAT_Bot_GUI;

import java.util.HashMap;

/**
 * Saves, loads and sends the routes to the BoeBot
 */

public class RouteController {

    private HashMap<String, String> routes;

    public RouteController(){
        this.routes = RouteIO.read();
    }

    /**
     * Adds a route to the datafile
     * @param name The name of the route
     * @param route the route
     */

    public void addRoute(String name, String route){
        for(String routeName : this.getRouteNames()){
            if(routeName.equals(name)){
                this.routes.replace(routeName, route);
                RouteIO.write(this.routes);
                return;
            }
        }

        this.routes.put(name, route);
        RouteIO.write(this.routes);
    }

    /**
     * Removes a route from the file
     * @param name The route name that needs to be removed
     */
    public void removeRoute(String name){
        for(String routeName : this.getRouteNames()){
            if(routeName.equals(name)){
                this.routes.remove(routeName);
                RouteIO.write(this.routes);
                return;
            }
        }
    }

    /**
     * Gets a route from the routes file
     * @param name
     * @return
     */
    public String getRoute(String name){
        return this.routes.get(name);
    }

    /**
     * Gets an array with all the routes saved in the file
     * @return
     */
    public String[] getRouteNames(){
        String[] result = new String[this.routes.keySet().size()];
        this.routes.keySet().toArray(result);
        return result;
    }

}
