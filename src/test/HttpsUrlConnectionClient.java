package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * 资料地址:http://www.blogjava.net/stevenjohn/archive/2012/09/27/388647.html
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  [陈世荣:jx.chenshirong@mail01.huawei.com]
 * @version  [版本号, Jul 26, 2014]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HttpsUrlConnectionClient extends TestCase
{
    // 客户端密钥库
    private String sslKeyStorePath;
    
    private String sslKeyStorePassword;
    
    private String sslKeyStoreType;
    
    // 客户端信任的证书
    private String sslTrustStore;
    
    private String sslTrustStorePassword;
    
    // 上面发布的servlet请求地址
    private String httpsUrlConnectionUrl = "https://localhost:8443/ssl/httpsUrlConnectionRequest";
    
    @Before
    public void setUp()
    {
        // 这是密钥库
        sslKeyStorePath = "G:/temp/cert/20140726/jxclient.keystore";
        sslKeyStorePassword = "mobilejx";
        sslKeyStoreType = "JKS"; // 密钥库类型，有JKS PKCS12等
        // 信任库，这里需要服务端来信任客户端才能调用，因为这个我是配置的https双向验证，不但是要客户端信任服务端，服务端也要信任客户端。
        sslTrustStore = "G:/temp/cert/20140726/jxtrust.keystore";
        sslTrustStorePassword = "jxmobile";
        System.setProperty("javax.net.ssl.keyStore", sslKeyStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", sslKeyStorePassword);
        System.setProperty("javax.net.ssl.keyStoreType", sslKeyStoreType);
        // 设置系统参数
        System.setProperty("javax.net.ssl.trustStore", sslTrustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", sslTrustStorePassword);
        System.setProperty("java.protocol.handler.pkgs", "sun.net.www.protocol");
    }
    
    @Test
    public void testHttpsUrlConnectionClient()
    {
        try
        {
            URL url = new URL(httpsUrlConnectionUrl);
            // 对于主机名的验证，因为配置服务器端的tomcat.keystore的证书的时候，是需要填写用户名的，一般用户名来说是本地ip地址，或者本地配置的域名
            HostnameVerifier hv = new HostnameVerifier()
            {
                public boolean verify(String urlHostName, SSLSession session)
                {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            // 编写HttpsURLConnection 的请求对象，这里需要注意HttpsURLConnection
            // 比我们平时用的HttpURLConnection对了一个s，因为https是也是遵循http协议的，并且是采用ssl这个安全套接字来传输信息的，但是也有可能遭到黑客的攻击
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置请求方式为post,这里面当然也可以用get，但是我这里必须用post
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setReadTimeout(30000);
            String user = "abin";
            String pwd = "abing";
            String request = "user=" + user + "&pwd=" + pwd;
            OutputStream out = connection.getOutputStream();
            // 下面的这句话是给servlet发送请求内容
            out.write(request.getBytes());
            out.flush();
            out.close();
            // 接收请求的返回值
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null)
            {
                stb.append(line);
            }
            Integer statusCode = connection.getResponseCode();
            System.out.println("返回状态码:" + statusCode);
            reader.close();
            connection.disconnect();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
