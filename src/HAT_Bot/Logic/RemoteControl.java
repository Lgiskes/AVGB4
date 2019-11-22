package HAT_Bot.Logic;

import HAT_Bot.Logic.Updatable;
import HAT_Bot.Sensors.Infrared;


public class RemoteControl implements Updatable {

    private int buttonValue;
    private Infrared infrared;
    private int speedValue;
    private boolean forward;
    private MotionController motionController;

    public RemoteControl(int pinInfrared, MotionController motionController) {
        this.infrared = new Infrared(pinInfrared);
        this.forward = true;
        this.motionController = motionController;
    }

    public void actions(){
        switch (this.buttonValue){
            case 0: // 1
                this.speedValue = 10;
                drive();
                break;
            case 64: // 2
                this.speedValue = 20;
                drive();
                break;
            case 32: // 3
                this.speedValue = 30;
                drive();
                break;
            case 96: // 4
                this.speedValue = 40;
                drive();
                break;
            case 16: // 5
                this.speedValue = 50;
                drive();
                break;
            case 80: // 6
                this.speedValue = 60;
                drive();
                break;
            case 48: // 7
                this.speedValue = 70;
                drive();
                break;
            case 112: // 8
                this.speedValue = 80;
                drive();
                break;
            case 8: // 9
                this.speedValue = 90;
                drive();
                break;
            /*case ... // 0
                this.speedValue = 100;
                drive();
                break;
            case ... // pijltje
                this.speedValue = 0;
                drive();
                break;*/
        }
    }

    public void drive(){
        if (forward){
            motionController.setSpeed(buttonValue);
        }
        else {
            motionController.setSpeed(-buttonValue);
        }
    }

    public void update() {
        this.buttonValue = infrared.getValue();
        if (this.buttonValue != -1){
            actions();
        }
    }

}
