package HAT_Bot.Hardware.Actuators;

public interface Actuator {

    boolean isOn();
    void setOn(boolean on);
    int getPin();

}
