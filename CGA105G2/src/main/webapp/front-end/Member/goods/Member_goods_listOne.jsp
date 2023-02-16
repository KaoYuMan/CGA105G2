<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.goods.model.Goods.pojo.*" %>
<%@ page import="com.goods.model.service.*" %>


<%
    Integer storeId = (Integer) request.getAttribute("storeId");
    Goods goods = (Goods) request.getAttribute("goods"); //EmpServlet.java(Concroller), 存入req的goods物件
    GoodsService goodsSvc = new GoodsService();
    List<Goods> list = goodsSvc.getAll(storeId);//要拿店家ID
    pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>🗃️店家商品陳列</title>
</head>

<body>
<div id="page-start-anchor"></div>

<!-- header start -->
<%@ include file="/front-end/Member/01h/headerin.jsp" %>
<!-- header end -->
<!-- main -->
<div class="container-fluid container">
    <div class="row">
        <main role="main" class="col-md-9 m-sm-auto col-lg-10 px-md-4 my-5 container">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <div>
                    <h1 class="h2">瀏覽商品</h1>
                </div>
            </div>
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
                                商品編號：<%=goods.getGoodsId()%>
                            </p>
                            <p class="lead">
                                商品名稱：<%=goods.getGoodsName()%>
                            </p>
                            <p class="lead" style="color: red">
                                商品價格：$<%=goods.getGoodsPrice()%>
                            </p>
                            <p class="lead">
                                商品說明：<%=goods.getGoodsText()%>
                            </p>
                            <p class="lead" style="display: none;">
                                <c:if test="${goods.goodsStatus == 0}">商品狀態：下架</c:if>
                                <c:if test="${goods.goodsStatus == 1}">商品狀態：上架</c:if>
                                <c:if test="${goods.goodsStatus == 2}">商品狀態：審核中</c:if>
                            </p>
                            <br> <br> <br> <a
                                class="btn btn-secondary btn-lg  mt-auto fs-4 addToCart"
                                href="#">加入購物車 <span style="display: none">${goods.goodsId}</span>
                            <span style="display: none">${goods.storeId}</span>
                            <span style="display: none">${memId}</span></a>
                            <a
                                    href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?action=getStoreId_For_Display&storeId=${storeId}"
                                    class="btn btn-secondary btn-lg  mt-auto fs-4" role="button"
                                    aria-pressed="true">回商品列表</a>
                        </div>
                    </div>
                </div>
            </section>
            <!-- 商品瀏覽結束 -->
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