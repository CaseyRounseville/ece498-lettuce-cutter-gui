package main;

import model.PowerStatus;

import util.ConcurrentObservable;

public class PowerSystem implements Runnable {
    private ConcurrentObservable<PowerStatus> powerStatus;

    public PowerSystem() {
        powerStatus = new ConcurrentObservable<PowerStatus>(PowerStatus.OFF);
    }

    public ConcurrentObservable<PowerStatus> getPowerStatus() {
        return powerStatus;
    }

    @Override
    public void run() {
        while (true) {
            switch (powerStatus.getValue()) {
                case TURNING_ON:
                    // do powering on stuff
                    System.out.println("turning power on");

                    // wait for one second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // switch to ON
                    powerStatus.setValue(PowerStatus.ON);
                    break;
                case ON:
                    //System.out.println("power is on");
                    // wait for one second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // do nothing
                    break;
                case TURNING_OFF:
                    // do powering off stuff
                    System.out.println("turning power off");

                    // wait for one second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // switch to OFF
                    powerStatus.setValue(PowerStatus.OFF);
                    break;
                case OFF:
                    //System.out.println("power is off");
                    // wait for one second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // do nothing
                    break;
            }
        }
    }
}
