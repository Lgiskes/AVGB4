package HAT_Bot.Actuators;

import HAT_Bot.Logic.StoppableTimer;
import HAT_Bot.Logic.Updatable;
import TI.BoeBot;

public class LED implements Actuator {
    private int pin;
    private boolean isOn;

    public LED(int pin){
        this.pin = pin;
        this.isOn = true;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void setOn(boolean on) {
        this.isOn = on;
        BoeBot.digitalWrite(this.pin, on);
    }

    @Override
    public int getPin() {
        return this.pin;
    }

}
