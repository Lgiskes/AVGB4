package HAT_Bot.Hardware.Actuators;

import HAT_Bot.Controllers.Updatable;
import TI.BoeBot;
import TI.StoppableTimer;

/**
 * Controls a beeper
 */
public class Beeper implements Actuator, Updatable {
    private boolean isOn;
    private int pin;

    private StoppableTimer beepTimer;
    private int frequencyToPlay = -1;

    /**
     * @param pin the number of the pin connected to the beeper
     */
    public Beeper(int pin){
        this.pin = pin;
        this.isOn = false;

        this.beepTimer = new StoppableTimer(1000);
        this.beepTimer.stop();
    }

    /**
     * Checks if the beeper is on
     * @return true if the beeper is on, else it returns false
     */
    @Override
    public boolean isOn() {
        return this.isOn;
    }

    /**
     * Sets the beeper on or off
     * @param on true sets the beeper on, false sets the beeper off
     */
    @Override
    public void setOn(boolean on) {
        this.isOn=on;
    }

    /**
     * Gets the pin the beeper is connected to
     * @return
     */
    @Override
    public int getPin() {
        return this.pin;
    }

    /**
     * Makes a sound with a certain frequency for a given duration
     * @param frequency the frequancy of the sound
     * @param playTime the duration of the sound
     */
    public void makeSound(int frequency, int playTime){
        if(frequency <= 0 || playTime <= 0){
            return;
        }

        if(this.isOn){
            this.frequencyToPlay = frequency;
            this.beepTimer.setInterval(playTime);
            this.beepTimer.start();
        }
    }

    /**
     * Updates the beeper
     */
    @Override
    public void update() {
        //checks if the duration has been reached
        if(this.beepTimer.timeout()){
            this.beepTimer.stop();
            this.frequencyToPlay = -1;

        }
        else if(this.frequencyToPlay > 0) {

            if(!this.isOn){
                this.beepTimer.stop();
                this.frequencyToPlay = -1;
            }
            else {
                BoeBot.freqOut(this.pin, this.frequencyToPlay, 15);
            }

        }

    }
}
