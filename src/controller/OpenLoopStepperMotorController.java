package controller;

import model.OpenLoopStepperMotorModel;

import serial.ArduinoCmder;

public class OpenLoopStepperMotorController {
    private OpenLoopStepperMotorModel motorModel;

    public OpenLoopStepperMotorController(
        OpenLoopStepperMotorModel motorModel
    ) {
        this.motorModel = motorModel;
    }

    // turn the motor on
    public void turnOn() {
        byte cmdId = motorModel.getCmdPwr();
        byte[] cmdData = new byte[ArduinoCmder.CMD_DATA_LEN];
        cmdData[0] = 1;
        ArduinoCmder.getInstance().sendCmd(cmdId, cmdData);
        motorModel.setCurrentlyOn(true);
    }

    // turn the motor off
    public void turnOff() {
        byte cmdId = motorModel.getCmdPwr();
        byte[] cmdData = new byte[ArduinoCmder.CMD_DATA_LEN];
        cmdData[0] = 0;
        ArduinoCmder.getInstance().sendCmd(cmdId, cmdData);
        motorModel.setCurrentlyOn(false);
    }
}
