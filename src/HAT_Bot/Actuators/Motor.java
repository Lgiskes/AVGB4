package HAT_Bot.Actuators;

import HAT_Bot.Controllers.Updatable;
import TI.Servo;
import TI.Timer;

public class Motor implements Updatable {

    private int pin;
    private boolean inverted;
    private Servo servo;
    private Timer timer;

    public  Motor(int pin, boolean inverted) {
        this.pin = pin;
        this.inverted = inverted;
        this.timer = new Timer(10);
        this.servo = new Servo(pin);
    }

    public int getSpeed() {
        int speed = (this.servo.getPulseWidth() - 1500) / 2;

        if(this.inverted){
            speed *= -1;
        }

        return speed;
    }

    public void setSpeed(int speed) {
        speed = Math.max(-100, speed);
        speed = Math.min(100, speed);

        if(this.inverted){
            speed *= -1;
        }

        int newSpeed = speed * 2 + 1500;
        this.servo.update(newSpeed);
    }

    @Override
    public void update() {


        if (timer.timeout()) {
            servo.stop();
        }
    }
}
