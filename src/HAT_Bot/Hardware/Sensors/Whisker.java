package HAT_Bot.Hardware.Sensors;

import TI.BoeBot;

public class Whisker implements Sensor {
    private int pin;

    public Whisker(int pin) {
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }

    public int getValue(){
        if(BoeBot.digitalRead(this.pin)){
            return 1;
        }else{
            return 0;
        }
    }

    public boolean getBoolean(){
        return BoeBot.digitalRead(this.pin);
    }
}
