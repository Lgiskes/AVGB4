package HAT_Bot.Logic;

import HAT_Bot.Logic.Updatable;
import HAT_Bot.Sensors.Infrared;


public class RemoteControl implements Updatable {

    private int buttonValue;
    private Infrared infrared;

    public RemoteControl(int pinInfrared) {
        this.infrared = new Infrared(pinInfrared);
    }

    public void actions(){
        switch (this.buttonValue){
            
        }
    }

    public void update() {
        this.buttonValue = infrared.getValue();
    }

}
