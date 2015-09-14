<%@page import="org.bc.yycf.entity.Food"%>
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
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String foodId = request.getParameter("foodId");
CommonDaoService dao = SimpDaoTool.getGlobalCommonDaoService();
List<Map> list = dao.listAsMap("select name as name , value as value , unit as unit from Nutrient where foodId=? and unit <>'%' ", Integer.valueOf(foodId));

//计算每种营养素的均值avg
List<Map> avgList = dao.listAsMap("select AVG(value) as value ,name as name ,unit as unit  from Nutrient where unit<> '%' group by name , unit  order by name ");
Map<String , Double> avgMap  = new HashMap<String , Double>();
for(Map map : avgList){
	avgMap.put((String)map.get("name"), (Double)map.get("value"));
}
//计算每种营养素与均值的比 pavg
Double maxPAVG = 0d;
for(Map nutrient : list){
	if(avgMap.containsKey(nutrient.get("name"))){
		Float v1 = (Float)nutrient.get("value");
		Double v2 = (Double)avgMap.get(nutrient.get("name"));
		nutrient.put("avg", v2);
		if(v1/v2>maxPAVG){
			maxPAVG = v1/v2;
		}
		nutrient.put("pavg", v1/v2);
	}else{
		//不应该存在
	}
}
final Double finalMaxPAVG = maxPAVG;
//按照 pavg/maxPAVG排序
Collections.sort(list, new Comparator<Map>(){
	
	@Override  
    public int compare(Map b1, Map b2) {  
		Double pavg1 = (Double)b1.get("pavg");
		Double pavg2 = (Double)b2.get("pavg");
        if( (pavg1/finalMaxPAVG) <= (pavg2/finalMaxPAVG)){
        	return 1;
        }else{
        	return -1;
        }
    }  
});
Food po = dao.get(Food.class, Integer.valueOf( foodId));
request.setAttribute("fname", po.name);
request.setAttribute("nutrients", list);
request.setAttribute("maxPAVG", maxPAVG);
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
			width:jQuery(this).attr('data-percent')*($('body').width()-80-20)
			//width:'10%'
			//width:jQuery(this).attr('data-percent')*100+'%'
		},1000);
	});
});

function getRandomColor(){ 
	return "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6); 
}

function sort(name){
	window.location='sort.jsp?name='+name;
}
</script>
<style type="text/css">
body{margin:0px;font-family: 微软雅黑;width: 98%;padding-left: 1%;}
.name{text-align:center;height: 40px;line-height: 40px;font-size: 20px;}
.avg{
font-size: 11px;
height: 35px;
line-height: 35px;
position: relative;}
.desc{margin-bottom: 10px;}
</style>
</head>
<body>
<div class="name">${fname }</div>
<div class="desc">每100克的营养成分含量(括号内表示均值)</div>
<c:forEach items="${nutrients }"  var="netrient">
<div class="itemRow" onclick="sort('${netrient.name}')">
	<div class="skillbar clearfix " data-percent="${netrient.pavg / maxPAVG }">
		<div class="skillbar-title" style="background: #2980b9;"><span>${netrient.name}</span></div>
		<div class="skillbar-bar" style="background: #3498db;"></div>
		<div class="skill-bar-percent">${netrient.value }${netrient.unit }  <span class="avg">(<f:formatNumber value="${netrient.avg }" pattern="#.###"/>)</span></div>
	</div>
</div>
</c:forEach>
</body>
</html>
