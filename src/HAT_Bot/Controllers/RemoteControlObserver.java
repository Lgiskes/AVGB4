package HAT_Bot.Controllers;

/**
 * Rules for al the remote control classes.
 */
public interface RemoteControlObserver {

    void onRemoteControlDetected(RemoteControl r, RemoteControlCommand command); // looks for the right button and the right command.

}
