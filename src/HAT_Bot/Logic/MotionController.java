package HAT_Bot.Logic;

import HAT_Bot.Actuators.Motor;
import HAT_Bot.Sensors.Whisker;
import TI.Timer;

public class MotionController implements Updatable {

    private Motor leftMotor;
    private Motor rightMotor;

    private Whisker leftWhisker;
    private Whisker rightWhisker;


    public MotionController(int pinLeftMotor, int pinRightMotor, int pinLeftWhisker, int pinRightWhisker) {
        this.leftMotor = new Motor(pinLeftMotor, false);
        this.rightMotor = new Motor(pinRightMotor, true);

        this.leftWhisker = new Whisker(pinLeftWhisker);
        this.rightWhisker = new Whisker(pinRightWhisker);
    }

    public void turnLeft() {

    }

    public void turnLeft(int degrees){

    }

    public void turnRight(){

    }

    public void turnRight(int degrees){

    }

    @Override
    public void update() {

    }
}
