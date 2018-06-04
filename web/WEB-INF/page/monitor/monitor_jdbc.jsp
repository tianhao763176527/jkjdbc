<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>数据库内存监控</title>
	<%@ include file="../../org/inc.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>web/WEB-INF/css/style.css">
	<link rel="stylesheet" href="<%=basePath%>web/WEB-INF/css/tab.css">
</head>
<body>
 <div id="top_title">数据库监听</div>
  <div id="result"></div>
  <table id="table-sparkline">
	  <thead>
	  <tr>
		  <th>数据库名称</th>
		  <th>表空间名称</th>
		  <th>表空间大小（G）</th>
		  <th>表空间使用大小（G）</th>
		  <th>表空间剩余（G）</th>
		  <th>使用率（%）</th>
		  <th></th>
	  </tr>
	  </thead>
	  <tbody id="tbody-sparkline">
	  </tbody>
  </table>
<%--  <button name="btn_start" onclick="funclick()">开始执行</button>--%>
<%--  <button name="btn_ned" onclick="stopTimeOp()">停止执行</button>--%>
 <video  id="vedio_" hidden="hidden">
	 <source src="<%=basePath%>/web/WEB-INF/vedio/2125.mp3" type="audio/mpeg">
     <embed src="<%=basePath%>/web/WEB-INF/vedio/2125.mp3">
 </video >
</body>
</html>
<script>
    $(document).ready(function() {
        load();
       showMask();
    });
   /* function funclick(){ oneTimeOp('1das',timeOPtion)}*/
    //定时加载
    function timeOPtion() {
	    var url = '<%=basePath%>monitorJdbcAction.do';
	    window. window.location.href = url;
    }
    
    function load() {
        //执行返回获取数据
        var url = contextPath+"/getJkJdbcInfor.do";
        $.post(url,function (data) {
            jsonData = $.parseJSON(data);
            var htmlstr= jsonToHtml(jsonData);
            $("#tbody-sparkline").append(htmlstr);
            hiddenMask();
        });
        oneTimeOp('3hs',timeOPtion);
    }
    
    function jsonToHtml(jsonData){
        var htmlstr='';
        var contentHtm='';
        var talhtmlstr= '';
        $.each(jsonData,function (index,data) {
            htmlstr = "<tr id='"+data.username+"'>";
            if(data.resutData.length == 0){
                //无内容，表示数据库链接异常
                htmlstr = "<tr><td class='td_head'>"+data.dbaName+"</td>"
                    +"<td colspan='5' class='exception'>数据库链接异常，请检查数据库配置文件</td></tr>";
            }else{
                $.each(data.resutData,function (colum,trdata) {
                    playVedio(trdata);
                    insErrorMsg(trdata,"SEND");
                    contentHtm = '<tr>';
                    if(colum == 0){
                        contentHtm +="<td class='td_head' rowspan='"+data.resutData.length+"'>"+data.dbaName+"</td>";
                    }
                    contentHtm +=" <td id='TABLESPACE_NAME'>"+trdata.TABLESPACE_NAME+"</td>";
                    contentHtm +=" <td id='TOTAL'>"+trdata.TOTAL+"</td>";
                    contentHtm +=" <td id='SPARE'>"+trdata.SPARE+"</td>";
                    contentHtm +=" <td id='FREE'>"+trdata.FREE+"</td>";
		            /* contentHtm +=" <td id='RATE'>"+trdata.RATE+"</td>";*/
                    contentHtm += "<td id='RATE'><div class='progress'>" +
                        " <span class='"+getColorClass(trdata)+"' style='width: "+trdata.RATE+";'>" +
                        "<span>"+trdata.RATE+"</span></span></div></td>";
                    contentHtm += '</tr>';
                    htmlstr+=contentHtm;
                });
                htmlstr +="</tr>"
            }
            talhtmlstr +=htmlstr;
        });
        return talhtmlstr;
    }

    //根据返回的使用率，返回要显示的颜色
    function getColorClass(trdata) {
        var rate = (trdata.SPARE/trdata.TOTAL)*100;
        if(rate <=75){
            return "blue";
        }else if (rate > 90){
            return "red";
        }else{
            return "orange";
        }
    }
    //播放警告音乐
    function playVedio(data) {
        var vedio = document.getElementById("vedio_");
        if( data.TABLESPACE_NAME=="DATA" && data.SPARE > data.TOTAL*0.95){
            vedio.play();
        }
    }
    //将错误信息添加到json中
    function insErrorMsg(data,isSend){
        if( data.TABLESPACE_NAME=="DATA" && data.SPARE > data.TOTAL*0.95){
            if ($.isEmptyObject(data)){
                erromsg =data.TABLESPACE_NAME+"数据库DATA空间超过95%";
            }
	        if(isSend == "SEND"){
                $.post(contextPath+"/sendErrorMsg.do",{"msg":erromsg});
            }
        }
    }
</script>
