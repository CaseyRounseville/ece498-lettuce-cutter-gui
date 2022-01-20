package util;

public class TimeUtil {
    public static void sleepMilliseconds(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepNanoseconds(int nanoseconds) {
        long start = System.nanoTime();
        while(System.nanoTime() - start < nanoseconds);
    }
}
