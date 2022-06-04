package symbols.utils;

import com.google.gson.*;
import javafx.util.Pair;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SymbolsManager {

    public static class SymbolDetails{
        public SymbolDetails(String name, Integer total, Double median, Double mrp) {
            this.name = name;
            this.total = total;
            this.median = median;
            this.mrp = mrp;
        }

        public String name;
        public Integer total;
        public Double median;
        public Double mrp;

        @Override
        public String toString() {
            return "{" +
                    "name='" + name + '\'' +
                    ", total=" + total +
                    ", median=" + median +
                    ", mrp=" + mrp +
                    '}';
        }
    }

    public SymbolsManager(){
        getAllSymbols(-1);
    }
    public SymbolsManager(int topN){
        getAllSymbols(topN);
    }
    public static final MyHashUtil myTrie = new MyHashUtil();
    public static final List<SymbolModel> symbolDetailsList = new ArrayList<>();

    public List<String> allSymbols = new ArrayList<>();

    public SymbolDetails getDetails(String sym){
        SymbolModel model = symbolDetailsList.get(myTrie.getIndex(sym));
        Pair<Double, Double> info = model.infiniteMedian.getMedianAndMRP();
        return new SymbolDetails(sym, model.infiniteMedian.getSize(), info.getValue(), info.getKey());
    }


    public void getAllSymbols(int topN) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.binance.com/api/v3/exchangeInfo";
        JsonParser parser = new JsonParser();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            JsonObject rootObj = parser.parse(str).getAsJsonObject();
            JsonArray arr = rootObj.getAsJsonArray("symbols");
            System.out.println("Size: " + arr.size());
            if(topN < 0) topN = arr.size();
            for(int i=0;i<topN;i++) {
                String sy = arr.get(i).getAsJsonObject().get("symbol").getAsString();
                allSymbols.add(sy.toLowerCase());
                symbolDetailsList.add(new SymbolModel());
            }
            System.out.println(allSymbols.size());

            //create indexes for each of the symbol
            myTrie.addAll(allSymbols);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    public static void main(String[] args) {
        SymbolsManager sm = new SymbolsManager();
    }

}
