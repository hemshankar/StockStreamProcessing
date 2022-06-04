package utils;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class QueueHandler {
    private final static BlockingDeque<String> bq = new LinkedBlockingDeque<>();

    public static void add(String str) throws InterruptedException {
        bq.put(str);
    }

    public static String get() throws InterruptedException {
        return bq.take();
    }

    public static int getSize() {
        return bq.size();
    }
}
