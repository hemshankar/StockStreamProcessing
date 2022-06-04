package webUtils;
import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;

@WebSocket
public class SecureClientSocket
{
    private static final Logger LOG = Log.getLogger(SecureClientSocket.class);

    public static void main(String[] args)
    {
        String url = "wss://stream.binance.com:9443/ws/NEOETH@trade";//wss://qa.sockets.stackexchange.com/";

        SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
        // Tell sslContextFactory to trust all server certificates
        // This is suitable for test/qa environments, and internal environments,
        // but IS NOT SUITABLE FOR PRODUCTION.
        // Note: this is not actually necessary for wss://qa.sockets.stackexchange.com/
        sslContextFactory.setTrustAll(true);
        // If you do choose to comment out the above, this option will cause the
        // Java client side SSL/TLS to validate the server certificate name
        // against the URL used to connect to the server, if it doesn't match
        // then the connection is not established.
        sslContextFactory.setEndpointIdentificationAlgorithm("HTTPS");

        HttpClient httpClient = new HttpClient(sslContextFactory);
        try
        {
            httpClient.start();
            WebSocketClient client = new WebSocketClient(httpClient);
            client.start();
            SecureClientSocket socket = new SecureClientSocket();
            Future<Session> fut = client.connect(socket, URI.create(url));
            Session session = fut.get();
            String msg = "{\"method\": \"SUBSCRIBE\",\"params\":[\"btcusdt@trade\"],\"id\": 1}";
            session.getRemote().sendString(msg);
            //session.getRemote().sendString("155-questions-active");
        }
        catch (Throwable t)
        {
            LOG.warn(t);
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session sess)
    {
        LOG.info("onConnect({})", sess);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        LOG.info("onClose({}, {})", statusCode, reason);
    }

    @OnWebSocketError
    public void onError(Throwable cause)
    {
        LOG.warn(cause);
    }

    @OnWebSocketMessage
    public void onMessage(String msg)
    {
        LOG.info("onMessage() - {}", msg);
    }
}