package solution;

import symbols.utils.SymbolsManager;
import utils.EventProcessor;
import webUtils.BinanceWebSocketManager;

public class Solution {

    public static void main(String[] args) {
        SymbolsManager sm = new SymbolsManager(10);
        BinanceWebSocketManager webSocketManager = new BinanceWebSocketManager(sm);
        EventProcessor queueProcessor = new EventProcessor();
        queueProcessor.processEvents();
        webSocketManager.startStreaming();
    }

}
