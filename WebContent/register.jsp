<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="css/bootstrapValidator.min.css" type="text/css" />
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入bv表单验证插件 -->
<script src="js/bootstrapValidator.min.js"></script>

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
<script type="text/javascript">
	function change(img){
		img.src = "${pageContext.request.contextPath}/checkCode?time="+new Date().getTime();
	}
</script>
<!-- 表单简单验证 -->

</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form id="registerForm" 
					class="form-horizontal" 
					style="margin-top: 5px;"
					action="${pageContext.request.contextPath }/userServlet?action=register"
					method="POST">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username"
								placeholder="请输入用户名" name="username">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="inputPassword3"
								placeholder="请输入密码" name="password">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd"
								name="confirmpwd" placeholder="请输入确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="inputEmail3"
								placeholder="Email" name="email">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="inputEmail3"
								placeholder="请输入手机号" name="telephone">
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="usercaption"
								placeholder="请输入姓名" name="name">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio1" value="男">
								男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio2" value="女">
								女
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday">
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-2">
							<input type="text" name="checkCode" class="form-control">
						</div>
						<div class="col-sm-2">
							<img src="${pageContext.request.contextPath}/checkCode" onclick="change(this)" 
							style="cursor:pointer" alt="验证码" title="点我刷新"/>
						</div>
						<div class="col-sm-5">
							<!-- 这里用来显示验证码是否正确的消息 -->
							<span style="color: red">${errorMsg }</span>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<!-- <input type="submit" width="100" value="注册" name="submit"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;"> -->
								<button type="submit"  class="btn btn-primary" style="width: 100px">注册</button>
								<button type="reset"  class="btn btn-danger" style="width: 100px;margin-left: 50px">重置</button>
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>
<script type="text/javascript">
$(function () {
    $("#registerForm").bootstrapValidator({
        live: "enabled",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields: {
            username: {
                message: "用户名验证失败",
                validators: {
                    notEmpty: {
                        message: "用户名不能为空"
                    }
                }
            },
            password: {
                message: "密码验证失败",
                validators: {
                    notEmpty: {
                        message: "密码不能为空"
                    }
                }
            },
            confirmpwd: {
                message: "确认密码验证失败",
                validators: {
                    notEmpty: {
                        message: "确认密码不能为空"
                    }
                }
            },
            telephone: {
                message: "手机号验证失败",
                validators: {
                    notEmpty: {
                        message: "手机号不能为空"
                    }
                }
            }
        }
    });
});
</script>




