package HAT_Bot.Logic;

import HAT_Bot.Actuators.Motor;
import HAT_Bot.Sensors.Whisker;
import TI.Timer;

public class MotionController implements Updatable {

    private Motor leftMotor;
    private Motor rightMotor;

    private Whisker leftWhisker;
    private Whisker rightWhisker;

    private StoppableTimer turnAroundTimer;
    private StoppableTimer turnRightTimer;
    private StoppableTimer turnLeftTimer;

    public MotionController(int pinLeftMotor, int pinRightMotor, int pinLeftWhisker, int pinRightWhisker) {
        this.leftMotor = new Motor(pinLeftMotor, false);
        this.rightMotor = new Motor(pinRightMotor, true);

        this.leftWhisker = new Whisker(pinLeftWhisker);
        this.rightWhisker = new Whisker(pinRightWhisker);

        this.turnAroundTimer = new StoppableTimer(1000);
        this.turnRightTimer = new StoppableTimer(1000);
        this.turnLeftTimer = new StoppableTimer(1000);
        turnAroundTimer.stop();
        turnRightTimer.stop();
        turnLeftTimer.stop();

    }

    public void turnLeft() {
        leftMotor.setSpeed(-50);
        rightMotor.setSpeed(50);
        turnLeftTimer.setInterval(1000);
        turnLeftTimer.start();

    }

    public void turnLeft(int degrees){

    }

    public void turnRight(){
        leftMotor.setSpeed(50);
        rightMotor.setSpeed(-50);
        turnRightTimer.setInterval(1000);
        turnRightTimer.start();

    }

    public void turnRight(int degrees){

    }

    public void turnAround(){
        leftMotor.setSpeed(-100);
        rightMotor.setSpeed(-100);
        turnAroundTimer.setInterval(500);
        turnAroundTimer.start();

    }

    public void emergencyBrake (){
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
    }

    @Override
    public void update() {
        if (turnAroundTimer.timeout()) {
            turnAroundTimer.stop();
            turnRight(180);
        }
        if (turnRightTimer.timeout()) {
            turnRightTimer.stop();
            emergencyBrake();
        }
        if (turnLeftTimer.timeout()){
            turnLeftTimer.stop();
            emergencyBrake();
        }

        if (this.rightWhisker.getValue() == 0 && this.leftWhisker.getValue() == 0){
            emergencyBrake();
            turnAround();

        }else if(this.rightWhisker.getValue() == 0){
            //turnLeft();


        }else if(this.leftWhisker.getValue() == 0){
            //turnRight();

        }else{
            leftMotor.setSpeed(100);
            rightMotor.setSpeed(100);
        }

    }
}
