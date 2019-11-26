package HAT_Bot.Logic;

import HAT_Bot.Controllers.*;
import com.sun.jndi.cosnaming.RemoteToCorba;

public class OperatingLogic implements Updatable, ObstacleDetectionObserver, RemoteControlObserver {

    private IndicatorController indicatorController;
    private MotionController motionController;
    private ObstacleDetection obstacleDetection;
    private RemoteControl remoteControl;

    private boolean forward = true;
    private int currentSpeed = 50;

    public OperatingLogic(IndicatorController indicatorController, MotionController motionController, ObstacleDetection obstacleDetection, RemoteControl remoteControl) {
        this.indicatorController = indicatorController;
        this.motionController = motionController;
        this.obstacleDetection = obstacleDetection;
        this.remoteControl = remoteControl;
        obstacleDetection.setObserver(this);
        remoteControl.setObserver(this);
    }

    public void onObstacleDetected (ObstacleDetection o, String command){
        System.out.println(command);
        if (command.equals("Slow down")){
            this.motionController.goToSpeed(0);
            this.indicatorController.foundObstacleIndication();
        }
        else if(command.equals("Stop")){
            this.motionController.emergencyBrake();
            this.indicatorController.inFrontOfObstacleIndication();
        }
        else{
            this.motionController.goToSpeed(100);
            this.indicatorController.drivingIndication();
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
            case "setSpeed 00":
                this.currentSpeed = 0;
                drive();
                break;
            case "emergencyBrake":
                motionController.emergencyBrake();
                this.currentSpeed = 0;
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
                    motionController.turnLeft();
                }
                else {
                    motionController.turnLeftCurve(forward, currentSpeed);
                }
                break;
            case "turnRight":
                if (currentSpeed == 0) {
                    motionController.turnRight();
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
            motionController.goToSpeed(this.currentSpeed);
        }
        else {
            motionController.goToSpeed(this.currentSpeed);
        }
    }

    @Override
    public void update() {

    }
}
