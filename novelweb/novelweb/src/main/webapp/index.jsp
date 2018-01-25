<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小说搜索</title>
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/layer/layer.js"></script>
</head>
<script type="text/javascript">
	$(function() {
		//点击事件
		$("#serchNovel").click(serch);
		//回车事件
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
				  serch();
			  }
		});
	})
	//根据书名或者作者查找小说
	function serch() {
		var keyword = $("#keyword").val().trim();
		var pageNum = 0;
		var pageSize = 10;
		if(keyword.length == 0){
			layer.alert('请输入要搜索的小说或者作者!', {
				  skin: 'layui-layer-molv' //样式类名
				  ,closeBtn: 1
				}, function(index){
					 layer.close(index);
				});
		}else{
			//根据关键词查找小说
			getNovelsByKeyword(keyword,pageNum,pageSize);
		}
	}
	//将platfromId转换成对应网站
	function getPlatfrom(platfromId){
		switch (platfromId) {
		case 1: return "顶点小说";
		case 2: return "笔趣阁";
		case 3: return "看书中";
		case 4: return "笔下文学";
		default: return "未知";
		}
	}
	//翻页
	function page(start){
		var keyword = $("#keyword").val().trim();
		var pageNum = start;
		var pageSize =10;
		getNovelsByKeyword(keyword,pageNum,pageSize);
	}
	//Go翻页
	function gotoPage(){
		var keyword = $("#keyword").val().trim();
		var pageNum = $("#gotoPage").val().trim();
		var reg = /^[0-9]$/; 
		var pageSize =10;
		if(pageNum.length==0||!reg.test(pageNum)){
			layer.alert('请输入正确的页码!', {
				  skin: 'layui-layer-molv' //样式类名
				  ,closeBtn: 1
				}, function(index){
					 layer.close(index);
				});
		}else{
			getNovelsByKeyword(keyword,pageNum,pageSize);
		}
	}
	//根据关键词查找小说
	function getNovelsByKeyword(keyword,pageNum,pageSize){
		$.ajax({
			url:"./getNovelsByKeyword.do",
			type:"post",
			dataType:"json",
			data:{"keyword":keyword,"pageNum":pageNum,"pageSize":pageSize},
			success:function (result){
				$("#novelList").empty();
				$(result.data.novelList).each(function (index,novel){
					var status = novel.status == 1 ? "连载" : "完结";
					var platfrom = getPlatfrom(novel.platfromId);
					$("#novelList").append("<tr style='text-align:center;'>"+
							"<td>"+(index+1)+"</td>"+
							"<td><a href='./showChapterList.do?url="+novel.novelUrl+"'>《"+novel.name+"》</td>"+
							"<td>"+novel.author+"</td>"+
							"<td><a href ='./showChapterDetail.do?url="+novel.lastUpdateChapterUrl+"&chapterListUrl='>"+novel.lastUpdateChapter+"</td>"+
							"<td>"+status+"</td>"+
							"<td>"+platfrom+"</td>"+
							"<td><a onclick='downloadNovel(\""+novel.novelUrl+"\",\""+novel.name+"\")'>下载</a></td>"+
						"</tr>");
					});
					var pages =parseInt(result.data.pages);
					$("td .btn-group").empty();
					for(var start = 1; start <= pages; start++){
						if(start==1){
							$("td .btn-group").append("<button type='button' class='btn btn-default' onclick='page("+start+")' >首页</button>");
// 							$("td .btn-group").append("<button type='button' class='btn btn-default' onclick='page("+(start-1)+")' >上一页</button>");
						}
						if(start==pages){
							$("td .btn-group").append("<button type='button' class='btn btn-default' onclick='page("+start+")' >"+start+"</button>");
// 							$("td .btn-group").append("<button type='button' class='btn btn-default' onclick='page("+(start+1)+")' >下一页</button>");
							$("td .btn-group").append("<button type='button' class='btn btn-default' onclick='page("+pages+")' >尾页</button>");
							$("td .btn-group").append("<button type='button' class='btn btn-default' onclick='gotoPage()' >Go</button>");
							$("td .btn-group").append("<input type='text' id='gotoPage' class='form-control' id='gotopage_text' "+
															"style='width: 80px; display: inline-block' placeholder='页码'>");
						}else{
							
							$("td .btn-group").append("<button type='button' class='btn btn-default' onclick='page("+start+")' >"+start+"</button>");
							
						}
					}
					
			},
			error:function (result){
				alert(result.status)
			}
			
		})
	}
	//下载小说
	function downloadNovel(novelUrl,name){
		
		layer.alert("确定要下载《"+name+"》吗？", {
			  skin: 'layui-layer-molv' //样式类名
			  ,closeBtn: 1
			}, function(index){
				layer.close(index);
				$.ajax({
					url:"./novelDownload.do",
					type:"post",
					dataType:"json",
					data:{"novelUrl":novelUrl},
					success:function (result){
						if(result.status==1){
							alert("下载完成,保存路径:"+result.data);
						}else{
							alert("下载失败")
						}
					},
					error:function (result){
						alert(result.status)
					}
				})
				
			});
	}
</script>
<body style="background-color:#F0FFF0 ">
	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="input-group">
						<input type="text" id="keyword" class="form-control"  placeholder="可以输入书名，作者名，甚至可以是符合条件的URL...">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" id="serchNovel">小说搜搜</button>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>

</script>
	<div class="container no-table-responsive">
		<table
			class="table table-striped table-bordered table-condensed table-hover" >
			<thead>
				<tr style="text-align:center;font-weight: bolder;">
					<td>序号</td>
					<td>书名</td>
					<td>作者</td>
					<td>最新章节</td>
					<td>状态</td>
					<td>平台</td>
					<td></td>
				</tr>
			</thead>
			<tbody id="novelList">
			</tbody>
			
			<tfoot>
				<tr>
					<td colspan="7">
						<div class="btn-group" style="float: right;">
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>

</body>

</html>