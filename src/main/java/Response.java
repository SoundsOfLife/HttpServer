public class Response {

    private String status = "HTTP/1.1 " + 200 + "OK\r\n";
    private String date = "Date: " + Utils.getGMTDate() + "\r\n";
    private String server = "server: kikyou1.0\r\n";
    private String contentEncoding = "Content_Encoding: gzip\r\n";
    private String contentType = "Content_Type: text/html\r\n";

    private String text;

    public Response() {
    }

    public void SetResponse(Request request) {
        String url = request.getUrl();
        if (url.contains("html") || url.contains("htm")) {
            contentType = "Content_Type: " + "text/html\r\n";
        } else if (url.contains(".jpg") || url.contains("jpeg")) {
            contentType = "Content_Type: " + "image/jpeg\r\n";
        } else if (url.contains("gif")) {
            contentType = "Content_Type: " + "image/gif\r\n";
        } else {
            contentType = "Content_Type: " + "application/octet-stream\r\n";
        }
    }

    public Response(String status, String contentType) {
        this.status = "HTTP/1.1 " + status + "\r\n";
        this.contentType = "Content_Type: " + contentType + "\r\n";
    }

    public Response(String text) {
        this.text = text;
    }

    public Response(String status, String contentType, String text) {
        this.status = "HTTP/1.1 " + status + "\r\n";
        this.contentType = "Content_Type: " + contentType + "\r\n";
        this.text = text;
    }

    public byte[] getResponse() {
        StringBuilder sb = new StringBuilder();
        sb.append(status);
        sb.append(contentType);
        sb.append(text);
        sb.append("\r\n");
        return sb.toString().getBytes();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
