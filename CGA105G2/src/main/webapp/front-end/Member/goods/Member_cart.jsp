<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.goods.model.Goods.pojo.*" %>
<%@ page import="com.goods.model.service.*" %>
<%@ page import="com.goods.model.Cart.pojo.Cart" %>
<%@ page import="com.store.model.service.StoreService" %>
<%@ page import="com.goods.model.Cart.pojo.CartItem" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--購物車--%>


<jsp:useBean id="goodsSvc" scope="page" class="com.goods.model.service.GoodsService"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>🛒購物車</title>
</head>

<body>
<!-- header start -->
<%@ include file="/front-end/Member/01h/headerin.jsp" %>
<!-- header end -->
<!-- main -->
<div class="container-fluid container">
    <div class="row">
        <main role="main" class="col-md-9 m-sm-auto col-lg-10 px-md-4 my-5 container">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom text-center">
                <h1 class="h2 text-center mx-auto mt-5">🛒購物車</h1>
            </div>
            <section class="section container pt-0" id="contacts">
                <!-- 商品列表開始 -->
                <form class="pt80 pb80 col-md-9 m-sm-auto col-lg-10 px-md-4 my-5 container" method="post"
                      action="<%=request.getContextPath()%>/order/order.do" name="form1">
                    <input type="hidden" name="action" value="checkout">
                    <div class="container px-4 px-lg-5 mt-5 ">
                        <div class="row">
                            <div class="col-sm-12 mb-5">
                                <div class="table-responsive-sm" id="listTable">
                                    <table class="table table-hover">
                                        <thead class="all-text-white bg-grad">
                                        <tr>
                                            <th scope="col" class="text-center">商品名稱</th>
                                            <th scope="col">商品照片</th>
                                            <th scope="col">單價</th>
                                            <th scope="col">數量</th>
                                            <th scope="col">小計</th>
                                            <th scope="col"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                        <tfoot>
                                        </tfoot>
                                    </table>
                                </div>
                                <hr>
                                <div class="d-flex justify-content-between col-12">
                                    <button type="submit" class="btn btn-secondary btn-lg  mt-auto fs-4 checkout col-12">前往結賬</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </section>
        </main>
        <!-- 商品列表結束 -->
    </div>
</div>

<!-- main -->

<!-- footer start -->
<%@ include file="/front-end/Member/01h/footerin.jsp" %>
<!-- footer end -->
<script>
    $("a:contains(🛒)").closest("a").addClass("active disabled topage");
