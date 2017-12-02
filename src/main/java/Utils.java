import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Utils {

    public static final String _H_1_1_ = "HTTP/1.1";
    public static final String _OK_ = "OK\r\n";
    public static final String __ = " ";

    public static final String _200_ = "200";
    public static final String _404_ = "404";
    public static final String _500_ = "500";
    public static final String _503_ = "503";

    // gzip压缩函数
    public static byte[] gzip(byte[] data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] ret = bos.toByteArray();
        bos.close();
        return ret;
    }

    // gzip解压缩函数
    public static byte[] ungzip(byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buf = new byte[1024];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((num = gzip.read(buf, 0, buf.length)) != -1) {
            bos.write(buf, 0, num);
        }
        gzip.close();
        bis.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        return ret;
    }

    public static String getGMTDate() {
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'\r\n", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String str = sdf.format(cd.getTime());
        return str;
    }

    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    public static String getURI(String requestText) {
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

    public static String getResponseHead(String url, int statusCode) {
        /* 创建HTTP响应结果 */
        String contentType = getContentType(url);
        StringBuffer sb = new StringBuffer();
        // HTTP响应的第一行
        String responseFirstLine = "HTTP/1.1 " + statusCode + " OK\r\n";
        sb.append(responseFirstLine);
        // HTTP响应头
        String responseHeader = "Content-Type:" + contentType + "\r\n\r\n";
        sb.append(responseHeader);

        return sb.toString();
    }

    public static byte[] getReponse(String requestText) throws Exception {

        StringBuffer sb = new StringBuffer();

        String url = getURI(requestText);
        String method = getMethod(requestText);

        System.out.println(method);

        if (method.equals("GET")) {
            InputStream htmlInputStream = null;
            try {
                htmlInputStream = getResponseContent(url);
                sb.append(getResponseHead(url, 200));

                byte[] buffer = new byte[128];

                int len = 0;
                if (htmlInputStream != null) {
                    while ((len = htmlInputStream.read(buffer)) != -1) {
                        String str = new String(buffer);
                        sb.append(str);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                sb.append(getResponseHead(url, 404));
            }
        } else if (method.equals("POST")) {

        } else if (method.equals("PUT")) {

        } else if (method.equals("DELETE")) {

        }

        System.out.println("Text:" + sb.toString());

//        return gzip(sb.toString().getBytes());
        return sb.toString().getBytes();
    }
}
