package model;

import util.ConcurrentObservable;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ProximitySensorModel {
    private ConcurrentObservable<ProximitySensorStatus> proximitySensorStatus;

    // observable value for distance detected
    // from the proximity sensor, in centimeters
    private ConcurrentObservable<Double> distanceCm;

    public ProximitySensorModel() {
        proximitySensorStatus = new ConcurrentObservable<ProximitySensorStatus>(ProximitySensorStatus.BOOT);
	distanceCm = new ConcurrentObservable<Double>(0.0);

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(
	    RaspiPin.GPIO_07,
	    PinPullResistance.PULL_DOWN
	);

	// trig pin
        final GpioPinDigitalInput trigPin = gpio.provisionDigitalOutputPin(
	    RaspiPin.GPIO_07
	);

	// echo pin
        final GpioPinDigitalInput echoPin = gpio.provisionDigitalInputPin(
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
}
