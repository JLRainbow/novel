<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<!-- 章节标题 -->
<title>${chapterDetail.title }-章节内容</title>
<link rel="stylesheet"
	href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">

<style>
body {
	background-color: #E5E4DB;
}

.content {
	font-size: 16px;
	background-color: #F6F4EC;
	color: #333;
	padding: 20px;
	border-radius: 5px;
	-webkit-border-radiu: 5px;
}

.jumbotron {
	padding-top: 10px;
	padding-bottom: 10px;
	background-color: #F5F5F5;
}
</style>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<ol class="breadcrumb">
						<li><a href="./">Home</a></li>
						<li><a href="./showChapterList.do?url=${chapterListUrl }">章节列表</a></li>
						<li class="active">${chapterDetail.title }</li>
					</ol>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<h3 style="text-align: center;">${chapterDetail.title }</h3>
		<!-- 章节内容区域 -->
		<div class="content">
			<c:if test="${isSuccess }">
				${chapterDetail.content }
				<!-- 前一章 章节列表 后一章 链接区域 -->
				<div style="text-align: center;">
					<a href="./showChapterDetail.do?url=${chapterDetail.prev }&chapterListUrl=${chapterListUrl}">前一章</a>
					<a href="./showChapterList.do?url=${chapterListUrl }">章节列表</a> 
					<a href="./showChapterDetail.do?url=${chapterDetail.next }&chapterListUrl=${chapterListUrl}">后一章</a>
				</div>
			</c:if>
			<c:if test="${!isSuccess }">
				很抱歉，读取章节内容失败了，请再试一次！
			</c:if>
		</div>
	</div>
</body>
<script
	src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>