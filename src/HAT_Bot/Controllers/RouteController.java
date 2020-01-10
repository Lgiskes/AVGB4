package HAT_Bot.Controllers;


import com.sun.imageio.spi.RAFImageOutputStreamSpi;

import java.util.ArrayList;

/**
 * Controls the decisions at intersections
 */

public class RouteController {
    private RouteObserver observer;
    private int index;
    private ArrayList<RouteCommand> route;
    private RouteCommand previousCommand;

    public RouteController() {
        this.observer = null;
        this.route = new ArrayList<>();
        this.previousCommand = RouteCommand.None;
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

    public RouteCommand getPreviousCommand(){
        return this.previousCommand;
    }

    /**
     * hanldes the motion when the bot is at an intersection
     */
    public void crossroadManoeuvre() {
        if (this.index < this.route.size()) {
            this.observer.onCrossroadDetected(this, this.route.get(this.index));
            this.previousCommand = this.route.get(this.index);
            this.index++;
        }
        else {
            this.observer.onCrossroadDetected(this, RouteCommand.stop);
            this.previousCommand = RouteCommand.stop;
        }
    }
}
