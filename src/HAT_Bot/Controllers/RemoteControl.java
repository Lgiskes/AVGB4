package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Sensors.Bluetooth;
import HAT_Bot.Hardware.Sensors.Infrared;
import TI.BoeBot;
import TI.StoppableTimer;

import java.awt.*;

/**
 * This class is for the remoteControl and is part of the hardware layer that sends information from the remote control
 * to the interface layer.
 */
public class RemoteControl implements Updatable {

    private int buttonValue;
    private Infrared infrared;
    private StoppableTimer delayTimer;
    private RemoteControlObserver observer;

    /**
     * Constructor for the remoteCrontol.
     * @param pinInfrared, This gets the right infrared data from the right pin.
     * @param observer, this looks which button is pushed on the remote control.
     */
    public RemoteControl(int pinInfrared, RemoteControlObserver observer) {
        this.infrared = new Infrared(pinInfrared);
        this.observer = observer;
        this.delayTimer = new StoppableTimer(200);
    }


    /**
     * @param observer, this is a setter for observer.
     */
    public void setObserver(RemoteControlObserver observer) {
        this.observer = observer;
    }


    /**
     * This actions method sends a command to the OperatingLogic to make the Boe-bot doe the right action.
     */
    public void actions(int buttonValue){
        switch (buttonValue){
            case 256: // button 1
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed10);
                break;
            case 258: // button 2
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed20);
                break;
            case 260: // button 3
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed30);
                break;
            case 262: // button 4
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed40);
                break;
            case 264: // button 5
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed50);
                break;
            case 266: // button 6
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed60);
                break;
            case 268: // button 7
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed70);
                break;
            case 270: // button 8
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed80);
                break;
            case 272: // button 9
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed90);
                break;
            case 274: // button 0
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed100);
                break;
            case 280: // arrow
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.setSpeed0);
                break;
            case 298: // break button
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.emergencyBrake);
                break;
            case 288: // forward
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.forward);
                break;
            case 290: // backwards
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.backward);
                break;
            case 294: // turn left
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.turnLeft);
                break;
            case 292: // turn right
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.turnRight);
                break;
            case 296: // mute button
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.mute);
                break;
            case 922: // drive square
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.driveSquare);
                break;
            case 314: // drive circle
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.driveCircle);
                break;
            case 924: // drive triangle
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.driveTriangle);
                break;
            case 330: // resume
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.resume);
                break;
            case 368: // RGB toggle
                this.observer.onRemoteControlDetected(this, RemoteControlCommand.toggleLights);
                break;
        }
    }

    /**
     * The delayTimer manages a timer, so when a button is pressed on the remote it only gives one value back every
     * 0.2 seconds. If the timer is stopped the infrared attribute kan be updated again, so that means there can be
     * pressed a button again. but only if the infrared gives valid values, otherwise there will be no reaction on the
     * pressed button.
     */
    public void update() {
        if(this.delayTimer.timeout()){
            this.delayTimer.stop();
        }
        else if(this.delayTimer.isStopped()) {
            this.infrared.update();
            this.buttonValue = this.infrared.getValue();
            if (this.buttonValue != -1){
                actions(this.buttonValue);
                this.delayTimer.start();
            }
        }

    }
}
