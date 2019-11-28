package HAT_Bot.Actuators;

import HAT_Bot.Controllers.Updatable;
import TI.Servo;
import TI.Timer;

public class Motor implements Updatable {

    private int pin;
    private boolean inverted;
    private Servo servo;
    private Timer timer;

    /**
     * Controls a Servo
     * @param pin the number of the pin the servo is connected to
     * @param inverted true if the turnrotation is inverted, else false
     */
    public  Motor(int pin, boolean inverted) {
        this.pin = pin;
        this.inverted = inverted;
        this.timer = new Timer(10);
        this.servo = new Servo(pin);
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
     * Updates the servo
     */
    @Override
    public void update() {


        if (timer.timeout()) {
            servo.stop();
        }
    }
}
