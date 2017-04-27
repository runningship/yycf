<%@page import="org.jsoup.nodes.Document"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.bc.yycf.util.PullDataHelper"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="org.bc.sdak.Page"%>
<%@page import="org.bc.sdak.SimpDaoTool"%>
<%@page import="org.bc.sdak.CommonDaoService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String name = request.getParameter("name");
//增加百度查看链接
String html = PullDataHelper.getHttpData("http://baike.baidu.com/search?word="+name,"utf8");
Document result = Jsoup.parse(html);
String href = result.select(".search-list .result-title").first().attr("href");
request.setAttribute("link", href);
CommonDaoService dao = SimpDaoTool.getGlobalCommonDaoService();
Page<Map> p = new Page<Map>();
p.order = "desc";
p.orderBy="n.value";
p.setPageSize(100);
p = dao.findPage(p,"select n.value as value ,n.unit as unit ,f.name as fname ,f.id as fid from Nutrient n , Food f where f.id = n.foodId and n.name=?",true, new Object[]{ name});
request.setAttribute("name", name);
request.setAttribute("netrients", p.getResult());
if(p.getResult().size()>0){
	request.setAttribute("maxValue", p.getResult().get(0).get("value"));	
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />     
<meta name="apple-mobile-web-app-capable" content="yes" /> 
<link href="css/zhuzi.css?2" rel="stylesheet" />
<script type="text/javascript" src="js/jquery1.11.0.js"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery('.skillbar').each(function(){
		jQuery(this).find('.skillbar-title').css('background',getRandomColor());
		jQuery(this).find('.skillbar-bar').css('background',getRandomColor());
		jQuery(this).find('.skillbar-bar').animate({
			width:jQuery(this).attr('data-percent')*($('body').width()-80-10)
			//width:500
		},1000);
	});
});

function getRandomColor(){ 
	return "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6); 
}

function detail(id){
	window.location='detail.jsp?foodId='+id;
}
</script>
<style type="text/css">
body{margin:0px;font-family: 微软雅黑;width: 98%;padding-left: 1%;}
.name{text-align:center;height: 40px;line-height: 40px;font-size: 20px;}
.conts{position: absolute;
right: -100px;
top: 0px;
font-size: 11px;
height: 35px;
line-height: 35px;}
.itemRow{margin-right: 100px;
position: relative;}
.desc{margin-bottom: 10px;}
</style>
</head>
<body>
<div class="name" ><a href="${link }">${name }</a></div>
<div class="desc">每100克的营养成分含量</div>
<c:forEach items="${netrients }"  var="netrient">
	<div onclick="detail(${netrient.fid})" class="skillbar clearfix " data-percent="${netrient.value / maxValue }">
		<div class="skillbar-title" ><span>${netrient.fname}</span></div>
		<div class="skillbar-bar" ></div>
		<div class="skill-bar-percent">${netrient.value }${netrient.unit }</div>
	</div>
</c:forEach>
</body>
<!-- 食物营养成分含量比例排序 -->
</html>
