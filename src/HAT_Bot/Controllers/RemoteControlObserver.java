package HAT_Bot.Controllers;

/**
 * Rules for the remote control classes.
 */
public interface RemoteControlObserver {

    void onRemoteControlDetected(RemoteControl r, String command); // Check if there is an button pressed.

}
