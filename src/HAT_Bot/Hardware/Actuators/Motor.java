package HAT_Bot.Hardware.Actuators;

import HAT_Bot.Controllers.MotionObserver;
import HAT_Bot.Controllers.Updatable;
import TI.Servo;
import TI.StoppableTimer;
import TI.Timer;

public class Motor implements Updatable {

    private int pin;
    private boolean inverted;
    private Servo servo;
    private StoppableTimer timer;
    private int toSpeed;
    private MotionObserver motionObserver;

    /**
     * Controls a Servo
     * @param pin the number of the pin the servo is connected to
     * @param inverted true if the turnrotation is inverted, else false
     */
    public  Motor(int pin, boolean inverted) {
        this.pin = pin;
        this.inverted = inverted;
        this.toSpeed = 0;
        this.timer = new StoppableTimer(10);
        this.timer.stop();
        this.servo = new Servo(pin);
        this.motionObserver = null;
    }

    /**
     * Sets the toSpeed
     * @param toSpeed the speed the motor is approaching
     */
    public void setToSpeed(int toSpeed){
        this.toSpeed = toSpeed;
    }

    /**
     * Gets the speed of the servo from -100 to 100
     * @return the speed of the servo
     */
    public int getSpeed() {
        int speed = (this.servo.getPulseWidth() - 1500) / 2;

        if(this.inverted){
            speed *= -1;
        }

        return speed;
    }

    /**
     * Sets the speed of a servo
     * @param speed the speed from -100 to 100
     */
    public void setSpeed(int speed) {
        speed = Math.max(-100, speed);
        speed = Math.min(100, speed);

        if(this.inverted){
            speed *= -1;
        }

        int newSpeed = speed * 2 + 1500;
        this.servo.update(newSpeed);
    }

    /**
     * Sets the timer
     * @param interval the interval in millseconds
     */
    public void setTimer(int interval){
        this.setTimer(interval);
    }

    /**
     * Starts the timer
     */
    public void startTimer(){
        this.timer.start();
    }

    /**
     * Updates the servo
     */
    @Override
    public void update() {

        if(this.timer.timeout()){
            int currentSpeed = this.getSpeed();
            if(currentSpeed < this.toSpeed){
                this.setSpeed(currentSpeed + 1);
            }
            else if(currentSpeed>this.toSpeed){
                this.setSpeed(currentSpeed - 1);
            }
            else{
                this.timer.stop();
                    onMotionEnd("goToSpeed(" + this.toSpeed + ")");
            }
        }
    }

    /**
     * Observes the motion and sends it to the motionController
     * @param motionFunction
     */
    private void onMotionEnd(String motionFunction){
        if(this.motionObserver != null){
            this.motionObserver.onMotionEnd(null, motionFunction);
        }
    }
}
