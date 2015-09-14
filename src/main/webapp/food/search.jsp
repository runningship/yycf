<%@page import="org.bc.yycf.entity.Food"%>
<%@page import="org.bc.sdak.Page"%>
<%@page import="org.bc.sdak.SimpDaoTool"%>
<%@page import="org.bc.sdak.CommonDaoService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
CommonDaoService dao = SimpDaoTool.getGlobalCommonDaoService();
Page<Food> p = new Page<Food>();
p.setPageSize(5);
p = dao.findPage(p , "from Food where leibie=? order by NewID()", "谷类");
request.setAttribute("guleiList", p.getResult());

p = dao.findPage(p , "from Food where leibie=? order by NewID()", "蔬菜");
request.setAttribute("sucaiList", p.getResult());

p = dao.findPage(p , "from Food where leibie=? order by NewID()", "水果");
request.setAttribute("shuiguoList", p.getResult());

p = dao.findPage(p , "from Food where leibie=? order by NewID()", "肉类");
request.setAttribute("rouleiList", p.getResult());

p = dao.findPage(p , "from Food where leibie=? order by NewID()", "菌类");
request.setAttribute("junleiList", p.getResult());

p = dao.findPage(p , "from Food where leibie=? order by NewID()", "零食饮料");
request.setAttribute("linshiList", p.getResult());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />     
<meta name="apple-mobile-web-app-capable" content="yes" /> 
<script type="text/javascript" src="admin/Js/jquery.js"></script>
<!-- <script type="text/javascript" src="admin/Js/buildHtml.js"></script> -->
<script type="text/javascript">
var prepareSearch = false;
var searching = false;
$(function(){
});
function doSearch(){
	var a=$('form[name=form1]').serialize();
	searching = true;
	$.ajax({
	    type: 'post',
	    url: '/food/c/admin/food/listData',
	    data: a,
	    dataType:'json',
	    success: function(json){
	        buildHtmlWithJsonArray("resultItem",json['data']);
	    },
	    complete:function(){
	    	searching = false;
	    }
	  });
}

function keyUp2(){
	prepareSearch=true;
	setTimeout(function(){
		if($('#name').val().trim()==''){
			//clear result
			buildHtmlWithJsonArray("resultItem",[]);
			return;
		}
		if(prepareSearch && !searching){
			doSearch();
		}
	} , 500);
}
function keyDown2(){
	prepareSearch = false;
}

function blurSearch(){
	buildHtmlWithJsonArray("resultItem",[]);
}
function detail(id){
	window.location="detail.jsp?foodId="+id;
}

function bodyClick(){
	if($(event.srcElement).attr('xx')=='query' || $(event.srcElement).attr('id')=='name'){
		
	}else{
		blurSearch();
	}
}
</script>
<style type="text/css">
body{background: #333;margin:0px;color:white;font-family: 微软雅黑;}
.top{background:#666;height:40px;line-height: 40px; display:none;}
.top .historyBtn{float: right;margin-right: 1%;
color: white;background: #999;height: 26px;line-height: 26px;margin-top: 10px;font-size: 13px;padding: 0px 10px;}
.main{width: 82%;margin-left: 4%;margin-top: 8%;}
.search{position:relative;}
.search input{background:url('images/search_16.png');background-repeat: no-repeat;width:100%;background-color: beige;display:block;
	height:35px;line-height:35px\9;background-position: 12px 11px;padding-left:3em;border:0px solid;border-radius: 20px;}
.search .voice{position:absolute;right:-15px;top:8px;height:20px;padding: 0px 7px;}
.row{height:30px;line-height:30px;padding: 10px 0px;vertical-align:top;}
.row .type{color:deeppink;width:40px;display:inline-block;}
.row a{padding-right:10px;}
.row .items{display:inline}
.search .result{background: white;color: black;font-size: 18px;margin-left: 30px;width: 90%;position: absolute; max-height:400px;overflow: auto;}
.search .result .none{font-size:13px;display:none}
.search .result  div{height:40px;line-height:40px;border-bottom: 1px solid #eee;margin-left:12px;}
</style>
</head>
<body onclick="bodyClick();">
<div class="top">
	<a href="search.jsp">刷新</a><span class="historyBtn">历史搜索</span>
</div>
<div class="main">
<div class="search">
	<form name="form1">
	<input name="name"  id="name" onkeyup="keyUp2();" onkeydown="keyDown2();" />
	<img onclick="alert(1);" class="voice" src="images/voice_24.png" alt="" />
	</form>
	<div class="result">
		<div class="none">没有合适的结果</div>
		<span>
			<div class="resultItem"  xx="query" style="display:none" onclick="detail($[id])">$[name]</div>
		</span>
	</div>
</div>
<div class="category">
	<p></p>
	<table>
	<tr class="row">
		<td><div class="type">谷类</div></td>
		<td><div class="items">
			<c:forEach items="${guleiList }" var="food">
			<a onclick="detail(${food.id})">${food.name }</a>
		</c:forEach>
		</div></td>
	</tr>
	<tr class="row">
		<td><div class="type">蔬菜</div></td>
		<td><div class="items">
			<c:forEach items="${sucaiList }" var="food">
			<a onclick="detail(${food.id})">${food.name }</a>
		</c:forEach>
		</div></td>
	</tr>
	<tr class="row">
		<td><div class="type">水果</div></td>
		<td><div class="items">
			<c:forEach items="${shuiguoList }" var="food">
			<a onclick="detail(${food.id})">${food.name }</a>
		</c:forEach>
		</div></td>
	</tr>
	<tr class="row">
		<td><div class="type">肉类</div></td>
		<td><div class="items">
			<c:forEach items="${rouleiList }" var="food">
			<a onclick="detail(${food.id})">${food.name }</a>
		</c:forEach>
		</div></td>
	</tr>
	<tr class="row">
		<td><div class="type">菌类</div></td>
		<td><div class="items">
			<c:forEach items="${junleiList }" var="food">
			<a onclick="detail(${food.id})">${food.name }</a>
		</c:forEach>
		</div></td>
	</tr>
	<tr class="row">
		<td><div class="type">零食饮料</div></td>
		<td><div class="items">
			<c:forEach items="${linshiList }" var="food">
			<a onclick="detail(${food.id})">${food.name }</a>
		</c:forEach>
		</div></td>
	</tr>
	</table>
</div>
</div>
</body>
</html>
