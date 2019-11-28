package HAT_Bot.Logic;

import HAT_Bot.Controllers.*;
import com.sun.jndi.cosnaming.RemoteToCorba;

public class OperatingLogic implements Updatable, ObstacleDetectionObserver, RemoteControlObserver {

    private IndicatorController indicatorController;
    private MotionController motionController;
    private ObstacleDetection obstacleDetection;
    private RemoteControl remoteControl;
    private String status;

    private boolean forward = true;
    private int currentSpeed = 50;

    public OperatingLogic(IndicatorController indicatorController, MotionController motionController, ObstacleDetection obstacleDetection, RemoteControl remoteControl) {
        this.indicatorController = indicatorController;
        this.motionController = motionController;
        this.obstacleDetection = obstacleDetection;
        this.remoteControl = remoteControl;
        obstacleDetection.setObserver(this);
        remoteControl.setObserver(this);
        this.status = "";
    }

    public void onObstacleDetected (ObstacleDetection o, String command){
        if (command.equals("Slow down")){
            if(this.forward) {
                this.motionController.goToSpeed(0);
            }
            this.indicatorController.foundObstacleIndication();
            this.status = "Slow down";
        }
        else if(command.equals("Stop")){
            this.motionController.emergencyBrake();
            this.indicatorController.inFrontOfObstacleIndication();
            this.status = "Stop";
        }
        else{
            this.status = "Okay";
            this.drive();
        }

    }

    public void onRemoteControlDetected(RemoteControl r, String command) {
        switch (command) {
            case "setSpeed 10":
                this.currentSpeed = 10;
                drive();
                break;
            case "setSpeed 20":
                this.currentSpeed = 20;
                drive();
                break;
            case "setSpeed 30":
                this.currentSpeed = 30;
                drive();
                break;
            case "setSpeed 40":
                this.currentSpeed = 40;
                drive();
                break;
            case "setSpeed 50":
                this.currentSpeed = 50;
                drive();
                break;
            case "setSpeed 60":
                this.currentSpeed = 60;
                drive();
                break;
            case "setSpeed 70":
                this.currentSpeed = 70;
                drive();
                break;
            case "setSpeed 80":
                this.currentSpeed = 80;
                drive();
                break;
            case "setSpeed 90":
                this.currentSpeed = 90;
                drive();
                break;
            case "setSpeed 100":
                    this.currentSpeed = 100;
                    drive();
                break;
            case "setSpeed 00":
                this.currentSpeed = 0;
                drive();
                break;
            case "emergencyBrake":
                motionController.emergencyBrake();
                this.indicatorController.standingStillIndication();
                break;
            case "forward":
                this.forward = true;
                drive();
                break;
            case "backward":
                this.forward = false;
                drive();
                break;
            case "turnLeft":
                if (currentSpeed == 0) {
                    motionController.turningLeft(10);
                }
                else {
                    motionController.turnLeftCurve(forward, currentSpeed);
                }
                break;
            case "turnRight":
                if (currentSpeed == 0) {
                    motionController.turningRight(10);
                }
                else {
                    motionController.turnRightCurve(forward, currentSpeed);
                }
                break;
            case "mute":
                indicatorController.mute(!indicatorController.getMuteState());
                break;
            case "driveSquare":
                motionController.driveSquare();
                break;
            case "driveCircle":
                motionController.driveCircle();
                break;
            case "driveTriangle":
                motionController.driveTriangle();
                break;
        }
    }

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

    @Override
    public void update() {

    }
}
