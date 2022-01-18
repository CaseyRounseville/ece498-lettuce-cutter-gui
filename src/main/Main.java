package main;

import model.BladeModel;
import model.PusherModel;
import model.LettuceCutterModel;
import model.Status;
import model.ProduceType;

import view.StatusView;
import view.StatisticsView;
import view.ProduceTypeView;

import util.ConcurrentObservable;
import util.ConcurrentObserver;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;

public class Main {
    private static void createAndShowGUI(
        BladeSystem bladeSystem,
        PusherSystem pusherSystem
    ) {
        // create and set up the window
        JFrame frame = new JFrame("Lettuce Cutter GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set layout
        frame.getContentPane().setLayout(new FlowLayout());

        // create the lettuce cutter model
        // LettuceCutterModel lettuceCutterModel = new LettuceCutterModel();

        // add the status view
        StatusView statusView = new StatusView(
            bladeSystem.getBladeModel().getBladeStatus(),
            pusherSystem.getPusherModel().getPusherStatus()
        );
        frame.getContentPane().add(statusView);

        // add the statistics view
        ConcurrentObservable<Integer> lettuceRaftCount = new ConcurrentObservable<Integer>(0);
        ConcurrentObservable<Integer> microgreenRaftCount = new ConcurrentObservable<Integer>(0);
        StatisticsView statisticsView = new StatisticsView(
            lettuceRaftCount,
            microgreenRaftCount
        );
        frame.getContentPane().add(statisticsView);

        // add the produce type view
        ConcurrentObservable<ProduceType> produceType = new ConcurrentObservable<ProduceType>(ProduceType.LETTUCE);
        ProduceTypeView produceTypeView = new ProduceTypeView(produceType);
        frame.getContentPane().add(produceTypeView);

        // display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // create blade system
        BladeModel bladeModel = new BladeModel();
        BladeSystem bladeSystem = new BladeSystem(bladeModel);

        // create pusher system
        PusherModel pusherModel = new PusherModel();
        PusherSystem pusherSystem = new PusherSystem(pusherModel);

        // start blade system
        Thread bladeSystemThread = new Thread(bladeSystem);
        bladeSystemThread.start();

        // start pusher system
        Thread pusherSystemThread = new Thread(pusherSystem);
        pusherSystemThread.start();

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(
                    bladeSystem,
                    pusherSystem
                );
            }
        });
    }
}
