package main;

import model.PusherModel;
import util.ConcurrentObservable;

public class PusherSystem implements Runnable {
    private PusherModel pusherModel;

    public PusherSystem(PusherModel pusherModel) {
        this.pusherModel = pusherModel;
    }

    public PusherModel getPusherModel() {
        return pusherModel;
    }

    @Override
    public void run() {
        while (true) {
            // do pusher stuff

            // wait for one second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
