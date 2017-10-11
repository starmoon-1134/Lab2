<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!DOCTYPE html>
<!--  <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive">

<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700,900' rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="css/myStyle.css" type="text/css">

<link rel="stylesheet" href="css/hiddenLayer.css" type="text/css">

<link rel="stylesheet" href="css/Tooltip.css" type="text/css">

<script src="http://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"></script>

<script src="js/superValidator.js"></script>

<script src="js/showBook.js"></script>

<title>图书管理系统</title>
<script>
$(document).ready(function(){
	  //var headHeight=$(".container").outerHeight();
	 // var totleHeight=$("#back-layer-2").outerHeight();
	  //$(".showBook-container").Height(80);
	  });
	

</script>

<!-- 显示  隐藏效果切换 -->
<script type="text/javascript">
$(function(){
	$('.fh5co-underHeader span').css({opacity:1}).show();
	$(".fh5co-underHeader span").hover(function(){
			$(this).stop().fadeTo(500, 0);
		}, function(){
			$(this).stop().fadeTo(500, 1);
		}
	);
});



 
</script>


</head>
<body>

<div id="fh5co-page">
<!-- div--header -->
  	<!--               修改图书信息的表，默认隐藏                                 -->
	<div id="alterForm">
		
		<div class="smart-green">
			<h1>Alter Book Infos
			<span>Please alter book infos in the text fields.</span>
			</h1>
			<table>
				<tr><th>题目:</th> <td><input name="Title" tip="" check="2" reg="\S"/> </td></tr>
				<tr><th>出版社:</th> <td><input name="Publisher" tip="" check="2" reg="\S"/> </td></tr>
				<tr><th>出版日期:</th> <td><input name="PublishDate" tip="日期格式为YYYY-MM-DD" check="2" reg="^\d{4}-\d{2}-\d{2}$"/> </td></tr>
				<tr><th>价格:</th> <td><input name="Price" tip="正数" check="2" reg="^(\d+.\d+|\d+)$"/> </td></tr>
				
				<tr><th>作者:</th> <td><input name="AuthorName" tip="作者与ID组合不在数据库中会创建新的作者" check="2" reg="\S"/> </td></tr>
				<tr><th>ID:</th> <td><input name="AuthorID" tip="作者与ID组合不在数据库中会创建新的作者" check="2" reg="^\d+$"/> </td></tr>
				<tr><th>年龄:</th> <td><input name="Age" tip="1~3位整数" check="2" reg="^[1-9]\d{0,2}$"/> </td></tr>
				<tr><th>国家:</th> <td><input name="Country" tip="" check="2" reg="\S"/> </td></tr>
				<tr><td><input name="ISBN" type="hidden"/></td></tr>
			</table>
			<input type="button" class="button" value="Submit" submitAlterForm=''/>
			<input type="button" class="button" value="Cancel" closeAlterForm=''/>
		</div>
	
	</div>
	
	<div class="showDetailLayer">
		<div class="showDetailCard">
			<h4><span class="closeBtn"></span></h4>
			
			<table>
				<tr><th>题目:</th> <td><span name="Title" ></span> </td></tr>
				<tr><th>ISBN:</th><td><span name="ISBN" ></span> </td></tr>
				<tr><th>出版社:</th> <td><span name="Publisher" ></span> </td></tr>
				<tr><th>出版日期:</th> <td><span name="PublishDate" ></span> </td></tr>
				<tr><th>价格:</th> <td><span name="Price" ></span> </td></tr>
				<tr> <th class="cut-off">             </th><td class="cut-off">          </td></tr>
				<tr><th>作者:</th> <td><span name="AuthorName" ></span> </td></tr>
				<tr><th>ID:</th> <td><span name="AuthorID"> </span> </td></tr>
				<tr><th>年龄:</th> <td><span name="Age" ></span> </td></tr>
				<tr><th>国家:</th> <td><span name="Country" ></span> </td></tr>
			</table>
		</div>
	
	</div>
	
	
	<div id="back-layer-2">
	<!-- head部分 --> 
	<div class="container">
		<div class="nav-header">
			<h1 id="fh5co-logo"><a href="#">Book<span class="">Manager</span></a></h1>
			<nav id="fh5co-menu-wrap" role="navigation">
				<ul class="sf-menu" id="fh5co-primary-menu">
					<li class="active"><a href="indexJSP">主页</a></li>
					<li><a href="addBookJSP">新增</a></li>
				</ul>
			</nav>
		</div>
	</div>
			
	
			
	<!-- 正文 -->		
	<div class="fh5co-underHeader">
		<!-- <a class="btn btn-primary" href="#">Start Your Journey</a> -->
		<div class="showBook-container">
		<iframe class="showBook" src="jsp/showBook.jsp" frameborder="0"> </iframe>
		
		</div>
	</div>
</div>	
</div>	
					
		
</body>
