package HAT_Bot.Logic;

import HAT_Bot.Actuators.Motor;
import HAT_Bot.Actuators.Updatable;
import TI.Timer;

public class MotionController implements Updatable {

    private Motor leftMotor;
    private Motor rightMotor;

    private Timer leftTimer;
    private Timer rightTimer;


    public MotionController() {
        this.leftMotor = new Motor(15);
        this.rightMotor = new Motor(14);

        this.rightTimer = new Timer(100);
        this.leftTimer = new Timer (100);
    }

    public void turnLeft() {
        leftMotor.setSpeed(100);
        // leftMotor.start();
        this.leftTimer.setInterval(900);
        this.leftTimer.mark();
    }

    @Override
    public void update() {

        if (this.leftTimer.timeout()) {
            //this.leftMotor.stop();
        }
        if (this.rightTimer.timeout()) {
            //this.rightTimer.stop();
        }
    }
}
