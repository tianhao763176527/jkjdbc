<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="telephone=no,email=no" name="format-detection">
	<%@ include file="../../org/inc.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>web/WEB-INF/css/tab3.css"/>
	<title>网址监控</title>
	<style type="text/css">
	</style>
</head>
<body>

<div class="demo-title">网址监控</div>
<video  id="vedio_" hidden="hidden">
	<source src="<%=basePath%>/web/WEB-INF/vedio/2125.mp3" type="audio/mpeg">
	<embed src="<%=basePath%>/web/WEB-INF/vedio/2125.mp3">
</video >
 <div id="div_main">
 </div>
<div id="myModal" class="reveal-modal">
	<h2 style="text-align: center;">删除/添加/修改</h2>
	<div id="content">
	</div>
	<p style="text-align: center;"><button onclick="fnClickAddEdit()">确&emsp;&emsp;认</button></p>
	<a class="close-reveal-modal">×</a> </div>
</body>
<script src="<%=basePath%>web/WEB-INF/js/jquery.reveal.js"></script>
<script >
    $(document).ready(function() {
        hiddenMask();//隐藏加载
	    $.post(contextPath+"/getTableHtml.do",function (data) {
		    $("#div_main").append(data);
            $("#div_main table").addClass("edtitable");
            $("#div_main table tr td").addClass("tg-baqh");
            $("#div_main table p").addClass("a_context");
        });
        fnEachHtml();
        addEachEvent();
        $("body").everyTime('20s',fnEachHtml);
    });
    function fnClickAddEdit() {
        $(obj).empty();//清空
        $.each($("#content").children(),function (index,value) {
            var name = $(value).find("input[name='name']").val();
            var href = $(value).find("input[name='href']").val();
            var isactivity = $(value).find("input[name='isactivity']:checked").val();

            //console.log(name+href+isactivity);
            if(jQuery.isEmptyObject(name) && jQuery.isEmptyObject(href) && jQuery.isEmptyObject(isactivity)){
                return ;
            }
            //添加a标签到表格中
            var
                html = "<p class='a_context'><a href='"+href+"' isactivity='"+isactivity+"' target='_blank'>"+name+"</a></p>";
            $(obj).append(html);
        });
	    
	    //添加完成后，重置input
	    $("#myModal input").val('');
        //关闭窗口
        $(".close-reveal-modal").click();
    }
    //添加点击事件
    function addEachEvent(){
	    /* 给每一个<a>标签添加双击事件*/
        $(".tg-baqh").dblclick( function (e) {
            window.obj = e.target;
            var defaults = {
                animation: 'fade', //可选模式为三种：fade, fadeAndPop, none
                animationspeed: 300, //动画效果速度
                closeonbackgroundclick: true, //设置点击模态化背景时是否关闭弹出层
                dismissmodalclass: 'close-reveal-modal'//设置关闭关闭的样式
            };
            //添加数据，首先将其制空
            $("#content").empty();
	        $.each($(obj).children(".a_context"),function (index,avalue) {
	            var name = $.trim($(avalue).find("a").text());
	            var href = $.trim($(avalue).find("a").attr("href"));
	            //var isactivity = $(avalue).attr("isactivity");
		        console.log(name,href);
		        var contexStr =
			            "<p>名&emsp;&emsp;称：<input name='name' type='text' class='input' size='10' value='"+name+"' >" +
			            "地&emsp;&emsp;址：<input name='href' type='text' class='input' size='25' value='"+href+"'> " +
			            "&emsp;&emsp;是否可用:<label class='mr10'><input type='radio' name='isactivity' value='true'  checked='checked'/>是</label><label class='mr10'><input type='radio' name='isactivity' value='false'/>否</label></p>";
               $("#content").append(contexStr);
            });
            $("#myModal").reveal(defaults);
        });
    }
    function fnEachHtml(){
       $.each($(".a_context").children(),function (index,html) {
             
              var isactivity = $(html).attr("isactivity");
              if(isactivity !=undefined && isactivity == "false"){
                  $(html).addClass("backgroup_clor_orange") ;
                  return;
              }
               var url = $(html).attr("href");
               $.post(contextPath+"/getUrlResInfo.do",{"url":url},function (data) {
                   var value = $.parseJSON(data);
                   if(value.state == "YES"){
                       $(html).addClass("backgroup_clor_blue");
                   }else{
                       $(html).addClass("backgroup_clor_red");
                       playVedio();
                   }
               });
       });
        //$("body").oneTime('20s',fnEachHtml);
    }
    //播放警告音乐
    function playVedio() {
        var vedio = document.getElementById("vedio_");
        vedio.play();
    }
</script>
</html>
