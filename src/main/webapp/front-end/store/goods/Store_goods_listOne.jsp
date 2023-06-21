<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.goods.model.Goods.pojo.*"%>
<%@ page import="com.goods.model.service.*"%>


<%
Goods goods = (Goods) request.getAttribute("goods"); //EmpServlet.java(Concroller), 存入req的goods物件
Integer storeId = (Integer) request.getAttribute("storeId");
GoodsService goodsSvc = new GoodsService();
List<Goods> list = goodsSvc.getAll(storeId);//storeID
pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />

<title>🔻商城管理</title>

</head>

<body>
	<div id="page-start-anchor"></div>

	<!-- header start -->
	<%@ include file="/front-end/store/01h/headerin.jsp"%>
	<!-- header end -->

	<!-- main -->


	<!-- main -->
	<div class="container-fluid ">
		<div class="row">
			<!-- nav start -->
			<%@ include file="/front-end/store/01h/nav/navin04.jsp"%>
			<!-- nav end -->
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
				<section class="section jarallax text-white p-1" data-jarallax
					data-speed="0.2">
					<div
						class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 mb-3 border-bottom">
						<div class="h1Text ml-5">
							<h1 class="h1">
								<font color="black">瀏覽商品</font>
							</h1>
						</div>
					</div>
				</section>
				<!-- 商品瀏覽開始 -->
				<section class="py-1">
					<div class="container px-4 px-lg-5 my-5">
						<div class="row gx-4 gx-lg-5 align-items-center">
							<div class="col-md-6">
								<img class="card-img-top mb-5 mb-md-0"
									src="${pageContext.request.contextPath}/front-end/Member/goods/goods.do?action=getGoodsImg&goodsId=${goods.goodsId}"
									style="border: 2px gray solid" height="500px">
							</div>

							<div class="col-md-6">
								<p class="lead" style="display: none;">
									商品編號：<%=goods.getGoodsId()%></p>
								<p class="lead" style="display: none;">
									店家編號：<%=goods.getStoreId()%></p>
								<p class="lead">
									商品名稱：<%=goods.getGoodsName()%></p>
								<p class="lead" style="color: red">
									商品價格：$<%=goods.getGoodsPrice()%></p>
								<p class="lead">
									商品說明：<%=goods.getGoodsText()%>
								</p>
								<p class="lead">
									<c:if test="${goods.goodsStatus == 0}">商品狀態：下架</c:if>
									<c:if test="${goods.goodsStatus == 1}">商品狀態：上架</c:if>
									<c:if test="${goods.goodsStatus == 2}">商品狀態：審核中</c:if>
								</p>
								<br> <br> 
								<a
									href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?action=getStoreId_For_Display_store"
									class="btn btn-secondary btn-lg  mt-auto fs-4">回商品列</a> 
									<a
									class="btn btn-secondary btn-lg  mt-auto fs-4"
									href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?goodsId=<%=goods.getGoodsId()%>&action=getOne_For_Update">再次修改商品</a>

							</div>
						</div>
					</div>
				</section>
			</main>
		</div>

	</div>


	<!-- main -->

	<!-- footer start -->
	<%@ include file="/front-end/store/01h/footerin.jsp"%>
	<!-- footer end -->
	<script>
		$("a:contains(🗃️管理)").closest("a").addClass("active disabled topage");
		$("a:contains(🔻商城管理)").closest("a").attr("data-toggle", "show");
		$("#pageSubmenu3").removeClass("collapse");
		$("#pageSubmenu3 a:contains(🔆總覽商品)").closest("a").addClass("active disabled bg-white topage");
	</script>


</body>

</html>