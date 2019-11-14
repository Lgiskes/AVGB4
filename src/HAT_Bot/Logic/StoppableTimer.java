package HAT_Bot.Logic;

import TI.Timer;

public class StoppableTimer extends Timer {

    private boolean stopped;

    public StoppableTimer(int interval){
        super(interval);
        this.stopped = false;
    }

    public void start(){
        super.mark();
        this.stopped = false;
    }

    public void stop(){
        this.stopped = true;
    }

    public boolean isStopped(){
        return this.stopped;
    }

    @Override
    public boolean timeout() {
        if(this.stopped){
            return false;
        }
        else{
            return super.timeout();
        }
    }
}
