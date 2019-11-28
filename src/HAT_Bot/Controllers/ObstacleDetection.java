package HAT_Bot.Controllers;

import HAT_Bot.Sensors.Ultrasone;

/**
 * This class converts the measured values from the ultrasone to commands
 */
public class ObstacleDetection implements Updatable {

    private Ultrasone ultrasone;
    private ObstacleDetectionObserver observer;
    private String previousCommand;


    /**
     * @param ultrasone The ultrasone sensor
     * @param observer This sends the correct commands to the OperatingLogic
     */
    public ObstacleDetection(Ultrasone ultrasone, ObstacleDetectionObserver observer) {
        this.ultrasone = ultrasone;
        this.observer = observer;
        this.previousCommand = "";
    }

    public void setObserver(ObstacleDetectionObserver observer) {
        this.observer = observer;
    }

    /**
     * This gets the values from the ultrasone class and checks if the robot needs to do something
     */
    @Override
    public void update() {
        ultrasone.update();

        if (ultrasone.getValue() <= 10) {
            if(!this.previousCommand.equals("Stop")){
                this.previousCommand = "Stop";
                observer.onObstacleDetected(this, "Stop");
            }

        }
        else if (ultrasone.getValue() <= 30) {
            if( !this.previousCommand.equals("Slow down")){
                this.previousCommand = "Slow down";
                observer.onObstacleDetected(this, "Slow down");
            }

        }
        else {
            if(!this.previousCommand.equals("Okay")){
                this.previousCommand = "Okay";
                observer.onObstacleDetected(this, "Okay");
            }

        }
    }
}
