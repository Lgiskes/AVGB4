package HAT_Bot.Sensors;

import TI.StoppableTimer;
import HAT_Bot.Controllers.Updatable;
import TI.BoeBot;

/**
 * This class has as input the values from the remote control that we will be converted into a bit number.
 */
public class Infrared implements Sensor, Updatable {

    private int pin;
    private int value;
    private StoppableTimer timerInfrared;

    /**
     * Constructor for the Infrared.
     * @param pin, gets the values form the infrared.
     */
    public Infrared(int pin) {
        this.pin = pin;
        this.timerInfrared = new StoppableTimer(10);
        this.timerInfrared.start();
    }

    /**
     * @return gets the calculated values of the infrared.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @return gets the pin of the infrared sensor.
     */
    public int getPin() {
        return this.pin;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    /**
     * This method will transfer the incoming values of the infrared to an easy to read bit number.
     */
    @Override
    public void update() {
        if (this.timerInfrared.timeout()) { // Happens only when the timer finished.
            boolean validValue = true;
            int pulse = BoeBot.pulseIn(this.pin, false, 6000);
            int bitNumber = 0;
            if (pulse > 2000) {
                int lengtes[] = new int[12]; // An List that is 12 numbers long;
                for (int i = 0; i < 12; i++) {
                    // Puts the pulse of the infrared in an the List.
                    lengtes[i] = BoeBot.pulseIn(this.pin, false, 20000);
                }
                // For every number in the List we are going to look if it equals a 0 or an 1.
                for (int i = lengtes.length - 1; i >= 0; i--) {
                    // When the pulse is between 200 and 850, the value equals an 0 in the bit number
                    if (lengtes[i] > 200 && lengtes[i] < 850) {
                        bitNumber += 0;
                        // We bitshift the bit number, so there can be put the 0 on the end of the number.
                        bitNumber = bitNumber << 1;
                    } else if // When the pulse is between 950 and 1450, the value equals an 0 in the bit number.
                    (lengtes[i] > 950 && lengtes[i] < 1450) {
                        bitNumber += 1;
                        // We bitshift the bit number, so there can be put the 1 ono the end of the numer.
                        bitNumber = bitNumber << 1;
                    } else {
                        validValue = false; // Looks if the value is valid.
                    }
                }
                if (validValue) {
                    this.value = bitNumber; // If al the numbers were valid, the value is set to the bit number.
                }
                else {
                    this.value = -1; // When the value is not valid, the value is set to -1.
                }
            }
            else {
                this.value = -1; // Happens when the timer is not over.
            }
        }
    }
}
