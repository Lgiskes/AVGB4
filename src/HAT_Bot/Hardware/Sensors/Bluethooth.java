package HAT_Bot.Hardware.Sensors;

import HAT_Bot.Controllers.Updatable;

public class Bluethooth implements Sensor, Updatable {
    
    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public int getPin() {
        return 0;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public void update() {

    }

}
