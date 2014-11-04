package test;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
/**
 * 
 * <测试没通过>
 * <http://www.cnblogs.com/devinzhang/archive/2012/02/28/2371631.html>
 * 
 * @author  [陈世荣:jx.chenshirong@mail01.huawei.com]
 * @version  [版本号, Jul 28, 2014]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyX509TrustManager implements X509TrustManager {
    /*
     * The default X509TrustManager returned by SunX509.  We'll delegate
     * decisions to it, and fall back to the logic in this class if the
     * default X509TrustManager doesn't trust it.
     */
    X509TrustManager sunJSSEX509TrustManager;
    MyX509TrustManager() throws Exception {
        // create a "default" JSSE X509TrustManager.
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("G:/temp/cert/20140727/truststore.jks"),
            "222222".toCharArray());
        TrustManagerFactory tmf =
        TrustManagerFactory.getInstance("SunX509", "SunJSSE");
        tmf.init(ks);
        TrustManager tms [] = tmf.getTrustManagers();
        /*
         * Iterate over the returned trustmanagers, look
         * for an instance of X509TrustManager.  If found,
         * use that as our "default" trust manager.
         */
        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                return;
            }
        }
        /*
         * Find some other way to initialize, or else we have to fail the
         * constructor.
         */
        throw new Exception("Couldn't initialize");
    }
    /*
     * Delegate to the default trust manager.
     */
    public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException excep) {
            // do any special handling here, or rethrow exception.
        }
    }
    /*
     * Delegate to the default trust manager.
     */
    public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException excep) {
            /*
             * Possibly pop up a dialog box asking whether to trust the
             * cert chain.
             */
        }
    }
    /*
     * Merely pass this through.
     */
    public X509Certificate[] getAcceptedIssuers() {
        return sunJSSEX509TrustManager.getAcceptedIssuers();
    }
    
    
    public static void main(String[] args)
    {
        try
        {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            // 创建URL对象
            URL myURL = new URL("https://localhost:8443/ssl/index.jsp?username=10");
            // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
            HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
            httpsConn.setSSLSocketFactory(ssf);
            // 取得该连接的输入流，以读取响应内容
            InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream());
            // 读取服务器的响应内容并显示
            int respInt = insr.read();
            while (respInt != -1) {
                System.out.print((char) respInt);
                respInt = insr.read();
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

       