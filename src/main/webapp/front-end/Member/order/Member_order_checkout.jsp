<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.order.model.Order.pojo.*"%>
<%@ page import=" com.member.model.service.*"%>
<%@ page import=" com.goods.model.Cart.pojo.*"%>
<%@ page import="com.goods.model.Goods.pojo.*"%>
<%@ page import="com.member.model.Member.pojo.*"%>
<%@ page import="com.goods.model.service.*"%>
<%@ page import="com.order.model.service.*"%>

<%
Integer storeId = (Integer) request.getAttribute("storeId");
Integer memId = (Integer) request.getAttribute("memId");
GoodsService goodsSvc = new GoodsService();
List<Goods> checkOutlist = goodsSvc.getAll(storeId);
pageContext.setAttribute("checkOutlist", checkOutlist);
MemberService memberSvc = new MemberService();
Member member = memberSvc.getById(memId);
pageContext.setAttribute("member", member);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>🛒購物車結帳</title>
</head>

<body>
	<!-- header start -->
	<%@ include file="/front-end/Member/01h/headerin.jsp"%>
	<!-- header end -->
	<!-- main -->
	<div class="container-fluid container">
		<div class="row">
			<main role="main"
				class="col-md-9 m-sm-auto col-lg-10 px-md-4 my-5 container">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom text-center">
					<h1 class="h2 text-center mx-auto mt-5">🛒購物車結帳</h1>
				</div>
				<section class="section container pt-0" id="contacts">
					<form
						class="pt80 pb80 col-md-9 m-sm-auto col-lg-10 px-md-4 my-5 container"
						method="post"
						action="<%=request.getContextPath()%>/order/order.do" name="form1">
