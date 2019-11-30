package HAT_Bot.Logic;

import HAT_Bot.Controllers.*;

/**
 * Manages the behavior of the bot
 */
public class OperatingLogic implements Updatable, ObstacleDetectionObserver, RemoteControlObserver, LineDetectionObserver {

    private IndicatorController indicatorController;
    private MotionController motionController;
    private ObstacleDetection obstacleDetection;
    private RemoteControl remoteControl;
    private LineDetectionController lineDetectionController;
    private ObstacleDetectionCommand status;

    private boolean forward = true;
    private int currentSpeed = 50;

    /**
     * @param indicatorController an object that manages the indicators
     * @param motionController an object that manages the servos
     * @param obstacleDetection an object that manages the detection of objects
     * @param remoteControl an objects that manages the control inputs
     * @param lineDetectionController
     */
    public OperatingLogic(IndicatorController indicatorController, MotionController motionController, ObstacleDetection obstacleDetection, RemoteControl remoteControl, LineDetectionController lineDetectionController) {
        this.indicatorController = indicatorController;
        this.motionController = motionController;
        this.obstacleDetection = obstacleDetection;
        this.remoteControl = remoteControl;
        this.lineDetectionController = lineDetectionController;
        obstacleDetection.setObserver(this);
        remoteControl.setObserver(this);
        lineDetectionController.setObserver(this);
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
                this.motionController.emergencyBrake();
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
                if (this.currentSpeed == 0) {
                    this.motionController.turningLeft(10);
                }
                else {
                    this.motionController.turnLeftCurve(this.forward, this.currentSpeed);
                }
                break;
            case turnRight:
                if (this.currentSpeed == 0) {
                    this.motionController.turningRight(10);
                }
                else {
                    this.motionController.turnRightCurve(this.forward, this.currentSpeed);
                }
                break;
            case mute:
                this.indicatorController.mute(!this.indicatorController.getMuteState());
                break;
            case driveSquare:
                this.motionController.driveSquare();
                break;
            case driveCircle:
                this.motionController.driveCircle();
                break;
            case driveTriangle:
               this. motionController.driveTriangle();
                break;
        }
    }

    @Override
    public void onLineDetected(LineDetectionController l, LineDetectionCommand command) {
        switch (command) {
            case left:
                motionController.turnLeftCurve(true, 25);
                break;
            case right:
                motionController.turnRightCurve(true, 25);
                break;
            case slightLeft:
                motionController.turnLeftCurve(true, 75);
                break;
            case slightRight:
                motionController.turnRightCurve(true, 75);
                break;
            case forward:
                motionController.goToSpeed(100);
                break;
        }
    }


    /**
     * Drives with a speed equal to this.currentSpeed
     */
    public void drive() {
        if (this.forward) {
            if(this.status.equals("Okay")) {
                this.motionController.goToSpeed(this.currentSpeed);

                if(this.currentSpeed == 0){
                    this.indicatorController.standingStillIndication();
                }
                else {
                    this.indicatorController.drivingIndication();
                }
            }
        }
        else {
            this.motionController.goToSpeed(-this.currentSpeed);

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
