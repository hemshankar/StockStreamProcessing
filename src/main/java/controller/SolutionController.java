package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import symbols.utils.SymbolsManager;
import utils.EventProcessor;
import webUtils.BinanceWebSocketManager;

import java.util.List;
import java.util.Map;

@RestController
public class SolutionController {

    SymbolsManager sm = new SymbolsManager(0,10000);
    BinanceWebSocketManager webSocketManager = new BinanceWebSocketManager(sm);
    EventProcessor queueProcessor = new EventProcessor();
    public SolutionController(){
        super();
        queueProcessor.processEvents();
        webSocketManager.startStreaming();
    }

    @GetMapping("/")
    public List<String> index() {
        return sm.allSymbols;
    }

    @GetMapping("/symbols")
    public List<String> symbols() {
        return sm.allSymbols;
    }

    @GetMapping("/{symbol}")
    public SymbolsManager.SymbolDetails getDetails(@PathVariable String symbol) {
        return sm.getDetails(symbol);
    }
}
