<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.goods.model.Goods.pojo.*" %>
<%@page import="com.goods.model.service.*" %>

<%
    Goods goods = (Goods) request.getAttribute("goods");
    Integer storeID = goods.getStoreId();
%>

<html>

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
<div class="container-fluid">
    <div class="row">
        <!-- nav start -->
        <%@ include file="/front-end/store/01h/nav/navin04.jsp" %>
        <!-- nav end -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <div
                    class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">商品修改</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group mr-2">
                        <button type="button" class="btn btn-sm btn-outline-secondary">Share</button>
                        <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                    </div>
                </div>
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

            <FORM METHOD="post"
                  ACTION="<%=request.getContextPath()%>/front-end/Member/goods/goods.do"
                  name="form2" enctype="multipart/form-data">
                <jsp:useBean id="goodsSvc" scope="page"
                             class="com.goods.model.service.GoodsService"/>
                <div class="form-row align-items-center">
                    <!--商品編號 -->

                    <span hidden="hidden" class="input-group-text"><%=goods.getGoodsId()%></span>

                    <!-- 店家編號  用session帶 -->

                    <input type="hidden" name="storeId" value="<%=goods.getStoreId()%>">
                    <%--                          			<%=goods.getStoreId()%> --%>


                    <!-- 商品圖片 -->
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroupFileAddon01">商品圖片</span>
                        </div>
                        <%-- 							<input type="file" name="goodsImg" value="<%=goods.getGoodsImg()%>" />  --%>
                        <div class="custom-file">
                            <input type="file" name="goodsImg"
                                   value="<%=goods.getGoodsImg()%>" class="custom-file-input"
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
                               value="<%=(goods == null) ? "請輸入商品價格" : goods.getGoodsPrice()%>"
                               class="form-control"
                               aria-label="Dollar amount (with dot and two decimal places)">
                        <span class="input-group-text">元</span>
                    </div>
                    <!-- 商品狀態 -->
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="inputGroupSelect01">商品狀態</label>
                        </div>
                        <select class="custom-select" size="1" name="goodsStatus"
                                id="inputGroupSelect01">
                            <c:choose>
                                <c:when test="${goods.goodsStatus == '0'}">
                                    <option value="0" selected>下架</option>
                                    <option value="1">上架</option>
                                </c:when>
                                <c:when test="${goods.goodsStatus == '1'}">
                                    <option value="0">下架</option>
                                    <option value="1" selected>上架</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="2" selected>審核中</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <!-- 商品介紹 -->
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">商品介紹</span>
                        </div>
                        <textarea class="form-control" name="goodsText"
                                  aria-label="With textarea"><%=(goods == null) ? "請輸入商品介紹" : goods.getGoodsText()%></textarea>
                    </div>


                    <!-- 送出修改 -->
                    <input type="hidden"
                           name="action" value="update"> <input type="hidden"
                                                                name="goodsId" value="<%=goods.getGoodsId()%>">
                    <button type="submit" class="btn btn-secondary btn-lg mr-3">送出修改</button>
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
</body>

</html>