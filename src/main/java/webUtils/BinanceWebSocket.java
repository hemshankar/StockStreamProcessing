package webUtils;

import com.binance.connector.client.impl.WebsocketClientImpl;

import java.util.ArrayList;
import java.util.List;

public class BinanceWebSocket {
    public static void main(String[] args) {

        WebsocketClientImpl client = new WebsocketClientImpl(); // defaults to production envrionment unless stated,
        int streamID1 = client.tradeStream("reibusd",((event) -> {
            System.out.println("===============" + event);
        }));

        SymbolsManager sm = new SymbolsManager();
        sm.getAllSumbols();
        List<ArrayList<String>> streams = new ArrayList<>();

        int count = 0;
        while(count < sm.allSymbols.size()){
            ArrayList<String> stream = new ArrayList<>();
            for(int i=count; i<sm.allSymbols.size();i++) {
                count++;
                if(count %2000 == 0) break;
                String sy = sm.allSymbols.get(i);
                if (!sy.equals("reibusd")) {
                    String str = sy + "@trade";
                    System.out.println("Adding : " + str);
                    stream.add(str);
                }
                streams.add(stream);
            }
        }

        //Combining Streams

        /*streams.add("btcusdt@trade");
        streams.add("bnbusdt@trade");
        streams.add("ltcbtc@trade");*/
        List<Integer> streamIds = new ArrayList<>();
        int streamID2 = client.combineStreams(streams.get(0), ((event) -> {
            System.out.println("---------------------" + event);
            //System.out.println(event);
        }));

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {

        }
        //Closing a single stream
        client.closeConnection(streamID1);
        client.closeConnection(streamID2);

        //Closing all streams
        client.closeAllConnections();
    }
}
