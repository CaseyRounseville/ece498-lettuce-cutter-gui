package model;

import util.ConcurrentObservable;

public class PusherModel {
    private ConcurrentObservable<PusherStatus> pusherStatus;

    public PusherModel() {
        pusherStatus = new ConcurrentObservable<PusherStatus>(PusherStatus.BOOT);
    }

    public ConcurrentObservable<PusherStatus> getPusherStatus() {
        return pusherStatus;
    }
}
