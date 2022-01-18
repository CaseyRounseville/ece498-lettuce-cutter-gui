package model;

import util.ConcurrentObservable;

public class BladeModel {
    private ConcurrentObservable<BladeStatus> bladeStatus;

    public BladeModel() {
        bladeStatus = new ConcurrentObservable<BladeStatus>(BladeStatus.BOOT);
    }

    public ConcurrentObservable<BladeStatus> getBladeStatus() {
        return bladeStatus;
    }
}
