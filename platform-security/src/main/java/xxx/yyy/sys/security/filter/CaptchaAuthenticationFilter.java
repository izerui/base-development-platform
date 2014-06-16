/**
 * Copyright (C) 2014 serv (liuyuhua69@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xxx.yyy.sys.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xxx.yyy.sys.base.context.SystemContextHolder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 验证码登录认证Filter
 * 
 * @author izerui.com
 * 
 */
public class CaptchaAuthenticationFilter extends FormAuthenticationFilter {
//	@Autowired
//	CustomSessionHandler customSessionHandler;

	// 在session中的存储验证码的key名称
    public final static String SESSION_CAPTCHA_KEY = "sessionCaptcha";
	// 在session中存储的登录次数的key名称
    public final static String SESSION_LOGINNUM_KEY = "sessionLoginNum";
	// 保存是否显示验证码的 sessionAttribute key名称
	public final static String SESSION_SHOWCAPTCHA_KEY = "sessionShowCaptcha";
	// 允许登录次数，当登录次数大于该数值时，会在页面中显示验证码
	private Integer allowLoginNum;

    //captcha input name ，用来获取用户输入的验证码
    private String captchaParamName  = "captchaParam";

	/**
	 * 重写父类方法，在shiro执行登录时先对比验证码，正确后在登录，否则直接登录失败
	 */
	@Override
	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception {
		
		Session session = getSubject(request, response).getSession();
		//获取登录次数
		Integer number = (Integer) session.getAttribute(SESSION_LOGINNUM_KEY);
		
		//首次登录，将该数量记录在session中
		if (number == null) {
			number = new Integer(1);
			session.setAttribute(SESSION_LOGINNUM_KEY, number);
		}
		Boolean sessionShowCaptcha = (Boolean) session.getAttribute(SESSION_SHOWCAPTCHA_KEY);
        if(sessionShowCaptcha==null){
            sessionShowCaptcha = false;
        }
		//如果登录次数大于allowLoginNum，需要判断验证码是否一致
		if (number > allowLoginNum||sessionShowCaptcha) {
			//获取当前验证码
			String currentCaptcha = (String) session.getAttribute(SESSION_CAPTCHA_KEY);
			//获取用户输入的验证码
			String submitCaptcha = WebUtils.getCleanParam(request, "captcha");
			//如果验证码不匹配，登录失败
			if (StringUtils.isEmpty(submitCaptcha) || !StringUtils.equals(currentCaptcha, submitCaptcha.toLowerCase())) {
				return onLoginFailure(this.createToken(request, response), new AccountException("验证码不正确"), request, response);
			}
		
		}
		return super.executeLogin(request, response);
	}


    public void setCaptchaParamName(String captchaParamName) {
        this.captchaParamName = captchaParamName;
    }

	public void setAllowLoginNum(Integer allowLoginNum) {
		this.allowLoginNum = allowLoginNum;
	}


	/**
	 * 重写父类方法，当登录失败将异常信息设置到request的attribute中
	 */
	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		if (ae instanceof IncorrectCredentialsException) {
			request.setAttribute(getFailureKeyAttribute(), "用户名密码不正确");
		} else {
			request.setAttribute(getFailureKeyAttribute(), ae.getMessage());
		}
	}

	/**
	 * 重写父类方法，当登录失败次数大于allowLoginNum（允许登录次）时，将显示验证码
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		Session session = getSubject(request, response).getSession(false);

		Integer number = (Integer) session.getAttribute(SESSION_LOGINNUM_KEY);

		// 如果失败登录次数大于allowLoginNum时，展示验证码
		if (number > allowLoginNum - 1) {
			session.setAttribute(SESSION_SHOWCAPTCHA_KEY, true);
		}

		session.setAttribute(SESSION_LOGINNUM_KEY, ++number);

		return super.onLoginFailure(token, e, request, response);
	}

	/**
	 * 重写父类方法，当登录成功后，重置下一次登录的状态
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		Session session = subject.getSession(false);

        //for druid
        session.setAttribute("userInfo",SystemContextHolder.getSessionContext().getUser().getName());

		session.removeAttribute(SESSION_LOGINNUM_KEY);
		session.removeAttribute(SESSION_SHOWCAPTCHA_KEY);

        //解决 超时 重新登录后，ReqeustContextHolder 中request为空
        if(request instanceof ShiroHttpServletRequest){
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes((ShiroHttpServletRequest) request));
        }


//		customSessionHandler.setCustomSessionAttributes(request);

		return super.onLoginSuccess(token, subject, request, response);
	}

	/**
	 * 重写父类方法，创建一个自定义的{@link}
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

		String username = getUsername(request);
		String password = getPassword(request);
		String host = getHost(request);

		boolean rememberMe = false;
		String rememberMeValue = request.getParameter(getRememberMeParam());
		// 如果提交的rememberMe参数存在值,将rememberMe设置成true
		if (StringUtils.isNotEmpty(rememberMeValue)) {
			rememberMe = true;
		}

		return new UsernamePasswordToken(username, password, rememberMe, host);
	}


}