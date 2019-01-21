package cn.itrip.trade.config;

public class AlipayConfig {

    // 商户appid
    private String appid = "";
    // 私钥 pkcs8格式的
    private String rsaPrivatekey = "";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String notifyUrl = "";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    private String returnUrl = "";
    // 请求网关地址
    private String url = "";
    // 编码
    private String charset = "";
    // 返回格式
    private String format = "";
    // 支付宝公钥
    private String alipayPublickey = "";
    // 日志记录目录
    private String logPath = "";
    // RSA2
    private String signtype = "";
    //支付成功跳转页
    private String paymentSuccessUrl="";
    //支付失败跳转页
    private String paymentFailUrl="";

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getRsaPrivatekey() {
        return rsaPrivatekey;
    }

    public void setRsaPrivatekey(String rsaPrivatekey) {
        this.rsaPrivatekey = rsaPrivatekey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAlipayPublickey() {
        return alipayPublickey;
    }

    public void setAlipayPublickey(String alipayPublickey) {
        this.alipayPublickey = alipayPublickey;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    public String getPaymentSuccessUrl() {
        return paymentSuccessUrl;
    }

    public void setPaymentSuccessUrl(String paymentSuccessUrl) {
        this.paymentSuccessUrl = paymentSuccessUrl;
    }

    public String getPaymentFailUrl() {
        return paymentFailUrl;
    }

    public void setPaymentFailUrl(String paymentFailUrl) {
        this.paymentFailUrl = paymentFailUrl;
    }
}
