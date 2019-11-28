package HAT_Bot.Controllers;

import HAT_Bot.Actuators.Motor;
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
        turnAroundTimer.stop();
        turnDegreesTimer.stop();
        goToSpeedLeftTimer.stop();
        goToSpeedRightTimer.stop();

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
        leftMotor.setSpeed(-100);
        rightMotor.setSpeed(-100);
        turnAroundTimer.setInterval(500);
        turnAroundTimer.start();

    }

    /**
     * Stops the bot instantly
     */
    public void emergencyBrake (){
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
    }

    /**
     * Updates the motioncontroller
     */
    @Override
    public void update() {

        //checks if the turnDegrees action has ended
        if(turnDegreesTimer.timeout()){
            this.emergencyBrake();
            turnDegreesTimer.stop();
            onMotionEnd("turnDegrees");
        }

        //checks if the goToSpeed action has ended for the right motor
        if(this.goToSpeedRightTimer.timeout()){
            int currentSpeed = rightMotor.getSpeed();
            if(currentSpeed < this.toSpeed){
                rightMotor.setSpeed(currentSpeed + 1);
            }
            else if(currentSpeed>this.toSpeed){
                rightMotor.setSpeed(currentSpeed - 1);
            }
            else{
                goToSpeedRightTimer.stop();
                if(goToSpeedLeftTimer.isStopped()){
                    onMotionEnd("goToSpeed(" + toSpeed + ")");
                }
            }
        }

        //checks if the goToSpeed action has ended for the left motor
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

        //checks if one side of a square has been driven
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

        //checks if one side of a triangle has been driven
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

        //checks if the circle action has been completed
        if(driveCircleTimer.timeout()){
            driveCircleTimer.stop();
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
        rightMotor.setSpeed(-speed);
        leftMotor.setSpeed(speed);
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
        rightMotor.setSpeed(-100);
        leftMotor.setSpeed(-100);
    }

    /**
     * Instantly sets the speed of the bot to a given speed
     * @param speed the speed of the bot
     */
    public void setSpeed(int speed) {
        leftMotor.setSpeed(speed);
        rightMotor.setSpeed(speed);
    }

    /**
     * Slowly accelerates the bot to the given speed
     * @param speed the speed to which the bot accelerates
     */
    public void goToSpeed(int speed){
        int rightTime;
        int leftTime;
        int accelerationTime = 1000;
        if(speed-rightMotor.getSpeed() == 0){
            rightTime = 1000;
        }
        else {
            rightTime = accelerationTime / (Math.abs(speed - rightMotor.getSpeed()));
        }
        if(speed - leftMotor.getSpeed() == 0){
            leftTime = 1000;
        }
        else{
            leftTime = accelerationTime / (Math.abs(speed - leftMotor.getSpeed()));
        }

        this.goToSpeedRightTimer.setInterval(rightTime);
        this.goToSpeedLeftTimer.setInterval(leftTime);
        goToSpeedLeftTimer.start();
        goToSpeedRightTimer.start();
        this.toSpeed = speed;
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
            this.rightMotor.setSpeed(25*speed/100);
        }
        else {
            this.leftMotor.setSpeed(-speed);
            this.rightMotor.setSpeed(-25*speed/100);
        }
    }

    /**
     * The bot drives a square
     */
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

    /**
     * the bot drives a circle
     */
    public void driveCircle(){
        driveCircleTimer.start();
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
