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

<link rel="stylesheet" href="css/addBook.css" type="text/css">

<!--  
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css">

<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
-->
<script src="http://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"></script>

<script src="js/addBook.js"></script>

<title>新增图书</title>

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
	
	<div id="back-layer-2">
	<!-- head部分 --> 
	<div class="container">
		<div class="nav-header">
			<h1 id="fh5co-logo"><a href="#">Book<span class="">Manager</span></a></h1>
			<nav id="fh5co-menu-wrap" role="navigation">
				<ul class="sf-menu" id="fh5co-primary-menu">
					<li><a href="indexJSP">主页</a></li>
					<li class="active"><a href="addBookJSP">新增</a></li>
				</ul>
			</nav>
		</div>
	</div>
			
	
			
	<!-- 正文 -->		
	<div class="addBookContainer">
		<div class="smart-green">
			<div class="tableContainer">
				<table id="au">
					<tr><th>题目:</th> <td><input name="Title" tip="非空" check="2" reg="\S"/> </td></tr>
					<tr><th>ISBN:</th> <td><input name="ISBN" tip="非空" check="2" reg="\S"/> </td></tr>
					<tr><th>出版社:</th> <td><input name="Publisher" tip="非空" check="2" reg="\S"/> </td></tr>
					<tr><th>出版日期:</th> <td><input name="PublishDate" tip="日期格式为YYYY-MM-DD" check="2" reg="^\d{4}-\d{2}-\d{2}$"/> </td></tr>
					<tr><th>价格:</th> <td><input name="Price" tip="正数" check="2" reg="^(\d+.\d+|\d+)$"/> </td></tr>
				</table>
				
				<div class="cut-off"></div>
				
				<table id="bo" class="clearen">	
					<tr><th style="width:3em;">作者:</th> <td><input name="AuthorName" tip="作者与ID组合不在数据库中会创建新的作者" check="2" reg="\S"/> </td></tr>
					<tr><th style="width:3em;">ID:</th> <td><input name="AuthorID"/ tip="作者与ID组合不在数据库中会创建新的作者" check="2" reg="^\d+$"> </td></tr>
					<tr><th style="width:3em;">年龄:</th> <td><input name="Age" tip="1~3位整数,作者与ID相匹配时可不填，如果填了也不修改作者信息" check="1" reg="^[1-9]\d{0,2}$"/> </td></tr>
					<tr><th style="width:3em;">国家:</th> <td><input name="Country" tip="作者与ID相匹配时可不填，如果填了也不修改作者信息" check="1" reg="\S"/> </td></tr>
				</table>
			</div>
			<input type="button" class="button" value="Submit" submitAddForm=''/>
			</div>
			
		
		
	
	</div>
</div>	
</div>	
					
		
</body>
