package HAT_Bot.Sensors;

import TI.StoppableTimer;
import HAT_Bot.Controllers.Updatable;
import TI.BoeBot;

/**
 * This class takes care of the hardware layer of the ultrasone sensor
 * It saves the distance between the sensor and an object every 50 ms
 * This value can be retrieved with the get function for further use
 */
public class Ultrasone implements Sensor, Updatable {

    private int pinTrigger;
    private int pinEcho;
    private int value;
    private StoppableTimer timerUltrasone;
    private StoppableTimer timerShort;

    /**
     * Constructor for adding a ultrasone sensor
     * @param pinTrigger The number of the pin connected to the trigger
     * @param pinEcho The number of the pin connected to the echo
     */
    public Ultrasone(int pinTrigger, int pinEcho) {
        this.pinTrigger = pinTrigger;
        this.pinEcho = pinEcho;
        this.timerUltrasone = new StoppableTimer(50);
        this.timerShort = new StoppableTimer(1);
    }

    /**
     * Getter for the value of the ultrasone
     * @return The last value measured by the ultrasone sensor
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * Getter for the pin the ultrasone sensor is connected to
     * @return The pin connection for the ultrasone
     */
    @Override
    public int getPin() {
        return this.pinEcho;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    /**
     * The update method measures the distance between the sensor and an object, this happens every 50 ms
     */
    @Override
    public void update() {
        //sends a short burst of sound
        if (this.timerUltrasone.timeout()) {
            BoeBot.digitalWrite(0, true);
            this.timerShort.start();
            this.timerUltrasone.stop();
        }

        //measures the echo of the sound and calculates and saves the distance in cm
        if (this.timerShort.timeout()) {
            this.timerShort.stop();
            BoeBot.digitalWrite(0, false);

            int pulse = BoeBot.pulseIn(1, true, 10000);
            this.value = pulse / 58;

            this.timerUltrasone.start();
        }
    }
}
