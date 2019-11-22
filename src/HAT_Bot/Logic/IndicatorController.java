package HAT_Bot.Logic;

import HAT_Bot.Actuators.Beeper;
import HAT_Bot.Actuators.LED;

public class IndicatorController implements Updatable{
    private LED led;
    private Beeper beeper;
    private int status;
    // 0 =bot is off.  1 =bot is standing still. 2 = bot is driving. 3 = bot found obstacle. 4 =  bot is in front of obstacle
    private StoppableTimer ledTimer = new StoppableTimer(1000);
    private StoppableTimer beeperTimer = new StoppableTimer(1000);

    public IndicatorController(int ledPin, int beeperPin){
        this.led = new LED(ledPin);
        this.beeper = new Beeper(beeperPin);
        int status = 0;
    }

    public void standingStillIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.status = 1;
    }

    public void drivingIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.ledTimer.setInterval(1000);
        this.beeperTimer.setInterval(5000);
        this.ledTimer.start();
        this.beeperTimer.start();
        this.status = 2;
    }

    public void foundObstacleIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.ledTimer.setInterval(50);
        this.beeperTimer.setInterval(3000);
        this.ledTimer.start();
        this.beeperTimer.start();
        this.status = 3;
    }

    public void inFrontOfObstacleIndication(){
        this.ledTimer.stop();
        this.beeperTimer.stop();
        this.ledTimer.setInterval(50);
        this.beeperTimer.setInterval(1000);
        this.ledTimer.start();
        this.beeperTimer.start();
        this.status = 4;
    }

    @Override
    public void update() {
        switch (this.status){
            case 0:
                this.led.setOn(false);
                this.beeper.setOn(false);

            case 1:
                this.led.setOn(true);

            case 2:
                if(this.ledTimer.timeout()){
                    this.led.setOn(!this.led.isOn());
                }
                if(this.beeperTimer.timeout()){
                    this.beeper.makeSound(250);
                }

            case 3:
                if(this.ledTimer.timeout()){
                    this.led.setOn(!this.led.isOn());
                }
                if(this.beeperTimer.timeout()){
                    this.beeper.makeSound(2000);
                }

            case 4:
                if(this.ledTimer.timeout()){
                    this.led.setOn(!this.led.isOn());
                }
                if(this.beeperTimer.timeout()){
                    this.beeper.makeSound(1000);
                }
        }
    }
}
