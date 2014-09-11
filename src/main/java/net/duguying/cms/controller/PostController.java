package net.duguying.cms.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import net.duguying.cms.model.Post;
import net.duguying.web.Utils;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * 文章
 * @author rex
 *
 */
public class PostController extends Controller{
	public void index() {
		renderText("This");
	}
	
	/**
	 * 添加文章
	 */
	@Before(POST.class)
	public void add(){
		String title = getPara("title");
		String content = getPara("content");
		
		String username = getSessionAttr("username");
		if(username == null){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "login first please");
			renderJson(result);
			return;
		}
		
		Post post = new Post();
		boolean rst = post.set("title", title).set("content", content).set("author", username).save();
		
		if(rst){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", true);
			result.put("msg", "post added");
			result.put("redirection", "/cms/admin");
			renderJson(result);
		}else{
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "added failed");
			renderJson(result);
		}
	}
	
	/**
	 * 删除文章
	 */
	public void delete(){
		String idStr = getPara("id");
		if(idStr == null){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "invalid request, id is needed");
			renderJson(result);
			return;
		}
		
		int id = 0;
		try{
			id = Integer.parseInt(idStr);
		}catch(Exception e){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "invalid request, id is invalid");
			renderJson(result);
			return;
		}
		
		// login first
		String username = getSessionAttr("username");
		if(username == null){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "login first please");
			renderJson(result);
			return;
		}
		
//		boolean rst = Post.dao.deleteById(id);
		boolean rst = Post.dao.deleteByIdAndAuthor(id);
		
		if(rst){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", true);
			result.put("msg", "delete post success");
			renderJson(result);
		}else{
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "delete post failed");
			renderJson(result);
		}
	}
	
	/**
	 * 编辑文章页面
	 */
	public void edit(){
		String idStr = getPara("id");
		if(idStr == null){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "invalid request, id is needed");
			renderJson(result);
			return;
		}
		
		int id = 1;
		try{
			id = Integer.parseInt(idStr);
		}catch(Exception e){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "invalid request, id is invalid");
			renderJson(result);
			return;
		}
		
		Post post = Post.dao.findById(id);
		setAttr("post", post);
		
		// 登录口
		String loginTag = "";
		String username = getSessionAttr("username");
		if(username == null){
			loginTag = Utils.loginTagA;
		}else{
			loginTag = Utils.loginTagB + username + Utils.loginTagC + Utils.loginTagD;
		}
		setAttr("logintag", loginTag);
		
		render("edit.html");
	}
	
	/**
	 * 更新文章
	 */
	@Before(POST.class)
	public void update(){
		String title = getPara("title");
		String content = getPara("content");
		
		String idStr = getPara("id");
		if(idStr == null){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "invalid request, id is needed");
			renderJson(result);
			return;
		}
		
		int id = 0;
		try{
			id = Integer.parseInt(idStr);
		}catch(Exception e){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "invalid request, id is invalid");
			renderJson(result);
			return;
		}
		
		String username = getSessionAttr("username");
		if(username == null){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "login first please");
			renderJson(result);
			return;
		}
		
//		boolean rst = Db.update("post", Db.findById("post", id).set("title", title).set("content", content));
		boolean rst = Post.updateArticle(title, content, username, id);
		
		if(rst){
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", true);
			result.put("msg", "post added");
			renderJson(result);
		}else{
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("result", false);
			result.put("msg", "added failed");
			renderJson(result);
		}
	}
	
	/**
	 * 获取文章
	 */
	@Before(GET.class)
	public void article(){
		String idStr = getPara("id");
		int id = 0;
		if(idStr != null){
			try{
				id = Integer.parseInt(idStr);
			}catch(Exception e){
				id = 0;
			}
		}
		
		Post post = Post.dao.findById(id);
		
		if(null == post){
			renderText("文章不存在");
		}
		
		// 登录口
		String loginTag = "";
		String username = getSessionAttr("username");
		if(username == null){
			loginTag = Utils.loginTagA;
		}else{
			loginTag = Utils.loginTagB + username + Utils.loginTagC + Utils.loginTagD;
		}
		setAttr("logintag", loginTag);
		
		
		setAttr("article", post);
		render("article.html");
	}
	
	public void search(){
		String keyword = getPara("keyword");
		setAttr("keyword", keyword);
		Page<Post> pageData = Post.searchArticle(keyword,1,10);
		
		setAttr("data", pageData);
		// 登录口
		String loginTag = "";
		String username = getSessionAttr("username");
		if(username == null){
			loginTag = Utils.loginTagA;
		}else{
			loginTag = Utils.loginTagB + username + Utils.loginTagC + Utils.loginTagD;
		}
		setAttr("logintag", loginTag);
				
		render("search.html");
	}
	
}
