package model;

import util.ConcurrentObservable;

public class ProximitySensorModel {
    private ConcurrentObservable<ProximitySensorStatus> proximitySensorStatus;

    public ProximitySensorModel() {
        proximitySensorStatus = new ConcurrentObservable<ProximitySensorStatus>(ProximitySensorStatus.BOOT);
    }

    public ConcurrentObservable<ProximitySensorStatus> getProximitySensorStatus() {
        return proximitySensorStatus;
    }
}
