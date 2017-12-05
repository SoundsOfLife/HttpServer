import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int port = 8088;

    public static void main(String args[]) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        Socket socket;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    socket = serverSocket.accept();
                    ExecutorThread requestThread = new ExecutorThread(socket);
                    executor.execute(requestThread);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            executor.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建socket失败");
        }

    }
}
