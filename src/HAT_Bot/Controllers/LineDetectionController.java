package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Sensors.LineFollower;
import TI.StoppableTimer;

public class LineDetectionController implements Updatable {
    private LineFollower leftLineFollower;
    private LineFollower middleLineFollower;
    private LineFollower rightLineFollower;
    private LineDetectionObserver observer;
    private StoppableTimer lineDetectionTimer;

    private LineDetectionCommand previousCommand;

    /**
     * Computes the signals from all the LineFollowers
     * @param leftLineFollower
     * @param middleLineFollower
     * @param rightLineFollower
     * @param observer
     */

    public LineDetectionController(LineFollower leftLineFollower, LineFollower middleLineFollower, LineFollower rightLineFollower, LineDetectionObserver observer) {
        this.leftLineFollower = leftLineFollower;
        this.middleLineFollower = middleLineFollower;
        this.rightLineFollower = rightLineFollower;
        this.observer = observer;
        this.previousCommand = LineDetectionCommand.none;
        lineDetectionTimer = new StoppableTimer(10);
    }

    public void setObserver(LineDetectionObserver observer) {
        this.observer = observer;
    }

    public void setPreviousCommand(LineDetectionCommand previousCommand) {
        this.previousCommand = previousCommand;
    }

    @Override
    public void update() {
        leftLineFollower.update();
        middleLineFollower.update();
        rightLineFollower.update();

        if (this.lineDetectionTimer.timeout()) {
        if (leftLineFollower.getBoolean()) {
            if (middleLineFollower.getBoolean()) {
                if(this.previousCommand != LineDetectionCommand.slightLeft){
                    if(this.previousCommand == LineDetectionCommand.left){
                        this.previousCommand = LineDetectionCommand.forward;
                        observer.onLineDetected(this, LineDetectionCommand.forward);
                    }
                    //if the left and middle are detecting a line; turn slightly left
                    this.previousCommand = LineDetectionCommand.slightLeft;
                    observer.onLineDetected(this, LineDetectionCommand.slightLeft);
                }
            }
            else {
                //if only the left detects a line, stand still and turn left
                if(this.previousCommand != LineDetectionCommand.left){
                    this.previousCommand = LineDetectionCommand.left;
                    observer.onLineDetected(this, LineDetectionCommand.left);
                }
            }
        }
        else if (rightLineFollower.getBoolean()) {
            if (middleLineFollower.getBoolean()) {
                if(this.previousCommand != LineDetectionCommand.slightRight){
                    if(this.previousCommand == LineDetectionCommand.right){
                        this.previousCommand = LineDetectionCommand.forward;
                        observer.onLineDetected(this, LineDetectionCommand.forward);
                    }
                    //if the middle and right detect a line, turn slightly right
                    this.previousCommand = LineDetectionCommand.slightRight;
                    observer.onLineDetected(this, LineDetectionCommand.slightRight);
                }
            }
            else {
                if(this.previousCommand != LineDetectionCommand.right){
                    //if the right detects a line, stand still and turn right
                    this.previousCommand = LineDetectionCommand.right;
                    observer.onLineDetected(this, LineDetectionCommand.right);
                }
            }
        }
        else if (middleLineFollower.getBoolean()) {
            if(this.previousCommand != LineDetectionCommand.forward){
                //if the middle detects a line, go forward
                this.previousCommand = LineDetectionCommand.forward;
                observer.onLineDetected(this, LineDetectionCommand.forward);
            }
        }
        else if(this.previousCommand != LineDetectionCommand.stop) {
            //stops
            this.previousCommand = LineDetectionCommand.stop;
            observer.onLineDetected(this, LineDetectionCommand.stop);
        }
        }
    }
}
