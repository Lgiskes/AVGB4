package HAT_Bot.Actuators;

public interface Actuator {

    boolean isOn();
    void setOn(boolean on);
    int getPin();

}
