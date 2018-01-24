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

<title>章节列表</title>

<link rel="stylesheet"
	href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
.jumbotron {
	padding-top: 10px;
	padding-bottom: 10px;
}
</style>
</head>
<body style="background-color: #F0FFF0">
	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<ol class="breadcrumb">
					<li><a href="./">Home</a></li>
					<li class="active">章节列表</li>
				</ol>
			</div>
		</div>
	</div>
	<div class="container no-table-responsive">
		<table
			class="table table-striped table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th colspan="4" style="text-align: center;">章节列表</th>
				</tr>
			</thead>
			<tbody>
				<!-- 每四章换一列 -->
				<c:if test="${isSuccess }">
					<c:forEach var="chapter" items="${chapters }" varStatus="status">
						<c:if test="${status.index % 4 == 0}" var="testResult">
							<tr>
						</c:if>
						<td><a
							href="./showChapterDetail.do?url=${chapter.url }&chapterListUrl=${chapterListUrl }"
							target="_blank">${chapter.title}</a></td>
						<c:if test="${(status.index + 1) % 4 == 0 }">
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${!isSuccess }">
					<tr>
						<td colspan="4" style="text-align: center;">
							很抱歉，章节列表下载失败，您可以再试一次！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

</body>

</html>