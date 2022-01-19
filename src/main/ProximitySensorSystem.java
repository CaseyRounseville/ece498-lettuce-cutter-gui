package main;

import model.ProximitySensorModel;
import model.ProximitySensorStatus;

import util.ConcurrentObservable;

public class ProximitySensorSystem implements Runnable {
    private ProximitySensorModel proximitySensorModel;

    public ProximitySensorSystem(ProximitySensorModel proximitySensorModel) {
        this.proximitySensorModel = proximitySensorModel;
    }

    public ProximitySensorModel getProximitySensorModel() {
        return proximitySensorModel;
    }

    @Override
    public void run() {
        int numSeconds = 0;
        while (true) {
            // wait for a few milliseconds
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            numSeconds++;
        }
    }
}
