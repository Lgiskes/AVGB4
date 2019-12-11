package HAT_Bot.Controllers;


import java.util.ArrayList;

/**
 * Controls the decisions at intersections
 */

public class RouteController {
    private RouteObserver observer;
    private int index;
    private ArrayList<RouteCommand> route;

    public RouteController() {
        this.observer = null;
        this.route = new ArrayList<>();
    }

    public void setObserver(RouteObserver observer) {
        this.observer = observer;
    }

    public void setRoute(ArrayList<RouteCommand> route) {
        this.route = route;
        restart();
    }

    /**
     * restarts the route from this position
     */
    public void restart() {
        continueFrom(0);
    }

    /**
     * continues the route from a certain intersection
     * @param index the index of the intersection the bot will restart the route from
     */
    public void continueFrom(int index) {
        this.index = index;
    }

    /**
     * adds an extra command to the series of intersections
     * @param routeCommand
     */
    public void addCommand(RouteCommand routeCommand) {
        this.route.add(routeCommand);
    }

    /**
     * hanldes the motion when the bot is at an intersection
     */
    public void crossroadManoeuvre() {
        if (this.index < route.size()) {
            observer.onCrossroadDetected(this, this.route.get(this.index));
            this.index++;
        }
        else {
            observer.onCrossroadDetected(this, RouteCommand.stop);
        }
    }
}
