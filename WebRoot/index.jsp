<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(request.getParameter("username")+"");
out.println(request.getParameter("username")+"");
out.println(request.getParameter("username")+"");
out.println(request.getParameter("username")+"");
out.println(request.getParameter("username")+"");


for(Enumeration en=request.getAttributeNames();en.hasMoreElements();){
    String name=(String)en.nextElement();
    out.println("</br>");
    out.println(name);
    out.println("</br>");
    out.println(" = "+request.getAttribute(name));
    out.println("</br>");
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my JSP page. <br>
    <form action="index2.jsp" method="post">
     <input type="hidden" name="username" value="jxgacsr" />
     <input type="submit" value="提交" />
    </form>
    
  </body>
</html>
