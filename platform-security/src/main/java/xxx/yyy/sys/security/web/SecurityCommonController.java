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
package xxx.yyy.sys.security.web;

import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import xxx.yyy.sys.rbac.service.AccountService;
import xxx.yyy.sys.security.filter.CaptchaAuthenticationFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/** 
 * @author  serv
 */
@Controller
@SessionAttributes(CaptchaAuthenticationFilter.SESSION_SHOWCAPTCHA_KEY)
public class SecurityCommonController {
	
	@Autowired
	private Producer captchaProducer;
	
	/**
	 * 生成验证码C
	 * 
	 * @throws java.io.IOException
	 */
	@RequestMapping("/getCaptcha")
	public ResponseEntity<byte[]> getCaptcha(ModelMap model) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		String captcha = captchaProducer.createText();//CaptchaUtils.getCaptcha(80, 32, 5, outputStream).toLowerCase();
		model.addAttribute(CaptchaAuthenticationFilter.SESSION_CAPTCHA_KEY,captcha);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedImage bi = captchaProducer.createImage(captcha);
		ImageIO.write(bi, "jpeg", outputStream);
		return new ResponseEntity<byte[]>(outputStream.toByteArray(),headers, HttpStatus.OK);
	}


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
