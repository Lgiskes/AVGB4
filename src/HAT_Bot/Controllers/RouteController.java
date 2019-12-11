package HAT_Bot.Controllers;


import java.util.ArrayList;

public class RouteController {
    private RouteObserver observer;
    private int index;
    private ArrayList<RouteCommand> route;

    public RouteController() {
        this.observer = null;
    }

    public void setObserver(RouteObserver observer) {
        this.observer = observer;
    }

    public void setRoute(ArrayList<RouteCommand> route) {
        this.route = route;
        restart();
    }

    public void restart() {
        continueFrom(0);
    }

    public void continueFrom(int index) {
        this.index = index;
    }

    public void addCommand(RouteCommand routeCommand) {
        this.route.add(routeCommand);
    }

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
