package webUtils;


import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public final class WebSocketEco extends WebSocketListener {
    private void run() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url("wss://stream.binance.com:9443/ws/neoeth@trade")
                .build();
        client.newWebSocket(request, this);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();
    }

    @Override public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("{\"method\": \"SUBSCRIBE\",\"params\":[\"btcusdt@trade\",\"ltcbtc@trade\"],\"id\": 1}");
        //webSocket.send("...World!");
        //webSocket.send(ByteString.decodeHex("deadbeef"));
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webSocket.send("{\"method\": \"LIST_SUBSCRIPTIONS\",\"id\": 1}");
                    Thread.sleep(10000);
                    webSocket.send("{\"method\": \"LIST_SUBSCRIPTIONS\",\"id\": 1}");
                    webSocket.send("{\"method\": \"UNSUBSCRIBE\",\"params\":[\"btcusdt@trade\",\"ltcbtc@trade\"],\"id\": 1}");
                    System.out.println("============================");
                    webSocket.send("{\"method\": \"LIST_SUBSCRIPTIONS\",\"id\": 1}");
                    webSocket.close(1000, "Goodbye, World!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override public void onMessage(WebSocket webSocket, String text) {
        System.out.println("MESSAGE1: " + text);
    }

    @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("MESSAGE: " + bytes.hex());
    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

    public static void main(String... args) {
        new WebSocketEco().run();
    }
}