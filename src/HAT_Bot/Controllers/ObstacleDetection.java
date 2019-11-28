package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Sensors.Ultrasone;

/**
 * This class converts the measured values from the ultrasone to commands
 */
public class ObstacleDetection implements Updatable {

    private Ultrasone ultrasone;
    private ObstacleDetectionObserver observer;
    private ObstacleDetectionCommand previousCommand;


    /**
     * @param ultrasone The ultrasone sensor
     * @param observer This sends the correct commands to the OperatingLogic
     */
    public ObstacleDetection(Ultrasone ultrasone, ObstacleDetectionObserver observer) {
        this.ultrasone = ultrasone;
        this.observer = observer;
        this.previousCommand = ObstacleDetectionCommand.None;
    }

    public void setObserver(ObstacleDetectionObserver observer) {
        this.observer = observer;
    }

    /**
     * This gets the values from the ultrasone class and checks if the robot needs to do something
     */
    @Override
    public void update() {
        this.ultrasone.update();

        if (this.ultrasone.getValue() <= 10) {
            if(this.previousCommand != ObstacleDetectionCommand.Stop){
                this.previousCommand = ObstacleDetectionCommand.Stop;
                this.observer.onObstacleDetected(this, ObstacleDetectionCommand.Stop);
            }

        }
        else if (this.ultrasone.getValue() <= 30) {
            if(this.previousCommand != ObstacleDetectionCommand.SlowDown){
                this.previousCommand = ObstacleDetectionCommand.SlowDown;
                this.observer.onObstacleDetected(this, ObstacleDetectionCommand.SlowDown);
            }

        }
        else {
            if(this.previousCommand != ObstacleDetectionCommand.Okay){
                this.previousCommand = ObstacleDetectionCommand.Okay;
                this.observer.onObstacleDetected(this, ObstacleDetectionCommand.Okay);
            }

        }
    }
}
