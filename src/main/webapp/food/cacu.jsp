<%@page import="org.bc.yycf.entity.Food"%>
<%@page import="org.bc.sdak.Page"%>
<%@page import="org.bc.sdak.SimpDaoTool"%>
<%@page import="org.bc.sdak.CommonDaoService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />     
<meta name="apple-mobile-web-app-capable" content="yes" /> 
<script type="text/javascript" src="js/jquery1.11.0.js"></script>
<script type="text/javascript" src="js/buildHtml.js"></script>
<script type="text/javascript" src="js/layer/layer.js"></script>
<script type="text/javascript">
function openFoodWindow(){
	layer.open({
	    type: 1,
	    skin: 'layui-layer-rim', //加上边框
	    area: ['200pt', '100pt'], //宽高
	    content: 'html内容'
	});
}

var prepareSearch = false;
var searching = false;
var input;
function doSearch(){
	var a=$('form[name=form1]').serialize();
	searching = true;
	$.ajax({
	    type: 'post',
	    url: '/yycf/c/admin/food/listData',
	    data: {name:$(input).val()},
	    dataType:'json',
	    success: function(json){
	    	buildHtmlWithJsonArray("resultItem",json['data']);	
	    	if(json['data'].length<=0){
	    		$('#empty').text('没有名称包含 '+$(input).val()+' 的食物');
	    		$('#empty').show();
	    	}else{
	    		$('#empty').hide();
	    	}
	    },
	    complete:function(){
	    	searching = false;
	    }
	  });
}

function keyUp2(obj){
	input = obj;
	prepareSearch=true;
	$('#searchPanel').css('top' , (obj.clientHeight+obj.offsetTop)+'px')
	setTimeout(function(){
		if($(input).val().trim()==''){
			//clear result
			clearResult();
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

function clearResult(){
	buildHtmlWithJsonArray("resultItem",[]);
	$('#empty').hide();
}
function setFood(obj){
	$(input).val($(obj).text());
	$(input).attr('foodId' , $(obj).attr('foodId'));
	clearResult();
}

function addFoodItem(){
	var html='<div class="item">'
			+'<input value="" placeholder="食物名称" class="name" onkeyup="keyUp2(this);" onkeydown="keyDown2(this);" ></input><input type="tel" class="value" placeholder="重量(g)" />'
			+'</div>'
			+'<div class="clear"></div>';
	$('.foodlist').append(html);
}

function cacu(){
	var items = $('.foodlist .item');
	var arr = [];
	for(var i=0;i<items.length;i++){
		var json = JSON.parse("{}");
		json.foodId= $(items[i]).find('.name').attr('foodId');
		json.value= $(items[i]).find('.value').val();
		if(!json.foodId){
			layer.msg('请先选择食物');
			return;
		}
		if(!json.value){
			layer.msg('请先填写摄入量');
			return;
		}
		arr.push(json);
	}
	$.ajax({
	    type: 'post',
	    url: '/yycf/c/admin/food/cacu',
	    data: {data : JSON.stringify(arr)},
	    dataType:'json',
	    success: function(json){
	    	buildHtmlWithJsonArray("nutrient",json.result);
	    	$('#result').show();
	    }
	  });
}
</script>
<style type="text/css">
body{margin:0px;font-family: 微软雅黑;}
.fr{float:right;}
.white{color:white;}
.toolbar{    height: 26pt;    line-height: 26pt; background:#333;}
.toolbar .title{color:white;}
.foodlist .item {    height: 48pt;    line-height: 48pt;}
.foodlist .value{    height: 38pt;    line-height: 47pt;    width: 25%;  text-align:center;    color: crimson;  float: right;    font-size: 17pt;    margin-right: 10pt;    border: none; }
.foodlist .item .name{height: 38pt;    line-height: 47pt;    width: 60%;    text-align: center;  font-size: 17pt;    margin-right: 10pt;    border: none;}
.foodlist .item .unit{margin-right:10pt;}
.clear{    width: 94%;    border-bottom: 1px solid #ddd;    margin-left: auto;    margin-right: auto;}
.cacu-btn{height: 28pt;    line-height: 28pt;    background: cadetblue;    color: white;    text-align: center;    width: 94%;    margin-left: auto;    margin-right: auto;    margin-top: 10pt;    border-radius: 32px;}

.result {margin-top:15pt;}
.result table{width:96%;}
.result .item{    height: 25pt;    line-height: 25pt;}
.result .name{text-align:center;}
.result .value{text-align:right;padding-right:15pt;}
.result .row_1{background: beige;}
.result .row_0{background: lavender;}

.search {position: absolute;    width: 100%;}
.search .result{background: white;color: black;font-size: 18px;margin-left: auto; margin-right:auto; width: 90%;max-height:400px;overflow: auto;    margin-top: 0pt;}
.search .result .none{font-size:13px;display:none}
.search .result  div{height:40px;line-height:40px;border-bottom: 1px solid #eee;margin-left:12px;color: #254A65;}
.search .result .tips{float: right; margin-right: 7pt;    font-size: 10pt;    color: #ccc;}
.refresh{    text-align: center;    margin-top: 10pt;    color: skyblue;    text-decoration: underline;}
.search .result .empty{font-size:10pt;}

.hidden{display:none;}
</style>
</head>
<body onclick="clearResult();">
	<div class="toolbar"><span class="title">营养计算器</span><a class="fr white" href="#" onclick="addFoodItem();">添加食物</a></div>
	<div class="foodlist">
		<div class="item">
			<input value="" placeholder="食物名称" class="name" onkeyup="keyUp2(this);" onkeydown="keyDown2(this);" ></input><input class="value" placeholder="重量(g)"  type="tel" />
		</div>
		<div class="clear"></div>
		
	</div>
	
	<div class="cacu-btn" onclick="cacu();">计算</div>
	
	<div class="result hidden" id="result">
		<table align="center" cellpadding="0" cellspacing="0">
			<tr class="item row_$[classIndex] nutrient">
				<td class="name">$[name] </td>
				<td class="value">$[value] $[unit]</td>
			</tr>
		</table>
	</div>
	
	<div id="searchPanel" class="search">
		<div class="result">
			<div id="empty" class="empty"  style="display:none;"></div>
			<span>
				<div class="resultItem"  xx="query" style="display:none" foodId="$[id]" onclick="setFood(this)">$[name]</div>
			</span>
		</div>
	</div>
	
</body>
</html>
