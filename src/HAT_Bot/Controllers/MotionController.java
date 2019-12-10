package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Actuators.Motor;
import TI.StoppableTimer;

/**
 * Controls the motors with general commands
 */

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

    /**
     * @param pinLeftMotor number of the pin connected to the left motor
     * @param pinRightMotor the number of the pin connected to the left motor
     */

    public MotionController(int pinLeftMotor, int pinRightMotor) {
        this.leftMotor = new Motor(pinLeftMotor, false);
        this.rightMotor = new Motor(pinRightMotor, true);
        this.observer = observer;

        this.toSpeed = 0;

        this.turnAroundTimer = new StoppableTimer(1000);
        this.goToSpeedRightTimer = new StoppableTimer(1000);
        this.goToSpeedLeftTimer = new StoppableTimer(1000);
        this.turnDegreesTimer = new StoppableTimer(1000);
        this.turnAroundTimer.stop();
        this.turnDegreesTimer.stop();
        this.goToSpeedLeftTimer.stop();
        this.goToSpeedRightTimer.stop();

    }

    /**
     * turns the bot 90 degrees counter-clockwise
     */
    public void turnLeft() {

        turnLeft(90);
    }


    /**
     * turns the bot the given amount of degrees counter-clockwise
     * @param degrees the amount of degrees the bot turns counter-clockwise
     */
    public void turnLeft(int degrees){

        turnDegrees(degrees, -100);
    }

    /**
     * turns the bot 90 degrees clockwise
     */
    public void turnRight()
    {
        turnRight(90);
    }

    /**
     * turns the bot the given amount of degrees clockwise
     * @param degrees the amount of degrees the bot turns clockwise
     */
    public void turnRight(int degrees){

        turnDegrees(degrees, 100);
    }

    /**
     * turns the bot 180 degrees
     */

    public void turnAround(){
        this.leftMotor.setSpeed(-100);
        this.rightMotor.setSpeed(-100);
        this.turnAroundTimer.setInterval(500);
        this.turnAroundTimer.start();

    }

    /**
     * Stops the bot instantly
     */
    public void emergencyBrake (){
        this.leftMotor.setSpeed(0);
        this.rightMotor.setSpeed(0);
        this.rightMotor.setToSpeed(0, 1);
        this.leftMotor.setToSpeed(0, 1);
        this.toSpeed = 0;
    }

    /**
     * Updates the motioncontroller
     */
    @Override
    public void update() {

        //checks if the turnDegrees action has ended
        if(this.turnDegreesTimer.timeout()){
            this.emergencyBrake();
            this.turnDegreesTimer.stop();
            onMotionEnd("turnDegrees");
        }

        this.leftMotor.update();

        this.rightMotor.update();
        /*
        //checks if the goToSpeed action has ended for the right motor
        if(this.goToSpeedRightTimer.timeout()){
            int currentSpeed = this.rightMotor.getSpeed();
            if(currentSpeed < this.toSpeed){
                this.rightMotor.setSpeed(currentSpeed + 1);
            }
            else if(currentSpeed>this.toSpeed){
               this.rightMotor.setSpeed(currentSpeed - 1);
            }
            else{
                this.goToSpeedRightTimer.stop();
                if(this.goToSpeedLeftTimer.isStopped()){
                    onMotionEnd("goToSpeed(" + this.toSpeed + ")");
                }
            }
        }

        //checks if the goToSpeed action has ended for the left motor
        if(this.goToSpeedLeftTimer.timeout()){
            int currentSpeed = this.leftMotor.getSpeed();
            if(currentSpeed<this.toSpeed){
                this.leftMotor.setSpeed(currentSpeed+1);
            }
            else if(currentSpeed>this.toSpeed){
                this.leftMotor.setSpeed(currentSpeed-1);
            }
            else{
                this.goToSpeedLeftTimer.stop();
                if(this.goToSpeedRightTimer.isStopped()){
                    onMotionEnd("goToSpeed(" + this.toSpeed + ")");
                }
            }
        }*/

        //checks if one side of a square has been driven
        if(this.driveSquareTimer.timeout()){
            if(this.turnsMade<4){
                this.goToSpeed(0);
                this.driveSquareTimer.stop();
            }
            else{
                this.goToSpeed(0);
                this.driveSquareTimer.stop();
                this.observer = null;
                this.turnsMade=-1;
            }
        }

        //checks if one side of a triangle has been driven
        if(this.driveTriangleTimer.timeout()){
            if(this.turnsMade<3){
                this.goToSpeed(0);
                this.driveTriangleTimer.stop();
            }
            else {
                this.goToSpeed(0);
                this.driveTriangleTimer.stop();
                this.observer = null;
                this.turnsMade = -1;
            }
        }

        //checks if the circle action has been completed
        if(this.driveCircleTimer.timeout()){
            this.driveCircleTimer.stop();
            this.goToSpeed(0);
        }
    }

    /**
     * Turns the bot counter-clockwise with a given speed
     * @param speed the speed the bot turns
     */
    public void turningLeft(int speed){
        this.rightMotor.setSpeed(speed);
        this.leftMotor.setSpeed(-speed);
    }

    /**
     * Turns the bot clockwise with a given speed
     * @param speed the speed the bot turns
     */
    public void turningRight(int speed){
        this.rightMotor.setSpeed(-speed);
        this.leftMotor.setSpeed(speed);
    }


    /**
     * Turns the bot a certain amount of degrees with a given speed
     * @param degrees the amount of degrees the bot turns
     * @param speed the speed the bot turns
     */
    private void turnDegrees(int degrees,int speed){
        speed = Math.max(-100, speed);
        speed = Math.min(100,speed);
        double SpeedConstant = 1.45;
        int MeasuredSpeed = 100;
        double timeMillisec = (degrees * SpeedConstant * MeasuredSpeed * 1000) / (360.0 * Math.abs(speed));
        this.rightMotor.setSpeed(-speed);
        this.leftMotor.setSpeed(speed);
        this.turnDegreesTimer.setInterval((int)timeMillisec);
        this.turnDegreesTimer.start();
    }

    /**
     * Instantly sets the speed of the bot to 100% in the forward direction
     */

    public void forward(){
        goToSpeed(100);
    }

    /**
     * Instantly sets the speed of the bot to 100% in the backward direction
     */
    public void backward(){
        this.rightMotor.setSpeed(-100);
        this.leftMotor.setSpeed(-100);
    }

    /**
     * Instantly sets the speed of the bot to a given speed
     * @param speed the speed of the bot
     */
    public void setSpeed(int speed) {
        this.leftMotor.setSpeed(speed);
        this.rightMotor.setSpeed(speed);
    }

    /**
     * Slowly accelerates the bot to the given speed
     * @param speed the speed to which the bot accelerates
     */
    public void goToSpeed(int speed){
        this.goToSpeed(speed, 1000);
    }

    public void goToSpeed(int speed, int accelerationTime){
        int rightTime;
        int leftTime;

        if(speed-this.rightMotor.getSpeed() == 0){
            rightTime = accelerationTime;
        }
        else {
            rightTime = accelerationTime / (Math.abs(speed - this.rightMotor.getSpeed()));
        }
        if(speed - this.leftMotor.getSpeed() == 0){
            leftTime = accelerationTime;
        }
        else{
            leftTime = accelerationTime / (Math.abs(speed - this.leftMotor.getSpeed()));
        }

        this.rightMotor.setToSpeed(speed, rightTime);
        this.leftMotor.setToSpeed(speed, leftTime);
    }

    /**
     * Turns the bot counter-clockwise with a curve
     * @param forward true if the bot is moving forwards, false if the bot is moving backwards
     * @param speed sets the speed the bot turns
     */
    public void turnLeftCurve(boolean forward, int speed){
        if (forward){
            this.leftMotor.setSpeed(25 * speed / 100);
            this.rightMotor.setSpeed(speed);
        }
        else {
            this.leftMotor.setSpeed(-25 * speed / 100);
            this.rightMotor.setSpeed(-speed);
        }
    }

    /**
     * Turns the bot clockwise with a curve
     * @param forward true if the bot is moving forwards, false if the bot is moving backwards
     * @param speed sets the speed the bot turns
     */
    public void turnRightCurve(boolean forward, int speed){
        if (forward){
            this.leftMotor.setSpeed(speed);
            this.rightMotor.setSpeed(10*speed/100);
        }
        else {
            this.leftMotor.setSpeed(-speed);
            this.rightMotor.setSpeed(-10*speed/100);
        }
    }

    /**
     * The bot drives a square
     */
    public void driveSquare(){
        if(this.observer == null){
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

    /**
     * the bot drives a circle
     */
    public void driveCircle(){
        this.driveCircleTimer.start();
        turnRightCurve(true, 100);


    }

    /**
     * the bot drives a triangle
     */
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


    /**
     * Checks if a motion has finished
     * @param motionFunction
     */
    private void onMotionEnd(String motionFunction){
        if(this.observer != null){
            this.observer.onMotionEnd(this, motionFunction);
        }
    }

}
