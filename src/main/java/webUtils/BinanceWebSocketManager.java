package webUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceWebSocketManager {

    List<WebClientHandler> clients = new ArrayList<>();

    public void prepareForStreaming(){

        //fetch all the symbols
        SymbolsManager sm = new SymbolsManager();

        //create streams, each stream of made with combining 500 trade streams
        List<List<String>> streams = createStreams(sm, 500);
        streams.stream().forEach(x -> {
            ArrayList<String> strList = new ArrayList<>();
            strList.addAll(x);
            WebClientHandler cl = new WebClientHandler(strList, new EventHandler() {
                @Override
                public void handleEvent(String event) {
                    System.out.println(event);
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
                                stream().map(x -> x + "@trade")
                                        .collect(Collectors.toList())) ;
            count = count + batchSize;
        }
        return streams;
    }

    public void startStreaming(){
        clients.stream().forEach(x -> x.startStreaming());
    }

    public void stopStreaming(){
        clients.stream().forEach(x -> x.stopStreaming());
    }

    public static void main(String[] args) {
        BinanceWebSocketManager bws = new BinanceWebSocketManager();
        bws.prepareForStreaming();
        bws.startStreaming();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {

        }
        bws.stopStreaming();
    }
}
