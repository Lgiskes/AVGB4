package HAT_Bot.Logic;

import HAT_Bot.Controllers.*;
import TI.BoeBot;

import java.util.ArrayList;

/**
 * Manages the behavior of the bot
 */
public class OperatingLogic implements Updatable, ObstacleDetectionObserver, RemoteControlObserver, LineDetectionObserver, RouteObserver, ManoeuvreObserver {

    private IndicatorController indicatorController;
    private MotionController motionController;
    private ObstacleDetection obstacleDetection;
    private RemoteControl remoteControl;
    private LineDetectionController lineDetectionController;
    private RouteController routeController;
    private ObstacleDetectionCommand status;
    private HATState currentState;
    private ObstacleDetectionSide obstacleSide;
    private BluetoothController bluetoothController;

    private boolean forward = true;
    private int currentSpeed = 50;

    /**
     * @param indicatorController an object that manages the indicators
     * @param motionController an object that manages the servos
     * @param obstacleDetection an object that manages the detection of objects
     * @param remoteControl an objects that manages the control inputs
     * @param lineDetectionController
     */
    public OperatingLogic(IndicatorController indicatorController, MotionController motionController, ObstacleDetection obstacleDetection, RemoteControl remoteControl, LineDetectionController lineDetectionController, RouteController routeController, BluetoothController bluetoothController) {
        this.indicatorController = indicatorController;
        this.motionController = motionController;
        this.obstacleDetection = obstacleDetection;
        this.remoteControl = remoteControl;
        this.lineDetectionController = lineDetectionController;
        this.routeController = routeController;
        this.bluetoothController = bluetoothController;

        obstacleDetection.setObserver(this);
        remoteControl.setObserver(this);
        lineDetectionController.setObserver(this);
        this.motionController.setManoeuvreObserver(this);
        this.routeController.setObserver(this);

        this.status = ObstacleDetectionCommand.None;

        this.obstacleSide = ObstacleDetectionSide.None;

        ArrayList<RouteCommand> route = new ArrayList<>();
        route.add(RouteCommand.forward);
        route.add(RouteCommand.right);
        route.add(RouteCommand.right);
        route.add(RouteCommand.forward);
        route.add(RouteCommand.left);
        route.add(RouteCommand.stop);
        routeController.setRoute(route);

        onRemoteControlDetected(null, RemoteControlCommand.emergencyBrake);
    }

    /**
     * This method changes the current state into the new state if possible.
     * @param newState this is the object that changes the current state into the favoured state.
     */
    public void changeState(HATState newState) {

        HATState previousState = this.currentState;

        if (status == ObstacleDetectionCommand.Stop || status == ObstacleDetectionCommand.SlowDown) {
            if(newState != HATState.remoteControlled){
                this.currentState = HATState.obstacleDetected;
            }
        }
        else {
            this.currentState = newState;
        }

        switch (newState) {
            case remoteControlled:
                break;
            case obstacleDetected:
                break;
            case lineFollowing:
                if (previousState == HATState.routeFollowing){
                    this.currentState = previousState;
                }
                break;
            case manoeuvre:
                if (previousState == HATState.lineFollowing){
                    this.currentState = previousState;
                }
                break;
            case routeFollowing:
                if (previousState == HATState.manoeuvre || previousState == HATState.remoteControlled){
                    this.currentState = previousState;
                }
                break;
        }
    }

    /**
     * An observer that manages the controls when an object is detected
     * @param obstacleDetection an object that manages obstacle detection
     * @param command the command that is enlisted to the controls
     */
    public void onObstacleDetected (ObstacleDetection obstacleDetection, ObstacleDetectionCommand command, ObstacleDetectionSide side){

        this.obstacleSide = side;

        if (command == ObstacleDetectionCommand.SlowDown){
            if(this.forward && side == ObstacleDetectionSide.Front || !this.forward && side == ObstacleDetectionSide.Back || side == ObstacleDetectionSide.Both) {
                this.motionController.killManoeuvre();
                this.motionController.goToSpeed(0,500);
            }

            this.indicatorController.foundObstacleIndication();
            this.status = ObstacleDetectionCommand.SlowDown;
            changeState(HATState.obstacleDetected);
        }
        else if(command == ObstacleDetectionCommand.Stop){
            this.motionController.killManoeuvre();
            this.motionController.emergencyBrake();
            this.indicatorController.inFrontOfObstacleIndication();
            this.status = ObstacleDetectionCommand.Stop;
            changeState(HATState.obstacleDetected);
        }
        else{
            this.status = ObstacleDetectionCommand.Okay;
            changeState(HATState.remoteControlled);
            this.indicatorController.standingStillIndication();
        }

    }

