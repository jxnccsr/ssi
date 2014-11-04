import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

public class Client {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream keyStoreIn = new FileInputStream(new File("G:/temp/cert/20140726/jxclient.keystore"));
        FileInputStream trustStoreIn = new FileInputStream(new File("G:/temp/cert/20140726/jxtrust.keystore"));
        
        try {
            keyStore.load(keyStoreIn, "mobilejx".toCharArray());
            trustStore.load(trustStoreIn, "jxmobile".toCharArray());
        } finally {
            keyStoreIn.close();
            trustStoreIn.close();
        }
        SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore, "mobilejx", trustStore);
        httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https",socketFactory, 8443));
        HttpPost httpget = new HttpPost("https://localhost:8443/ssl/index.jsp?username=10");
        System.out.println("Request:" + httpget.getRequestLine());
        HttpResponse response = httpclient.execute(httpget);
        System.out.println(response.getStatusLine());
        String returnString = "";
        InputStream returnStream = response.getEntity().getContent();
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
        httpclient.getConnectionManager().shutdown();
        
        
    }
}

