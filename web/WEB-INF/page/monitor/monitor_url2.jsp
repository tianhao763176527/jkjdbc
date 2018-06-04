<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../org/inc.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>web/WEB-INF/css/sku_style.css" />
	<title>网址监控</title>
	<script type="text/javascript" src="<%=basePath%>web/WEB-INF/js/createSkuTable.js"></script>
	<script type="text/javascript" src="<%=basePath%>web/WEB-INF/js/customSku.js"></script>
	<script type="text/javascript" src="<%=basePath%>web/WEB-INF/js/layer.js"></script>
</head>
<body>

<div class="demo-title">网址监控</div>
 <div id="div_main">
<ul class="SKU_TYPE">
	<li is_required='1' propid='1' sku-type-name="系统名称"><em>*</em>存储：</li>
</ul>
<ul>
	<li><label><input type="checkbox" class="sku_value" propvalid='11' value="湖北待遇" />湖北待遇</label></li>
	<li><label><input type="checkbox" class="sku_value" propvalid='12' value="湖北医保" />湖北医保</label></li>
</ul>
<div class="clear"></div>
<ul class="SKU_TYPE">
	<li is_required='1' propid='2' sku-type-name="地址名称"><em>*</em>地址名称：</li>
</ul>
<ul>
	<li><label><input type="checkbox" class="sku_value" propvalid='21' value="十堰中心" />十堰中心</label></li>
	<li><label><input type="checkbox" class="sku_value" propvalid='22' value="孝感" />孝感</label></li>
	<li><label><input type="checkbox" class="sku_value" propvalid='23' value="黄石" />黄石</label></li>
	<li><label><input type="checkbox" class="sku_value" propvalid='24' value="襄阳" />襄阳</label></li>
	<li><label><input type="checkbox" class="sku_value" propvalid='25' value="随州" />随州</label></li>
</ul>
<div class="clear"></div>

<ul class="SKU_TYPE">
	<li is_required='1' propid='3' sku-type-name="应用名"><em>*</em>应用名：</li>
</ul>
<ul>
	<li><label><input type="checkbox" class="sku_value" propvalid='31' value="hbdy" />hbdy</label></li>
	<li><label><input type="checkbox" class="sku_value" propvalid='32' value="hbjk" />hbjk</label></li>
</ul>
<div class="clear"></div>
<ul class="SKU_TYPE">
	<li is_required='1' propid='4' sku-type-name="端口"><em>*</em>端口：</li>
</ul>
<ul>
	<li><label><input type="checkbox" class="sku_value" propvalid='41' value="7003" />7003</label></li>
	<li><label><input type="checkbox" class="sku_value" propvalid='42' value="7005" />7005</label></li>
	<li><label><input type="checkbox" class="sku_value" propvalid='42' value="7007" />7007</label></li>
</ul>
<div class="clear"></div>
<button class="cloneSku">添加自定义sku属性</button>

<!--sku模板,用于克隆,生成自定义sku-->
<div id="skuCloneModel" style="display: none;">
	<div class="clear"></div>
	<ul class="SKU_TYPE">
		<li is_required='0' propid='' sku-type-name="">
			<a href="javascript:void(0);" class="delCusSkuType">移除</a>
			<input type="text" class="cusSkuTypeInput" />：
		</li>
	</ul>
	<ul>
		<li>
			<input type="checkbox" class="model_sku_val" propvalid='' value="" />
			<input type="text" class="cusSkuValInput" />
		</li>
		<button class="cloneSkuVal">添加自定义属性值</button>
	</ul>
	<div class="clear"></div>
</div>
<!--单个sku值克隆模板-->
<li style="display: none;" id="onlySkuValCloneModel">
	<input type="checkbox" class="model_sku_val" propvalid='' value="" />
	<input type="text" class="cusSkuValInput" />
	<a href="javascript:void(0);" class="delCusSkuVal">删除</a>
</li>
<div class="clear"></div>
<div id="skuTable"></div>
<script type="text/javascript" src="<%=basePath%>web/WEB-INF/js/getSetSkuVals.js"></script>
</div>
</div>
</body>
<script >
    $(document).ready(function() {
        hiddenMask();
    });
</script>
</html>
