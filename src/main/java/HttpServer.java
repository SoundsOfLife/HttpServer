import java.io.*;
import java.net.*;

public class HttpServer extends Thread {
    private static final int port = 8088;
    private ServerSocket serverSocket = null;

    public HttpServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("HTTPServer startup OK...");
    }

    public String getRequest(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        int size = socketIn.available();
        byte[] requestBuff = new byte[size];
        socketIn.read(requestBuff);
        return new String(requestBuff);
    }

    public static String getURL(String requestText) {
        String firstLine = requestText.substring(0, requestText.indexOf("\r\n"));
        String[] parts = firstLine.split(" ");

        return parts[1];
    }

    public static String getContentType(String URI) {
        // 决定HTTP响应正文的类型
        String contentType;
        if (URI.indexOf("html") != -1 || URI.indexOf("htm") != -1)
            contentType = "text/html";
        else if (URI.indexOf("jpg") != -1 || URI.indexOf("jpeg") != -1)
            contentType = "image/jpeg";
        else if (URI.indexOf("gif") != -1)
            contentType = "image/gif";
        else
            contentType = "application/octet-stream";
        return contentType;
    }

    public static InputStream getResponseContent(String URI)
            throws FileNotFoundException {
        InputStream htmlInputStream = new FileInputStream(
                System.getProperty("user.dir") + "/WebRoot" + URI);
        return htmlInputStream;
    }

    public static String getMethod(String requestText) {
        String firstLine = requestText.substring(0, requestText.indexOf("\r\n"));
        String[] parts = firstLine.split(" ");

        return parts[0];
    }

    public static String getResponseHead(String url, Integer statusCode, Integer contentLength) {
        /* 创建HTTP响应结果 */
        String contentType = getContentType(url);
        StringBuffer sb = new StringBuffer();

        // HTTP响应版本和状态码
        String responseStatus = "HTTP/1.1 " + statusCode + " OK\r\n";
        sb.append(responseStatus);

        // HTTP响应文件类型
        String responseContentType = "Content-Type:" + contentType + "\r\n";
        sb.append(responseContentType);

        // 响应日期
        String responseDate = "Date: " + Utils.getGMTDate();
        sb.append(responseDate);

        // 响应服务器类型
        String responseServer = "Server: kikyou\r\n";
        sb.append(responseServer);

//        String responseVary = "Vary: Accept-Encoding,User-Agent\r\n";
//        sb.append(responseVary);

//        String responseContentLength = "Content-Length: " + contentLength + "\r\n";
//        sb.append(responseContentLength);

        // HTTP响应压缩格式
//        String responseContentEncoding = "Content-Encoding: gzip\r\n\r\n";
//        sb.append(responseContentEncoding);

        return sb.toString();
    }

    public static String Get(String url) {
        StringBuilder sb = new StringBuilder();
        InputStream htmlInputStream = null;
        try {
            htmlInputStream = getResponseContent(url);

            StringBuilder temp = new StringBuilder();
            byte[] buffer = new byte[128];

            if (htmlInputStream != null) {
                while (htmlInputStream.read(buffer) != -1) {
                    temp.append(new String(buffer));
                }
            }
//            byte[] text = Utils.gzip(temp.toString().getBytes());
            byte[] text = temp.toString().getBytes();
            sb.append(getResponseHead(url, 200, text.length));
            sb.append(new String(text));
        } catch (FileNotFoundException e) {
            sb.append(getResponseHead(url, 404, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String Post() {
        StringBuilder sb = new StringBuilder();
        //TODO
        return sb.toString();
    }

    public static String Put() {
        StringBuilder sb = new StringBuilder();
        //TODO
        return sb.toString();
    }

    public static String Delete() {
        StringBuilder sb = new StringBuilder();
        //TODO
        return sb.toString();
    }

    public static byte[] getResponse(String requestText) throws Exception {

        StringBuilder sb = new StringBuilder();

        String url = getURL(requestText);
        String method = getMethod(requestText);

        if (method.equals("GET")) {
            sb.append(Get(url));
        } else if (method.equals("POST")) {

        } else if (method.equals("PUT")) {

        } else if (method.equals("DELETE")) {

        }

        System.out.println(sb.toString());
        return sb.toString().getBytes();
    }

    public void service() throws InterruptedException {
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();

                String request = getRequest(socket);
                System.out.println("HttpServer receive request:\n" + request);
                OutputStream out = socket.getOutputStream();

                out.write(getResponse(request));

                Thread.sleep(1000);
                socket.close(); // 关闭TCP连接
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException,
            InterruptedException {
        HttpServer httpServer = new HttpServer();
        // System.out.println(System.getProperty("user.dir"));
        httpServer.service();
    }

}