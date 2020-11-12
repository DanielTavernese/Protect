package ca.dantav.game.timer;

public abstract class GameTimer {

    private int ticks;

    private int currTicks;

    private int timesExecuted;

    private boolean immediate;

    private boolean running;

    public GameTimer(int ticks) {
        this.ticks = ticks;
        this.currTicks = ticks;
    }

    public GameTimer(int ticks, boolean immediate) {
        this.ticks = ticks;
        this.currTicks = ticks;
        this.immediate = immediate;
    }

    /*
    What is to be executed
     */
    public abstract void execute();

    /*
     * stopping condition
     */
    public abstract boolean stoppingCondition();

    /*
    The ending of the timer
     */
    public void end() {

    }

    /*
    * Stops the timer
     */
    public void stop() {
        this.running = false;
    }

    public int getTicks() {
        return ticks;
    }

    public void setCurrTicks(int currTicks) {
        this.currTicks = currTicks;
    }

    public void setTimesExecuted(int timesExecuted) {
        this.timesExecuted = timesExecuted;
    }

    public int getTimesExecuted() {
        return timesExecuted;
    }

    public int getCurrTicks() {
        return currTicks;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isImmediate() { return immediate; }


}
