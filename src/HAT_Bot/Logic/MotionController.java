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
    private StoppableTimer goToSpeedLeftTimer;
    private StoppableTimer goToSpeedRightTimer;
    private StoppableTimer turnDegreesTimer;

    private int toSpeed;

    public MotionController(int pinLeftMotor, int pinRightMotor, int pinLeftWhisker, int pinRightWhisker) {
        this.leftMotor = new Motor(pinLeftMotor, false);
        this.rightMotor = new Motor(pinRightMotor, true);

        this.leftWhisker = new Whisker(pinLeftWhisker);
        this.rightWhisker = new Whisker(pinRightWhisker);

        this.toSpeed = 0;

        this.turnAroundTimer = new StoppableTimer(1000);
        this.goToSpeedRightTimer = new StoppableTimer(1000);
        this.goToSpeedLeftTimer = new StoppableTimer(1000);
        this.turnDegreesTimer = new StoppableTimer(1000);
        turnAroundTimer.stop();
        turnDegreesTimer.stop();
        goToSpeedLeftTimer.stop();
        goToSpeedRightTimer.stop();

    }

    public void turnLeft() {
        turnLeft(90);
    }

    public void turnLeft(int degrees){
        turnDegrees(degrees, -100);
    }

    public void turnRight(){
        turnRight(90);
    }

    public void turnRight(int degrees){
        turnDegrees(degrees, 100);
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

        if(turnAroundTimer.isStopped() && turnDegreesTimer.isStopped()){
            if (this.rightWhisker.getValue() == 0 && this.leftWhisker.getValue() == 0){
                emergencyBrake();
                turnAround();

            }else if(this.rightWhisker.getValue() == 0){
                turnLeft();


            }else if(this.leftWhisker.getValue() == 0){
                turnRight();

            }else{
                leftMotor.setSpeed(100);
                rightMotor.setSpeed(100);
            }
        }

        if(turnDegreesTimer.timeout()){
            this.emergencyBrake();
            turnDegreesTimer.stop();
        }

        if(this.goToSpeedRightTimer.timeout()){
            int currentSpeed = rightMotor.getSpeed();
            if(currentSpeed<this.toSpeed){
                rightMotor.setSpeed(currentSpeed+1);
            }
            else if(currentSpeed>this.toSpeed){
                rightMotor.setSpeed(currentSpeed-1);
            }
            else{
                goToSpeedRightTimer.stop();
            }
        }

        if(this.goToSpeedLeftTimer.timeout()){
            int currentSpeed = leftMotor.getSpeed();
            if(currentSpeed<this.toSpeed){
                leftMotor.setSpeed(currentSpeed+1);
            }
            else if(currentSpeed>this.toSpeed){
                leftMotor.setSpeed(currentSpeed-1);
            }
            else{
                goToSpeedLeftTimer.stop();
            }
        }

    }

    private void turnDegrees(int graden,int speed){
        speed = Math.max(-100, speed);
        speed = Math.min(100,speed);
        double omloopConstante=1.45;
        int meetSnelheid=100;
        double tijdMillisec = (graden*omloopConstante*meetSnelheid*1000)/(360.0*Math.abs(speed));
        rightMotor.setSpeed(speed);
        leftMotor.setSpeed(-speed);
        System.out.println(tijdMillisec);
        this.turnDegreesTimer.setInterval((int)tijdMillisec);
        this.turnDegreesTimer.start();
    }

    public void forward(){
        rightMotor.setSpeed(100);
        leftMotor.setSpeed(100);
    }

    public void backward(){
        rightMotor.setSpeed(-100);
        leftMotor.setSpeed(-100);
    }

    public void goToSpeed(int speed){
        int accelerationTime = 1000;
        int rightTime=accelerationTime/(speed-rightMotor.getSpeed());
        int leftTime = accelerationTime/(speed-leftMotor.getSpeed());
        this.goToSpeedRightTimer.setInterval(rightTime);
        this.goToSpeedLeftTimer.setInterval(leftTime);
        goToSpeedLeftTimer.start();
        goToSpeedRightTimer.start();
        this.toSpeed = speed;
    }
}
