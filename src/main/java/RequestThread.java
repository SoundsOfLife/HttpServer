import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class RequestThread implements Runnable {

    private Socket responseSocket;

    public RequestThread(Socket responseSocket) {
        this.responseSocket = responseSocket;
    }

    public void run() {
        //从socket读入数据
        InputStream socketIn = null;
        try {
            socketIn = responseSocket.getInputStream();
            int size = socketIn.available();
            byte[] requestBuff = new byte[size];
            socketIn.read(requestBuff);
            String requestText = new String(requestBuff);
            System.out.println(requestText);
            responseSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
