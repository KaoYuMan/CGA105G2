<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.goods.model.Goods.pojo.*" %>
<%@ page import="com.goods.model.service.*" %>
<%-- 複合查詢--%>

<jsp:useBean id="listGoods_ByCompositeQuery" scope="request"
             type="java.util.List<Goods>"/>
<jsp:useBean id="goodsSvc" scope="page"
             class="com.goods.model.service.GoodsService"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>🗃️店家商品陳列</title>


    <style type="text/css">


        .section-background-image {
            background-image: url('images/img4.png');
        }

        .item.img-fluid {
            width: 100%;
        }
    </style>
</head>

<body>
<!-- header start -->
<%@ include file="/front-end/Member/01h/headerin.jsp" %>
<!-- header end -->
<!-- main -->
<div class="container-fluid">
    <div class="row">
        <!-- nav start -->
        <%@ include file="/front-end/Member/01h/nav/navin04.jsp" %>
        <!-- nav end -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4 my-5">
            <div
                    class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">查詢的商品</h1>
            </div>

            <!-- 商品列表開始 -->
            <div class="container  px-4 px-lg-5 mt-5">
                <div class="row">
                    <c:forEach var="goods" items="${listGoods_ByCompositeQuery}">
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
                                           href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?goodsId=${goods.goodsId}&storeId=${storeId}&action=getOne_For_Display">瀏覽商品</a>
                                    </div>

                                    <!-- 										<div class="text-center p-2"> -->
                                    <!-- 											<a class="btn btn-outline-danger mt-auto" -->
                                        <%-- 												href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?goodsId=${goods.goodsId}&action=">加入購物車</a> --%>
                                    <!-- 										</div> -->
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
<%@ include file="/front-end/Member/01h/footerin.jsp" %>
<!-- footer end -->
<script>
    $("a:contains(🗃️管理)").closest("a").addClass("active disabled topage");
    $("a:contains(🔻訂單)").closest("a").attr("data-toggle", "show");
    $("#pageSubmenu2").removeClass("collapse");
    $("#pageSubmenu2 a:contains(🔆訂單查詢)").closest("a").addClass("active disabled bg-white topage");
</script>
</body>

</html>