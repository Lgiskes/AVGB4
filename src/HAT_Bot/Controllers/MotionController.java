package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Actuators.Motor;
import TI.BoeBot;
import TI.StoppableTimer;

/**
 * Controls the motors with general commands
 */

public class MotionController implements Updatable {

    private Motor leftMotor;
    private Motor rightMotor;
    private MotionObserver observer;
    private MotionObserver oldObserver;

    private StoppableTimer turnAroundTimer;
    private StoppableTimer goToSpeedLeftTimer;
    private StoppableTimer goToSpeedRightTimer;
    private StoppableTimer turnDegreesTimer;
    private StoppableTimer slightlyForwardTimer;

    private int toSpeed;
    private ManoeuvreObserver manoeuvreObserver;
    private ManoeuvreCommand command;

    private StoppableTimer driveSquareTimer = new StoppableTimer(1000);
    private StoppableTimer driveTriangleTimer = new StoppableTimer(1000);
    private StoppableTimer driveCircleTimer = new StoppableTimer(7000);
    private int turnsMade=0;

    /**
     * @param pinLeftMotor number of the pin connected to the left motor
     * @param pinRightMotor the number of the pin connected to the left motor
     */
    public MotionController(int pinLeftMotor, int pinRightMotor) {
        this.leftMotor = new Motor(pinLeftMotor, false);
        this.rightMotor = new Motor(pinRightMotor, true);
        this.manoeuvreObserver = null;

        this.toSpeed = 0;

        this.turnAroundTimer = new StoppableTimer(1000);
        this.goToSpeedRightTimer = new StoppableTimer(1000);
        this.goToSpeedLeftTimer = new StoppableTimer(1000);
        this.turnDegreesTimer = new StoppableTimer(1000);
        this.slightlyForwardTimer = new StoppableTimer(1000);
        this.turnAroundTimer.stop();
        this.turnDegreesTimer.stop();
        this.goToSpeedLeftTimer.stop();
        this.goToSpeedRightTimer.stop();
        this.slightlyForwardTimer.stop();
        this.driveSquareTimer.stop();
        this.driveTriangleTimer.stop();
        this.driveCircleTimer.stop();
    }

    /**
     * Sets the command
     * @param command the command the changState function executes after the manoeuvre has ended
     */
    public void setCommand(ManoeuvreCommand command) {
        this.command = command;
        leftMotor.setCommand(command);
        rightMotor.setCommand(command);
    }

    public void setManoeuvreObserver(ManoeuvreObserver manoeuvreObserver) {
        this.manoeuvreObserver = manoeuvreObserver;
        this.rightMotor.setManoeuvreObserver(manoeuvreObserver);
        this.leftMotor.setManoeuvreObserver(manoeuvreObserver);
    }

    /**
     * Moves the bot slightly forward after passing a intersection
     * @param milliSeconds the time the bot drives forward in milliseconds
     */
    public void slightlyForward(int speed, int milliSeconds) {
        setSpeed(speed);
        BoeBot.wait(milliSeconds);
        this.manoeuvreObserver.onManoeuvreDetected(this, this.command);
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
     * Stops the bot instantly
     */
    public void emergencyBrake (){
        this.leftMotor.setSpeed(0);
        this.rightMotor.setSpeed(0);
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
            this.manoeuvreObserver.onManoeuvreDetected(this, this.command);
        }

        //checks if the goToSpeed action for the left Motor has ended
        if(this.goToSpeedLeftTimer.timeout()) {
            this.leftMotor.update();
            if(this.leftMotor.getSpeed() == this.toSpeed){
                this.goToSpeedLeftTimer.stop();

                onMotionEnd("goToSpeed(0)");

            }
        }

        //checks if the goToSpeed action for the right Motor has ended
        if(this.goToSpeedRightTimer.timeout()) {
            this.rightMotor.update();
            if(this.rightMotor.getSpeed() == this.toSpeed){
                this.goToSpeedRightTimer.stop();
            }
        }


        //checks if one side of a square has been driven
        if(this.driveSquareTimer.timeout()){
            if(this.turnsMade<4){
                this.goToSpeed(0);
                this.driveSquareTimer.stop();
            }
            else{
                this.goToSpeed(0);
                this.driveSquareTimer.stop();
                this.observer = this.oldObserver;
                this.turnsMade=-1;
                this.manoeuvreObserver.onManoeuvreDetected(this, this.command);
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
                this.observer = this.oldObserver;
                this.turnsMade = -1;
                this.manoeuvreObserver.onManoeuvreDetected(this, this.command);
            }
        }

        //checks if the circle action has been completed
        if(this.driveCircleTimer.timeout()){
            this.driveCircleTimer.stop();
            this.emergencyBrake();
            this.manoeuvreObserver.onManoeuvreDetected(this, this.command);
        }
    }

    /**
     * Stops the current manoeuvre instantly
     */
    public void killManoeuvre(){
        this.observer = this.oldObserver;
        this.driveTriangleTimer.stop();
        this.driveSquareTimer.stop();
        this.driveCircleTimer.stop();
        this.turnsMade=-1;
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
     * @param accelerationTime the time it takes to reach the desired speed
     */
    public void goToSpeed(int speed, int accelerationTime){
        int rightTime;
        int leftTime;
        if(speed-this.rightMotor.getSpeed() == 0){
            rightTime = 1000;
        }
        else {
            rightTime = accelerationTime / (Math.abs(speed - this.rightMotor.getSpeed()));
        }
        if(speed - this.leftMotor.getSpeed() == 0){
            leftTime = 1000;
        }
        else{
            leftTime = accelerationTime / (Math.abs(speed - this.leftMotor.getSpeed()));
        }

        this.goToSpeedLeftTimer.setInterval(leftTime);
        this.goToSpeedRightTimer.setInterval(rightTime);
        this.goToSpeedLeftTimer.start();
        this.goToSpeedRightTimer.start();

        this.toSpeed = speed;
        this.rightMotor.setToSpeed(speed,rightTime);
        this.leftMotor.setToSpeed(speed,leftTime);
    }

    public void goToSpeed(int speed){
        goToSpeed(speed, 1000);
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
        if(this.observer == null){
            this.turnsMade = 0;
            this.driveSquareTimer.start();
            this.oldObserver = this.observer;
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
            this.goToSpeed(0);
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
            this.oldObserver = this.observer;
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
            this.goToSpeed(0);
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
