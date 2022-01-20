package model;

import util.ConcurrentObservable;
import util.TimeUtil;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ProximitySensorModel {
    private ConcurrentObservable<ProximitySensorStatus> proximitySensorStatus;

    // observable value for distance detected
    // from the proximity sensor, in centimeters
    private ConcurrentObservable<Double> distanceCm;

    // pins
    private GpioPinDigitalOutput trigPin;
    private GpioPinDigitalInput echoPin;

    public ProximitySensorModel(
        GpioPinDigitalOutput trigPin,
        GpioPinDigitalInput echoPin
    ) {
        proximitySensorStatus = new ConcurrentObservable<ProximitySensorStatus>(ProximitySensorStatus.BOOT);
        distanceCm = new ConcurrentObservable<Double>(0.0);

        this.trigPin = trigPin;
        this.echoPin = echoPin;

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(
            RaspiPin.GPIO_07,
            PinPullResistance.PULL_DOWN
        );

        // set shutdown state for this input pin
        myButton.setShutdownOptions(true);

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.HIGH) {
                    proximitySensorStatus.setValue(ProximitySensorStatus.IDLE);
                } else {
                    proximitySensorStatus.setValue(ProximitySensorStatus.TRIGGER);
                }
            }

        });

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }

    public ConcurrentObservable<ProximitySensorStatus> getProximitySensorStatus() {
        return proximitySensorStatus;
    }

    public ConcurrentObservable<Double> getDistanceCm() {
        return distanceCm;
    }

    public GpioPinDigitalOutput getTrigPin() {
        return trigPin;
    }

    public GpioPinDigitalInput getEchoPin() {
        return echoPin;
    }

    public void measureDistance() {
        // set the trigger to high
        trigPin.high();

        // set trigger after 0.01ms to low
        // 0.01ms = 10000ns
        TimeUtil.sleepNanoseconds(10000);
        trigPin.low();

        long startTimeNs = System.nanoTime();
        long arrivalTimeNs = System.nanoTime();

        // save start time of the sound wave
        long timeoutGuardNs = System.nanoTime();
        while (echoPin.isLow()) {
            //System.out.println("echo pin low");
            startTimeNs = System.nanoTime();
            if (startTimeNs - timeoutGuardNs > 0.2e9) {
                distanceCm.setValue(Double.POSITIVE_INFINITY);
                return;
            }
        }

        // save time of arrival of the sound wave
        timeoutGuardNs = System.nanoTime();
        while (echoPin.isHigh()) {
            //System.out.println("echo pin high");
            arrivalTimeNs = System.nanoTime();
            if (startTimeNs - timeoutGuardNs > 0.2e9) {
                distanceCm.setValue(Double.POSITIVE_INFINITY);
                return;
            }
        }

        // time difference between the start and arrival times
        long timeElapsedNs = arrivalTimeNs - startTimeNs;

        // calculate distance
        double distCm = (((double)timeElapsedNs / 1.0e9) * 34300.0) / 2.0;

        // set the observable distance
        distanceCm.setValue(distCm);
    }
}
