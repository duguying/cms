package net.duguying.cms.controller;

import java.util.HashMap;
import java.util.List;

import net.duguying.cms.config.CmsConfig;
import net.duguying.cms.model.User;
import net.duguying.web.Utils;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;

public class UserController extends Controller {
	
	/**
	 * 登录
	 */
//	@Before(POST.class)
	public void login() {
		if ("POST".equalsIgnoreCase(this.getRequest().getMethod().toUpperCase())){
			// post
			String username = getPara("username");
			String password = getPara("password");
			
			List<User> resultMap = User.dao.find("select * from user where username='"+ username +"' limit 1");
			
			if(0 == resultMap.size()){
				HashMap<String, Object> result = new HashMap<String, Object>();
				result.put("result", false);
				result.put("msg", "cannot find user, login failed");
				renderJson(result);
				return;
			}
			
			String passwordInTable = resultMap.get(0).get("password");
			String salt = resultMap.get(0).get("salt");
			
			String passwordAfterMd5 = Utils.md5(password + salt);
	
			if(passwordInTable.equals(passwordAfterMd5)){
				
				setSessionAttr("username", username);
				HashMap<String, Object> result = new HashMap<String, Object>();
				result.put("result", true);
				result.put("msg", "login success");
				result.put("redirection", CmsConfig.Root+"admin");
				renderJson(result);
			}else{
				HashMap<String, Object> result = new HashMap<String, Object>();
				result.put("result", false);
				result.put("msg", "password error, login failed");
				renderJson(result);
			}
			
		}else{
			// get
			render("login.html");
		}

	}

	/**
	 * 登出
	 */
	public void logout() {
		removeSessionAttr("username");
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		result.put("result", true);
//		result.put("msg", "logout success");
//		renderJson(result);
		renderHtml("<script>location='/cms/admin';</script>");
	}

	/**
	 * 注册
	 */
	@Before(POST.class)
	public void registor() {
		String username = getPara("username");
		String password = getPara("password");
		
		String salt = "";
		if(username == "" || password == ""){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "post request avalible only");
			renderJson(result);
			return;
		}else{
			salt = Utils.RandString(8);
			password = Utils.md5(password + salt);
		}
		
		User user = new User();
		boolean rst = user.set("username", username).set("password", password)
				.set("salt", salt).set("level", "0").save();
		if(rst){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", true);
			result.put("msg", "add user success");
			renderJson(result);
		}else{
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "add user failed");
			renderJson(result);
		}
	}
	
}
