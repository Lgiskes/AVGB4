package HAT_Bot.Sensors;

import HAT_Bot.Logic.StoppableTimer;
import HAT_Bot.Logic.Updatable;
import TI.BoeBot;

public class Ultrasone implements Sensor, Updatable {

    private int pinTrigger;
    private int pinEcho;
    private int value;
    private StoppableTimer timerUltrasone;
    private StoppableTimer timerShort;

    public Ultrasone(int pinTrigger, int pinEcho) {
        this.pinTrigger = pinTrigger;
        this.pinEcho = pinEcho;
        timerUltrasone = new StoppableTimer(50);
        timerShort = new StoppableTimer(1);
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public int getPin() {
        return this.pinEcho;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public void update() {
        if (timerUltrasone.timeout()) {
            BoeBot.digitalWrite(0, true);
            timerShort.start();
            if (timerShort.timeout()) {
                timerShort.stop();
                BoeBot.digitalWrite(0, false);

                int pulse = BoeBot.pulseIn(1, true, 10000);
                this.value = pulse / 58;
            }
        }
    }
}
