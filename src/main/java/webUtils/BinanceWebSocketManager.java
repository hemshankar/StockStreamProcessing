package webUtils;

import symbols.utils.SymbolsManager;
import utils.QueueHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BinanceWebSocketManager {
    AtomicInteger added = new AtomicInteger(0);
    List<WebClientHandler> clients = new ArrayList<>();
    SymbolsManager sm = null;


    public BinanceWebSocketManager(SymbolsManager sm1){
        sm = sm1;
        prepareForStreaming();
    }

    public void prepareForStreaming(){
        //create streams, each stream of made with combining 500 trade streams
        List<List<String>> streams = createStreams(sm, 500);
        streams.stream().forEach(x -> {
            ArrayList<String> strList = new ArrayList<>();
            strList.addAll(x);
            WebClientHandler cl = new WebClientHandler(strList, new EventHandler() {
                @Override
                public void handleEvent(String event) {
                    //System.out.println(event);
                    try {
                        QueueHandler.add(event);
                        //System.out.println("Events Added " + added.getAndIncrement());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            clients.add(cl);
        });
    }

    private List<List<String>> createStreams(SymbolsManager sm, Integer batchSize) {
        List<List<String>> streams = new ArrayList<>();

        int count = 0;
        while(count < sm.allSymbols.size()){
            streams.add(sm.allSymbols.subList(count, Math.min(count + batchSize,sm.allSymbols.size())).
                    stream()
                    .map(x -> x + "@trade")
                    .collect(Collectors.toList())) ;
            count = count + batchSize;
        }
        return streams;
    }

    public void startStreaming(){
        clients.stream().forEach(x -> x.startStreaming());
    }

    public void stopStreaming(){
        System.out.println("Closing all connections");
        clients.stream().forEach(x -> x.stopStreaming());
    }

    public static void main(String[] args) {
        SymbolsManager sm = new SymbolsManager();
        BinanceWebSocketManager bws = new BinanceWebSocketManager(sm);

        bws.startStreaming();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {

        }
        bws.stopStreaming();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        stopStreaming();
    }
}
