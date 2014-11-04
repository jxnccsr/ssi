package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ReceiveHttpsUrlConnectionRequest extends HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("receive https request");
        /**
         * 这个主要是接收，由对方给以POST形式发过来的内容，这种内容不是以key-value的形式发的，而是直接通过Java的 string content="test https double auth";
         * BufferedWriter writer = new BufferedWriter(); writer.writer(content.getBytes());
         * 通过这种形式发过来的内容的接收，由于直接放到request里面发送过来的，所以的从request里面来接收。 之前做银联的手机支付的时候也是这么传递参数的。
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuffer stb = new StringBuffer();
        // 循环的一行一行的读取内容
        while ((line = reader.readLine()) != null)
        {
            stb.append(line);
        }
        // 打印读取到的内容。
        System.out.println("stb=" + stb.toString());
        // 给调用者返回内容
        PrintWriter write = response.getWriter();
        write.write("receive HttpsUrlConnection success");
        write.flush();
        write.close();
    }
}
