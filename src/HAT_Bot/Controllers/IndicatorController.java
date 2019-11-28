package HAT_Bot.Controllers;

import HAT_Bot.Actuators.Beeper;
import HAT_Bot.Actuators.LED;
import TI.StoppableTimer;

/**
 * Controls the beeper and the LED
 */
public class IndicatorController implements Updatable{
    private LED led;
    private Beeper beeper;
    private int status;
    // 0 =bot is off.  1 =bot is standing still. 2 = bot is driving. 3 = bot is driving backwards 4 = bot found obstacle. 5 =  bot is in front of obstacle
    private StoppableTimer ledTimer = new StoppableTimer(1000);
    private StoppableTimer beeperTimer = new StoppableTimer(1000);

    /**
     * @param ledPin the number of the pin connected to the LED
     * @param beeperPin the number of the pin connected to the beeper
     */
    public IndicatorController(int ledPin, int beeperPin){
        this.led = new LED(ledPin);
        this.beeper = new Beeper(beeperPin);
        this.status = 0;

        this.beeper.setOn(true);
    }

    /**
     * Mutes or unmutes the beeper
     * @param muteState true mutes the beeper, false unmutes the beeper
     */
    public void mute(boolean muteState){
        this.beeper.setOn(!muteState);
    }

    /**
     * Checks if the beeper is muted
     * @return true if the beeper is muted, false if the beeper is not muted
     */
    public boolean getMuteState(){
        return !this.beeper.isOn();
    }

    /**
     * Sets the state of the LED and beeper for the case when the bot is standing still
     */
    public void standingStillIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.status = 1;
    }
    /**
     * Sets the state of the LED and beeper for the case when the bot is driving
     */
    public void drivingIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.ledTimer.setInterval(1000);
        this.beeperTimer.setInterval(5000);
        this.ledTimer.start();
        this.beeperTimer.start();
        this.status = 2;
    }

    public void drivingBackwardsIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.ledTimer.setInterval(500);
        this.beeperTimer.setInterval(1000);
        this.ledTimer.start();
        this.beeperTimer.start();
        this.status = 3;
    }
    /**
     * Sets the state of the LED and beeper for the case when the bot has found an obstacle
     */
    public void foundObstacleIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.ledTimer.setInterval(50);
        this.beeperTimer.setInterval(750);
        this.ledTimer.start();
        this.beeperTimer.start();
        this.status = 4;
    }

    /**
     * Sets the state of the LED and beeper for the case when the bot is in front of an obstacle
     */
    public void inFrontOfObstacleIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.ledTimer.setInterval(50);
        this.beeperTimer.setInterval(1000);
        this.ledTimer.start();
        this.beeperTimer.start();
        this.status = 5;
    }
    /**
     * Updates the beeper and LED
     */
    @Override
    public void update() {
        this.beeper.update();

        switch (this.status){
            case 0: //when the bot is off
                this.led.setOn(true);
                //this.beeper.setOn(false);
                break;
            case 1: //when the bot is standing still
                this.led.setOn(false);
                break;

            case 2: //when the bot is driving
                if(this.ledTimer.timeout()){
                    this.led.setOn(!this.led.isOn());
                }
                if(this.beeperTimer.timeout()){
                    this.beeper.makeSound(1000, 250);
                }
                break;
            case 3: //when the bot is driving backwards
                if(this.ledTimer.timeout()){
                    this.led.setOn(!this.led.isOn());
                }
                if(this.beeperTimer.timeout()){
                    this.beeper.makeSound(1000, 250);
                }
                break;

            case 4: //when the bot has found an obstacle
                if(this.ledTimer.timeout()){
                    this.led.setOn(!this.led.isOn());
                }
                if(this.beeperTimer.timeout()){
                    this.beeper.makeSound(1000, 250);
                }
                break;

            case 5: //when the bot is in front of an obstacle
                if(this.ledTimer.timeout()){
                    this.led.setOn(!this.led.isOn());
                }
                if(this.beeperTimer.timeout()){
                    this.beeper.makeSound(1000, 1000);
                }
                break;
        }
    }
}
