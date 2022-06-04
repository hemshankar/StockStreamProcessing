package webUtils;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SymbolsManager {

    public List<String> allSymbols = new ArrayList<>();

    public void getAllSumbols() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.binance.com/api/v3/exchangeInfo";
        JsonParser parser = new JsonParser();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            JsonObject rootObj = parser.parse(str).getAsJsonObject();
            JsonArray arr = rootObj.getAsJsonArray("symbols");
            System.out.println("Size: " + arr.size());
            for(int i=0;i<arr.size();i++) {
                String sy = arr.get(i).getAsJsonObject().get("symbol").getAsString();
                allSymbols.add(sy.toLowerCase());
            }
            System.out.println(allSymbols.size());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    public static void main(String[] args) {
        SymbolsManager sm = new SymbolsManager();
        sm.getAllSumbols();
    }

}
