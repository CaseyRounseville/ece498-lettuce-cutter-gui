package main;

import model.ProximitySensorModel;
import model.ProximitySensorStatus;

import util.ConcurrentObservable;
import util.TimeUtil;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ProximitySensorSystem implements Runnable {
    private ProximitySensorModel proximitySensorModel_1;

    public ProximitySensorSystem(ProximitySensorModel proximitySensorModel_1) {
        this.proximitySensorModel_1 = proximitySensorModel_1;
    }

    public ProximitySensorModel getProximitySensorModel_1() {
        return proximitySensorModel_1;
    }

    @Override
    public void run() {
        int numSeconds = 0;
        while (true) {
            // reference:
            // https://tutorials-raspberrypi.com/raspberry-pi-ultrasonic-sensor-hc-sr04/
            proximitySensorModel_1.measureDistance();

            // wait for a few milliseconds
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            numSeconds++;
        }
    }
}
