package HAT_Bot.Logic;

import HAT_Bot.Logic.Updatable;
import HAT_Bot.Sensors.Infrared;
import TI.BoeBot;


public class RemoteControl implements Updatable {

    private int buttonValue;
    private Infrared infrared;
    private int speedValue;
    private boolean forward;
    private MotionController motionController;

    public RemoteControl(int pinInfrared, MotionController motionController) {
        this.infrared = new Infrared(pinInfrared);
        this.forward = true;
        this.speedValue = 0;
        this.motionController = motionController;
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
                break;
            case 292: // turn right
                break;
            case 296: // mute button
                break;
            case 922: // square
                break;
            case 314: // circle
                break;
            case 924: // triangle
                break;
            case 926: // other form
                break;
        }
    }

    public void drive(){
        if (forward){
            motionController.goToSpeed(-buttonValue);
        }
        else {
            motionController.goToSpeed(buttonValue);
        }
    }

    public void update() {
        infrared.update();
        this.buttonValue = infrared.getValue();
        if (this.buttonValue != -1){
            System.out.println(this.speedValue);
            actions();
        }
    }

}
