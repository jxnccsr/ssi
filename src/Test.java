import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.Iterator;
import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test
{
    
    public static SSLSocketFactory getSocketFactory() throws Exception{
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream keyStoreIn = new FileInputStream(new File("G:/temp/cert/20140428/tomcat.keystore"));
        FileInputStream trustStoreIn = new FileInputStream(new File("G:/temp/cert/20140428/tomcat.keystore"));
        
        try {
            keyStore.load(keyStoreIn, "password".toCharArray());
            trustStore.load(trustStoreIn, "password".toCharArray());
        } finally {
            keyStoreIn.close();
            trustStoreIn.close();
        }
        SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore, "password", trustStore);
        
        return socketFactory;
    }
    
    /**
     * <测试1>
     * <功能详细描述>
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static void test1() throws Exception{
        HttpClient httpclient = new DefaultHttpClient();
//      httpclient.getParams().setParameter("http.useragent",
//      "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//
     
      httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https",getSocketFactory(), 8443));
      HttpPost httpget = new HttpPost("https://localhost:8443/ssl/index.jsp?username=10");
      System.out.println("Request:" + httpget.getRequestLine());
      HttpResponse response = httpclient.execute(httpget);
      System.out.println(response.getEntity().getContentLength());
      System.out.println(response.getStatusLine());
      DataInputStream in = new DataInputStream(response.getEntity().getContent());
      String s="";
      try {            
          s=in.readLine();
          System.out.println(s.length());
          
          while((s=in.readLine()).length()!=0) { 
           System.out.println(s.length());
          System.out.println("返回："+new String(s.getBytes("iso-8859-1"),"utf-8"));            
          }
          in.close();
             
       }catch(IOException  e)   {            
          e.printStackTrace();        
       }  
      
      httpclient.getConnectionManager().shutdown();
    }
    
    /**
     * <测试2>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public static void test2(){
        try
        {
            InputStream returnStream = null;
            HttpsURLConnection conn = null;
            int connectTimeout=15000;
            int readTimeout=30000;
            String requestMethod="GET";
            String charEncode="utf-8";
            String url="https://localhost:8443/ssl/index.jsp?username=10";
            System.setProperty("javax.net.debug","ssl");
            System.setProperty("javax.net.ssl.keyStore", "G:/temp/cert/20140428/tomcat.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword","password");
            System.setProperty("javax.net.ssl.keyStoreType", "JKS");
            System.setProperty("javax.net.ssl.trustStore", "G:/temp/cert/20140428/tomcat.keystore");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            URL serverUrl = new URL(null, url, new sun.net.www.protocol.https.Handler());
            conn = (HttpsURLConnection) serverUrl.openConnection();
            
            if (connectTimeout != -1) {
                conn.setConnectTimeout(connectTimeout);
            }
            if (readTimeout != -1) {
                conn.setReadTimeout(readTimeout);
            }
            conn.setRequestMethod(requestMethod);   
            conn.setDoOutput(true);
            conn.connect();
            conn.getOutputStream().write("jxgacsr".getBytes(charEncode));
            if (conn.getResponseCode() != 200) {
                returnStream = conn.getErrorStream();
            } else {
                returnStream = conn.getInputStream();
            } 
            String returnString = "";
            if (returnStream != null) {
                try {
                    BufferedReader br   = new BufferedReader(new InputStreamReader(returnStream, "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    String line         = "";
                    while((line = br.readLine()) != null) {
                        buffer.append(line);
                    }
                    
                    returnString = buffer.toString();
                    System.out.println("返回：==》"+returnString);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    /**
     * <测试2>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public static void test3(){
        try
        {
            InputStream returnStream = null;
            HttpsURLConnection conn = null;
            int connectTimeout=15000;
            int readTimeout=30000;
            String requestMethod="GET";
            String charEncode="utf-8";
            String url="https://localhost:8443/ssl/index.jsp?username=10";
            System.setProperty("javax.net.debug","ssl");
            System.setProperty("javax.net.ssl.keyStore", "G:/temp/cert/20140727/client/client.p12");
            System.setProperty("javax.net.ssl.keyStorePassword","123456");
            System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
            System.setProperty("javax.net.ssl.trustStore", "G:/temp/cert/20140727/truststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "222222");
            System.setProperty("javax.net.ssl.truststoreType", "JKS");
            URL serverUrl = new URL(null, url, new sun.net.www.protocol.https.Handler());
            conn = (HttpsURLConnection) serverUrl.openConnection();
            
            if (connectTimeout != -1) {
                conn.setConnectTimeout(connectTimeout);
            }
            if (readTimeout != -1) {
                conn.setReadTimeout(readTimeout);
            }
            conn.setRequestMethod(requestMethod);   
            conn.setDoOutput(true);
            conn.connect();
            conn.getOutputStream().write("jxgacsr".getBytes(charEncode));
            if (conn.getResponseCode() != 200) {
                returnStream = conn.getErrorStream();
            } else {
                returnStream = conn.getInputStream();
            } 
            String returnString = "";
            if (returnStream != null) {
                try {
                    BufferedReader br   = new BufferedReader(new InputStreamReader(returnStream, "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    String line         = "";
                    while((line = br.readLine()) != null) {
                        buffer.append(line);
                    }
                    
                    returnString = buffer.toString();
                    System.out.println("返回：==》"+returnString);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        Test.test1();
//        Test.test2();
//        Test.test3();
        Test.test3();
    }

}
