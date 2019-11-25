package HAT_Bot.Actuators;

import HAT_Bot.Controllers.Updatable;
import TI.BoeBot;
import TI.StoppableTimer;

public class Beeper implements Actuator, Updatable {
    private boolean isOn;
    private int pin;

    private StoppableTimer beepTimer;
    private int frequencyToPlay = -1;


    public Beeper(int pin){
        this.pin = pin;
        this.isOn = false;

        this.beepTimer = new StoppableTimer(1000);
        this.beepTimer.stop();
    }
    @Override
    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public void setOn(boolean on) {
        this.isOn=on;
    }

    @Override
    public int getPin() {
        return this.pin;
    }

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

    @Override
    public void update() {

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
