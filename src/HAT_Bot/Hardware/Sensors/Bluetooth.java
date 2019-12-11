package HAT_Bot.Hardware.Sensors;

import HAT_Bot.Controllers.Updatable;
import TI.SerialConnection;

/**
 * This class has as input the values form the bluetooth and look if they are bigger than 0.
 */

public class Bluetooth implements Sensor {

    private SerialConnection serialConnection = new SerialConnection();

    public Bluetooth() {

    }

    /**
     * This method looks if the values are bigger than 0, than it can return the value. if not than it returns -1
     * as sign for an incorrect value.
     * @return the value or -1.
     */
    @Override
    public int getValue() {
        if (this.serialConnection.available() > 0){
            return this.serialConnection.readByte();
        }
        return -1;
    }

    @Override
    public int getPin() {
        return -1;
    }

    /**
     * This method looks if the connection is available.
     * @return if the connection is available.
     */
    @Override
    public boolean getBoolean() {
        if (this.serialConnection.available() > 0) {
            return true;
        }
        return false;
    }

}
