package model;

public class OpenLoopStepperMotorModel {
    // the commands to tell the arduino to turn the motor
    // on or off
    private byte cmdPwr;

    // whether the motor is on or not
    private boolean currentlyOn;

    public OpenLoopStepperMotorModel(
        byte cmdPwr
    ) {
        this.cmdPwr = cmdPwr;
        currentlyOn = false;
    }

    public byte getCmdPwr() {
        return cmdPwr;
    }

    public boolean isCurrentlyOn() {
        return currentlyOn;
    }

    public void setCurrentlyOn(boolean currentlyOn) {
        this.currentlyOn = currentlyOn;
    }
}
