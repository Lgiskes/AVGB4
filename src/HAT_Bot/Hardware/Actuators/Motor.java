package HAT_Bot.Hardware.Actuators;

import HAT_Bot.Controllers.ManoeuvreCommand;
import HAT_Bot.Controllers.ManoeuvreObserver;
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
    private ManoeuvreObserver manoeuvreObserver;
    private ManoeuvreCommand command;

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
        this.manoeuvreObserver = null;
    }

    public void setManoeuvreObserver(ManoeuvreObserver manoeuvreObserver) {
        this.manoeuvreObserver = manoeuvreObserver;
    }

    public void setCommand(ManoeuvreCommand command) {
        this.command = command;
    }

    /**
     * Sets the toSpeed
     * @param toSpeed the speed the motor is approaching
     */
    public void setToSpeed(int toSpeed, int interval){
        this.toSpeed = toSpeed;
        this.timer.setInterval(interval);
        this.timer.start();
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
     * @param goToSpeed true if this function is called from the goToSpeed function
     */
    private void setSpeed(int speed, boolean goToSpeed){
        if(!goToSpeed){
            if(!this.timer.isStopped()){
                this.timer.stop();
                onMotionEnd("goToSpeed(" + this.toSpeed + ")");
            }
        }

        speed = Math.max(-100, speed);
        speed = Math.min(100, speed);

        if(this.inverted){
            speed *= -1;
        }

        int newSpeed = speed * 2 + 1500;
        this.servo.update(newSpeed);
    }

    public void setSpeed(int speed) {
        this.setSpeed(speed, false);
    }

    /**
     * Updates the servo
     */
    @Override
    public void update() {

        if(this.timer.timeout()){
            int currentSpeed = this.getSpeed();
            if(currentSpeed < this.toSpeed){
                this.setSpeed(currentSpeed + 1, true);
            }
            else if(currentSpeed>this.toSpeed){
                this.setSpeed(currentSpeed - 1, true);
            }
            else{
                this.timer.stop();
                    onMotionEnd("goToSpeed(" + this.toSpeed + ")");
                    this.manoeuvreObserver.onManoeuvreDetected(null, this.command);
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
