package main;

import model.BladeModel;
import model.PusherModel;
import model.ProximitySensorModel;
import model.LettuceCutterModel;
import model.Status;
import model.ProduceType;
import model.OpenLoopStepperMotorModel;

import view.PowerView;
import view.StatusView;
import view.StatisticsView;
import view.ProduceTypeView;
import view.MotorView;

import controller.OpenLoopStepperMotorController;

import serial.ArduinoCmder;

import util.ConcurrentObservable;
import util.ConcurrentObserver;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Main {
    private static void createAndShowGUI(
        PowerSystem powerSystem,
        BladeSystem bladeSystem,
        PusherSystem pusherSystem,
        ProximitySensorSystem proximitySensorSystem
    ) {
        // create and set up the window
        JFrame frame = new JFrame("Lettuce Cutter GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set layout
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        // create the lettuce cutter model
        // LettuceCutterModel lettuceCutterModel = new LettuceCutterModel();

        // add the power view
        PowerView powerView = new PowerView(
            powerSystem.getPowerStatus()
        );
        constr.fill = GridBagConstraints.BOTH;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        frame.getContentPane().add(powerView, constr);

        // add the status view
        StatusView statusView = new StatusView(
            bladeSystem.getBladeModel().getBladeStatus(),
            pusherSystem.getPusherModel().getPusherStatus(),
            proximitySensorSystem.getProximitySensorModel_1().getProximitySensorStatus(),
            proximitySensorSystem.getProximitySensorModel_1().getDistanceCm()
        );
        constr.fill = GridBagConstraints.BOTH;
        constr.gridx = 0;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        frame.getContentPane().add(statusView, constr);

        // add the statistics view
        ConcurrentObservable<Integer> lettuceRaftCount = new ConcurrentObservable<Integer>(0);
        ConcurrentObservable<Integer> microgreenRaftCount = new ConcurrentObservable<Integer>(0);
        StatisticsView statisticsView = new StatisticsView(
            lettuceRaftCount,
            microgreenRaftCount
        );
        constr.fill = GridBagConstraints.BOTH;
        constr.gridx = 0;
        constr.gridy = 2;
        constr.insets = new Insets(5, 5, 5, 5);
        frame.getContentPane().add(statisticsView, constr);

        // add the produce type view
        ConcurrentObservable<ProduceType> produceType = new ConcurrentObservable<ProduceType>(ProduceType.LETTUCE);
        ProduceTypeView produceTypeView = new ProduceTypeView(produceType);
        constr.fill = GridBagConstraints.BOTH;
        constr.gridx = 1;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        frame.getContentPane().add(produceTypeView, constr);

        // add the motor view
        OpenLoopStepperMotorModel motorModel1 = new OpenLoopStepperMotorModel(
            ArduinoCmder.CMD_MOTOR_1_PWR
        );
        OpenLoopStepperMotorController motorController1 =
            new OpenLoopStepperMotorController(motorModel1);
        OpenLoopStepperMotorModel motorModel2 = new OpenLoopStepperMotorModel(
            ArduinoCmder.CMD_MOTOR_2_PWR
        );
        OpenLoopStepperMotorController motorController2 =
            new OpenLoopStepperMotorController(motorModel2);
        MotorView motorView = new MotorView(
            motorModel1,
            motorModel2,
            motorController1,
            motorController2
        );
        constr.fill = GridBagConstraints.BOTH;
        constr.gridx = 1;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        frame.getContentPane().add(motorView, constr);

        // display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // create gpio controller
        GpioController gpio = GpioFactory.getInstance();

        // create power system
        PowerSystem powerSystem = new PowerSystem();

        // create blade system
        BladeModel bladeModel = new BladeModel();
        BladeSystem bladeSystem = new BladeSystem(bladeModel);

        // create pusher system
        PusherModel pusherModel = new PusherModel();
        PusherSystem pusherSystem = new PusherSystem(pusherModel);

        // create proximity sensor system
        GpioPinDigitalOutput trigPin = gpio.provisionDigitalOutputPin(
            RaspiPin.GPIO_00
        );
        GpioPinDigitalInput echoPin = gpio.provisionDigitalInputPin(
            RaspiPin.GPIO_01
        );
        ProximitySensorModel proximitySensorModel_1 = new ProximitySensorModel(
            trigPin,
            echoPin
        );
        ProximitySensorSystem proximitySensorSystem = new ProximitySensorSystem(
            proximitySensorModel_1
        );

        // start power system
        Thread powerSystemThread = new Thread(powerSystem);
        powerSystemThread.start();

        // start blade system
        Thread bladeSystemThread = new Thread(bladeSystem);
        bladeSystemThread.start();

        // start pusher system
        Thread pusherSystemThread = new Thread(pusherSystem);
        pusherSystemThread.start();

        // start proximity sensor system
        Thread proximitySensorSystemThread = new Thread(proximitySensorSystem);
        proximitySensorSystemThread.start();

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(
                    powerSystem,
                    bladeSystem,
                    pusherSystem,
                    proximitySensorSystem
                );
            }
        });
    }
}
