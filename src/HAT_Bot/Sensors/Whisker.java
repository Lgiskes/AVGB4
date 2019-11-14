package HAT_Bot.Sensors;

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
}
