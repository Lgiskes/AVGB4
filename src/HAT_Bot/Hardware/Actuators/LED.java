package HAT_Bot.Hardware.Actuators;

import TI.BoeBot;

/**
 * Controls a LED
 */
public class LED implements Actuator {
    private int pin;
    private boolean isOn;

    /**
     * @param pin the number of the pin the LED is connected to
     */
    public LED(int pin){
        this.pin = pin;
        this.isOn = true;
    }

    /**
     * Checks if the LED is on
     * @return true when on, false when off
     */
    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     * Sets the LED on or off
     * @param on true for onm false for off
     */
    @Override
    public void setOn(boolean on) {
        this.isOn = on;
        BoeBot.digitalWrite(this.pin, on);
    }

    /**
     * Gets the pin the LED is connected to
     * @return the pin the LED is connected to
     */
    @Override
    public int getPin() {
        return this.pin;
    }

}
