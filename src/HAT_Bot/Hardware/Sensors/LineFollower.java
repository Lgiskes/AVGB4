package HAT_Bot.Hardware.Sensors;

import HAT_Bot.Controllers.Updatable;
import TI.BoeBot;
import TI.StoppableTimer;

public class LineFollower implements Sensor, Updatable {
    private int pin;
    private int value;
    private StoppableTimer timerLineSensor;

    public LineFollower(int pin) {
        this.pin = pin;
        this.value = 0;
        this.timerLineSensor = new StoppableTimer(100);
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public int getPin() {
        return this.pin;
    }

    @Override
    public boolean getBoolean() {
        if (this.value > 1000 && this.value < 1750) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (timerLineSensor.timeout()) {
            this.value = BoeBot.analogRead(this.pin);

        }
    }
}
