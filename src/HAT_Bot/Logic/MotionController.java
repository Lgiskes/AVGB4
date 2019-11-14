package HAT_Bot.Logic;

import HAT_Bot.Actuators.Motor;
import HAT_Bot.Sensors.Whisker;
import TI.Timer;

public class MotionController implements Updatable {

    private Motor leftMotor;
    private Motor rightMotor;

    private Whisker leftWhisker;
    private Whisker rightWhisker;

    private Timer turnAroundTimer;

    public MotionController(int pinLeftMotor, int pinRightMotor, int pinLeftWhisker, int pinRightWhisker) {
        this.leftMotor = new Motor(pinLeftMotor, false);
        this.rightMotor = new Motor(pinRightMotor, true);

        this.leftWhisker = new Whisker(pinLeftWhisker);
        this.rightWhisker = new Whisker(pinRightWhisker);

        this.turnAroundTimer = new Timer(20);

    }

    public void turnLeft() {

    }

    public void turnLeft(int degrees){

    }

    public void turnRight(){

    }

    public void turnRight(int degrees){

    }

    public void turnAround(){
        leftMotor.setSpeed(-100);
        rightMotor.setSpeed(-100);
        turnAroundTimer.setInterval(500);

    }

    public void emergencyBrake (){
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
    }

    @Override
    public void update() {
        if (turnAroundTimer.timeout()) {
            turnRight(180);
        }

        if (this.rightWhisker.getValue() == 0 && this.leftWhisker.getValue() == 0){
            emergencyBrake();
            turnAround();

        }else if(this.rightWhisker.getValue() == 0){

        }else if(this.leftWhisker.getValue() == 0){

        }else{
            leftMotor.setSpeed(100);
            rightMotor.setSpeed(100);
        }

    }
}
