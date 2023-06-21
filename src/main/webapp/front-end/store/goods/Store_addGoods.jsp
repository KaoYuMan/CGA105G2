<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.goods.model.Goods.pojo.*" %>
<%@page import="com.goods.model.service.*" %>

<%
    Goods goods = (Goods) request.getAttribute("goods");
    Integer storeId = (Integer) request.getSession().getAttribute("storeId");
    pageContext.setAttribute("storeId", storeId);
%>

<html>
<title>🔻商城管理</title>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>


</head>

<body>
<!-- header start -->
<%@ include file="/front-end/store/01h/headerin.jsp" %>
<!-- header end -->


<!-- main -->
<div class="container-fluid">
    <div class="row">
        <!-- nav start -->
        <%@ include file="/front-end/store/01h/nav/navin04.jsp" %>
        <!-- nav end -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <div
                    class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">新增管理</h1>
            </div>
            <%-- 錯誤表列 --%>
            <c:if test="${not empty errorMsgs}">
            <font style="color: red">請修正以下錯誤:</font>
            <ul>
                <c:forEach var="message" items="${errorMsgs}">
                    <li style="color: red">${message}</li>
                </c:forEach>
            </ul>
            </c:if>

            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/Member/goods/goods.do" name="form1"
                  enctype="multipart/form-data">
                <jsp:useBean id="goodsSvc" scope="page"
                             class="com.goods.model.service.GoodsService"/>
                <div class="form-row align-items-center">

                    <input type="hidden" name="storeId" value="${storeId}">
                    <%--<%=goods.getStoreId()%> --%>


                    <!-- 商品圖片 -->
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroupFileAddon01">商品圖片</span>
                        </div>
                        <div class="custom-file">
                            <input type="file" name="goodsImg" class="custom-file-input"
                                   id="inputGroupFile01" aria-describedby="inputGroupFileAddon01">
                            <label class="custom-file-label" for="inputGroupFile01">選擇圖片</label>
                        </div>
                    </div>

                    <!-- 商品名稱 -->
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroup-sizing-default">商品名稱</span>
                        </div>
                        <input type="text" name="goodsName" size="45"
                               value="<%=(goods == null) ? "請輸入商品名稱" : goods.getGoodsName()%>"
                               class="form-control" aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default">
                    </div>

                    <!-- 商品價格 -->
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text">商品價格</span>
                        </div>
                        <input type="text" name="goodsPrice" size="15"
                               value="<%=(goods == null) ? "000" : goods.getGoodsPrice()%>"
                               class="form-control"
                               aria-label="Dollar amount (with dot and two decimal places)">
                        <span class="input-group-text">元</span>
                    </div>

                    <!-- 商品狀態 -->
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text">商品狀態</label>
                        </div>
                        <span class="input-group-text">上架</span>
                        <input type="hidden" name="goodsStatus" value="1">
                    </div>

                    <!-- 商品介紹 -->
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">商品介紹</span>
                        </div>
                        <textarea class="form-control" name="goodsText"
                                  placeholder="<%=(goods == null) ? "請輸入商品介紹" : goods.getGoodsText()%>"
                                  aria-label="With textarea"></textarea>
                    </div>
                    <!-- 送出新增 -->
                    <input type="hidden"
                           name="action" value="insert">
                    <br> <br> <br> <br> <input type="hidden"
                                               name="storeId" value="${good.storeId}">
                    <button type="submit" class="btn btn-secondary btn-lg mr-3">送出新增</button>
            </form>

            <a
                    href="<%=request.getContextPath()%>/front-end/Member/goods/goods.do?action=getStoreId_For_Display_store"
                    class="btn btn-secondary btn-lg">回商品列</a>
    </div>

    <canvas class="my-4 w-100" id="myChart" width="900" height="10"></canvas>

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

<script>
    $(document).ready(function () {
        $(".custom-file-input").change(function () {
            $(this).next(".custom-file-label").html($(this).val().split("\\").pop());
        });
    });
</script>
</body>

</html>