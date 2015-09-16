<%@page import="java.util.ArrayList"%>
<%@page import="org.bc.yycf.util.FileHelper"%>
<%@page import="org.apache.commons.io.filefilter.DirectoryFileFilter"%>
<%@page import="org.apache.commons.io.filefilter.FileFileFilter"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String rootPath = request.getServletContext().getRealPath("/");
String cssPath = request.getServletContext().getRealPath("/css");
String jsPath = request.getServletContext().getRealPath("/js");
String htmlPath = request.getServletContext().getRealPath("/html");
//String imgaePath = request.getServletContext().getRealPath("/css");
List<File> allFiles = new ArrayList<File>();
allFiles.addAll(FileHelper.listFiles(new File(cssPath)));
allFiles.addAll(FileHelper.listFiles(new File(jsPath)));
allFiles.addAll(FileHelper.listFiles(new File(htmlPath)));
JSONArray files = new JSONArray();
JSONObject jobj = new JSONObject();
for(File file : allFiles){
	if(file.isDirectory()){
		continue;
	}
	String fileName =file.getAbsolutePath().replace(rootPath, "").replace("\\","/"); 
	jobj.put(fileName , file.length());
	//jobj.put("length", file.length());
	files.add(fileName);
}
jobj.put("files", files);
out.write(jobj.toString());
%>