    /**
     * An observer that manages the commands from the remote controller
     * @param remoteControl the object that manages the command inputs
     * @param command the command that is enlisted to the bot
     */
    public void onRemoteControlDetected(RemoteControl remoteControl, RemoteControlCommand command) {

        changeState(HATState.remoteControlled);

        switch (command) {
            case setSpeed10:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 10;
                drive();
                break;
            case setSpeed20:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 20;
                drive();
                break;
            case setSpeed30:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 30;
                drive();
                break;
            case setSpeed40:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 40;
                drive();
                break;
            case setSpeed50:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 50;
                drive();
                break;
            case setSpeed60:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 60;
                drive();
                break;
            case setSpeed70:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 70;
                drive();
                break;
            case setSpeed80:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 80;
                drive();
                break;
            case setSpeed90:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 90;
                drive();
                break;
            case setSpeed100:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 100;
                drive();
                break;
            case setSpeed0:
                changeState(HATState.manoeuvre);
                this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 0;
                drive();
                break;
            case emergencyBrake:
                this.motionController.emergencyBrake();
                this.indicatorController.standingStillIndication();
                this.currentSpeed = 0;
                break;
            case forward:
                if (this.currentState == HATState.remoteControlled) {
                    this.forward = true;
                    drive();
                }
                break;
            case backward:
                this.forward = false;
                drive();
                break;
            case turnLeft:
                this.indicatorController.drivingIndication();
                if (this.currentSpeed == 0 || this.currentState == HATState.obstacleDetected) {
                    this.motionController.turningLeft(10);
                } else {
                    this.motionController.turnLeftCurve(this.forward, this.currentSpeed);
                }
                break;
            case turnRight:
                this.indicatorController.drivingIndication();
                if (this.currentSpeed == 0 || this.currentState == HATState.obstacleDetected) {
                    this.motionController.turningRight(10);
                } else {
                    this.motionController.turnRightCurve(this.forward, this.currentSpeed);
                }
                break;
            case mute:
                this.indicatorController.mute(!this.indicatorController.getMuteState());
                break;
            case driveSquare:
                if (this.currentState == HATState.remoteControlled) {
                    changeState(HATState.manoeuvre);
                    this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                    this.motionController.driveSquare();
                }
                break;
            case driveCircle:
                if (this.currentState == HATState.remoteControlled) {
                    changeState(HATState.manoeuvre);
                    this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                    this.motionController.driveCircle();
                }
                break;
            case driveTriangle:
                if (this.currentState == HATState.remoteControlled) {
                    changeState(HATState.manoeuvre);
                    this.motionController.setCommand(ManoeuvreCommand.remoteControl);
                    this.motionController.driveTriangle();
                }
                break;
            case resume:
                changeState(HATState.lineFollowing);
                this.indicatorController.drivingIndication();
                this.lineDetectionController.setPreviousCommand(LineDetectionCommand.none);
                break;
            case toggleLights:
                this.indicatorController.toggleRGBCycle();
                break;
        }
    }

    /**
     * Handles what the Bot does at a line
     * @param lineDetectionController the controller of the line detectors
     * @param command the command the bot executes on the line
     */

    @Override
    public void onLineDetected(LineDetectionController lineDetectionController, LineDetectionCommand command) {

        if (this.currentState == HATState.lineFollowing) {
            final int speed = 40;

            switch (command) {
                case left:
                    this.motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    this.motionController.turningLeft(30);
                    break;
                case right:
                    this.motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    this.motionController.turningRight(30);
                    break;
                case forward:
                    this.motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    this.motionController.setSpeed(speed);
                    break;
                case stop:
                    this.motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    this.motionController.goToSpeed(0);
                    break;
                case crossroad:
                    changeState(HATState.routeFollowing);
                    this.routeController.crossroadManoeuvre();
                    break;
            }
        }
    }


    /**
     * Handles what the Bot does at an intersection
     * @param routeController The controller that controls the routes
     * @param command the command the Bot executes at the intersection
     */
    @Override
    public void onCrossroadDetected(RouteController routeController, RouteCommand command) {
        changeState(HATState.manoeuvre);
        if (this.currentState == HATState.manoeuvre) {
            if(command != RouteCommand.stop){
                if(this.routeController.getPreviousCommand() == RouteCommand.stop){
                    this.motionController.slightlyForward(40, 50);
                }
                this.motionController.slightlyForward(40, 100); // Drives a bit past the intersection
            }

            switch (command) {
                case forward:
                    this.motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    break;
                case right:
                    this.motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    this.motionController.turnRight();
                    BoeBot.wait(400); // So that the BoeBot is able to make a turn
                    break;
                case left:
                    this.motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    this.motionController.turnLeft();
                    BoeBot.wait(400); // So that the BoeBot is able to make a turn
                    break;
                case turnAround:
                    this.motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    this.motionController.turnRight();
                    BoeBot.wait(800); // So that the BoeBot is able to make a turn
                    break;
                case stop:
                    changeState(HATState.remoteControlled);
                    this.motionController.emergencyBrake();
                    this.indicatorController.standingStillIndication();
                    break;
            }

        }
    }


    /**
     * Changes the state according to the previous state
     * @param m the motioncontroller
     * @param command the state to change to
     */
    @Override
    public void onManoeuvreDetected(MotionController m, ManoeuvreCommand command) {
        if (command == ManoeuvreCommand.lineFollowing) {
            changeState(HATState.lineFollowing);
        }
        else {
            changeState(HATState.remoteControlled);
        }
    }

    /**
     * Drives with a speed equal to this.currentSpeed
     */
    public void drive() {
        if (this.forward) {
            if(this.status == ObstacleDetectionCommand.Okay || this.obstacleSide != ObstacleDetectionSide.Front && this.obstacleSide != ObstacleDetectionSide.Both) {
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
            if(this.status == ObstacleDetectionCommand.Okay || this.obstacleSide != ObstacleDetectionSide.Back && this.obstacleSide != ObstacleDetectionSide.Both){
                this.motionController.goToSpeed(-this.currentSpeed);

                if(this.currentSpeed == 0){
                    this.indicatorController.standingStillIndication();
                }
                else {
                    this.indicatorController.drivingBackwardsIndication();
                }
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
