package main;

import model.BladeModel;
import model.BladeStatus;

import util.ConcurrentObservable;

public class BladeSystem implements Runnable {
    private BladeModel bladeModel;

    public BladeSystem(BladeModel bladeModel) {
        this.bladeModel = bladeModel;
    }

    public BladeModel getBladeModel() {
        return bladeModel;
    }

    @Override
    public void run() {
        int numSeconds = 0;
        while (true) {
            // do blade stuff
            ConcurrentObservable<BladeStatus> bladeStatusObservable = bladeModel.getBladeStatus();
            BladeStatus bladeStatusValue = bladeStatusObservable.getValue();
            
            if (numSeconds % 3 == 0) {
                if (bladeStatusValue == BladeStatus.BOOT) {
                    bladeStatusObservable.setValue(BladeStatus.IDLE);
                } else if (bladeStatusValue == BladeStatus.IDLE) {
                    bladeStatusObservable.setValue(BladeStatus.CUT);
                } else if (bladeStatusValue == BladeStatus.CUT) {
                    bladeStatusObservable.setValue(BladeStatus.IDLE);
                }
            }

            // wait for one second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            numSeconds++;
        }
    }
}
