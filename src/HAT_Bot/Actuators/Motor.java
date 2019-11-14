package HAT_Bot.Actuators;

import TI.Servo;
import TI.Timer;

public class Motor implements Updatable {


    private Servo servo;
    private Timer timer;

    public  Motor(int pin) {
        this.servo = new Servo(pin);
        this.timer = new Timer(10);
    }

    public void setSpeed(int speed) {
        int speedNew = speed*2+1500;
    }



    @Override
    public void update() {


        if (timer.timeout()) {
            servo.stop();
        }
    }
}
