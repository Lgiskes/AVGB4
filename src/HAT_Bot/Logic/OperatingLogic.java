package HAT_Bot.Logic;

import HAT_Bot.Controllers.*;

/**
 * Manages the behavior of the bot
 */
public class OperatingLogic implements Updatable, ObstacleDetectionObserver, RemoteControlObserver {

    private IndicatorController indicatorController;
    private MotionController motionController;
    private ObstacleDetection obstacleDetection;
    private RemoteControl remoteControl;
    private ObstacleDetectionCommand status;

    private boolean forward = true;
    private int currentSpeed = 50;

    /**
     * @param indicatorController an object that manages the indicators
     * @param motionController an object that manages the servos
     * @param obstacleDetection an object that manages the detection of objects
     * @param remoteControl an objects that manages the control inputs
     */
    public OperatingLogic(IndicatorController indicatorController, MotionController motionController, ObstacleDetection obstacleDetection, RemoteControl remoteControl) {
        this.indicatorController = indicatorController;
        this.motionController = motionController;
        this.obstacleDetection = obstacleDetection;
        this.remoteControl = remoteControl;
        obstacleDetection.setObserver(this);
        remoteControl.setObserver(this);
        this.status = ObstacleDetectionCommand.None;
    }

    /**
     * An observer that manages the controls when an object is detected
     * @param obstacleDetection an object that manages obstacle detection
     * @param command the command that is enlisted to the controls
     */
    public void onObstacleDetected (ObstacleDetection obstacleDetection, ObstacleDetectionCommand command){
        if (command == ObstacleDetectionCommand.SlowDown){
            if(this.forward) {
                this.motionController.goToSpeed(0);
            }
            this.indicatorController.foundObstacleIndication();
            this.status = ObstacleDetectionCommand.SlowDown;
        }
        else if(command == ObstacleDetectionCommand.Stop){
            this.motionController.emergencyBrake();
            this.indicatorController.inFrontOfObstacleIndication();
            this.status = ObstacleDetectionCommand.Stop;
        }
        else{
            this.status = ObstacleDetectionCommand.Okay;
            this.drive();
        }

    }

    /**
     * An observer that manages the commands from the remote controller
     * @param remoteControl the object that manages the command inputs
     * @param command the command that is enlisted to the bot
     */
    public void onRemoteControlDetected(RemoteControl remoteControl, RemoteControlCommand command) {
        switch (command) {
            case setSpeed10:
                this.currentSpeed = 10;
                drive();
                break;
            case setSpeed20:
                this.currentSpeed = 20;
                drive();
                break;
            case setSpeed30:
                this.currentSpeed = 30;
                drive();
                break;
            case setSpeed40:
                this.currentSpeed = 40;
                drive();
                break;
            case setSpeed50:
                this.currentSpeed = 50;
                drive();
                break;
            case setSpeed60:
                this.currentSpeed = 60;
                drive();
                break;
            case setSpeed70:
                this.currentSpeed = 70;
                drive();
                break;
            case setSpeed80:
                this.currentSpeed = 80;
                drive();
                break;
            case setSpeed90:
                this.currentSpeed = 90;
                drive();
                break;
            case setSpeed100:
                    this.currentSpeed = 100;
                    drive();
                break;
            case setSpeed0:
                this.currentSpeed = 0;
                drive();
                break;
            case emergencyBrake:
                motionController.emergencyBrake();
                this.indicatorController.standingStillIndication();
                break;
            case forward:
                this.forward = true;
                drive();
                break;
            case backward:
                this.forward = false;
                drive();
                break;
            case turnLeft:
                if (currentSpeed == 0) {
                    motionController.turningLeft(10);
                }
                else {
                    motionController.turnLeftCurve(forward, currentSpeed);
                }
                break;
            case turnRight:
                if (currentSpeed == 0) {
                    motionController.turningRight(10);
                }
                else {
                    motionController.turnRightCurve(forward, currentSpeed);
                }
                break;
            case mute:
                indicatorController.mute(!indicatorController.getMuteState());
                break;
            case driveSquare:
                motionController.driveSquare();
                break;
            case driveCircle:
                motionController.driveCircle();
                break;
            case driveTriangle:
                motionController.driveTriangle();
                break;
        }
    }

    /**
     * Drives with a speed equal to this.currenSpeed
     */
    public void drive() {
        if (forward) {
            if(this.status.equals("Okay")) {
                motionController.goToSpeed(this.currentSpeed);

                if(this.currentSpeed == 0){
                    this.indicatorController.standingStillIndication();
                }
                else {
                    this.indicatorController.drivingIndication();
                }
            }
        }
        else {
            motionController.goToSpeed(-this.currentSpeed);

            if(this.currentSpeed == 0){
                this.indicatorController.standingStillIndication();
            }
            else {
                this.indicatorController.drivingBackwardsIndication();
            }
        }
    }

    /**
     * Updates OperatingLogic
     */
    @Override
    public void update() {

    }
}
