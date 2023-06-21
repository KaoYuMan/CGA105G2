<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.goods.model.Goods.pojo.*" %>
<%@ page import="com.goods.model.service.*" %>


<%
    Integer storeId = (Integer) request.getAttribute("storeId");
    GoodsService goodsSvc = new GoodsService();
    List<Goods> list = goodsSvc.getAll(storeId);
    pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>🗃️管理</title>
</head>

<body>
<!-- header start -->
<%@ include file="/front-end/Member/01h/headerin.jsp" %>
<!-- header end -->
<!-- main -->
<div class="container-fluid container">
    <div class="row">
        <main role="main" class="col-md-9 m-sm-auto col-lg-10 px-md-4 my-5 container">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center p-2 pb-2 mb-3 border-bottom">
                <div>
                    <h1 class="h2">商城商品</h1>
                </div>
                <div>
                    <FORM METHOD="post"
                          ACTION="<%=request.getContextPath()%>/front-end/Member/goods/goods.do"
                          name="form1">
                        <b>搜尋商品:</b> <input type="text" name="goods_name">
                        <input type="submit" value="送出">
                        <input type="hidden" name="action" value="listGoods_ByCompositeQuery">
                        <input type="hidden" name="storeId" value="${storeId}">
                    </FORM>
                </div>
            </div><!-- 商品列表開始 -->
            <div class="container  px-4 px-lg-5 mt-5">
                <div class="row">
                    <c:forEach var="goods" items="${list}">
                        <div class="col-12 col-md-4 pb-4">
                            <div class="card">
                                <img
                                        src="${pageContext.request.contextPath}/front-end/Member/goods/goods.do?action=getGoodsImg&goodsId=${goods.goodsId}"
                                        width="ˇ350px">
                                <div class="card-body p-2">
                                    <div class="text-center">
                                        <!-- Product name-->
                                        <h3 class="fw-bolder">${goods.goodsName}</h3>
                                        <!-- Product price-->
                                        $${goods.goodsPrice}
                                    </div>
                                </div>
                                <div class="card-footer p-0 pt-0 border-top-0 bg-transparent">
                                    <div class="text-center p-1">
                                        <a class="btn btn-outline-dark mt-auto fs-4"
                                           href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?goodsId=${goods.goodsId}&storeId=${storeId}&action=getOne_For_Display">瀏覽商品</a>
                                    </div>
                                    <div class="text-center p-2">
                                        <a class="btn btn-outline-danger mt-auto addToCart" href="#">加入購物車
                                            <span style="display: none">${goods.goodsId}</span><span
                                                    style="display: none">${goods.storeId}</span><span
                                                    style="display: none">${memId}</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>
    </div>
</div>
<!-- main -->
<!-- footer start -->
<%@ include file="/front-end/Member/01h/footerin.jsp" %>
<!-- footer end -->
<script>
    let carts = document.querySelectorAll(".addToCart");
    carts.forEach(cart => {
        cart.addEventListener("click", function (e) {

            e.preventDefault();
            let value = parseInt(document.getElementById("cartIcon").innerText) + 1;
            document.getElementById("cartIcon").innerText = value;
            sessionStorage.setItem("cartIcon", value.toString());
            let goodsId = $(this).children('span').eq(0).text();
            let storeId = $(this).children('span').eq(1).text();
            let memId = $(this).children('span').eq(2).text();
            let url = "<%=request.getContextPath()%>/cart/add?goodsId=" + goodsId + "&storeId=" + storeId + "&memId=" + memId;
            console.log(url);
            fetch(url, {method: 'post'});
            salert();
        })
    })

    function salert() {
        Swal.fire({
            position: 'center',
            icon: 'success',
            title: '商品已加入購物車！',
            showConfirmButton: false,
            timer: 1500
        })
    }

</script>
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>

</html>