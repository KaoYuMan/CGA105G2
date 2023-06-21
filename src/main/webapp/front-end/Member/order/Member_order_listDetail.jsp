<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.order.model.Order.pojo.*" %>
<%@ page import="com.goods.model.Goods.pojo.*" %>
<%@ page import="com.order.model.OrderDetail.pojo.*" %>
<%@ page import="com.order.model.service.*" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>🔻訂單</title>
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
                <h1 class="h2">🔆訂單明細</h1>
                <a href="<%=request.getContextPath()%>/order/order.do?action=getMemId_For_Display_Order" class="btn btn-outline-dark fs-4 mr-5">返回歷史訂單</a>
            </div>

            <!-- 商品列表開始 -->
            <section class="pt80 pb80 listingDetails Campaigns">
                <div class="container">
                    <div class="row">

                        <div class="col-lg-12 col-md-12 col-sm-12 ">
                            <table class="table table-hover table-responsive-sm fold-table">
                                <thead class="thead-light">
                                <tr class="text-center">
                                    <th scope="col">訂單編號</th>
                                    <th scope="col">商品照片</th>
                                    <th scope="col">商品名稱</th>
                                    <th scope="col">商品小計</th>
                                    <th scope="col">商品數量</th>

                                </tr>
                                </thead>

                                <tbody class="text-center">
                                <c:forEach var="orderDetail" items="${orderDetailList}">
                                    <tr>
                                        <td>#${orderDetail.orderId}</td>
                                        <td><img
                                                src="${pageContext.request.contextPath}/front-end/Member/goods/goods.do?action=getGoodsImg&goodsId=${orderDetail.goodsId}"
                                                width="80px"></td>
                                        <td>${orderDetail.goods.goodsName}</td>
                                        <td>${orderDetail.detailPrice}</td>
                                        <td>${orderDetail.detailQuantity}</td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>

            </section>
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

<script src="/profi_1.0.2/src/assets/js/vendor.js"></script>
<script src="/profi_1.0.2/src/assets/js/polyfills.js"></script>
<script src="/profi_1.0.2/src/assets/js/app.js"></script>

<!-- Bootstrap 4.6.2 & Vue 3 & jquery 3.4.1-->

<!-- Bootstrap js -->
<script
        src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
        crossorigin="anonymous"></script>
<!-- Vue -->
<script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
<script>
    const {createApp} = Vue;

    createApp({
        data() {
            return {
                message: "Hello Vue!",
            };
        },
    }).mount("#app");


</script>
</body>

</html>