</script>
<script>
    // ----------------------------------------------------
    (async function getCart() {
        let url = "<%=request.getContextPath()%>/cart/Write?memId=<%= request.getSession().getAttribute("memId")%>";//
        let response = await fetch(url, {method: 'post'});
        let carts = await response.json();
        listCarts(carts);
    })();

    //動態生成表格
    function listCarts(carts) {
        //移除原本存在的表格
        $("#listTable").empty();

        //生成表格
        let table = document.createElement("table");

        //生成表頭
        table.innerHTML += "<thead><tr><th>商店名稱</th><th>商品名稱</th>"
            + "<th>商品價格</th><th>商品數量</th><th>總金額</th><th>刪除</th>"
            + "</tr></thead>";

        //生成表格內容
        let tbody = document.createElement("tbody");

//     console.log(carts);
        //循環依加入商品數量生成表格數量
        for (let storeId in carts.storeMap) {
            for (let goodsId in carts.storeMap[storeId]) {
                console.log(carts.storeMap[storeId][goodsId].goodsName);
                let tr = document.createElement("tr");
                let storeId2 = carts.storeMap[storeId][goodsId].storeId;
                let goodsId2 = carts.storeMap[storeId][goodsId].goodsId;
                let storeName = carts.storeMap[storeId][goodsId].storeName;
                let goodsName = carts.storeMap[storeId][goodsId].goodsName;
                let goodsPrice = carts.storeMap[storeId][goodsId].goodsPrice;
                let detailQuantity = carts.storeMap[storeId][goodsId].detailQuantity;
                let goodsTotalPrice = carts.storeMap[storeId][goodsId].goodsTotalPrice;

                tr.insertAdjacentHTML("beforeend", '<td storeId= ' + storeId2 + ' goodsId=' + goodsId2 + '>' + storeName + '</td>');
                tr.insertAdjacentHTML("beforeend", '<td storeId= ' + storeId2 + ' goodsId=' + goodsId2 + '>' + goodsName + '</td>');
                tr.insertAdjacentHTML("beforeend", '<td storeId= ' + storeId2 + ' goodsId=' + goodsId2 + '>' + "$" + goodsPrice + '</td>');
// //             --------------------------------------------------------------
// //             --------------------------------------------------------------
                tr.insertAdjacentHTML("beforeend", '<td storeId= ' + storeId2 + ' goodsId=' + goodsId2 + '>' + detailQuantity + '</td>');
                tr.insertAdjacentHTML("beforeend", '<td storeId= ' + storeId2 + ' goodsId=' + goodsId2 + '>' + "$" + goodsTotalPrice + '</td>');
                tr.insertAdjacentHTML("beforeend", '<input type="hidden" name=memId value=' + '<%=request.getSession().getAttribute("memId")%>' + '>');
                tr.insertAdjacentHTML("beforeend", '<input type="hidden" name=storeId value=' + storeId2 + '>');
                tr.insertAdjacentHTML("beforeend", '<input type="hidden" name=goodsId value=' + goodsId + '>');
                tr.insertAdjacentHTML("beforeend", '<input type="hidden" name=goodsPrice value=' + goodsPrice + '>');
                tr.insertAdjacentHTML("beforeend", '<input type="hidden" name=detailQuantity value=' + detailQuantity + '>');
                tr.insertAdjacentHTML("beforeend", '<input type="hidden" name=goodsName value=' + goodsName + '>');
                tr.insertAdjacentHTML("beforeend", '<td><input type="button" value="x" storeId=' + storeId + ' goodsId=' + goodsId + ' onclick="removeCartList(this)"/></td>');
                tbody.append(tr);
            }
            table.append(tbody);
            table.setAttribute("class", "tableclass table table-hover");
            table.setAttribute("id", "tableRows");
            $("#listTable").append(table);
        }

    }

    async function removeCartList(btn) {
        let tr = btn.parentNode.parentNode;
        let storeId = btn.getAttribute("storeId");
        let goodsId = btn.getAttribute("goodsId");
        console.log(goodsId);
        let url = "<%=request.getContextPath()%>/cart/delete?goodsId=" + goodsId + "&storeId=" + storeId;
        console.log(url);
        let response = await fetch(url, {method: 'post'});
        tr.remove();
    }

    async function removeCheckList(removeCheck) {
        let tr = removeCheck.parentNode.parentNode;
        let info = tr.children;
        let children = tr.children;
        let price = parseInt(children[4].innerHTML);
        all -= price;
        changeTotal();					//改變合計金額
        let storeId = btn.getAttribute("storeId");
        let goodsId = btn.getAttribute("goodsId");
        let url = "<%=request.getContextPath()%>/cart/delete?goodsId=" + goodsId + "&storeId=" + storeId;
        let response = await fetch(url, {method: 'post'});
        tr.remove();

    }

    async function reduce(btn) {			//減少商品數量
        let amount = btn.nextElementSibling.value;
        if (amount == 0) {
            return;				//若商品等於0則退出
        }
        amount--;
        btn.nextElementSibling.value = amount;		//更新商品數量

        let value = parseInt(btn.parentNode.previousElementSibling.innerHTML);	//獲取商品單價
        btn.parentNode.nextElementSibling.innerHTML = value * amount;		//更新商品總價
        all -= value;		//更新總價。
        changeTotal();		//刷新總價

        let storeId = btn.getAttribute("storeId");
        let goodsId = btn.getAttribute("goodsId");
        let path = window.location.pathname;
        let webCtx = path.substring(0, path.indexOf('/', 1));
        let url = webCtx + `/cart/reduce?storeId=${storeId}&goodsId=${goodsId}`;
        let response = await fetch(url, {method: 'post'});

    }

    async function increase(btn) {		//增加商品數量
        let amount = btn.previousElementSibling.value;
        amount++;
        btn.previousElementSibling.value = amount;		//更新商品數量

        let value = parseInt(btn.parentNode.previousElementSibling.innerHTML);
        btn.parentNode.nextElementSibling.innerHTML = value * amount;

        all += value;		//更新總價
        changeTotal();		//刷新總價

        let storeId = btn.getAttribute("storeId");
        let goodsId = btn.getAttribute("goodsId");
        let path = window.location.pathname;
        let webCtx = path.substring(0, path.indexOf('/', 1));
        let url = webCtx + `/cart/add?storeId=${storeId}&goodsId=${goodsId}`;
        let response = await fetch(url, {method: 'post'});
    }

    function changeTotal() {		//更新total函數，在每次改變結帳清單時使用。
        let total = document.getElementById("total");
        total.innerHTML = all;
    }

    window.onload = function () {
        let checkout = document.getElementById("checkOut");
        checkout.addEventListener("click", swalCheck)
// +++++++++++++++++++++++++++++++++++++++++
//     +++++++++++++++++++++++++++++++++++++++++++++++++
        function swalCheck() {
            // swal({
            //     title: "即將進行扣款",
            //     text:`即將扣款${all}點數`,
            //     icon: "warning",
            // })
            swal({
                title: "請確認",
                text: `即將扣款${all}點數`,
                icon: "warning",
                buttons: true,
                dangerMode: true,
            })
                .then((willDelete) => {
                    if (willDelete) {
                        swal("您已成功扣款", {
                            icon: "success",
                            buttons: false,
                        });
                        setTimeout(() => {
                            cleanRedis();
                        }, 1000)

                    } else {
                        swal("您已取消扣款");
                    }
                });
        }
    }


</script>


</body>

</html>