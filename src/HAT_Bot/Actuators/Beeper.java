package HAT_Bot.Actuators;

import HAT_Bot.Logic.StoppableTimer;
import HAT_Bot.Logic.Updatable;
import TI.BoeBot;

public class Beeper implements Updatable, Actuator {
    private boolean isOn;
    private int pin;
    private StoppableTimer beeperTimer = new StoppableTimer(2000);

    public Beeper(int pin){
        this.pin = pin;
        this.isOn = false;
        beeperTimer.start();
    }

    @Override
    public void update() {
        if(isOn && beeperTimer.timeout()){
            BoeBot.freqOut(this.pin,800, 3000);
        }
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
}
