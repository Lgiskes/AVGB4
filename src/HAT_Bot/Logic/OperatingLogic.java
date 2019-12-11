package HAT_Bot.Logic;

import HAT_Bot.Controllers.*;
import TI.BoeBot;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    private boolean forward = true;
    private int currentSpeed = 50;
    private boolean isValid = true;

    /**
     * @param indicatorController an object that manages the indicators
     * @param motionController an object that manages the servos
     * @param obstacleDetection an object that manages the detection of objects
     * @param remoteControl an objects that manages the control inputs
     * @param lineDetectionController
     */
    public OperatingLogic(IndicatorController indicatorController, MotionController motionController, ObstacleDetection obstacleDetection, RemoteControl remoteControl, LineDetectionController lineDetectionController, RouteController routeController) {
        this.indicatorController = indicatorController;
        this.motionController = motionController;
        this.obstacleDetection = obstacleDetection;
        this.remoteControl = remoteControl;
        this.lineDetectionController = lineDetectionController;
        this.routeController = routeController;
        obstacleDetection.setObserver(this);
        remoteControl.setObserver(this);
        lineDetectionController.setObserver(this);
        this.status = ObstacleDetectionCommand.None;
        changeState(HATState.remoteControlled);
        ArrayList<RouteCommand> route = new ArrayList<>();
        route.add(RouteCommand.left);
        route.add(RouteCommand.straight);
        route.add(RouteCommand.right);
        route.add(RouteCommand.turnAround);
        routeController.setRoute(route);
    }

    /**
     * This method changes the current state into the new state if possible.
     * @param newState this is the object that changes the current state into the favoured state.
     */
    public void changeState(HATState newState) {

        HATState previousState = this.currentState;

        if (status == ObstacleDetectionCommand.Stop || status == ObstacleDetectionCommand.SlowDown) {
            this.currentState = HATState.obstacleDetected;
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
        if (command == ObstacleDetectionCommand.SlowDown){
            if(this.forward) {
                this.motionController.goToSpeed(0);
            }
            this.indicatorController.foundObstacleIndication();
            this.status = ObstacleDetectionCommand.SlowDown;
            changeState(HATState.obstacleDetected);
        }
        else if(command == ObstacleDetectionCommand.Stop){
            this.motionController.emergencyBrake();
            this.indicatorController.inFrontOfObstacleIndication();
            this.status = ObstacleDetectionCommand.Stop;
            changeState(HATState.obstacleDetected);
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
        changeState(HATState.remoteControlled);

        switch (command) {
            case setSpeed10:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 10;
                drive();
                break;
            case setSpeed20:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 20;
                drive();
                break;
            case setSpeed30:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 30;
                drive();
                break;
            case setSpeed40:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 40;
                drive();
                break;
            case setSpeed50:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 50;
                drive();
                break;
            case setSpeed60:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 60;
                drive();
                break;
            case setSpeed70:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 70;
                drive();
                break;
            case setSpeed80:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 80;
                drive();
                break;
            case setSpeed90:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 90;
                drive();
                break;
            case setSpeed100:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
                this.currentSpeed = 100;
                drive();
                break;
            case setSpeed0:
                changeState(HATState.manoeuvre);
                motionController.setCommand(ManoeuvreCommand.remoteControl);
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
                if (this.currentSpeed == 0 || this.currentState == HATState.obstacleDetected) {
                    this.motionController.turningLeft(10);
                } else {
                    this.motionController.turnLeftCurve(this.forward, this.currentSpeed);
                }
                break;
            case turnRight:
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
                    motionController.setCommand(ManoeuvreCommand.remoteControl);
                    this.motionController.driveSquare();
                }
                break;
            case driveCircle:
                if (this.currentState == HATState.remoteControlled) {
                    changeState(HATState.manoeuvre);
                    motionController.setCommand(ManoeuvreCommand.remoteControl);
                    this.motionController.driveCircle();
                }
                break;
            case driveTriangle:
                if (this.currentState == HATState.remoteControlled) {
                    changeState(HATState.manoeuvre);
                    motionController.setCommand(ManoeuvreCommand.remoteControl);
                    this.motionController.driveTriangle();
                }
                break;
            case resume:
                changeState(HATState.lineFollowing);
                lineDetectionController.setPreviousCommand(LineDetectionCommand.none);
                break;
        }
    }

    @Override
    public void onLineDetected(LineDetectionController l, LineDetectionCommand command) {

        if (this.currentState == HATState.lineFollowing) {
            final int speed = 80;

            for(int i = 0; i < 6; i++){
                BoeBot.rgbSet(i, Color.BLACK);
            }

            switch (command) {
                case left:
                    BoeBot.rgbSet(2, Color.RED);
                    BoeBot.rgbSet(3, Color.RED);
                    motionController.turningLeft(30);
                    break;
                case right:
                    BoeBot.rgbSet(0, Color.RED);
                    BoeBot.rgbSet(5, Color.RED);
                    motionController.turningRight(30);
                    break;
                case slightLeft:
                    BoeBot.rgbSet(2, Color.ORANGE);
                    BoeBot.rgbSet(3, Color.ORANGE);
                    motionController.turnLeftCurve(true, speed);
                    break;
                case slightRight:
                    BoeBot.rgbSet(0, Color.ORANGE);
                    BoeBot.rgbSet(5, Color.ORANGE);
                    motionController.turnRightCurve(true, speed);
                    break;
                case forward:
                    BoeBot.rgbSet(3, Color.GREEN);
                    BoeBot.rgbSet(4, Color.GREEN);
                    BoeBot.rgbSet(5, Color.GREEN);
                    motionController.goToSpeed(speed, 50);
                    break;
                case stop:
                    for (int i = 0; i < 6; i++) {
                        BoeBot.rgbSet(i, Color.WHITE);
                    }
                    motionController.goToSpeed(0, 50);
                    break;
                case crossroad:
                    changeState(HATState.routeFollowing);
                    routeController.crossroadManoeuvre();
                    break;
            }
        }

        BoeBot.rgbShow();
    }


    @Override
    public void onCrossroadDetected(RouteController r, RouteCommand command) {
        changeState(HATState.manoeuvre);
        if (this.currentState == HATState.manoeuvre) {
            switch (command) {
                case straight:
                    motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    motionController.slightlyForward(1000);
                    break;
                case right:
                    motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    motionController.turnRight();
                    break;
                case left:
                    motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    motionController.turnLeft();
                    break;
                case turnAround:
                    motionController.setCommand(ManoeuvreCommand.lineFollowing);
                    motionController.turnAround();
                    break;
                case stop:
                    changeState(HATState.lineFollowing);
                    motionController.goToSpeed(0);
                    break;
            }
        }
    }


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
        if (this.forward && this.currentState != HATState.obstacleDetected) {
            if(this.status == ObstacleDetectionCommand.Okay) {
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
            this.forward = false;
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
