<%--

    Copyright (C) 2014 serv (liuyuhua69@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
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
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js" type="text/javascript"></script>
</head>
<body onload='document.form.username.focus();'>
	<div id="wrap">
		<form name='form' action='${pageContext.request.contextPath}/login' method='POST' >
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
			<%
                Boolean showCapt = (Boolean) session.getAttribute(CaptchaAuthenticationFilter.SESSION_SHOWCAPTCHA_KEY);
				if(showCapt!=null&&showCapt){
			%>
			<div class="remeber"><label></label><input type="checkbox" id="rememberMe" name="rememberMe" >记住我的信息</div>
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
			<input type="submit" class="userBtn" value="提交"/>
			</div>
			<h2 class="loginWrapBtm"></h2>
        	<div id="loginTip">请输入用户名和密码，如果您还没有账号，请联系管理员！</div>
			</div>
		</form>
	</div>
</body>
</html>



