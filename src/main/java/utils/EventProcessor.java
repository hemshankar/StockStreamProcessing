package utils;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class EventProcessor {

    ExecutorService svc = Executors.newFixedThreadPool(1);
    AtomicInteger processed = new AtomicInteger(0);
    public void processEvents(){
        IntStream.range(0,10).forEach( i->
            svc.submit(new Runnable() {
                ProcessorDetains d = new ProcessorDetains();
                @Override
                public void run() {
                    while(true) {
                        try {
                            String event = QueueHandler.get();
                            //System.out.println(d.id + " processed: " + d.counter++);
                            System.out.println("Events Processed " + processed.getAndIncrement());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }));
    }

    public static class ProcessorDetains{
        int counter = 0;
        UUID id = UUID.randomUUID();
    }

}
