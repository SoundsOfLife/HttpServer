import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ExecutorThread implements Runnable {

    private String requestText;

    public ExecutorThread(String requestText) {
        this.requestText = requestText;
    }

    public void run() {
        System.out.println(requestText);
//            Request request = new Request(requestText);

    }
}
