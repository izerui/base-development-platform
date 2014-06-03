<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page import="xxx.yyy.sys.security.filter.CaptchaAuthenticationFilter" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width" />
<meta name="copyright" content="New Creatsoft" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-Cache">
</head>
<body onload='document.form.username.focus();'>
	<div id="wrap">
		<div id="loginLogo">
			<em>
				<a href="#" class="white" hidefocus="true" id="sethomepage1" onclick="addBookmark('登录系统')">加入收藏</a>
				<a href="#" hidefocus="true" onclick="addHomePage();" class="white" >设为首页</a>
			</em>
			<span></span>
    	</div>
		<form name='form' action='${pageContext.request.contextPath}/login' method='POST' onsubmit="checkForm();">
		<div id="loginLeft"></div>
		<div id="loginRight">
			<div class="loginWrap">
			<h2 class="loginWrapTop"></h2>
			<p>
				<label>用户名</label><input type="text" class="userTxt" name='username'
					id="username" value="admin" />
			</p>
			<p>
				<label>密 码</label><input type="password" class="userTxt"
					name='password' id="password" value="admin" />
			</p>
			<div class="remeber"><label></label><input type="checkbox" id="rememberMe" >记住我的信息</div>
			<%
                Boolean showCapt = (Boolean) session.getAttribute(CaptchaAuthenticationFilter.SESSION_SHOWCAPTCHA_KEY);
				if(showCapt!=null&&showCapt){
			%>
			<p>
				<label>验证码</label>
				<input type="text" class="userTxt" style="width:100px;"  title="请输入验证码" name="captcha" id="captcha" >
				<img id="captchaImg" src="getCaptcha" />
			</p>
			<%} %>
			<script type="text/javascript">
				$('#captchaImg').click(function () { 
				    $(this).hide()
				      .attr('src', '${pageContext.request.contextPath}/getCaptcha?date = ' + Math.floor(Math.random()*100) )
				      .fadeIn(); 
			  })
			</script>
			<input type="submit" class="userBtn" value=""/> <input type="hidden" name="vCode" />
			</div>
			<h2 class="loginWrapBtm"></h2>
        	<div id="loginTip">请输入用户名和密码，如果您还没有账号，请联系管理员！</div>
			</div>
		</form>
	</div>
</body>
</html>



