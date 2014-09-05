package net.duguying.cms.config;

import net.duguying.cms.controller.IndexController;
import net.duguying.cms.controller.PasswordController;
import net.duguying.cms.controller.PostController;
import net.duguying.cms.controller.UserController;
import net.duguying.cms.model.Post;
import net.duguying.cms.model.User;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class CmsConfig extends JFinalConfig {
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setBaseViewPath("/tpl");
		loadPropertyFile("conf.properties");
	}

	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
		me.add("/post", PostController.class, "post");
		me.add("/user", UserController.class, "user");
		me.add("/password", PasswordController.class, "password");
	}

	public void configPlugin(Plugins me) {
		C3p0Plugin  cp  =  new  C3p0Plugin("jdbc:mysql://127.0.0.1/cms", 
				"root", "lijun");
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.addMapping("user", User.class);
		arp.addMapping("post", Post.class);
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}
}