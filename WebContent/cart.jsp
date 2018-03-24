<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>黑马商城购物车</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
	</head>

	<body>
		<!-- 引入header.jsp -->
		<jsp:include page="/header.jsp"></jsp:include>
		<c:if test="${empty cart.map }">
		  <div style="text-align: center">
		      <img alt="您还没有添加任何商品呢~o(╥﹏╥)o"  title="您还没有添加任何商品呢~o(╥﹏╥)o" src="img/wrong.png">
		  </div>
		</c:if>
        <c:if test="${not empty cart.map }">
		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">购物车详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							<script>
						        function updateNum(num, pid) {
						        	location.href = "${pageContext.request.contextPath}/cart?action=updateNum&pid=" + pid + "&number="+num;
						        }
							</script>
							<c:forEach items="${cart.map}" var = "cartItem">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${cartItem.value.product.pimage }" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank"> ${cartItem.value.product.pname }</a>
								</td>
								<td width="20%">
									￥${cartItem.value.product.shop_price }
								</td>
								<td width="10%">
									<input type="text" name="quantity" onblur="updateNum(this.value,'${cartItem.key}')" value="${cartItem.value.number}" maxlength="4" size="10">
								</td>
								<td width="15%">
									<span class="subtotal">￥${cartItem.value.subTotal}</span>
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/cart?action=deleteProduct&pid=${cartItem.key}" class="delete">删除</a>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<div style="text-align:right;">
					<em style="color:#ff6600;">
				登录后确认是否享有优惠&nbsp;&nbsp;
			</em> 赠送积分: <em style="color:#ff6600;">596</em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥${cart.total }元</strong>
				</div>
				<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
				
				<!-- 清空购物车的方法 -->
				<script>
			        function clearCart() {
			        	if (confirm("确定要清空购物车吗?")) {
			        		location.href = "${pageContext.request.contextPath}/cart?action=clearCart";
			        	}
			        }
				</script>
					<a href="javascript:clearCart()" id="clear" class="btn btn-danger">清空购物车</a>
					
					<!-- 这里不用传数据过去，因为我们的购物车已经在session中了。 -->
					<a href="${pageContext.request.contextPath }/order?action=toOrderInfoJsp">
						<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
					</a>
				</div>
			</div>

		</div>
        </c:if>
		<!-- 引入footer.jsp -->
		<jsp:include page="/footer.jsp"></jsp:include>

	</body>

</html>