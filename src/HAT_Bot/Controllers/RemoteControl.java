package HAT_Bot.Controllers;

import HAT_Bot.Sensors.Infrared;
import TI.StoppableTimer;


public class RemoteControl implements Updatable {

    private int buttonValue;
    private Infrared infrared;
    private StoppableTimer delayTimer;
    private RemoteControlObserver observer;

    public RemoteControl(int pinInfrared, RemoteControlObserver observer) {
        this.infrared = new Infrared(pinInfrared);
        this.observer = observer;
        this.delayTimer = new StoppableTimer(200);
    }

    public void setObserver(RemoteControlObserver observer) {
        this.observer = observer;
    }

    public void actions(){
        switch (this.buttonValue){
            case 256: // 1
                observer.onRemoteControlDetected(this, "setSpeed 10");
                break;
            case 258: // 2
                observer.onRemoteControlDetected(this, "setSpeed 20");
                break;
            case 260: // 3
                observer.onRemoteControlDetected(this, "setSpeed 30");
                break;
            case 262: // 4
                observer.onRemoteControlDetected(this, "setSpeed 40");
                break;
            case 264: // 5
                observer.onRemoteControlDetected(this, "setSpeed 50");
                break;
            case 266: // 6
                observer.onRemoteControlDetected(this, "setSpeed 60");
                break;
            case 268: // 7
                observer.onRemoteControlDetected(this, "setSpeed 70");
                break;
            case 270: // 8
                observer.onRemoteControlDetected(this, "setSpeed 80");
                break;
            case 272: // 9
                observer.onRemoteControlDetected(this, "setSpeed 90");
                break;
            case 274: // 0
                observer.onRemoteControlDetected(this, "setSpeed 100");
                break;
            case 280: // arrow
                observer.onRemoteControlDetected(this, "setSpeed 00");
                break;
            case 298: // break button
                observer.onRemoteControlDetected(this, "emergencyBrake");
                break;
            case 288: // forward
                observer.onRemoteControlDetected(this, "forward");
                break;
            case 290: // backwards
                observer.onRemoteControlDetected(this, "backward");
                break;
            case 294: // turn left
                observer.onRemoteControlDetected(this, "turnLeft");
                break;
            case 292: // turn right
                observer.onRemoteControlDetected(this, "turnRight");
                break;
            case 296: // mute button
                observer.onRemoteControlDetected(this, "mute");
                break;
            case 922: // square
                observer.onRemoteControlDetected(this, "driveSquare");
                break;
            case 314: // circle
                observer.onRemoteControlDetected(this, "driveCircle");
                break;
            case 924: // triangle
                observer.onRemoteControlDetected(this, "driveTriangle");
                break;
            case 926: // other form
                break;
        }
    }

    public void update() {

        if(delayTimer.timeout()){
            delayTimer.stop();
        }
        else if(delayTimer.isStopped()) {
            infrared.update();
            this.buttonValue = infrared.getValue();
            if (this.buttonValue != -1){
                actions();
                delayTimer.start();
            }
        }

    }
}
