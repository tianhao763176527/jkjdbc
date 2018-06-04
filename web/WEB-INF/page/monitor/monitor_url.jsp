<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>网址监控</title>
	<%@ include file="../../org/inc.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>web/WEB-INF/css/tab2.css">
</head>
<body>
  <video id="vedio_mp3" hidden>
	  <source src="<%=basePath%>/web/WEB-INF/vedio/2125.mp3" type="audio/mpeg">
	  <embed src="<%=basePath%>/web/WEB-INF/vedio/2125.mp3">
  </video>
   <div>
	   <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"/>
   </div>
<%--    <button name="btn_start" onclick="everyTimeOp()">开始执行</button>
    <button name="btn_ned" onclick="stopTimeOp()">停止执行</button>--%>
  <h3 style="text-align: center;">url访问出错信息：</h3>
   <table id="table-sparkline">
	   <thead>
	   <tr>
		   <th>地址名称</th>
		   <th>URL</th>
		   <th>状态</th>
	   </tr>
	   </thead>
	   <tbody id="tbody-sparkline">
	   
	   </tbody>
   </table>
</body>
<script>
	var urlInfoStr;
	//用于存放三组数据
	var categories_=[];
	var data1_=[],data2_=[],data3_=[];
    $(document).ready(function() {
        //执行返回获取数据
	    /*var url = contextPath+"/getUrlInfo.do";
       $.post(url,function (data) {
            //setMonitorData_each(data);
	        urlInfoStr=data;
        });*/
        setMonitorData();
        showMask();
        oneTimeOp('3ds',setMonitorData);
    });
    //分批次加载
    function setMonitorData_each(){
        var url = contextPath+"/getUrlResInfo.do";
        var url_array = $.parseJSON(urlInfoStr);
        $.each(url_array,function (i,rowdata) {
            var index = categories_.length;
            if (categories_.length == url_array.length){
                //重新监听，重新加载
                categories_=data1_=data2_=data3_=[];
            }
            $.ajax({
	            url:url,
	            data:{"url":rowdata.url},
                async: true,
                success:function (value) {
                    categories_[index]=rowdata.name;
                    if(value.state == "YES"){
                        data1[index]=value.respondTime;
                    }else if(value.state == "TIMEOUT") {
                        data2[index] = value.respondTime;
                    }
                    else{
                        data3[index] = value.respondTime;
                    }
                }
            });
            showMonitor(categories_,data1_,data2_,data3_);
        });
        showMonitor(categories_,data1_,data2_,data3_);
    }
    //一次性加载所有的url，
    function setMonitorData(){
        //清空错误表格中的信息
	    $("#tbody-sparkline").empty();
        var url = contextPath+"/getOneAllUrlResInfo.do";
        $.ajax({
	        url:url,
	        type:"POST",
	        async:true,
            success:function(data){
                jsonData = $.parseJSON(data);
                //  console.log(data);
                var categories=[],data1=[],data2=[],data3=[];
                $.each(jsonData,function (index,value) {
                    //加载到表格中
                    insertUrlToTab(value);
                    //播放警告音乐‘
	                playVedio(value);
                    categories[index]=value.name;
                    data1[index]=data2[index]=data3[index]=null;
                    if(value.state == "YES"){
                        data1[index]=value.respondTime;
                    }else if(value.state == "TIMEOUT") {
                        data2[index] = value.respondTime;
                    }
                    else{
                        data3[index] = value.respondTime;
                    }
                })
                showMonitor(categories,data1,data2,data3);
            }
        });
        hiddenMask();
    }
    //设置参数
    var options = {
        chart: {
            type: 'column'
        },
        title :{ text:"网络监控图"},
        xAxis: {
            categories: [],
            crosshair: true
        },
        subtitle: {
            text: ''
        },
        yAxis: {
            min: 0,
            title: {
                text: '响应时间 (ms)'
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mi</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        colors : ['#24CBE5','#FFF263','#FF0000'],
        series : [{
            name: '正常',
            data: []
        }, {
            name: '超时',
            data: []
        }, {
            name: '无响应',
            data: []
        }]
    };
    // 图表初始化函数
    var chart = Highcharts.chart('container', options);
    function showMonitor(categories,data1,data2,data3){
        chart.update({
            xAxis :{
                categories: categories,
                title: {
                    text: null
                }
            },
            series:[
                {
                    name: '正常',
                    data: data1
                }, {
                    name: '超时',
                    data: data2
                }, {
                    name: '无响应',
                    data: data3
                }
            ]
        });
    }
    //添加无法访问的url信息到表格中
	function insertUrlToTab(data){
        if(data.state == "TIMEOUT" || data.state == "NO") {
            htmlStr = "<tr>";
            htmlStr +="<td>"+data.name+"</td>"
            htmlStr +="<td>"+data.url+"</td>"
	        htmlStr +="<td>"+data.state+"</td>"
	        htmlStr +="</tr>";
            $("#tbody-sparkline").append(htmlStr);
        }
	}
	//播放警告音乐
	function playVedio(data) {
	    var vedio = document.getElementById("vedio_mp3");
	    if(data.state != "OK"){
            vedio.play();
	    }
    }
</script>
</html>
