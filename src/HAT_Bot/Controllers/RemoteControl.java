package HAT_Bot.Controllers;

import HAT_Bot.Sensors.Infrared;
import TI.StoppableTimer;


public class RemoteControl implements Updatable {

    private int buttonValue;
    private Infrared infrared;
    private int speedValue;
    private boolean forward;
    private MotionController motionController;
    private IndicatorController indicatorController;
    private StoppableTimer delayTimer;

    public RemoteControl(int pinInfrared, MotionController motionController, IndicatorController indicatorController) {
        this.infrared = new Infrared(pinInfrared);
        this.forward = true;
        this.speedValue = 0;
        this.motionController = motionController;
        this.indicatorController = indicatorController;
        this.delayTimer = new StoppableTimer(200);
    }

    public void actions(){
        switch (this.buttonValue){
            case 256: // 1
                this.speedValue = 10;
                drive();
                break;
            case 258: // 2
                this.speedValue = 20;
                drive();
                break;
            case 260: // 3
                this.speedValue = 30;
                drive();
                break;
            case 262: // 4
                this.speedValue = 40;
                drive();
                break;
            case 264: // 5
                this.speedValue = 50;
                drive();
                break;
            case 266: // 6
                this.speedValue = 60;
                drive();
                break;
            case 268: // 7
                this.speedValue = 70;
                drive();
                break;
            case 270: // 8
                this.speedValue = 80;
                drive();
                break;
            case 272: // 9
                this.speedValue = 90;
                drive();
                break;
            case 274: // 0
                this.speedValue = 100;
                drive();
                break;
            case 280: // arrow
                this.speedValue = 0;
                drive();
                break;
            case 298: // break button
                motionController.emergencyBrake();
                this.speedValue = 0;
                break;
            case 288: // forward
                this.forward = true;
                drive();
                break;
            case 290: // backwards
                this.forward = false;
                drive();
                break;
            case 294: // turn left
                if (speedValue == 0){
                    motionController.turnLeft();
                }
                else{
                    if (forward){
                        motionController.turnLeftCurve(true, speedValue);
                    }
                    else {
                        motionController.turnLeftCurve(false, speedValue);
                    }
                }
                break;
            case 292: // turn right
                if (speedValue == 0){
                    motionController.turnRight();
                }
                else {
                    if (forward){
                        motionController.turnRightCurve(true, speedValue);
                    }
                    else {
                        motionController.turnRightCurve(false, speedValue);
                    }
                }
                break;
            case 296: // mute button
                this.indicatorController.mute(!this.indicatorController.getMuteState());
                break;
            case 922: // square
                motionController.driveSquare();
                break;
            case 314: // circle
                motionController.circle();
                break;
            case 924: // triangle
                motionController.driveTriangle();
                break;
            case 926: // other form
                break;
        }
    }

    public void drive(){
        if (forward){
            motionController.goToSpeed(speedValue);
        }
        else {
            motionController.goToSpeed(-speedValue);
        }
    }

    public void update() {

        if(delayTimer.timeout()){
            delayTimer.stop();
        }
        else if(delayTimer.isStopped()) {
            infrared.update();
            this.buttonValue = infrared.getValue();
            if (this.buttonValue != -1){
                actions();
                delayTimer.start();
            }
        }

    }

}