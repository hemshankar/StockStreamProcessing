package webUtils;

import com.binance.connector.client.impl.WebsocketClientImpl;

import java.util.ArrayList;

public class BinanceWebSocket {
    public static void main(String[] args) {

        WebsocketClientImpl client = new WebsocketClientImpl(); // defaults to production envrionment unless stated,
        int streamID1 = client.tradeStream("REIBUSD",((event) -> {
            System.out.println("===============" + event);
        }));

        //Combining Streams
        ArrayList<String> streams = new ArrayList<>();
        /*streams.add("btcusdt@trade");
        streams.add("bnbusdt@trade");
        streams.add("ltcbtc@trade");*/
        streams.add("ustcbusd@trade");
        streams.add("luncbusd@trade");
        streams.add("PUNDIXBUSD@trade");

        int streamID2 = client.combineStreams(streams, ((event) -> {
            System.out.println("---------------------" + event);
            //System.out.println(event);
        }));

        try {
            Thread.sleep(10000);
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
