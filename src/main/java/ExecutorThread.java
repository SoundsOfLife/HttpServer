import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ExecutorThread implements Runnable {

    private Socket socket;

    public ExecutorThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        InputStream socketIn = null;
        try {
            socketIn = socket.getInputStream();
            int size = socketIn.available();
            byte[] requestBuff = new byte[size];
            socketIn.read(requestBuff);
            String requestText = new String(requestBuff);

            Request request = new Request(requestText);
            String method = request.getMethod();
            String url = request.getUrl();
            Response response = new Response();
            try {
                byte[] text = Utils.getHtml(url);
                response.setStatus("200");
                response.setText(new String(text));
            } catch (IOException e) {
                response.setStatus("404");
            }
            
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
