package webUtils;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class SymbolsManager {

    public static void main(String[] args) {
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
                System.out.println(arr.get(i).getAsJsonObject().get("symbol"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
