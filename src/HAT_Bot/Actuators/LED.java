package HAT_Bot.Actuators;

import HAT_Bot.Logic.StoppableTimer;
import HAT_Bot.Logic.Updatable;
import TI.BoeBot;

public class LED implements Actuator, Updatable {
    private int pin;
    private boolean isOn;
    private StoppableTimer LEDTimer;

    public LED(int pin){
        this.pin = pin;
        this.isOn = true;
        this.LEDTimer = new StoppableTimer(1000);
        this.LEDTimer.start();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void setOn(boolean on) {
        this.isOn = on;
    }

    @Override
    public int getPin() {
        return this.pin;
    }

    @Override
    public void update() {
        if(this.LEDTimer.timeout()){
            this.isOn=!this.isOn;
            BoeBot.digitalWrite(this.pin,this.isOn);
        }
    }
}
