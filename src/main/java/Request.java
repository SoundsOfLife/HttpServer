public class Request {

    private String method;
    private String url;
    private String version = "HTTP/1.1";
    private String Host;
    private String acceptLanguage;
    private String acceptEncoding;
    private String accept;
    private String userAgent;
    private String connection;

    public Request() {
    }

    public Request(String requestText) {
        String[] lines = requestText.split("\r\n");
        if (lines.length > 0) {
            String[] header = lines[0].split(" ");
            method = header[0];
            url = header[1];
        }
    }

    public Request(String method, String url, String version, String host, String acceptLanguage, String acceptEncoding, String accept, String userAgent, String connection) {
        this.method = method;
        this.url = url;
        this.version = version;
        Host = host;
        this.acceptLanguage = acceptLanguage;
        this.acceptEncoding = acceptEncoding;
        this.accept = accept;
        this.userAgent = userAgent;
        this.connection = connection;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
