package HAT_Bot.Hardware.Sensors;

/**
 * Rules for al the Senor classes.
 */
public interface Sensor {

    int getValue(); // Get the value.
    int getPin(); // Gets the pin of the sensor.
    boolean getBoolean(); // Checks if they are true or false.

}
