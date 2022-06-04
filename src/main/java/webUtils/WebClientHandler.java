package webUtils;

import com.binance.connector.client.impl.WebsocketClientImpl;

import java.util.ArrayList;
import java.util.List;

public class WebClientHandler{
    WebsocketClientImpl client = new WebsocketClientImpl();
    ArrayList<String> stream = null;
    Integer streamID = -1;
    EventHandler eventHandler = null;
    public WebClientHandler(){
        stream = new ArrayList<>();
    }
    public WebClientHandler(ArrayList<String> stream_, EventHandler handler){
        stream = stream_;
        eventHandler = handler;
    }

    public void startStreaming(){
        if(eventHandler != null)

            streamID = client.combineStreams(stream, ((event) -> eventHandler.handleEvent(event)));
    }

    public void stopStreaming(){
        if(streamID != -1) {
            client.closeConnection(streamID);
        }
        client.closeAllConnections();
    }
}
