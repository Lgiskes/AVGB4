package HAT_Bot.Actuators;

import HAT_Bot.Logic.StoppableTimer;
import HAT_Bot.Logic.Updatable;
import TI.BoeBot;

public class Beeper implements Actuator {
    private boolean isOn;
    private int pin;

    public Beeper(int pin){
        this.pin = pin;
        this.isOn = false;
    }
    @Override
    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public void setOn(boolean on) {
        this.isOn=on;
    }

    @Override
    public int getPin() {
        return this.pin;
    }

    public void makeSound(int length){
        int frequency = 1000;
        if(this.isOn){
            BoeBot.freqOut(this.pin, frequency, length);
        }
    }
}