<!-- 					<input type="hidden" name="action" value="orderSuccess"> -->
						<input type="hidden"name="action" value="ecpay" />
						<section class="section container pt-0">
							<div class="login-box Booking-box">
								<div class="row">
									<div class="col-sm-12 mb-3">
										<h3 class="text-center mb-4">結帳清單</h3>
										<div class="table-responsive-sm">
											<table
												class="table table-lg table-noborder table-striped text-center">
												<thead class="all-text-white bg-grad">
													<tr>
														<th scope="col">商品名稱</th>
														<th scope="col">單價</th>
														<th scope="col">數量</th>
														<th scope="col">小計</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="goodsdetail" items="${goodsList}"
														varStatus="loop">
														<c:if test="${detailQuantityInt[loop.index] != null}">
															<tr>
																<td scope="row">${goodsdetail.goodsName}</td>
																<td>NT$ <span class="onePrice">
																		${goodsdetail.goodsPrice}</span>
																</td>
																<td>${detailQuantityInt[loop.index]}</td>
																<td>NT$ ${goodsdetail.goodsPrice * detailQuantityInt[loop.index]}<span
																	class="itemPrice"></span></td>
															</tr>
															<!--店家ID -->
															<input type="hidden" name="goodsId"
																value="${goodsdetail.goodsId}">
															<input type="hidden" name="goodsName"
																value="${goodsdetail.goodsName}">
															<input type="hidden" name="goodsPrice"
																value="${goodsdetail.goodsPrice}">
															<input type="hidden" name="detailQuantity"
																value="${detailQuantityInt[loop.index]}">
														</c:if>
													</c:forEach>
												</tbody>
											</table>
											<hr>
										</div>
										<h2 class="mt-60">訂購者資訊</h2>
										<p class="text-danger mb-20">＊為必填資訊</p>
										<div class="d-flex justify-content-between mb-20">
											<div class="col-lg-6 col-md-12 col-sm-12 name">
												<label><span class="text-danger">*</span>姓名</label> <input
													type="text" name="mem_name" class="form-control"
													value="${member.memName}">
											</div>
											<div class="col-lg-6 col-md-12 col-sm-12 email">
												<label><span class="text-danger">*</span>Email</label> <input
													type="text" name="mem_email" class="form-control"
													value="${member.memMail}">
											</div>
										</div>
										<div class="d-flex justify-content-between mb-20">
											<div class="col-lg-6 col-md-12 col-sm-12 phone">
												<label><span class="text-danger">*</span>連絡電話</label> <input
													type="text" name="mem_phone" class="form-control"
													value="${member.memPhone}">
											</div>
											<div class="col-lg-6 col-md-12 col-sm-12 address">
												<label><span class="mem_address">*</span>地址</label> <input
													type="text" name="tkt_order_mem_email" class="form-control"
													value="${member.memAddress}">
											</div>
										</div>
									</div>
									<hr>
									<div class="d-flex justify-content-between mb-20">
										<div class="col-lg-6 col-md-12 col-sm-12 creditCard">
											<label>選擇付款方式</label> <select
												class="custom-select select-big mb-3">
												<option selected>信用卡付款</option>
											</select>
										</div>
										<div class="col-lg-6 col-md-12 col-sm-12 deliver">
											<label>選擇運送方式</label> <select
												class="custom-select select-big mb-3" name="Fre"
												id="FrePrice">
												<option value="120" selected>宅配</option>
											</select>
										</div>
									</div>
								</div>
								<hr>
								<div class="login-top cardInfo col-lg-12 col-md-12 col-sm-12"></div>
								<div class="d-flex justify-content-between mb-20">
									<div class="col-lg-6 col-md-12 col-sm-12 email"></div>
									<div class="d-flex flex-column align-items-end mb-3">
										<c:set value="0" var="sum" />
										<c:forEach var="goodsList" items="${goodsList}"
											varStatus="loop">
											<c:set
												value="${sum + goodsList.goodsPrice * detailQuantityInt[loop.index]}"
												var="sum" />
										</c:forEach>
										<p>
											商品總金額： <span style="color: red";>${sum}</span> <input
												class="orderPrice" type="hidden" id="orderPrice"
												name="orderPrice" style="color: red;" value="${sum}">
											元
										</p>
										<p>
											運費金額： <span class="fre" style="color: red;">80</span> 元
										</p>
										<p>
											總付款金額： <span class="orderFPrice" style="color: red;">${sum + 80}</span>
											元
										</p>
									</div>
								</div>
								<div class="col-md-12">
									<input type="hidden" name="storeId" value="${storeId}">
									<!--運費-->
									<input type="hidden" name="orderFre" id="orderFre" value="80">
									<!--優惠卷-->
									<input type="hidden" name="codeId" id="codeId" value=1>
									<!--支付金額-->
									<input type="hidden" name="orderFprice" id="orderFprice"
										value="${sum + 80}"> 
										<input type="hidden"name="ReturnURL"value="http://tn.sly-ha.com.tw/demo/hoyo/ECPay.php"
										class="form-control" /> 
										<input type="hidden"name="ChoosePayment" value="ALL" /> 
										
									<div class="d-flex justify-content-between">
										<a href="<%=request.getContextPath()%>/front-end/Member/goods/Member_cart.jsp"
											class="btn btn-outline-primary">返回購物車</a>
										<button type="submit" class="btn btn-info ml-3" id="addOrder">確認付款</button>
									</div>
								</div>
							</div>
						</section>
					</form>
					<!-- 結帳內容結束 -->
				</section>
			</main>
		</div>
	</div>

	<!-- main -->

	<!-- footer start -->
	<%@ include file="/front-end/Member/01h/footerin.jsp"%>
	<!-- footer end -->
	<script>
		$("a:contains(🛒)").closest("a").addClass("active disabled topage");
	</script>
	<script>
		$("#checkCodeNum")
				.click(
						function() {
							//===實作(填入程式碼)
							let xhr = new XMLHttpRequest();
							let codeNum = $("#codeNum").val();
							let codeOff = 0;
							//設定好回呼函數
							if (codeNum != null) {
								let url = "/CGA105G2/order/order.do?action=checkDiscount&codeNum="
										+ codeNum;
								xhr.open("post", url, true);
								xhr.onload = function() {
									if (xhr.status == 200) {
										if (xhr.responseText !== null) {
											const codeDiscount = xhr.responseText;
											const codeArray = codeDiscount
													.split(" ");
											let codeId = codeArray[0];
											let codeOff = codeArray[1];
											document.getElementById("codeOff").textContext = codeOff;
											let sumNum = document
													.getElementById("orderPrice").value;
											// 正確
											console
													.log("原價:"
															+ document
																	.getElementById("orderPrice").value);
											let codeOffNum = parseInt(codeOff);
											// 正確
											let total = sumNum - codeOffNum;
											console.log("實際支付金額:" + total);
										}
									} else {
									}// status
								};// onload
								xhr.send(codeOff);
								console.log(xhr.status);
							}
						})
	</script>
</body>
</html>