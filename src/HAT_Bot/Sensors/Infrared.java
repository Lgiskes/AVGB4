package HAT_Bot.Sensors;

import HAT_Bot.Logic.StoppableTimer;
import HAT_Bot.Logic.Updatable;
import TI.BoeBot;

public class Infrared implements Sensor, Updatable {
    private int pin;
    private int value;
    private StoppableTimer timerinfrared;

    public Infrared(int pin) {
        this.pin = pin;
        timerinfrared = new StoppableTimer(10);
    }

    public int getValue() {
        return this.value;
    }


    public int getPin() {
        return this.pin;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public void update() {
        if (timerinfrared.timeout()) {
            boolean validValue = true;
            int pulse = BoeBot.pulseIn(this.pin, false, 6000);
            int bitNumber = 0;
            if (pulse > 2000) {
                int lengtes[] = new int[12];
                for (int i = 0; i < 12; i++) {
                    lengtes[i] = BoeBot.pulseIn(this.pin, false, 20000);
                }
                for (int i = lengtes.length - 1; i >= 0; i--) {
                    if (lengtes[i] > 200 && lengtes[i] < 850) {
                        bitNumber += 0;
                        bitNumber = bitNumber << 1;
                    } else if
                    (lengtes[i] > 950 && lengtes[i] < 1450) {
                        bitNumber += 1;
                        bitNumber = bitNumber << 1;
                    } else {
                        validValue = false;
                    }
                }
            }
            if (validValue) {
                this.value = bitNumber;
            }
            else {
                this.value = -1;
            }
        }
    }
}
