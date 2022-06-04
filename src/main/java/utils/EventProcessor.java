package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import symbols.utils.SymbolModel;
import symbols.utils.SymbolsManager;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class EventProcessor {

    /**
     * Event:
     * {
     *   "e": "trade",     // Event type
     *   "E": 123456789,   // Event time
     *   "s": "BNBBTC",    // Symbol
     *   "t": 12345,       // Trade ID
     *   "p": "0.001",     // Price
     *   "q": "100",       // Quantity
     *   "b": 88,          // Buyer order ID
     *   "a": 50,          // Seller order ID
     *   "T": 123456785,   // Trade time
     *   "m": true,        // Is the buyer the market maker?
     *   "M": true         // Ignore
     * }
     */

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
                            JsonObject obj = d.parser.parse(event).getAsJsonObject().get("data").getAsJsonObject();

                            //get the symbol out
                            String symbol = obj.get("s").getAsString();

                            //get the MRP
                            String price = obj.get("p").getAsString();

                            Integer index = SymbolsManager.myTrie.getIndex(symbol);

                            SymbolModel sDetails = SymbolsManager.symbolDetailsList.get(index);
                            sDetails.infiniteMedian.name = symbol;
                            sDetails.infiniteMedian.add(Double.parseDouble(price));
                            System.out.println(sDetails.infiniteMedian.getCurrentState());
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
        JsonParser parser = new JsonParser();
    }

}
