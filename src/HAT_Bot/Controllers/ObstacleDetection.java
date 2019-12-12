package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Sensors.Ultrasone;

/**
 * This class converts the measured values from the ultrasoneFront to commands
 */
public class ObstacleDetection implements Updatable {

    private Ultrasone ultrasoneFront;
    private Ultrasone ultrasoneBack;
    private ObstacleDetectionObserver observer;
    private ObstacleDetectionCommand previousCommand;


    /**
     * @param ultrasoneFront The ultrasoneFront sensor
     * @param observer This sends the correct commands to the OperatingLogic
     */
    public ObstacleDetection(Ultrasone ultrasoneFront, Ultrasone ultrasoneBack, ObstacleDetectionObserver observer) {
        this.ultrasoneFront = ultrasoneFront;
        this.ultrasoneBack = ultrasoneBack;
        this.observer = observer;
        this.previousCommand = ObstacleDetectionCommand.None;
    }

    public void setObserver(ObstacleDetectionObserver observer) {
        this.observer = observer;
    }

    /**
     * Gets the sides that are blocked by an object
     * @return
     */
    public ObstacleDetectionSide getBlockedSide(){
        ObstacleDetectionSide side = ObstacleDetectionSide.None;

        if (this.ultrasoneFront.getValue() <= 30 && this.ultrasoneBack.getValue() <= 30){
            side = ObstacleDetectionSide.Both;
        } else if (this.ultrasoneFront.getValue() <= 30){
            side = ObstacleDetectionSide.Front;
        } else if (this.ultrasoneBack.getValue() <= 30){
            side = ObstacleDetectionSide.Back;
        }

        return side;
    }

    /**
     * This gets the values from the ultrasoneFront class and checks if the robot needs to do something
     */
    @Override
    public void update() {
        this.ultrasoneFront.update();
        this.ultrasoneBack.update();

        //if there is an object directly in front or behind the bot
        if (this.ultrasoneFront.getValue() <= 10 || this.ultrasoneBack.getValue() <= 10) {
            if(this.previousCommand != ObstacleDetectionCommand.Stop){
                this.previousCommand = ObstacleDetectionCommand.Stop;
                ObstacleDetectionSide side = getBlockedSide();
                this.observer.onObstacleDetected(this, ObstacleDetectionCommand.Stop, side);
            }

        }
        // if there is an object near the front or the back of the bot
        else if (this.ultrasoneFront.getValue() <= 20 || this.ultrasoneBack.getValue() <= 20) {
            if(this.previousCommand != ObstacleDetectionCommand.SlowDown){
                this.previousCommand = ObstacleDetectionCommand.SlowDown;
                ObstacleDetectionSide side = getBlockedSide();
                this.observer.onObstacleDetected(this, ObstacleDetectionCommand.SlowDown, side);
            }

        }
        //if no obstacles are near the bot
        else {
            if(this.previousCommand != ObstacleDetectionCommand.Okay){
                this.previousCommand = ObstacleDetectionCommand.Okay;
                this.observer.onObstacleDetected(this, ObstacleDetectionCommand.Okay, ObstacleDetectionSide.None);
            }

        }
    }
}
