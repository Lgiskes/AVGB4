package HAT_Bot.Hardware.Actuators;

/**
 * Rules for the classes of Actuators.
 */
public interface Actuator {

    boolean isOn(); // Checks if the Actuator is on.
    void setOn(boolean on); // Sets the Actuator on or off.
    int getPin(); // Gets the pin of the Actuator.

}
