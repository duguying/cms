package net.duguying.cms.controller;

import net.duguying.cms.model.Post;
import net.duguying.web.Utils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * 首页
 * @author rex
 *
 */
public class IndexController extends Controller {
	public void index() {
		int page = 1;
		try{
			page = getParaToInt("page");
		}catch(Exception e){}
		
		if(0 == page){
			page = 1;
		}
		int numPerPage = 10;
		int totalPage = Post.totalPage(numPerPage);
		String nav = "";
		String prev = "<a class='btn btn-default navbar-btn' href='?page="+(page-1)+"'>上一页</a>";
		String next = "<a class='btn btn-default navbar-btn' href='?page="+(page+1)+"'>下一页</a>"; 
		if(page == 1){
			if(page == totalPage){
				nav = "";
			}else{
				nav += next;
			}
		}else{
			if(page != totalPage){
				nav += prev+next;
			}else{
				nav += prev;
			}
		}
		
		Page<Post> pageData = Post.getArticlePage(page,numPerPage);
		setAttr("data", pageData);
		setAttr("nav", nav);
		
		// 登录口
		String loginTag = "";
		String username = getSessionAttr("username");
		if(username == null){
			loginTag = Utils.loginTagA;
		}else{
			loginTag = Utils.loginTagB + username + Utils.loginTagC + Utils.loginTagD;
		}
		setAttr("logintag", loginTag);
		
		render("index.html");
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
