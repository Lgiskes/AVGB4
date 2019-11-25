package HAT_Bot.Controllers;

import HAT_Bot.Actuators.Motor;
import TI.StoppableTimer;

public class MotionController implements Updatable {

    private Motor leftMotor;
    private Motor rightMotor;
    private MotionObserver observer;

    private StoppableTimer turnAroundTimer;
    private StoppableTimer goToSpeedLeftTimer;
    private StoppableTimer goToSpeedRightTimer;
    private StoppableTimer turnDegreesTimer;

    private int toSpeed;

    private StoppableTimer driveSquareTimer = new StoppableTimer(1000);
    private StoppableTimer driveTriangleTimer = new StoppableTimer(1000);
    private StoppableTimer driveCircleTimer = new StoppableTimer(5500);
    private int turnsMade=0;

    public MotionController(int pinLeftMotor, int pinRightMotor) {
        this.leftMotor = new Motor(pinLeftMotor, false);
        this.rightMotor = new Motor(pinRightMotor, true);
        this.observer = observer;

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

        if(turnDegreesTimer.timeout()){
            this.emergencyBrake();
            turnDegreesTimer.stop();
            onMotionEnd("turnDegrees");
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
                if(goToSpeedLeftTimer.isStopped()){
                    onMotionEnd("goToSpeed(" + toSpeed + ")");
                }
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
                if(goToSpeedRightTimer.isStopped()){
                    onMotionEnd("goToSpeed(" + toSpeed + ")");
                }
            }
        }

        if(driveSquareTimer.timeout()){
            if(this.turnsMade<4){
                this.goToSpeed(0);
                driveSquareTimer.stop();
            }
            else{
                this.goToSpeed(0);
                driveSquareTimer.stop();
                this.observer = null;
                this.turnsMade=-1;
            }
        }

        if(driveTriangleTimer.timeout()){
            if(this.turnsMade<3){
                this.goToSpeed(0);
                driveTriangleTimer.stop();
            }
            else {
                this.goToSpeed(0);
                driveTriangleTimer.stop();
                this.observer = null;
                this.turnsMade = -1;
            }
        }

        if(driveCircleTimer.timeout()){
            driveCircleTimer.stop();
            this.goToSpeed(0);
        }
    }

    private void turnDegrees(int graden,int speed){
        speed = Math.max(-100, speed);
        speed = Math.min(100,speed);
        double omloopConstante=1.45;
        int meetSnelheid=100;
        double tijdMillisec = (graden*omloopConstante*meetSnelheid*1000)/(360.0*Math.abs(speed));
        rightMotor.setSpeed(-speed);
        leftMotor.setSpeed(speed);
        this.turnDegreesTimer.setInterval((int)tijdMillisec);
        this.turnDegreesTimer.start();
    }

    public void forward(){
        goToSpeed(100);
    }

    public void backward(){
        rightMotor.setSpeed(-100);
        leftMotor.setSpeed(-100);
    }

    public void setSpeed(int speed) {
        leftMotor.setSpeed(speed);
        rightMotor.setSpeed(speed);
    }

    public void goToSpeed(int speed){
        int rightTime;
        int leftTime;
        int accelerationTime = 1000;
        if(speed-rightMotor.getSpeed()==0){
            rightTime=1000;
        }
        else {
            rightTime=accelerationTime/(Math.abs(speed-rightMotor.getSpeed()));
        }
        if(speed-leftMotor.getSpeed()==0){
            leftTime=1000;
        }
        else{
            leftTime = accelerationTime/(Math.abs(speed-leftMotor.getSpeed()));
        }

        //System.out.println(speed + "=>" + rightTime + " : " + leftTime);

        this.goToSpeedRightTimer.setInterval(rightTime);
        this.goToSpeedLeftTimer.setInterval(leftTime);
        goToSpeedLeftTimer.start();
        goToSpeedRightTimer.start();
        this.toSpeed = speed;
    }

    public void turnLeftCurve(boolean forward, int speed){
        if (forward){
            this.leftMotor.setSpeed(25*speed/100);
            this.rightMotor.setSpeed(speed);
        }
        else {
            this.leftMotor.setSpeed(-25*speed/100);
            this.rightMotor.setSpeed(-speed);
        }
    }

    public void turnRightCurve(boolean forward, int speed){
        if (forward){
            this.leftMotor.setSpeed(speed);
            this.rightMotor.setSpeed(25*speed/100);
        }
        else {
            this.leftMotor.setSpeed(-speed);
            this.rightMotor.setSpeed(-25*speed/100);
        }
    }

    public void driveSquare(){
        if(observer == null){
            this.turnsMade = 0;
            this.driveSquareTimer.start();
            this.observer = new MotionObserver() {
                @Override
                public void onMotionEnd(MotionController sender, String motionFunction) {

                    if(sender.turnsMade >= 0){
                        if(motionFunction.equals("turnDegrees")){
                            sender.forward();
                            driveSquareTimer.start();
                        }
                        if(motionFunction.equals("goToSpeed(0)")){
                            sender.turnRight();
                            sender.turnsMade++;
                        }
                    }

                }
            };
        }

    }

    public void circle(){
        driveCircleTimer.start();
        turnRightCurve(true, 100);


    }

    public void driveTriangle(){
        if(this.observer == null){
            this.turnsMade = 0;
            this.driveTriangleTimer.start();
            this.observer = new MotionObserver() {
                @Override
                public void onMotionEnd(MotionController sender, String motionFunction) {

                    if(sender.turnsMade >= 0){
                        if(motionFunction.equals("turnDegrees")){
                            sender.forward();
                            driveTriangleTimer.start();
                        }
                        if(motionFunction.equals("goToSpeed(0)")){
                            sender.turnRight(120);
                            sender.turnsMade++;
                        }
                    }
                }
            };
        }
    }


    private void onMotionEnd(String motionFunction){
        if(this.observer != null){
            this.observer.onMotionEnd(this, motionFunction);
        }
    }

}
