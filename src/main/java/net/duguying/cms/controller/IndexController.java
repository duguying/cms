package net.duguying.cms.controller;

import com.jfinal.core.Controller;

/**
 * 首页
 * @author rex
 *
 */
public class IndexController extends Controller {
	public void index() {
		renderText("This is index page, shit.");
	}
	
	public void test(){
		String username = getSessionAttr("username");
		
		if(username == null){
			username = "null";
		}
		
		setAttr("username", username);
		render("test.html");
	}
}
