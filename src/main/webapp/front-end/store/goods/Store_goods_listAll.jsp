<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.goods.model.Goods.pojo.*" %>
<%@ page import="com.goods.model.service.*" %>


<%
    Integer storeId = (Integer) request.getSession().getAttribute("storeId");
    GoodsService goodsSvc = new GoodsService();
    List<Goods> list = goodsSvc.getAll(storeId);
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("storeId", storeId);
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no"/>

    <title>🔻商城管理</title>
</head>

<body>
<!-- header start -->
<%@ include file="/front-end/store/01h/headerin.jsp" %>
<!-- header end -->

<!-- main -->
<div class="container-fluid ">
    <div class="row">
        <!-- nav start -->
        <%@ include file="/front-end/store/01h/nav/navin04.jsp" %>
        <!-- nav end -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <section class="section jarallax text-white p-1" data-jarallax
                     data-speed="0.2">
                <div
                        class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">

                    <div class="h1Text ml-5">
                        <h1 class="h1">
                            <font color="black">全部商品</font>
                        </h1>
                    </div>
                    <div class="buttonDiv">
                        <a class="btn btn-outline-dark fs-4 mr-5"
                           href="${pageContext.request.contextPath}/front-end/store/goods/Store_addGoods.jsp">新增商品</a>
                    </div>

                </div>
            </section>

            <!-- 商品列表開始 -->
            <div class="container  px-4 px-lg-5 mt-5">
                <div class="row">
                    <c:forEach var="goods" items="${list}">
                        <div class="col-12 col-md-4 pb-4">
                            <div class="card">
                                <img
                                        src="${pageContext.request.contextPath}/front-end/Member/goods/goods.do?action=getGoodsImg&goodsId=${goods.goodsId}"
                                        width="350px">
                                <div class="card-body p-2">
                                    <div class="text-center">
                                        <!-- Product name-->
                                        <h3 class="fw-bolder">${goods.goodsName}</h3>
                                        <!-- Product price-->
                                        $${goods.goodsPrice}
                                    </div>
                                </div>
                                <!-- Product actions-->

                                <div class="card-footer p-0 pt-0 border-top-0 bg-transparent">
                                    <div class="text-center p-1">
                                        <a class="btn btn-outline-dark mt-auto fs-4"
                                           href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?goodsId=${goods.goodsId}&action=getOne_For_Update&storeId=${goods.storeId}">修改商品</a>
                                    </div>

                                    <div class="text-center p-2">
                                        <a class="btn btn-outline-danger mt-auto"
                                           href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?storeId=${goods.storeId}&action=delete&goodsId=${goods.goodsId}">刪除商品</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!-- 商品列表結束 -->

        </main>
    </div>

</div>

<!-- main -->

<!-- footer start -->
<%@ include file="/front-end/store/01h/footerin.jsp" %>
<!-- footer end -->
<script>
    $("a:contains(🗃️管理)").closest("a").addClass("active disabled topage");
    $("a:contains(🔻商城管理)").closest("a").attr("data-toggle", "show");
    $("#pageSubmenu3").removeClass("collapse");
    $("#pageSubmenu3 a:contains(🔆總覽商品)").closest("a").addClass("active disabled bg-white topage");
</script>

</body>

</html>